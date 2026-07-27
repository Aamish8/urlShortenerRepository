package com.aamish.url_shortener.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.aamish.url_shortener.controller.UrlController;
import com.aamish.url_shortener.dto.ShortenUrlRequest;
import com.aamish.url_shortener.dto.ShortenUrlResponse;
import com.aamish.url_shortener.dto.UrlStateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UrlController.class)
public class UrlControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UrlService service;
  @Autowired
  private ObjectMapper objectMapper;
  @Test
  void shouldReturnShortUrl() throws JsonProcessingException, Exception {
	  ShortenUrlRequest request=new ShortenUrlRequest();
	  request.setOriginalUrl("https://google.com");
	  ShortenUrlResponse response=ShortenUrlResponse.builder()
			                      .shortUrl("http://localhost:8080/ABC123")
			                      .shortCode("ABC123")
			                      .originalUrl("https://google.com")
			                      .build();
	  when(service.shortenUrl(any())).thenReturn(response);
	  mockMvc.perform(post("/api/v1/urls").contentType("application/json")
			  .content(objectMapper.writeValueAsString(request)))
			  .andExpect(status().isOk());
  }
  @Test
  void func() throws Exception {
	  when(service.redirect("ABC123")).thenReturn("https://google.com");
	  mockMvc.perform(get("/api/v1/urls/ABC123"))
	                 .andExpect(status().isFound())
	                 .andExpect(header().string("Location","https://google.com"));
	  verify(service).redirect("ABC123");
  }
  @Test
  void func2() throws Exception {
	  UrlStateResponse response=UrlStateResponse.builder()
			  .originalUrl("https://google.com")
			  .shortCode("ABC123")
			  .clickCounts(1)
			  .createdAt(LocalDateTime.now())
			  .build();
	  when(service.geStates("ABC123")).thenReturn(response);
	  mockMvc.perform(get("/api/v1/urls/ABC123/stats"))
	   					.andExpect(status().isOk());
	  verify(service).geStates("ABC123");
  }
}
