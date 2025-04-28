package tripqm.evn.java.power;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PowerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerServiceApplication.class, args);
    }
}
