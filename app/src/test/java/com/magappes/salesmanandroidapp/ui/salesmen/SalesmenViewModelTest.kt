package com.magappes.salesmanandroidapp.ui.salesmen

import com.magappes.salesmanandroidapp.data.model.Salesman
import com.magappes.salesmanandroidapp.domain.usecase.ObserveSalesmenMatchingAreaUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SalesmenViewModelTest {

    @MockK
    private lateinit var getSalesmenUseCase: ObserveSalesmenMatchingAreaUseCase

    private val testSalesmen = listOf(
        Salesman("Artem Titarenko", listOf("76133")),
        Salesman("Bernd Schmitt", listOf("7619*"))
    )

    private val mappedSalesmen = testSalesmen.mapIndexed { index, s ->
        SalesmanUi(index, s.name, s.areas)
    }

    private lateinit var sut: SalesmenViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeAll
    fun setupMocks() {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getSalesmenUseCase.invoke(null) } returns flowOf(testSalesmen)
        coEvery { getSalesmenUseCase.invoke("76133") } returns flowOf(listOf(Salesman("Artem Titarenko", listOf("76133"))))
        sut = SalesmenViewModel(getSalesmenUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has salesmen loaded`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val uiState = sut.uiState.value
        assertEquals(mappedSalesmen, uiState.salesmen)
        assertEquals(false, uiState.isLoading)
    }

    @Test
    fun `OnSearchQueryChanged updates searchQuery and filters salesmen after debounce`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        sut.onAction(SalesmanUiAction.OnSearchQueryChanged("76133"))

        testDispatcher.scheduler.advanceTimeBy(1000L)
        testDispatcher.scheduler.advanceUntilIdle()

        val uiState = sut.uiState.value
        assertEquals("76133", uiState.searchQuery)
        assertEquals(listOf(SalesmanUi(0, "Artem Titarenko", areas = listOf("76133"))), uiState.salesmen)
    }

    @Test
    fun `OnSalesmenItemClicked updates isSelected correctly`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val action = SalesmanUiAction.OnSalesmenItemClicked(index = 0, isSelected = true)
        sut.onAction(action)

        val uiState = sut.uiState.value
        assertEquals(true, uiState.salesmen[0].isSelected)
        assertEquals(false, uiState.salesmen[1].isSelected)
    }
}