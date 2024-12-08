package com.elevate.logbooknpedemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.StructuredHttpLogFormatter;

@Configuration
public class LogbookConfiguration {

  @Bean
  HttpLogWriter httpLogWriter() {
    return new HttpLogWriter() {
      private final Logger log = LoggerFactory.getLogger(Logbook.class);

      @Override
      public boolean isActive() {
        return log.isDebugEnabled();
      }

      @Override
      public void write(Precorrelation precorrelation, String request) {
        log.debug(request);
      }

      @Override
      public void write(Correlation correlation, String response) {
        log.debug(response);
      }
    };
  }

  @Bean
  StructuredHttpLogFormatter jsonHttpLogFormatter(ObjectMapper objectMapper) {
    return objectMapper::writeValueAsString;
  }

}