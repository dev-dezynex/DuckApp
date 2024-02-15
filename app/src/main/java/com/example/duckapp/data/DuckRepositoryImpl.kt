package com.example.duckapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class DuckRepositoryImpl @Inject constructor(
    private val api: DuckApi
) : DuckRepository {
    override suspend fun getDuck(): Flow<Result<Duck>> {

        return flow {
            val duckFromApi = try {
                api.getDuck()
            } catch (e: IOException) {
                e.printStackTrace()
                println("Error ${e.message}")
                emit(Result.Error(message = "Error loading duck"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                println("Error ${e.message}")
                emit(Result.Error(message = "Error loading duck"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error ${e.message}")
                emit(Result.Error(message = "Error loading duck"))
                return@flow
            }

            emit(Result.Success(duckFromApi))
        }
    }
}