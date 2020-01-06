package intern_server.shibing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"intern_server.shibing.dao"})
@SpringBootApplication
public class ShibingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShibingApplication.class, args);
    }

}
