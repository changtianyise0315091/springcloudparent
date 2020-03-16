package p1;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ArticleController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/article /callHello")
    public String callHello() {
        return restTemplate.getForObject("http://sa:sa@localhost:8081/user/hello", String.class);
    }

    @GetMapping("/article/callHello2")
    public String callHello2() {
        return restTemplate.getForObject("http://sa:sa@eureka-client-user-service/user/hello", String.class);
    }

    //@Autowired
    //@Qualifier(value = "eurekaClient")
    private EurekaClient eurekaClient;

    //@GetMapping("/article/infos")
    public Object serviceUrl() {
        return eurekaClient.getInstancesByVipAddress("eureka-client-user-service", false);
    }
}