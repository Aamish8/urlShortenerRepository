package com.aamish.url_shortener.service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.aamish.url_shortener.dto.ShortenUrlRequest;
import com.aamish.url_shortener.dto.ShortenUrlResponse;
import com.aamish.url_shortener.dto.UrlStateResponse;
import com.aamish.url_shortener.entity.Url;
import com.aamish.url_shortener.exception.UrlNotFoundException;
import com.aamish.url_shortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService{
	private final UrlRepository repo;
	private final StringRedisTemplate temp;
	public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {
		String code;
		do{
			code=generateCode(6);
		}
		while(repo.existsByShortCode(code));
		Url url=Url.builder()
				.originalUrl(request.getOriginalUrl())
				.shortCode(code)
				.clickCounts(0L)
				.createdAt(LocalDateTime.now())
				.build();
		repo.save(url);
		return ShortenUrlResponse.builder()
				.originalUrl(url.getOriginalUrl())
				.shortCode(code)
				.shortUrl("http://localhost:8080/"+code)
				.build();
	}
	@Override
	public String redirect(String shortCode) {
		String originalUrl=temp.opsForValue().get(shortCode);
		if(originalUrl!=null) {
			System.out.println("Cached from Redis");
			incrementCount(shortCode);
			return originalUrl;
		}
		
		Url url=repo.findByShortCode(shortCode);
		if(url==null)throw new UrlNotFoundException("URL not found");
		System.out.println("Fetched From Database");
		temp.opsForValue().set(shortCode,url.getOriginalUrl());
		url.setClickCounts(url.getClickCounts()+1);
		repo.save(url);
		return url.getOriginalUrl();
	}
	void incrementCount(String shortCode) {
		Url url=repo.findByShortCode(shortCode);
		url.setClickCounts(url.getClickCounts()+1);
		repo.save(url);
	}
	
	 private static final String CHARACTERS =
	            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	 private static final SecureRandom random=new SecureRandom();
	 public static String generateCode(int length) {
		 StringBuilder sb=new StringBuilder();
		 for(int i=0;i<length;i++) {
			 sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		 }
		 return sb.toString();
	 }
	@Override
	public UrlStateResponse geStates(String shortCode) {
		if(!repo.existsByShortCode(shortCode))throw new UrlNotFoundException("Invalid URL");
		Url url=repo.findByShortCode(shortCode);
		return UrlStateResponse.builder()
				.originalUrl(url.getOriginalUrl())
				.shortCode(shortCode)
				.clickCounts(url.getClickCounts())
				.createdAt(url.getCreatedAt())
				.build();
	}
	 
}
