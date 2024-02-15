package com.example.duckapp.data

import kotlinx.coroutines.flow.Flow

interface DuckRepository {
    suspend fun getDuck(): Flow<Result<Duck>>
}