package com.creativedock.productsservice.web

import com.creativedock.productsservice.domain.Watch
import com.creativedock.productsservice.domain.WatchDto
import com.creativedock.productsservice.service.WatchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Errors


class ControllerFunctions(private val watchService: WatchService) {

	private val log: Logger = LoggerFactory.getLogger(ControllerFunctions::class.java)

	suspend fun save(request: ServerRequest): ServerResponse {
		val toSave = request.awaitBody(WatchDto::class)
		val watchValidator = WatchValidator()
		val errors: Errors = BeanPropertyBindingResult(
			toSave,
			Watch::class.java.name
		)
		log.info("Receive to save $toSave")

		watchValidator.validate(toSave, errors)
		if (errors.allErrors.isEmpty()) {
			return getOkResponse().bodyValueAndAwait(watchService.save(toSave.toDomain()).toDto())
		}
		return ServerResponse.badRequest().bodyValueAndAwait(errors.allErrors)
	}

	private fun getOkResponse() = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
}
