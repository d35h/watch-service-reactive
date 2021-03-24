package com.creativedock.productsservice.domain

data class WatchDto(val id: Long?, val title: String?, val price: Double?, val description: String?, val imageBase64: String?) {
	fun toDomain() = Watch(id, title!!, price!!, description, imageBase64)
}
