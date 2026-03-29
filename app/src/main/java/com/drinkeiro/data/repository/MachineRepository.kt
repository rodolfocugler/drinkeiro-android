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
    private val localHistory = mutableListOf<HistoryEntry>()

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
                // Replace temp local entry with real one from server
                val idx = localMachines.indexOfFirst { it.id == temp.id }
                if (idx >= 0) localMachines[idx] = created
                Result.success(created)
            } else {
                Result.success(temp)
            }
        } catch (e: Exception) { Result.success(temp) }
    }

    suspend fun deleteMachine(machineId: String): Result<Unit> {
        localMachines.removeAll { it.id == machineId }
        return try {
            api.deleteMachine(machineId)
            Result.success(Unit)
        } catch (e: Exception) { Result.success(Unit) }
    }

    suspend fun brew(machineId: String, request: BrewRequest): Result<BrewResponse> {
        // Log locally regardless of backend
        val entry = HistoryEntry(
            id = "local_${System.currentTimeMillis()}",
            idDrink = request.idDrink,
            strDrink = request.strIngredient.firstOrNull()?.strIngredient ?: request.idDrink,
            ts = "Just now",
            user = "You",
        )
        localHistory.add(0, entry)
        return try {
            val r = api.brew(machineId, request)
            if (r.isSuccessful) Result.success(r.body()!!)
            else Result.success(BrewResponse(success = true, message = "Brewing (offline)"))
        } catch (e: Exception) {
            Result.success(BrewResponse(success = true, message = "Brewing (offline)"))
        }
    }

    suspend fun getHistory(machineId: String): Result<List<HistoryEntry>> {
        return try {
            val r = api.getHistory(machineId)
            if (r.isSuccessful) {
                val list = r.body()!!.content
                localHistory.clear(); localHistory.addAll(list)
                Result.success(localHistory.toList())
            } else {
                Result.success(localHistory.toList())
            }
        } catch (e: Exception) {
            Result.success(localHistory.toList())
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
        localPumps.sortBy { it.pumpNumber }
        return try {
            val r = api.createPump(machineId, pump)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(pump)
        } catch (e: Exception) { Result.success(pump) }
    }

    suspend fun updatePump(machineId: String, pump: Pump): Result<Pump> {
        val idx = localPumps.indexOfFirst { it.pumpNumber == pump.pumpNumber }
        if (idx >= 0) localPumps[idx] = pump
        return try {
            val r = api.updatePump(machineId, pump.pumpNumber, pump)
            if (r.isSuccessful) Result.success(r.body()!!) else Result.success(pump)
        } catch (e: Exception) { Result.success(pump) }
    }

    suspend fun deletePump(machineId: String, pumpNumber: Int): Result<Unit> {
        localPumps.removeAll { it.pumpNumber == pumpNumber }
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
