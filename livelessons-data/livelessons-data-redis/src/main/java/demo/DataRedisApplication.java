package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
@EnableCaching
public class DataRedisApplication {

	@Bean
	RedisCacheManager cacheManager(StringRedisTemplate template) {
		return new RedisCacheManager(template);
	}

	public static void main(String[] args) {
		SpringApplication.run(DataRedisApplication.class, args);
	}

}
