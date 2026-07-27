package com.aamish.url_shortener.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aamish.url_shortener.dto.ShortenUrlRequest;
import com.aamish.url_shortener.dto.ShortenUrlResponse;
import com.aamish.url_shortener.dto.UrlStateResponse;
import com.aamish.url_shortener.service.UrlService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/urls")
public class UrlController {
private final UrlService  service;
@Operation(summary="Generate short URL")
@PostMapping
public ShortenUrlResponse urlResponse(@Valid @RequestBody ShortenUrlRequest request) {
	return service.shortenUrl(request);
}
@Operation(summary="Get a short URL")
@GetMapping("/{shortCode}")
public ResponseEntity<Void> redirect(@PathVariable String shortCode){
	String originalUrl=service.redirect(shortCode);
	return ResponseEntity.status(HttpStatus.FOUND)
			.location(URI.create(originalUrl))
			.build();
}
@Operation(summary="Get api analytics")
@GetMapping("/{shortCode}/stats")
public UrlStateResponse getStates(@PathVariable String shortCode) {
	return service.geStates(shortCode);
	}

}
