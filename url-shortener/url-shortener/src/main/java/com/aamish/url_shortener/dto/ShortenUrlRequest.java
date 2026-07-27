package com.aamish.url_shortener.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenUrlRequest {
	@NotBlank(message="URL can not be blank")
	@URL(message="Please enter correct URL")
  private String originalUrl;
}
