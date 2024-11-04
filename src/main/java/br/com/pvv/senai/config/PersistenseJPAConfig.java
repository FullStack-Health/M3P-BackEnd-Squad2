package br.com.pvv.senai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "bt.com.pvv.senai")
public class PersistenseJPAConfig {

}
