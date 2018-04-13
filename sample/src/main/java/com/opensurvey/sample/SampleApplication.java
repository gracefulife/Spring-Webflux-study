package com.opensurvey.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SampleApplication {
  @RestController
  public static class TestController {
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/test") public String test(int index) {
//      return "test";

      // step 2
      String res = restTemplate.getForObject("http://localhost:8081/remote?req={req}", String.class, "index - " + index);
      return "test - " + index;


      // step 2
      // 그렇다면 1 thread 로 다수의 요청을 처리하도록 할 수 있을까 ?
      // 위의 코드는 동기/블라킹 이기 때문에, restTemplate.getForObject(url, String.class, index) 에서
      // 쓰레드를 점유한 상태로, 반환하지 않아, 지연이 발생한다.
      // 그럼 AsyncRestTemplate + ListenableFuture 를 쓰자.
      // but ..

      /**
       * AsyncRestTemplate 는 ..
       * 톰캣 쓰레드를 반납하지만,
       * ListenableFuture 를 반환하게 되면 (restTemplate.getForEntity) getForEntity 자체가 blocking 이기 때문에,
       * 문제가 생김.
       *
       * 즉 Tomcat 에게 쓰레드를 반환했지만, Spring 내부에 ListenableFuture 의 값을 반환하기 위해서,
       * Future 를 기다리는 쓰레드를 두고 처리함.
       *
       * 즉 어차피 쓰레드가 늘어나기 때문에 쓸모없음..^^;
       */
//      AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
//      ListenableFuture<ResponseEntity<String>> test() {
//        return asyncRestTemplate.getForEntity("http://localhost:8081/remote?req={req}", String.class, "index - " + index)
//      }


      // step 3
      // 그러면 DeferredResult + callback 을 사용한다면 된다.
      // NIO + Async 로 처리되기 때문에, 훌륭햐게 처리가 된다.
      // 그러나 코드는 .....
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(SampleApplication.class, args);
  }
}
