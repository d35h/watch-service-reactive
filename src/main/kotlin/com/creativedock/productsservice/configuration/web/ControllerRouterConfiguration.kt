package com.creativedock.productsservice.configuration.web

import com.creativedock.productsservice.web.ControllerExceptions.throwCustomException
import com.creativedock.productsservice.web.ControllerFunctions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ControllerRouterConfiguration {

	@FlowPreview
	@ExperimentalCoroutinesApi
	@Bean
	fun mainRouter(functions: ControllerFunctions) = coRouter {
		GET("/ping") { ServerResponse.ok().bodyValue("Pong!").awaitFirst() }
		accept(APPLICATION_JSON).nest {
			POST("/watches", functions::save)
		}
		onError<RuntimeException> { exception, _ -> throwCustomException(exception) }
	}

}
