package com.magappes.salesmanandroidapp.ui.salesmen


internal data class SalesmanUi(
    val index: Int,
    val name: String,
    val areas: List<String>,
    val isSelected: Boolean = false
) {
    fun firstLetterUppercase(): String {
        return name.first().uppercase()
    }

    fun areasWithCommas(): String {
        return areas.joinToString(separator = ", ")
    }
}

internal data class SalesmenUiState(
    val salesmen: List<SalesmanUi> = listOf(),
    val searchQuery: String = "",
    val isLoading: Boolean = true
)