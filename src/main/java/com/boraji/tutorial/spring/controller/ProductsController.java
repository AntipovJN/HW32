package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.entity.Product;
import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(value = "/products")
@SessionAttributes("user")
public class ProductsController {

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/store")
    public String storePageView(Model model, @ModelAttribute("user") User user) {
        if (Objects.isNull(user.getId())) {
            return "redirect:/login";
        }
        if (user.getRole().equals("admin")) {
            model.addAttribute("isAdmin", true);
        }
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String removeProduct(@RequestParam("productID") String id,
                                @ModelAttribute("user") User user) {
        if (!Objects.isNull(user.getId()) && user.getRole().equals("admin")) {
            productService.removeProduct(Long.valueOf(id));
        }
        return "redirect:/products/store";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String updateProduct(@RequestParam("productID") String id,
                                @ModelAttribute("user") User user,
                                Model model) {
        try {
            if (!Objects.isNull(user.getId()) && user.getRole().equals("admin")) {
                Optional<Product> optionalProduct = productService.getById(Long.valueOf(id));
                if (optionalProduct.isPresent()) {
                    Product editProduct = optionalProduct.get();
                    model.addAttribute("id", editProduct.getId());
                    model.addAttribute("name", editProduct.getName());
                    model.addAttribute("description", editProduct.getDescription());
                    model.addAttribute("price", editProduct.getPrice());
                    return "edit_product";
                }
            }
        } catch (NumberFormatException e) {
            return "redirect:/products/store";

        }
        return "redirect:/products/store";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProduct(@RequestParam("productID") String id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam String price,
                              @ModelAttribute("user") User user) {
        try {
            if (!Objects.isNull(user.getId()) &&
                    user.getRole().equals("admin")) {
                if (productService.getById(Long.valueOf(id)).isPresent()) {
                    productService.updateProduct(Long.valueOf(id), name, description, Double.valueOf(price));
                }
            }
        } catch (NumberFormatException e) {
            return "redirect:/products/store";
        }
        return "redirect:/products/store";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addProductView(@ModelAttribute("user") User user) {
        if (!Objects.isNull(user.getId()) && user.getRole().equals("admin")) {
            return "add_product";
        }
        return "redirect:/products/store";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addProduct(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String price,
                             @ModelAttribute("user") User user,
                             Model model) {
        System.out.println(user.getRole());
        try {
            if (!Objects.isNull(user.getId()) && user.getRole().equals("admin")) {
                productService.add(name, description, Double.valueOf(price));
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "add_product";
        }
        return "redirect:/products/store";
    }

}
