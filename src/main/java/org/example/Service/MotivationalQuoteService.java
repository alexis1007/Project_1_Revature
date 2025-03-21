/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package org.example.Service;

/**
 *
 * @author Propietario
 */

import org.example.DTO.QuoteDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MotivationalQuoteService {
    // Base URL for the motivational quotes API (e.g., ZenQuotes)
    private final String quoteApiUrl = "https://zenquotes.io/api/random";

    // RestTemplate to make HTTP requests.
    private final RestTemplate restTemplate;

    public MotivationalQuoteService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Calls the external quotes API to retrieve a random quote.
     *
     * @return A motivational quote as a String.
     */
    public String getRandomQuote() {
        // The API returns an array of quotes; we take the first one.
        QuoteDTO[] quotes = restTemplate.getForObject(quoteApiUrl, QuoteDTO[].class);
        if (quotes != null && quotes.length > 0) {
            return quotes[0].getQ() + " - " + quotes[0].getA();
        }
        // Fallback quote in case of failure.
        return "Motivational quote failed, but keep pushing forward!";
    }
}
