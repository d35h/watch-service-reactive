package com.creativedock.productsservice.web

import com.creativedock.productsservice.configuration.repository.WatchRepository
import com.creativedock.productsservice.domain.WatchDto
import com.creativedock.productsservice.testing.utils.TableRemoverExtension
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(TableRemoverExtension::class)
class WatchControllerTest {

	@Autowired
	private lateinit var repo: WatchRepository

	@Autowired
	private lateinit var webClient: WebTestClient

	@Test
	fun `Should save watch to DB`() = runBlocking<Unit> {
		val toSave = WatchDto(
			null,
			"title",
			1.0,
			"description",
			"abc"
		)
		val expected = WatchDto(
			1,
			"title",
			1.0,
			"description",
			"abc"
		)

		val actual = webClient.post()
			.uri("/watches")
			.bodyValue(toSave)
			.exchange()
			.expectStatus()
			.is2xxSuccessful
			.expectBody(WatchDto::class.java)
			.returnResult()
			.responseBody!!

		assertThat(actual).isEqualTo(expected)
		assertThat(repo.findById(1L)).isEqualTo(expected.toDomain())
	}

	@Test
	fun `Should return 400 when title is empty`() {
		val toSave = WatchDto(
			null,
			"",
			1.0,
			"description",
			"abc"
		)

		val actual = webClient.post()
			.uri("/watches")
			.bodyValue(toSave)
			.exchange()
			.expectStatus()
			.isBadRequest
			.expectBodyList(String::class.java)
			.returnResult()
			.responseBody
		assertThat(actual).contains(
			"[{\"codes\":[\"field.required.com.creativedock.productsservice.domain.Watch.title\",\"field.required.title\",\"field.required.java.lang.String\",\"field.required\"],\"arguments\":null,\"defaultMessage\":null,\"objectName\":\"com.creativedock.productsservice.domain.Watch\",\"field\":\"title\",\"rejectedValue\":\"\",\"bindingFailure\":false,\"code\":\"field.required\"}]"
		)
	}

	@Test
	fun `Should return 400 when price is empty`() {
		val toSave = WatchDto(
			null,
			"title",
			null,
			"description",
			"abc"
		)

		val actual = webClient.post()
			.uri("/watches")
			.bodyValue(toSave)
			.exchange()
			.expectStatus()
			.isBadRequest
			.expectBodyList(String::class.java)
			.returnResult()
			.responseBody
		assertThat(actual).contains(
			"[{\"codes\":[\"field.required.com.creativedock.productsservice.domain.Watch.price\",\"field.required.price\",\"field.required.java.lang.Double\",\"field.required\"],\"arguments\":null,\"defaultMessage\":null,\"objectName\":\"com.creativedock.productsservice.domain.Watch\",\"field\":\"price\",\"rejectedValue\":null,\"bindingFailure\":false,\"code\":\"field.required\"}]"
		)
	}

	@Test
	fun `Should return 400 when image is not base64`() {
		val toSave = WatchDto(
			null,
			"title",
			2.0,
			"description",
			"i�base64: invalid input"
		)

		val actual = webClient.post()
			.uri("/watches")
			.bodyValue(toSave)
			.exchange()
			.expectStatus()
			.isBadRequest
			.expectBodyList(String::class.java)
			.returnResult()
			.responseBody
		assertThat(actual).contains(
			"[{\"codes\":[\"field.notBase64.com.creativedock.productsservice.domain.Watch.imageBase64\",\"field.notBase64.imageBase64\",\"field.notBase64.java.lang.String\",\"field.notBase64\"],\"arguments\":null,\"defaultMessage\":null,\"objectName\":\"com.creativedock.productsservice.domain.Watch\",\"field\":\"imageBase64\",\"rejectedValue\":\"i�base64: invalid input\",\"bindingFailure\":false,\"code\":\"field.notBase64\"}]"
		)
	}

}
