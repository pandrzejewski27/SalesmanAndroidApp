package com.magappes.salesmanandroidapp.ui.salesmen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magappes.salesmanandroidapp.data.model.Salesman
import com.magappes.salesmanandroidapp.domain.usecase.ObserveSalesmenMatchingAreaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class SalesmenViewModel @Inject constructor(
    private val observeSalesmenMatchingAreaUseCase: ObserveSalesmenMatchingAreaUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SalesmenUiState())
    val uiState: StateFlow<SalesmenUiState> = _uiState

    init {
        viewModelScope.launch {
            observeSalesmenMatchingAreaUseCase()
                .collectLatest {
                    updateSalesmen(salesmen = mapToUi(salesmen = it))
                }
            _uiState
                .map { it.searchQuery.ifEmpty { null } }
                .drop(count = 1)
                .distinctUntilChanged()
                .debounce(DEBOUNCE_DELAY)
                .flatMapLatest { area ->
                    observeSalesmenMatchingAreaUseCase(area = area)
                }
                .map { mapToUi(salesmen = it) }
                .collectLatest { updateSalesmen(it) }
        }
    }

    private fun mapToUi(salesmen: List<Salesman>): List<SalesmanUi> =
        salesmen.mapIndexed { index, salesman ->
            SalesmanUi(
                index = index,
                name = salesman.name,
                areas = salesman.areas,
                isSelected = uiState.value.salesmen
                    .firstOrNull { it.name == salesman.name }
                    ?.isSelected ?: false
            )
        }

    private fun updateSalesmen(salesmen: List<SalesmanUi>) {
        _uiState.update { current ->
            current.copy(
                salesmen = salesmen,
                isLoading = false
            )
        }
    }

    fun onAction(salesmanUiAction: SalesmanUiAction) {
        when (salesmanUiAction) {
            is SalesmanUiAction.OnSalesmenItemClicked -> updateSalesmanSelection(salesmanUiAction)
            is SalesmanUiAction.OnSearchQueryChanged -> updateSearchQuery(salesmanUiAction)
        }
    }

    private fun updateSalesmanSelection(action: SalesmanUiAction.OnSalesmenItemClicked) {
        _uiState.update { current ->
            current.copy(
                salesmen = current.salesmen.map { salesman ->
                    if (salesman.index == action.index) {
                        salesman.copy(isSelected = action.isSelected)
                    } else {
                        salesman
                    }
                }
            )
        }
    }

    private fun updateSearchQuery(action: SalesmanUiAction.OnSearchQueryChanged) {
        _uiState.update { current ->
            current.copy(searchQuery = action.searchQuery)
        }
    }

    private companion object {
        private const val DEBOUNCE_DELAY = 1000L
    }
}