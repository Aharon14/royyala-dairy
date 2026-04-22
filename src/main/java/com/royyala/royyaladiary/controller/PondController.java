package com.royyala.royyaladiary.controller;

import com.royyala.royyaladiary.entity.Pond;
import com.royyala.royyaladiary.service.PondService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ponds")
public class PondController {

    private final PondService pondService;

    public PondController(PondService pondService) {
        this.pondService = pondService;
    }

    @GetMapping
    public String listPonds(Model model) {
        model.addAttribute("ponds", pondService.getMyPonds());
        return "ponds";
    }

    @GetMapping("/add")
    public String showAddPondForm(Model model) {
        model.addAttribute("pond", new Pond());
        return "add-pond";
    }

    @PostMapping("/add")
    public String addPond(
            @ModelAttribute Pond pond,
            RedirectAttributes redirectAttributes) {
        pondService.addPond(pond);
        redirectAttributes.addFlashAttribute("success",
                "Pond added successfully! 🎉");
        return "redirect:/ponds";
    }
}