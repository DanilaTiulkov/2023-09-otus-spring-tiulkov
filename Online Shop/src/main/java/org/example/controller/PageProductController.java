package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageProductController {

    @GetMapping({"/", "/products"})
    public String getProductsPage(Model model) {
        model.addAttribute("searchStatus", "Off");
        return "products";
    }

    @GetMapping("/products/{id}")
    public String getProductPage(@PathVariable long id, Model model) {
        model.addAttribute("productId", id);
        return "product";
    }

    @GetMapping("/products/new")
    public String getNewBookPage(Model model) {
        model.addAttribute("reference", "/products/new");
        return "editProduct";
    }

    @GetMapping("/products/edit/{id}")
    public String getEditBookPage(@PathVariable long id, Model model) {
        model.addAttribute("reference", "/products/edit");
        model.addAttribute("productId", id);
        return "editProduct";
    }

    @GetMapping("/products/categories/{id}")
    public String getProductPageWithCategory(@PathVariable long id, @RequestParam("product-title") String productTitle,
                                             Model model) {
        model.addAttribute("searchStatus", "On");
        model.addAttribute("categoryId", id);
        model.addAttribute("productTitle", productTitle);
        return "products";
    }
}
