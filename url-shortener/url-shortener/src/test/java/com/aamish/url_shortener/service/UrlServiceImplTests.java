package com.aamish.url_shortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.aamish.url_shortener.dto.ShortenUrlRequest;
import com.aamish.url_shortener.dto.ShortenUrlResponse;
import com.aamish.url_shortener.exception.UrlNotFoundException;
import com.aamish.url_shortener.repository.UrlRepository;

public class UrlServiceImplTests {
	@Mock
  private UrlRepository repo;
	@Mock
  private StringRedisTemplate temp;
	@InjectMocks
  private UrlServiceImpl service;
  @Mock
  private ValueOperations<String,String>valueOperations;
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(temp.opsForValue()).thenReturn(valueOperations);
	}
	@Test
	void shortenurl_shouldGenerateShortUrl() {
		ShortenUrlRequest request=new ShortenUrlRequest();
		request.setOriginalUrl("https://google.com");
		when(repo.existsByShortCode(anyString())).thenReturn(false);
		ShortenUrlResponse response=service.shortenUrl(request);
		assertNotNull(response);
		assertEquals("https://google.com",response.getOriginalUrl());
		assertNotNull(response.getShortCode());
		assertEquals(6,response.getShortCode().length());
		assertEquals("http://localhost:8080/"+response.getShortCode(),response.getShortUrl());
		verify(repo,atLeastOnce()).save(any());
		
	}
	@Test
	void redirect_shouldThrowException_whenShortCodeNotFound() {
		when(valueOperations.get(anyString())).thenReturn(null);
		when(repo.findByShortCode(anyString())).thenReturn(null);
		assertThrows(UrlNotFoundException.class,()->{
			service.redirect("abc123");
		});
	}
}
