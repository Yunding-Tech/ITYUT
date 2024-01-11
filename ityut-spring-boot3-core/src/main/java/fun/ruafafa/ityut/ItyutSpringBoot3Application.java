package fun.ruafafa.ityut;

import com.dtflys.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ForestScan(basePackages = "fun.ruafafa.ityut.client")
public class ItyutSpringBoot3Application {

    public static void main(String[] args) {
        SpringApplication.run(ItyutSpringBoot3Application.class, args);
    }

}
