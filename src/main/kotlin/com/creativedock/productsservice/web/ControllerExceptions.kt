package com.creativedock.productsservice.web

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

object ControllerExceptions {
    @Suppress("ThrowsCount")
    fun throwCustomException(e: Throwable): Nothing =
        when (e) {
            is NoSuchElementException ->
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Specify body", e)
            else -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e)
        }
}
