package com.creativedock.productsservice.repository

import com.creativedock.productsservice.configuration.repository.WatchRepository
import com.creativedock.productsservice.domain.Watch
import com.creativedock.productsservice.testing.utils.TableRemoverExtension
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(TableRemoverExtension::class)
class WatchRepositoryTest {

	@Autowired
	private lateinit var repo: WatchRepository

	@Test
	fun `Should save new watch`() = runBlocking<Unit> {
		val toSave = Watch(null, "requiredTitle", 25.0, "description", "abc")
		val actual = repo.save(toSave)
		val expected = Watch(1, toSave.title, toSave.price, toSave.description, toSave.imageBase64)
		assertThat(actual).isEqualTo(expected)
	}

	@Test
	fun `Should save and update watch`() = runBlocking<Unit> {
		val toSave = Watch(null, "requiredTitle", 25.0, "description", "abc")
		repo.save(toSave)
		val actual = repo.save(Watch(1, "newRequiredTitle", 26.0, "newDescription", "newAbc"))
		val expected = Watch(1, "newRequiredTitle", 26.0, "newDescription", "newAbc")
		assertThat(actual).isEqualTo(expected)
	}

}
