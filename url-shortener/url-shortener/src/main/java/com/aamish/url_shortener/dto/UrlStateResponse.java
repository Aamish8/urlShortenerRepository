package com.aamish.url_shortener.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlStateResponse {
	private String originalUrl;
	private String shortCode;
	private long clickCounts;
	private LocalDateTime createdAt;
}
