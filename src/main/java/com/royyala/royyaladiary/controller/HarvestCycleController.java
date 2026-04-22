package com.royyala.royyaladiary.controller;

import com.royyala.royyaladiary.entity.HarvestCycle;
import com.royyala.royyaladiary.entity.Pond;
import com.royyala.royyaladiary.repository.PondRepository;
import com.royyala.royyaladiary.service.HarvestCycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cycles")
public class HarvestCycleController {

    private final HarvestCycleService harvestCycleService;
    private final PondRepository pondRepository;

    public HarvestCycleController(HarvestCycleService harvestCycleService,
                                   PondRepository pondRepository) {
        this.harvestCycleService = harvestCycleService;
        this.pondRepository = pondRepository;
    }

    @GetMapping("/pond/{pondId}")
    public String listCycles(@PathVariable Long pondId, Model model) {
        Pond pond = pondRepository.findById(pondId)
                .orElseThrow(() -> new RuntimeException("Pond not found"));
        model.addAttribute("pond", pond);
        model.addAttribute("cycles", 
                harvestCycleService.getCyclesByPond(pondId));
        return "cycles";
    }

    @GetMapping("/start/{pondId}")
    public String showStartCycleForm(@PathVariable Long pondId, 
                                      Model model) {
        model.addAttribute("cycle", new HarvestCycle());
        model.addAttribute("pondId", pondId);
        return "start-cycle";
    }

    @PostMapping("/start/{pondId}")
    public String startCycle(@PathVariable Long pondId,
                              @ModelAttribute HarvestCycle cycle) {
        harvestCycleService.startCycle(cycle, pondId);
        return "redirect:/cycles/pond/" + pondId;
    }
}