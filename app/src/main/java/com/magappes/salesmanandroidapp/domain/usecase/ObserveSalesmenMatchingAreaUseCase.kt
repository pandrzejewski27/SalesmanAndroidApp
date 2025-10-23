package com.magappes.salesmanandroidapp.domain.usecase

import com.magappes.salesmanandroidapp.data.model.Salesman
import com.magappes.salesmanandroidapp.data.repository.SalesmenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


internal class ObserveSalesmenMatchingAreaUseCase @Inject constructor(
    private val salesmenRepository: SalesmenRepository
) {
    suspend operator fun invoke(area: String? = null): Flow<List<Salesman>> {
        return salesmenRepository.getSalesmen().map { salesmen ->
            val range = area?.let { parseAreaExpression(it) } ?: return@map salesmen

            salesmen.filter { salesman ->
                salesman.areas.any { it.isValidArea() && it.toInt() in range }
            }
        }
    }

    private fun parseAreaExpression(area: String): IntRange? {
        val areaTrimmed = area.trim()
        return when {
            areaTrimmed.endsWith(ASTERIX_SUFFIX) -> {
                val prefix = areaTrimmed.dropLast(1)
                if (!prefix.all { it.isDigit() }) return null
                val start = (prefix + "0".repeat(AREA_LENGTH - prefix.length)).toInt()
                val end = (prefix + "9".repeat(AREA_LENGTH - prefix.length)).toInt()
                start..end
            }
            areaTrimmed.length == AREA_LENGTH && areaTrimmed.all { it.isDigit() } -> {
                val code = areaTrimmed.toInt()
                code..code
            }
            else -> null
        }
    }

    private fun String.isValidArea() = length == AREA_LENGTH && all { it.isDigit() }

    private companion object {
        private const val AREA_LENGTH = 5
        private const val ASTERIX_SUFFIX = "*"
    }

}