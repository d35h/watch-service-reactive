package com.creativedock.productsservice.configuration.web

import com.creativedock.productsservice.service.WatchService
import com.creativedock.productsservice.web.ControllerFunctions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ControllerFunctionConfiguration {

	@Bean
	fun functions(watchService: WatchService) = ControllerFunctions(watchService)

}
