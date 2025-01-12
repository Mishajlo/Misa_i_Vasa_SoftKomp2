package com.example.Rezervacije_Servis.queues;

import com.example.Rezervacije_Servis.config.RestTemplateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class SendToClient {

    private final RestTemplateConfig restTemplate;
    private final String userServiceLink;

    public SendToClient(RestTemplateConfig restTemplate, @Value("http://localhost:8080") String userServiceLink) {
        this.restTemplate = restTemplate;
        this.userServiceLink = userServiceLink;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public int getReservationCount(Long userId, String token) {
        String url = UriComponentsBuilder
                .fromHttpUrl(userServiceLink)
                .path("/api/users/reservations/{userId}")
                .buildAndExpand(userId)
                .toUriString();

        HttpHeaders headers = createAuthHeaders(token);

        ResponseEntity<Integer> response = restTemplate.restTemplate().exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Integer.class
        );

        if (response.getBody() == null) {
            throw new RuntimeException("Reservation Count Failed");
        }

        return response.getBody();
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Integer updateReservationCount(Long userId, boolean increment, String token) {
        String url = UriComponentsBuilder
                .fromHttpUrl(userServiceLink)
                .path("/users/reservations/{userId}")
                .buildAndExpand(userId)
                .toUriString();
        System.out.println(token);
        HttpHeaders headers = createAuthHeaders(token);
        System.out.println(headers);

        UpdateReservationCountRequest request = new UpdateReservationCountRequest();
        request.setIncrement(increment);

        ResponseEntity<Integer> response = restTemplate.restTemplate().exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(request, headers),
                Integer.class
        );

        if (response.getBody() == null) {
            throw new RuntimeException("Failed to update reservation count in user service");
        }

        return response.getBody();
    }

    private HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.split(" ")[1]);
        return headers;
    }

    @lombok.Data
    private static class UpdateReservationCountRequest {
        private Boolean increment;
    }
}