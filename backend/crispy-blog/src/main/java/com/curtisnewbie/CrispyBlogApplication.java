package com.curtisnewbie;

import com.curtisnewbie.module.auth.config.EnableFeignJwtAuthorization;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.io.IOException;

@EnableFeignClients
@EnableFeignJwtAuthorization
@EnableDiscoveryClient
@EnableRedisHttpSession(redisNamespace = "crispy-blog:session")
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@SpringBootApplication
public class CrispyBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrispyBlogApplication.class, args);
	}

	@Value("${redisson-config}")
	private String redissonConfig;

	@Bean
	public RedissonClient redissonClient() throws IOException {
		Config config = Config.fromYAML(this.getClass().getClassLoader().getResourceAsStream(redissonConfig));
		RedissonClient redisson = Redisson.create(config);
		return redisson;
	}

}
