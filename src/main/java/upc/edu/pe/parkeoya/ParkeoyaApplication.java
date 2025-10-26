package upc.edu.pe.parkeoya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ParkeoyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkeoyaApplication.class, args);
    }

}
