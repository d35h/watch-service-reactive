package com.creativedock.productsservice.testing.utils

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.context.junit.jupiter.SpringExtension

open class TableRemoverExtension : BeforeEachCallback {

	override fun beforeEach(context: ExtensionContext) {
		val client = getDatabaseClient(context)
		client.sql("TRUNCATE TABLE watches RESTART IDENTITY").then().block()
	}

	private fun getDatabaseClient(context: ExtensionContext) = SpringExtension.getApplicationContext(context)
		.getBean(DatabaseClient::class.java)
}
