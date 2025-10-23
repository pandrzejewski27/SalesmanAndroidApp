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
            if (area.isNullOrBlank()) return@map salesmen
            salesmen.filter { salesman ->
                salesman.areas.any { it == area }
            }
        }
    }
}