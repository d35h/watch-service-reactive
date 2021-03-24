package com.creativedock.productsservice.service

import com.creativedock.productsservice.configuration.repository.WatchRepository
import com.creativedock.productsservice.domain.Watch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WatchService(private val repo: WatchRepository) {

	private val log: Logger = LoggerFactory.getLogger(WatchService::class.java)

	suspend fun save(toSave: Watch): Watch {
		log.info("Saving $toSave")
		return repo.save(toSave)
	}

	suspend fun findById(id: Long): Watch {
		log.info("Getting by $id")
		return repo.findById(id)
	}

}
