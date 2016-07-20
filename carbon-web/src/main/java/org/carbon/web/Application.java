package org.carbon.web;

import org.carbon.web.jcr.RepositoryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main application
 *
 */
@SpringBootApplication
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RepositoryFactory repositoryFactory(){
        RepositoryFactory factory = new RepositoryFactory();
        return factory;
    }
}
