package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HouseClientController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/call/data")
    public HouseInfo getData(@RequestParam("name") String name) {
        //当使用带有负载均衡的restTemplate时，要使用服务名进行访问服务
        return restTemplate.getForObject( "http://eureka-client-userservice:8081/house/data?name="+ name, HouseInfo.class);
    }
    @GetMapping("/call/data/{name}")
    public String getData2(@PathVariable("name") String name) {
        return restTemplate.getForObject( "http://eureka-client-userservice:8081/house/data/{name}", String.class, name);
    }

    @GetMapping("/call/dataEntity")
    public HouseInfo getData3(@RequestParam("name") String name) {
        ResponseEntity<HouseInfo> responseEntity = restTemplate
                .getForEntity("http://localhost:8081/house/data?name=" + name, HouseInfo.class);
        if (responseEntity.getStatusCodeValue() == 200) {
            return responseEntity.getBody();
        }
        return null;
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @GetMapping("/choose")
    public Object chooseUrl() {
        ServiceInstance instance = loadBalancerClient.choose("eureka-client-userservice");
        return instance;
    }
}
