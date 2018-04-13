package com.opensurvey.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

// @see https://www.youtube.com/watch?v=ExUfZkh7Puk
@Slf4j
public class TestLauncher {
  static AtomicInteger counter = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
    ExecutorService executorService = Executors.newFixedThreadPool(100);

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8080/test?index={index}";

    CyclicBarrier cyclicBarrier = new CyclicBarrier(101);

    for (int i = 0; i < 100; i++) {
      executorService.submit(() -> {
        int index = counter.addAndGet(1);
        cyclicBarrier.await();

        log.info("Thread - {}", index);
        StopWatch innerStopWatch = new StopWatch();
        innerStopWatch.start();

        String result = restTemplate.getForObject(url, String.class, index);
        innerStopWatch.stop();
        log.info("Elapsed: {} {} - {}", index, innerStopWatch.getTotalTimeSeconds(), result);

        return null; // 타입추론을 Callable 로 시키도록 그러면 cyclicBarrier 의 Exception을 잡을 수 있음. callable interface 참조.
      });
    }

    cyclicBarrier.await();
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    executorService.shutdown();
    executorService.awaitTermination(100, TimeUnit.SECONDS);

    stopWatch.stop();
    log.info("Total: {}", stopWatch.getTotalTimeSeconds());
  }
}
