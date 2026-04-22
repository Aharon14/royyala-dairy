package com.royyala.royyaladiary.service;

import com.royyala.royyaladiary.entity.HarvestCycle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class GeminiAiService {

	@Value("${gemini.api.key}")
	private String apiKey;

	private final FeedEntryService feedEntryService;

	public GeminiAiService(FeedEntryService feedEntryService) {
		this.feedEntryService = feedEntryService;
	}

	public String predictHarvestDate(HarvestCycle cycle) {

		long daysRunning = ChronoUnit.DAYS.between(cycle.getStartDate(), LocalDate.now());

		double totalFeedKg = feedEntryService.getFeedEntriesByCycle(cycle.getId()).stream()
				.mapToDouble(entry -> entry.getFeedQuantityKg()).sum();

		double totalFeedCost = feedEntryService.getTotalFeedCost(cycle.getId());

		String prompt = "You are an expert shrimp farming advisor" + " for farmers in Andhra Pradesh, India.\n\n"
				+ "A farmer has an active shrimp harvest cycle" + " with these details:\n" + "- Seed count: "
				+ cycle.getSeedCount() + " seeds\n" + "- Seed cost: Rs." + cycle.getSeedCost() + "\n"
				+ "- Cycle started: " + cycle.getStartDate() + "\n" + "- Days running so far: " + daysRunning
				+ " days\n" + "- Total feed given: " + totalFeedKg + " kg\n" + "- Total feed cost so far: Rs."
				+ totalFeedCost + "\n\n" + "Based on typical Andhra Pradesh shrimp farming"
				+ " practices (Vannamei shrimp usually ready in" + " 90-120 days), predict:\n"
				+ "1. Expected harvest date\n" + "2. Estimated harvest weight in kg\n"
				+ "3. One piece of advice for the farmer\n\n" + "Keep your answer simple and in 3 short points."
				+ " Respond in English.";

		Map<String, Object> requestBody = Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))));

		String url = "https://generativelanguage.googleapis.com" + "/v1beta/models/gemini-1.5-flash"
				+ ":generateContent?key=" + apiKey;

		try {
			Map response = WebClient.create().post().uri(url).header("Content-Type", "application/json")
					.bodyValue(requestBody).retrieve().bodyToMono(Map.class).block();

			List<Map> candidates = (List<Map>) response.get("candidates");
			Map content = (Map) candidates.get(0).get("content");
			List<Map> parts = (List<Map>) content.get("parts");
			return parts.get(0).get("text").toString();

		} catch (Exception e) {
			return "AI prediction unavailable. Please try again.\n" + "Error: " + e.getMessage();
		}
	}
}