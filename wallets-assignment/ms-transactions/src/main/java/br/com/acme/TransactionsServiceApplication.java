package br.com.acme;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransactionsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionsServiceApplication.class, args);
    }
}