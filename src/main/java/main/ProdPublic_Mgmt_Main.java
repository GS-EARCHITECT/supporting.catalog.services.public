package main;

import java.util.concurrent.Executor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableEurekaClient
@EnableAsync
@EntityScan(basePackages ={"cacheMgmt","compclasses_cache","location_classes_cache","pricerange_cache","ratings_cache", "resources_cache","resource_classes_cache"})
@EnableJpaRepositories(basePackages ={"cacheMgmt","compclasses_cache","location_classes_cache","pricerange_cache","ratings_cache", "resources_cache","resource_classes_cache"})
@ComponentScan(basePackages ={"cacheMgmt","compclasses_cache","location_classes_cache","pricerange_cache","ratings_cache", "resources_cache","resource_classes_cache"})
public class ProdPublic_Mgmt_Main extends SpringBootServletInitializer  
{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProdPublic_Mgmt_Main.class);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(ProdPublic_Mgmt_Main.class, args);
	}
	
	  @Bean(name = "asyncExecutor")
	  public Executor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(10);
	    executor.setMaxPoolSize(10);
	    executor.setQueueCapacity(500);
	    executor.setThreadNamePrefix("prodservmods");
	    executor.initialize();
	    return executor;
	  }
	
}