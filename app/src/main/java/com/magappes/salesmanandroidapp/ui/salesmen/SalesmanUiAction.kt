package com.magappes.salesmanandroidapp.ui.salesmen

internal sealed interface SalesmanUiAction {
    data class OnSalesmenItemClicked(val index: Int, val isSelected: Boolean) : SalesmanUiAction
    data class OnSearchQueryChanged(val searchQuery: String) : SalesmanUiAction
}