package com.magappes.salesmanandroidapp.data.repository

import com.magappes.salesmanandroidapp.data.model.Salesman
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface SalesmenRepository {
    suspend fun getSalesmen(): Flow<List<Salesman>>
}

class SalesmenRepositoryImpl @Inject constructor() : SalesmenRepository {
    val salesmen = listOf(
        Salesman(
            name = "Artem Titarenko",
            areas = listOf(
                "76133"
            )
        ),
        Salesman(
            name = "Bernd Schmitt",
            areas = listOf(
                "76196"
            )
        ),
        Salesman(
            name = "Chris Krapp",
            areas = listOf(
                "76254"
            )
        ),
        Salesman(
            name = "Alex Uber",
            areas = listOf(
                "86345"
            )
        )
    )

    override suspend fun getSalesmen(): Flow<List<Salesman>> {
        return flowOf(salesmen)
    }

}