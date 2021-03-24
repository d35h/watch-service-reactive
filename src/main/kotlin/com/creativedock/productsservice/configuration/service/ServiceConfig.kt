package com.creativedock.productsservice.configuration.service

import com.creativedock.productsservice.configuration.repository.WatchRepository
import com.creativedock.productsservice.service.WatchService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfig {

	@Bean
	fun watchService(repo: WatchRepository) = WatchService(repo)

}
