package com.elevate.logbooknpedemo;

import java.time.Duration;
import java.time.Instant;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/time")
public class TestController {

  @GetMapping
  public String getTime() {
    return Instant.now().toString();
  }

  @GetMapping("/stream")
  public Flux<ServerSentEvent<String>> getTimeStream() {
    return Flux.interval(Duration.ofSeconds(12L),
            Schedulers.newParallel("sse-interval-thread", 4))
        .map(iteration -> ServerSentEvent.<String>builder()
            .data(Instant.now().toString())
            .build());
  }

}
