package com.royyala.royyaladiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.royyala.royyaladiary.entity.FeedEntry;
import com.royyala.royyaladiary.service.FeedEntryService;
import com.royyala.royyaladiary.service.HarvestService;

@Controller
@RequestMapping("/feed")
public class FeedEntryController {

	private final FeedEntryService feedEntryService;
	private final HarvestService harvestService;

	public FeedEntryController(
	        FeedEntryService feedEntryService,
	        HarvestService harvestService) {
	    this.feedEntryService = feedEntryService;
	    this.harvestService = harvestService;
	}

    @GetMapping("/cycle/{cycleId}")
    public String cycleDetail(
            @PathVariable Long cycleId, Model model) {
        model.addAttribute("cycle",
                feedEntryService.getCycleById(cycleId));
        model.addAttribute("feedEntries",
                feedEntryService.getFeedEntriesByCycle(cycleId));
        model.addAttribute("totalFeedCost",
                feedEntryService.getTotalFeedCost(cycleId));
        model.addAttribute("harvest",
                harvestService.getHarvestByCycle(cycleId)
                        .orElse(null));
        return "cycle-detail";
    }

    @GetMapping("/add/{cycleId}")
    public String showAddFeedForm(
            @PathVariable Long cycleId, Model model) {
        model.addAttribute("feedEntry", new FeedEntry());
        model.addAttribute("cycleId", cycleId);
        return "add-feed";
    }

    @PostMapping("/add/{cycleId}")
    public String addFeedEntry(
            @PathVariable Long cycleId,
            @ModelAttribute FeedEntry feedEntry) {
        feedEntryService.addFeedEntry(feedEntry, cycleId);
        return "redirect:/feed/cycle/" + cycleId;
    }
}