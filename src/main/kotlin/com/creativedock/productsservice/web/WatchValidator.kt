package com.creativedock.productsservice.web

import com.creativedock.productsservice.domain.Watch
import com.creativedock.productsservice.domain.WatchDto
import org.apache.commons.codec.binary.Base64
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

class WatchValidator: Validator {
	override fun supports(clazz: Class<*>) = Watch::class.java.isAssignableFrom(clazz)

	override fun validate(target: Any, errors: Errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required")
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "field.required")
		rejectIfImageNotBase64(target, errors)
	}

	private fun rejectIfImageNotBase64(target: Any, errors: Errors) {
		val dto = target as WatchDto
		if (!Base64.isBase64(dto.imageBase64)) {
			errors.rejectValue("imageBase64", "field.notBase64")
		}
	}
}
