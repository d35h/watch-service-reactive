package com.creativedock.productsservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("watches")
data class Watch(
	@Id
	val id: Long?,
	val title: String,
	val price: Double,
	val description: String?,
	@Column("image_base64")
	val imageBase64: String?
) {
	fun toDto() = WatchDto(id, title, price, description, imageBase64)
}
