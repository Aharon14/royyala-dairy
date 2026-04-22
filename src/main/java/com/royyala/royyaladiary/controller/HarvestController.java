package com.royyala.royyaladiary.controller;

import com.royyala.royyaladiary.entity.Harvest;
import com.royyala.royyaladiary.service.HarvestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/harvest")
public class HarvestController {

    private final HarvestService harvestService;

    public HarvestController(HarvestService harvestService) {
        this.harvestService = harvestService;
    }

    @GetMapping("/log/{cycleId}")
    public String showLogHarvestForm(
            @PathVariable Long cycleId, Model model) {
        model.addAttribute("harvest", new Harvest());
        model.addAttribute("cycleId", cycleId);
        return "log-harvest";
    }

    @PostMapping("/log/{cycleId}")
    public String logHarvest(
            @PathVariable Long cycleId,
            @ModelAttribute Harvest harvest) {
        harvestService.logHarvest(harvest, cycleId);
        return "redirect:/feed/cycle/" + cycleId;
    }
}