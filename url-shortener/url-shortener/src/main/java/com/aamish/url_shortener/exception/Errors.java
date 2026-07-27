package com.aamish.url_shortener.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Errors {
   private String message;
   private int status;
   private LocalDateTime occurredAt;
}
