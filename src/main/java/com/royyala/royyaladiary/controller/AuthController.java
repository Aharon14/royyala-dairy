package com.royyala.royyaladiary.controller;

import com.royyala.royyaladiary.entity.Farmer;
import com.royyala.royyaladiary.service.FarmerService;
import com.royyala.royyaladiary.service.HarvestService;
import com.royyala.royyaladiary.service.PondService;
import com.royyala.royyaladiary.service.HarvestCycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final FarmerService farmerService;
    private final PondService pondService;
    private final HarvestCycleService harvestCycleService;
    private final HarvestService harvestService;

    public AuthController(
            FarmerService farmerService,
            PondService pondService,
            HarvestCycleService harvestCycleService,
            HarvestService harvestService) {
        this.farmerService = farmerService;
        this.pondService = pondService;
        this.harvestCycleService = harvestCycleService;
        this.harvestService = harvestService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("farmer", new Farmer());
        return "register";
    }

    @PostMapping("/register")
    public String registerFarmer(
            @ModelAttribute Farmer farmer, Model model) {
        if (farmerService.emailExists(farmer.getEmail())) {
            model.addAttribute("error",
                    "Email already registered! Please login.");
            return "register";
        }
        farmerService.registerFarmer(farmer);
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Farmer farmer = pondService.getLoggedInFarmer();
        Long farmerId = farmer.getId();

        model.addAttribute("farmerName",
                farmer.getFullName());
        model.addAttribute("pondCount",
                pondService.getPondCount());
        model.addAttribute("activeCycleCount",
                harvestCycleService.getActiveCycleCount());
        model.addAttribute("totalHarvestCount",
                harvestService.getTotalHarvestCount(farmerId));
        model.addAttribute("totalProfit",
                harvestService.getTotalProfit(farmerId));
        return "dashboard";
    }
}