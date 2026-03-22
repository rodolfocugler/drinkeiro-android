package com.drinkeiro.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drinkeiro.data.model.Pump
import com.drinkeiro.ui.components.*
import com.drinkeiro.ui.theme.DrinkeiroTheme
import com.drinkeiro.viewmodel.MachineViewModel

// ── Machine Settings Tab ──────────────────────────────────────────────────────

@Composable
fun MachineSettingsTab(vm: MachineViewModel) {
    val c  = DrinkeiroTheme.colors
    val ui by vm.ui.collectAsState()

    var showPumpEditor    by remember { mutableStateOf<Pump?>(null) }   // null=closed, Pump()=new, existing=edit
    var isNewPump         by remember { mutableStateOf(false) }
    var confirmDeletePump by remember { mutableStateOf<Int?>(null) }

    val usedNumbers = ui.pumps.map { it.pumpNumber }

    LazyColumn(
        contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier            = Modifier.fillMaxSize(),
    ) {
        // Machine status strip
        item {
            Surface(
                shape  = RoundedCornerShape(14.dp),
                color  = c.bg2,
                border = BorderStroke(1.dp, c.border),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatusDot(ui.activeMachine?.status == "online")
                        Column {
                            Text(ui.activeMachine?.name ?: "No machine", style = MaterialTheme.typography.titleLarge, color = c.cream)
                            Text(
                                if (ui.activeMachine?.status == "online") "Online" else "Offline",
                                style = MaterialTheme.typography.bodySmall, color = c.cream3,
                            )
                        }
                    }
                }
            }
        }

        // Pumps header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Column {
                    Text("Pump Configuration", style = MaterialTheme.typography.headlineSmall, color = c.cream)
                    Text("${ui.pumps.size}/8 configured", style = MaterialTheme.typography.bodySmall, color = c.cream3)
                }
                if (ui.pumps.size < 8) {
                    AmberChip(text = "+ Add", onClick = {
                        showPumpEditor = Pump(pumpNumber = 0, ingredient = "", flowRate = 8.0)
                        isNewPump = true
                    })
                }
            }
        }

        // 4×2 visual grid
        item {
            PumpGrid(
                pumps      = ui.pumps,
                onPumpTap  = { pump -> showPumpEditor = pump; isNewPump = false },
            )
        }

        // Pump list
        items(ui.pumps.sortedBy { it.pumpNumber }, key = { it.pumpNumber }) { pump ->
            PumpRow(
                pump         = pump,
                isTesting    = ui.testingPump == pump.pumpNumber,
                countdown    = ui.testCountdown,
                anyTesting   = ui.testingPump != null,
                onTest       = { vm.triggerPump(pump.pumpNumber) },
                onEdit       = { showPumpEditor = pump; isNewPump = false },
                onDelete     = { confirmDeletePump = pump.pumpNumber },
            )
        }
    }

    // ── Delete confirm ───────────────────────────────────────────────────────
    confirmDeletePump?.let { num ->
        AlertDialog(
            onDismissRequest = { confirmDeletePump = null },
            containerColor   = c.bg2,
            title = { Text("Delete Pump $num?", color = c.cream, style = MaterialTheme.typography.headlineSmall) },
            confirmButton = {
                TextButton(onClick = { vm.deletePump(num); confirmDeletePump = null }) {
                    Text("Delete", color = c.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDeletePump = null }) {
                    Text("Cancel", color = c.cream3)
                }
            },
        )
    }

    // ── Pump editor sheet ────────────────────────────────────────────────────
    showPumpEditor?.let { pump ->
        PumpEditorSheet(
            pump        = pump,
            isNew       = isNewPump,
            usedNumbers = if (isNewPump) usedNumbers else usedNumbers.filter { it != pump.pumpNumber },
            onSave      = { saved ->
                if (isNewPump) vm.createPump(saved) else vm.updatePump(saved)
                showPumpEditor = null
            },
            onDismiss   = { showPumpEditor = null },
        )
    }
}

// ── 4×2 pump grid ─────────────────────────────────────────────────────────────

@Composable
private fun PumpGrid(pumps: List<Pump>, onPumpTap: (Pump) -> Unit) {
    val c = DrinkeiroTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (row in 0..1) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (col in 1..4) {
                    val n    = row * 4 + col
                    val pump = pumps.firstOrNull { it.pumpNumber == n }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (pump != null) c.accentLo else c.bg2)
                            .border(
                                1.5.dp,
                                if (pump != null) c.accentMd else c.border,
                                RoundedCornerShape(14.dp),
                            )
                            .then(if (pump != null) Modifier.clickable { onPumpTap(pump) } else Modifier),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (pump != null) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("P$n", style = MaterialTheme.typography.labelSmall, color = c.accent, fontWeight = FontWeight.Bold)
                                Text(
                                    pump.ingredient.split(" ").take(2).joinToString(" "),
                                    style    = MaterialTheme.typography.labelSmall,
                                    color    = c.cream3,
                                    fontSize = 8.sp,
                                )
                                Text("${pump.flowRate}ml/s", style = MaterialTheme.typography.labelSmall, color = c.cream4, fontSize = 8.sp)
                            }
                        } else {
                            Text("P$n", style = MaterialTheme.typography.labelSmall, color = c.cream4)
                        }
                    }
                }
            }
        }
    }
}

// ── Individual pump row ───────────────────────────────────────────────────────

@Composable
private fun PumpRow(
    pump:       Pump,
    isTesting:  Boolean,
    countdown:  Int,
    anyTesting: Boolean,
    onTest:     () -> Unit,
    onEdit:     () -> Unit,
    onDelete:   () -> Unit,
) {
    val c = DrinkeiroTheme.colors
    Surface(
        shape  = RoundedCornerShape(16.dp),
        color  = c.bg2,
        border = BorderStroke(1.dp, c.border),
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(13.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // Pump badge
                Surface(
                    shape  = RoundedCornerShape(12.dp),
                    color  = c.accentLo,
                    border = BorderStroke(1.5.dp, c.accentMd),
                    modifier = Modifier.size(40.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("P${pump.pumpNumber}", style = MaterialTheme.typography.labelSmall, color = c.accent, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(pump.ingredient, style = MaterialTheme.typography.bodyMedium, color = c.cream, fontWeight = FontWeight.SemiBold)
                    Text("${pump.flowRate} ml/s", style = MaterialTheme.typography.bodySmall, color = c.cream3)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Test button
                    Surface(
                        onClick  = { if (!anyTesting) onTest() },
                        shape    = RoundedCornerShape(10.dp),
                        color    = if (isTesting) c.green.copy(alpha = 0.2f) else c.bg3,
                        border   = BorderStroke(1.5.dp, if (isTesting) c.green.copy(alpha = 0.4f) else c.border),
                        modifier = Modifier.size(34.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                if (isTesting) "$countdown" else "▶",
                                color    = if (isTesting) c.green else c.cream3,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    // Edit
                    Surface(
                        onClick  = onEdit,
                        shape    = RoundedCornerShape(10.dp),
                        color    = c.bg3,
                        border   = BorderStroke(1.5.dp, c.border),
                        modifier = Modifier.size(34.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) { Text("✎", color = c.cream3, fontSize = 15.sp) }
                    }
                    // Delete
                    Surface(
                        onClick  = onDelete,
                        shape    = RoundedCornerShape(10.dp),
                        color    = c.error.copy(alpha = 0.1f),
                        border   = BorderStroke(1.5.dp, c.error.copy(alpha = 0.3f)),
                        modifier = Modifier.size(34.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) { Text("×", color = c.error, fontSize = 17.sp) }
                    }
                }
            }
            // Testing progress bar
            if (isTesting) {
                Spacer(Modifier.height(10.dp))
                val progress = countdown / 10f
                LinearProgressIndicator(
                    progress         = { 1f - progress },
                    modifier         = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color            = c.green,
                    trackColor       = c.bg3,
                    strokeCap        = androidx.compose.ui.graphics.StrokeCap.Round,
                )
            }
        }
    }
}

// ── Machine Switcher Sheet ────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MachineSwitcherSheet(vm: MachineViewModel, onDismiss: () -> Unit) {
    val c          = DrinkeiroTheme.colors
    val ui         by vm.ui.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState       = sheetState,
        containerColor   = c.bg2,
        dragHandle       = { SheetHandle(modifier = Modifier.padding(top = 12.dp)) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("My Machines", style = MaterialTheme.typography.headlineSmall, color = c.cream)

            ui.machines.forEach { machine ->
                val isActive = machine.id == ui.activeMachine?.id
                Surface(
                    onClick = { vm.selectMachine(machine); onDismiss() },
                    shape   = RoundedCornerShape(16.dp),
                    color   = if (isActive) c.accentLo else c.bg3,
                    border  = BorderStroke(1.5.dp, if (isActive) c.accentMd else c.border),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        StatusDot(machine.status == "online")
                        Column(modifier = Modifier.weight(1f)) {
                            Text(machine.name, style = MaterialTheme.typography.bodyMedium, color = c.cream, fontWeight = FontWeight.SemiBold)
                            Text(
                                "${if (machine.status == "online") "Online" else "Offline"} · ${machine.collaborators.size + 1} users",
                                style = MaterialTheme.typography.bodySmall, color = c.cream3,
                            )
                        }
                        if (isActive) Text("✓", color = c.accent, fontSize = 18.sp)
                    }
                }
            }

            // Add new machine (stub)
            Surface(
                onClick = { /* TODO: navigate to new machine flow */ },
                shape   = RoundedCornerShape(16.dp),
                color   = c.bg3,
                border  = BorderStroke(1.5.dp, c.border),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("+ Add New Machine", color = c.cream3, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

// ── Pump Editor Sheet ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PumpEditorSheet(
    pump:        Pump,
    isNew:       Boolean,
    usedNumbers: List<Int>,
    onSave:      (Pump) -> Unit,
    onDismiss:   () -> Unit,
) {
    val c          = DrinkeiroTheme.colors
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val available = (1..8).filter { it !in usedNumbers || it == pump.pumpNumber }
    var pumpNumber  by remember { mutableStateOf(if (isNew) available.firstOrNull() ?: 1 else pump.pumpNumber) }
    var ingredient  by remember { mutableStateOf(pump.ingredient) }
    var flowRate    by remember { mutableStateOf(pump.flowRate.toFloat()) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState       = sheetState,
        containerColor   = c.bg2,
        dragHandle       = { SheetHandle(modifier = Modifier.padding(top = 12.dp)) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(if (isNew) "New Pump" else "Edit Pump", style = MaterialTheme.typography.headlineSmall, color = c.cream)

            // Pump number picker
            Column {
                SectionLabel("Pump Number")
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (1..8).forEach { n ->
                        val ok       = n in available
                        val selected = n == pumpNumber
                        Surface(
                            onClick  = { if (ok) pumpNumber = n },
                            shape    = RoundedCornerShape(12.dp),
                            color    = when { selected -> c.accentLo; ok -> c.bg3; else -> c.bg2 },
                            border   = BorderStroke(1.5.dp, if (selected) c.accentMd else c.border),
                            modifier = Modifier.size(44.dp),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "$n",
                                    color      = when { selected -> c.accent; ok -> c.cream2; else -> c.cream4 },
                                    fontWeight = FontWeight.Bold,
                                    fontSize   = 15.sp,
                                )
                            }
                        }
                    }
                }
            }

            // Ingredient name
            DrinkeiroTextField(
                value         = ingredient,
                onValueChange = { ingredient = it },
                label         = "Ingredient (English name)",
                placeholder   = "e.g. Vodka, Lime juice…",
            )

            // Flow rate slider
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    SectionLabel("Flow Rate")
                    Text("${flowRate.toInt()} ml/s", color = c.accent, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Slider(
                    value         = flowRate,
                    onValueChange = { flowRate = it },
                    valueRange    = 1f..30f,
                    steps         = 28,
                    colors        = SliderDefaults.colors(
                        thumbColor       = c.accent,
                        activeTrackColor = c.accent,
                    ),
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("1 ml/s", style = MaterialTheme.typography.labelSmall, color = c.cream4)
                    Text("30 ml/s", style = MaterialTheme.typography.labelSmall, color = c.cream4)
                }
            }

            // Action buttons
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GhostButton(text = "Cancel", onClick = onDismiss, modifier = Modifier.weight(1f))
                DrinkeiroButton(
                    text     = if (isNew) "Create Pump" else "Save Changes",
                    enabled  = ingredient.isNotBlank(),
                    onClick  = {
                        onSave(Pump(pumpNumber = pumpNumber, ingredient = ingredient.trim(), flowRate = flowRate.toDouble()))
                    },
                    modifier = Modifier.weight(2f),
                )
            }
        }
    }
}
