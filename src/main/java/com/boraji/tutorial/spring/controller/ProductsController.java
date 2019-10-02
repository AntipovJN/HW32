package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.entity.Product;
import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ProductsController {

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/user/store")
    public String storePageView(Model model, @AuthenticationPrincipal User user) {
        if (user.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("isAdmin", true);
        }
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    @RequestMapping(value = "/admin/remove", method = RequestMethod.GET)
    public String removeProduct(@RequestParam("productID") String id) {
        productService.removeProduct(Long.valueOf(id));
        return "redirect:/admin/store";
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
    public String updateProduct(@RequestParam("productID") String id, Model model) {
        try {
            Optional<Product> optionalProduct = productService.getById(Long.valueOf(id));
            if (optionalProduct.isPresent()) {
                Product editProduct = optionalProduct.get();
                model.addAttribute("productID", editProduct.getId());
                model.addAttribute("name", editProduct.getName());
                model.addAttribute("description", editProduct.getDescription());
                model.addAttribute("price", editProduct.getPrice());
                return "edit_product";
            }
        } catch (NumberFormatException e) {
            return "redirect:/admin/store";
        }
        return "redirect:/admin/store";
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public String editProduct(@RequestParam("productID") String id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam String price) {
        try {
            if (productService.getById(Long.valueOf(id)).isPresent()) {
                productService.updateProduct(Long.valueOf(id), name, description, Double.valueOf(price));
            }
        } catch (NumberFormatException e) {
            return "redirect:/admin/store";
        }
        return "redirect:/admin/store";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public String addProductView() {
        return "add_product";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String addProduct(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String price,
                             Model model) {
        try {
            productService.add(name, description, Double.valueOf(price));
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "add_product";
        }
        return "redirect:/admin/store";
    }
}
