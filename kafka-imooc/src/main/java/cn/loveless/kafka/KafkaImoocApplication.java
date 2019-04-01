package cn.loveless.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class KafkaImoocApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaImoocApplication.class, args);
	}

}
