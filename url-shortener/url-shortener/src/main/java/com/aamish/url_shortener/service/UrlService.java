package com.aamish.url_shortener.service;

import com.aamish.url_shortener.dto.ShortenUrlRequest;
import com.aamish.url_shortener.dto.ShortenUrlResponse;
import com.aamish.url_shortener.dto.UrlStateResponse;

public interface UrlService {
   ShortenUrlResponse shortenUrl(ShortenUrlRequest request);
   String redirect(String shortCode);
   UrlStateResponse geStates(String shortCode);
}
