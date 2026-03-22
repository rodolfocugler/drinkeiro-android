package com.drinkeiro.data.repository

import com.drinkeiro.data.api.DrinkeiroApi
import com.drinkeiro.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MachineRepository @Inject constructor(
    private val api: DrinkeiroApi,
) {
    suspend fun getMachines(): Result<List<Machine>> = runCatching {
        val r = api.getMachines()
        if (r.isSuccessful) r.body()!!.data
        else error("HTTP ${r.code()}")
    }

    suspend fun brew(machineId: String, request: BrewRequest): Result<BrewResponse> = runCatching {
        val r = api.brew(machineId, request)
        if (r.isSuccessful) r.body()!!
        else error("HTTP ${r.code()}")
    }

    suspend fun getHistory(machineId: String): Result<List<HistoryEntry>> = runCatching {
        val r = api.getHistory(machineId)
        if (r.isSuccessful) r.body()!!.data
        else error("HTTP ${r.code()}")
    }

    suspend fun getPumps(machineId: String): Result<List<Pump>> = runCatching {
        val r = api.getPumps(machineId)
        if (r.isSuccessful) r.body()!!.data
        else error("HTTP ${r.code()}")
    }

    suspend fun createPump(machineId: String, pump: Pump): Result<Pump> = runCatching {
        val r = api.createPump(machineId, pump)
        if (r.isSuccessful) r.body()!!
        else error("HTTP ${r.code()}")
    }

    suspend fun updatePump(machineId: String, pump: Pump): Result<Pump> = runCatching {
        val r = api.updatePump(machineId, pump.pumpNumber, pump)
        if (r.isSuccessful) r.body()!!
        else error("HTTP ${r.code()}")
    }

    suspend fun deletePump(machineId: String, pumpNumber: Int): Result<Unit> = runCatching {
        val r = api.deletePump(machineId, pumpNumber)
        if (!r.isSuccessful) error("HTTP ${r.code()}")
    }

    suspend fun triggerPump(machineId: String, pumpNumber: Int): Result<Unit> = runCatching {
        val r = api.triggerPump(machineId, pumpNumber)
        if (!r.isSuccessful) error("HTTP ${r.code()}")
    }

    suspend fun addCollaborator(machineId: String, email: String): Result<Machine> = runCatching {
        val r = api.addCollaborator(machineId, email)
        if (r.isSuccessful) r.body()!!
        else error("HTTP ${r.code()}")
    }

    suspend fun removeCollaborator(machineId: String, email: String): Result<Machine> = runCatching {
        val r = api.removeCollaborator(machineId, email)
        if (r.isSuccessful) r.body()!!
        else error("HTTP ${r.code()}")
    }
}
