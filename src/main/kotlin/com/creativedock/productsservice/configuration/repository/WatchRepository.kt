package com.creativedock.productsservice.configuration.repository

import com.creativedock.productsservice.domain.Watch
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WatchRepository: CoroutineCrudRepository<Watch, Long> {
	override suspend fun findById(id: Long): Watch
	suspend fun save(toSave: Watch): Watch
}
