package com.royyala.royyaladiary.controller;

import com.royyala.royyaladiary.entity.HarvestCycle;
import com.royyala.royyaladiary.service.GeminiAiService;
import com.royyala.royyaladiary.service.FeedEntryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ai")
public class AiController {

    private final GeminiAiService geminiAiService;
    private final FeedEntryService feedEntryService;

    public AiController(GeminiAiService geminiAiService,
                        FeedEntryService feedEntryService) {
        this.geminiAiService = geminiAiService;
        this.feedEntryService = feedEntryService;
    }

    @GetMapping("/predict/{cycleId}")
    public String predictHarvest(
            @PathVariable Long cycleId, Model model) {

        HarvestCycle cycle = feedEntryService
                .getCycleById(cycleId);

        String prediction = geminiAiService
                .predictHarvestDate(cycle);

        model.addAttribute("cycle", cycle);
        model.addAttribute("feedEntries",
                feedEntryService.getFeedEntriesByCycle(cycleId));
        model.addAttribute("totalFeedCost",
                feedEntryService.getTotalFeedCost(cycleId));
        model.addAttribute("prediction", prediction);
        model.addAttribute("harvest", null);

        return "cycle-detail";
    }
}