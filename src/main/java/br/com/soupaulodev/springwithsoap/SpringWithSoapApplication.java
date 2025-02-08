package br.com.soupaulodev.springwithsoap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringWithSoapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWithSoapApplication.class, args);
    }

}
