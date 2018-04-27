package com.gl365;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.gl365.job.core.executor.JobExecutor;

import redis.clients.jedis.JedisPoolConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableSwagger2
@EnableFeignClients
@EnableHystrix
@ServletComponentScan
@Configuration
@EnableAsync
public class Application {
	@Value("${gl365.wxpub.redis.maxTotal}")
	private int maxTotal;
	@Value("${gl365.wxpub.redis.maxIdle}")
	private int maxIdle;
	@Value("${gl365.wxpub.redis.maxWaitMillis}")
	private long maxWaitMillis;
	@Value("${gl365.wxpub.redis.database}")
	private int database;
	@Value("${gl365.wxpub.redis.host}")
	private String host;
	@Value("${gl365.wxpub.redis.password}")
	private String password;
	@Value("${gl365.wxpub.redis.port}")
	private int port;
	@Value("${gl365.wxpub.redis.usePool}")
	private boolean usePool;
	@Value("${gl365.wxpub.redis.timeout}")
	private int timeout;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Primary
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}
	
	@Bean
	public RedisConnectionFactory connectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(config());
		connectionFactory.setDatabase(database);
		connectionFactory.setHostName(host);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(port);
		connectionFactory.setUsePool(usePool);
		connectionFactory.setTimeout(timeout);
		return connectionFactory;
	}

	@Bean
	public JedisPoolConfig config() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}
	
	@Bean(initMethod = "start")
    public JobExecutor jobExecutor() {
        JobExecutor jobExecutor = new JobExecutor();
        //执行日志存放地址，默认/data/applogs/gl365-job/jobhandler/
        jobExecutor.setLogPath("/logs/");
        return jobExecutor;
    }
}
