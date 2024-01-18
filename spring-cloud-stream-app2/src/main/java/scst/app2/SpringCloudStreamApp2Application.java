package scst.app2;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@SpringBootApplication
@RestController
public class SpringCloudStreamApp2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApp2Application.class, args);
    }

    @Autowired
    StreamBridge streamBridge;

    // curl -X POST -H "Content-Type: text/plain" --data "StreamBridge Demo" http://localhost:8080/publish/demo
    
    @PostMapping(path = "/publish/demo")
    public void publishDemo(@RequestBody String data) {
        streamBridge.send("sensor-out-0", data);
    }
    
    @Bean
    Consumer<String> receive() {
        return s -> System.out.println("Received data: " + s);
    }

}
