package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.Order;
import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.BasketService;
import com.boraji.tutorial.spring.service.CodeService;
import com.boraji.tutorial.spring.service.MailService;
import com.boraji.tutorial.spring.service.OrderService;
import com.boraji.tutorial.spring.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
@SessionAttributes( "order")
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CodeService codeService;
    private final BasketService basketService;
    private final MailService mailService;

    @Autowired
    public OrderController(MailService mailService, OrderService orderService,
                           CodeService codeService, BasketService basketService) {
        this.orderService = orderService;
        this.codeService = codeService;
        this.basketService = basketService;
        this.mailService = mailService;
    }

    @ModelAttribute
    public Order getOrderId(Order order) {
        return order;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addOrderView(@AuthenticationPrincipal User user) {
        if(basketService.getBasket(user).getProducts().isEmpty()){
            return "redirect:/products/store";
        }
        return "order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrder(@AuthenticationPrincipal User user,
                           @SessionAttribute("order") Order order,
                           @RequestParam String address,
                           @RequestParam String payment,
                           Model model) {
            codeService.addCode(new Code(CodeGenerator.generateCode(), user));
            Optional<Code> optionalCode = codeService.getLastCodeForUser(user);
            if (optionalCode.isPresent()) {
                orderService.addOrder(optionalCode.get(), address, payment,
                        basketService.getBasket(user));
                Optional<Order> optionalOrder = orderService.getByCode(optionalCode.get());
                if (optionalOrder.isPresent()) {
                    order.setId(optionalOrder.get().getId());
                    order.setCode(optionalOrder.get().getCode());
                    order.setAddress(optionalOrder.get().getAddress());
                    order.setPayment(optionalOrder.get().getPayment());
                    order.setBasket(optionalOrder.get().getBasket());
                    model.addAttribute("orderId", order);
                    return "redirect:/order/confirm";
                }
        }
        return "redirect:/products/store";
    }

    @GetMapping("/confirm")
    public String confirmOrderView(@SessionAttribute("order") Order order, Model model) {
        if (!Objects.isNull(order)) {
            model.addAttribute("orderId", order.getId());
            model.addAttribute("sum", order.getSum());
            model.addAttribute("error", "");
            mailService.sendMessage(order.getCode());
            return "confirmOrder";
        }
        return "redirect:/products/store";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String login( @AuthenticationPrincipal User user,
                        @SessionAttribute("order") Order order,
                        Model model, @RequestParam("codeValue") String codeValue) {
        try {
            if (!Objects.isNull(order) ) {
                if (order.getCode().getCodeValue() == Integer.valueOf(codeValue)) {
                    basketService.removeProducts(user);
                    return "redirect:/products/store";
                }
                throw new NumberFormatException();
            }
            return "redirect:/products/store";
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid code");
            return "confirmOrder";
        }
    }

}
