package com.magappes.salesmanandroidapp.domain.usecase

import com.magappes.salesmanandroidapp.data.model.Salesman
import com.magappes.salesmanandroidapp.data.repository.SalesmenRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ObserveSalesmenMatchingAreaUseCaseTest {

    @MockK
    private lateinit var salesmenRepository: SalesmenRepository

    private lateinit var observeSalesmenMatchingAreaUseCase: ObserveSalesmenMatchingAreaUseCase

    private val testSalesmen = listOf(
        Salesman("Alex Uber", listOf("86453")),
        Salesman("Chris Krapp", listOf("76251")),
        Salesman("Bernd Schmitt", listOf("76195"))
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { salesmenRepository.getSalesmen() } returns flowOf(testSalesmen)
        observeSalesmenMatchingAreaUseCase = ObserveSalesmenMatchingAreaUseCase(salesmenRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `returns all salesmen when area is null`() = runBlocking {
        val result = observeSalesmenMatchingAreaUseCase(null).first()
        Assertions.assertEquals(testSalesmen, result)
    }

    @Test
    fun `returns all salesmen when area is empty`() = runTest {
        val result = observeSalesmenMatchingAreaUseCase.invoke("").first()
        Assertions.assertEquals(testSalesmen, result)
    }

    @Test
    fun `returns salesman when area match`() = runTest {
        val result = observeSalesmenMatchingAreaUseCase.invoke("86453").first()
        Assertions.assertEquals(listOf(testSalesmen[0]), result)
    }

    @Test
    fun `returns salesman when area match with asterix`() = runTest {
        val result = observeSalesmenMatchingAreaUseCase.invoke("86*").first()
        Assertions.assertEquals(listOf(testSalesmen[0]), result)
    }

    @Test
    fun `returns empty list when no match`() = runTest {
        val result = observeSalesmenMatchingAreaUseCase.invoke("99999").first()
        Assertions.assertEquals(emptyList<Salesman>(), result)
    }
}