package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagePersonalController {

    @GetMapping("/personal")
    public String getPersonalAreaPage(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/personal/orders")
    public String getOrdersPage(Model model) {
        return "orders";
    }

    @GetMapping("/personal/cart")
    public String getCartPage() {
        return "cart";
    }

    @GetMapping("/personal/order/make")
    public String getMakeOrderPage(@RequestParam("total-price") int totaPrice, Model model) {
        model.addAttribute("totalPrice", totaPrice);
        return "makeOrder";
    }

    @GetMapping("/personal/order/{id}/success")
    public String getSuccessOrderPage(@PathVariable("id") long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "successOrder";
    }
}
