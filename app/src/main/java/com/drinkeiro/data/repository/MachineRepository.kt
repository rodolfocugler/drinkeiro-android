package com.drinkeiro.data.repository

import android.util.Log
import com.drinkeiro.data.api.DrinkeiroApi
import com.drinkeiro.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "MachineRepo"

@Singleton
class MachineRepository @Inject constructor(
    private val api: DrinkeiroApi,
) {
    private val localMachines = mutableListOf<Machine>()
    private val localPumps = mutableListOf<Pump>()
    private val localHistory = mutableListOf<HistoryEntity>()

    suspend fun getMachines(): Result<List<Machine>> {
        return try {
            val r = api.getMachines()
            if (r.isSuccessful) {
                localMachines.clear(); localMachines.addAll(r.body()!!.content)
                Result.success(localMachines.toList())
            } else {
                Log.w(TAG, "getMachines HTTP ${r.code()} — using local")
                Result.success(localMachines.toList())
            }
        } catch (e: Exception) {
            Log.w(TAG, "getMachines offline: ${e.message}")
            Result.success(localMachines.toList())
        }
    }

    suspend fun createMachine(machine: Machine): Result<Machine> {
        val temp = machine.copy(id = "local_${System.currentTimeMillis()}")
        localMachines.add(temp)
        return try {
            val r = api.createMachine(machine)
            if (r.isSuccessful) {
                val created = r.body()!!
                val idx = localMachines.indexOfFirst { it.id == temp.id }
                if (idx >= 0) localMachines[idx] = created
                Result.success(created)
            } else {
                Result.success(temp)
            }
        } catch (e: Exception) { Result.success(temp) }
    }

    suspend fun updateMachine(machine: Machine): Result<Machine> {
        val idx = localMachines.indexOfFirst { it.id == machine.id }
        if (idx >= 0) localMachines[idx] = machine
        return try {
            val r = api.updateMachine(machine.id, machine)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(machine)
        } catch (e: Exception) { Result.success(machine) }
    }

    suspend fun deleteMachine(machineId: String): Result<Unit> {
        localMachines.removeAll { it.id == machineId }
        return try {
            api.deleteMachine(machineId)
            Result.success(Unit)
        } catch (e: Exception) { Result.success(Unit) }
    }

    suspend fun brew(machineId: String, request: BrewRequest): Result<Unit> {
        // Log locally regardless of backend
        val entry = HistoryEntity(
            id = "local_${System.currentTimeMillis()}",
            machineId = machineId,
            timestamp = System.currentTimeMillis(),
            historyEntries = request.strIngredient.map {
                HistoryEntry(
                    ingredientName = it.strIngredient,
                    name = it.strIngredient,
                    port = -1,
                    second = -1.0,
                    flowRateInMlPerSec = -1.0
                )
            }
        )
        localHistory.add(0, entry)
        return try {
            val r = api.brew(machineId, request)
            if (r.isSuccessful) Result.success(Unit)
            else Result.success(Unit)
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }

    suspend fun getHistory(
        machineId: String,
        page:      Int = 0,
        pageSize:  Int = 20,
    ): Result<List<HistoryEntity>> {
        return try {
            val r = api.getHistory(machineId, page = page, pageSize = pageSize)
            if (r.isSuccessful) {
                val list = r.body()!!.content
                if (page == 0) { localHistory.clear(); localHistory.addAll(list) }
                else localHistory.addAll(list)
                Result.success(list)
            } else {
                val from = page * pageSize
                Result.success(if (from >= localHistory.size) emptyList()
                               else localHistory.subList(from, minOf(from + pageSize, localHistory.size)))
            }
        } catch (e: Exception) {
            val from = page * pageSize
            Result.success(if (from >= localHistory.size) emptyList()
                           else localHistory.subList(from, minOf(from + pageSize, localHistory.size)))
        }
    }

    suspend fun getPumps(machineId: String): Result<List<Pump>> {
        return try {
            val r = api.getPumps(machineId)
            if (r.isSuccessful) {
                val list = r.body()!!
                localPumps.clear(); localPumps.addAll(list)
                Result.success(localPumps.toList())
            } else {
                Result.success(localPumps.toList())
            }
        } catch (e: Exception) {
            Result.success(localPumps.toList())
        }
    }

    suspend fun createPump(machineId: String, pump: Pump): Result<Pump> {
        localPumps.add(pump)
        localPumps.sortBy { it.port }
        return try {
            val r = api.createPump(machineId, pump)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(pump)
        } catch (e: Exception) { Result.success(pump) }
    }

    suspend fun updatePump(machineId: String, pump: Pump): Result<Pump> {
        val idx = localPumps.indexOfFirst { it.port == pump.port }
        if (idx >= 0) localPumps[idx] = pump
        return try {
            val r = api.updatePump(machineId, pump.port, pump)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(pump)
        } catch (e: Exception) { Result.success(pump) }
    }

    suspend fun deletePump(machineId: String, pumpNumber: Int): Result<Unit> {
        localPumps.removeAll { it.port == pumpNumber }
        return try {
            val r = api.deletePump(machineId, pumpNumber)
            if (r.isSuccessful) Result.success(Unit) else Result.success(Unit)
        } catch (e: Exception) { Result.success(Unit) }
    }

    suspend fun triggerPump(machineId: String, pumpNumber: Int): Result<Unit> {
        return try {
            val r = api.triggerPump(machineId, pumpNumber)
            if (r.isSuccessful) Result.success(Unit) else Result.success(Unit)
        } catch (e: Exception) { Result.success(Unit) }
    }

    suspend fun addCollaborator(machineId: String, email: String): Result<Machine> {
        val machine = localMachines.find { it.id == machineId }
            ?: return Result.failure(Exception("Machine not found"))
        val updated = machine.copy(collaborators = machine.collaborators + email)
        val idx = localMachines.indexOfFirst { it.id == machineId }
        if (idx >= 0) localMachines[idx] = updated
        return try {
            val r = api.addCollaborator(machineId, email)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(updated)
        } catch (e: Exception) { Result.success(updated) }
    }

    suspend fun removeCollaborator(machineId: String, email: String): Result<Machine> {
        val machine = localMachines.find { it.id == machineId }
            ?: return Result.failure(Exception("Machine not found"))
        val updated = machine.copy(collaborators = machine.collaborators - email)
        val idx = localMachines.indexOfFirst { it.id == machineId }
        if (idx >= 0) localMachines[idx] = updated
        return try {
            val r = api.removeCollaborator(machineId, email)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(updated)
        } catch (e: Exception) { Result.success(updated) }
    }
}
