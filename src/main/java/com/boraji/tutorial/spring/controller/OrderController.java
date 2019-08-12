package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.entity.Basket;
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
@SessionAttributes("order")
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
        Optional<Basket> optionalBasket = basketService.getBasket(user);
        if (!optionalBasket.isPresent() || optionalBasket.get().getProducts().isEmpty()) {
            return "redirect:/products/store";
        }
        return "order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrder(@AuthenticationPrincipal User user,
                           @RequestParam String address,
                           @RequestParam String payment,
                           Model model) {
        codeService.addCode(new Code(CodeGenerator.generateCode(), user));
        Optional<Code> optionalCode = codeService.getLastCodeForUser(user);
        if (optionalCode.isPresent()) {
            orderService.addOrder(optionalCode.get(), address, payment,
                    basketService.getBasket(user).get());
            Optional<Order> optionalOrder = orderService.getByCode(optionalCode.get());
            if (optionalOrder.isPresent()) {
                model.addAttribute("orderId", optionalOrder.get().getId());
                return "redirect:/order/confirm";
            }
        }
        return "redirect:/products/store";
    }

    @GetMapping("/confirm")
    public String confirmOrderView(@RequestParam String orderId, Model model) {
        Optional<Order> optionalOrder = orderService.getById(Long.valueOf(orderId));
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            model.addAttribute("orderId", order.getId());
            model.addAttribute("sum", order.getSum());
            model.addAttribute("error", "");
            mailService.sendMessage(order.getCode());
            return "confirmOrder";
        }
        return "redirect:/products/store";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String login(@AuthenticationPrincipal User user,
                        @RequestParam String orderId,
                        @RequestParam String codeValue,
                        Model model) {
        try {
            Optional<Order> optionalOrder = orderService.getById(Long.valueOf(orderId));
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
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
