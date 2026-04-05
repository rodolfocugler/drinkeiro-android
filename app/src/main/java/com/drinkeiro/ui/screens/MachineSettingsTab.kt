package com.drinkeiro.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drinkeiro.data.model.Pump
import com.drinkeiro.ui.components.*
import com.drinkeiro.ui.theme.DrinkeiroTheme
import com.drinkeiro.viewmodel.MachineViewModel
import kotlin.math.ceil
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MachineSettingsTab(vm: MachineViewModel) {
    val c = DrinkeiroTheme.colors
    val ui by vm.ui.collectAsState()

    var showPumpEditor by remember { mutableStateOf<Pump?>(null) }
    var isNewPump by remember { mutableStateOf(false) }
    var confirmDeletePump by remember { mutableStateOf<Int?>(null) }
    var showRenameMachine by remember { mutableStateOf(false) }
    var confirmDeleteMachine by remember { mutableStateOf(false) }

    val usedNumbers = ui.pumps.map { it.port }
    val listState = rememberLazyListState()

    PullToRefreshBox(
        isRefreshing = ui.isRefreshing,
        onRefresh = vm::refresh,
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            // ── Machine card ─────────────────────────────────────────────────
            item {
                MachineCard(
                    vm = vm,
                    onRename = { showRenameMachine = true },
                    onCollaborators = { vm.showCollaboratorsDialog() },
                    onDelete = { confirmDeleteMachine = true },
                    onCreateNew = { vm.showCreateMachineDialog() },
                )
            }

            // ── Pumps header ─────────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            "Pump Configuration",
                            style = MaterialTheme.typography.headlineSmall,
                            color = c.cream
                        )
                        Text(
                            "${ui.pumps.size}/8 configured",
                            style = MaterialTheme.typography.bodySmall,
                            color = c.cream3
                        )
                    }
                    if (ui.pumps.size < 8) {
                        AmberChip(text = "+ Add", onClick = {
                            showPumpEditor = Pump(port = 0, name = "", ingredientId = "", flowRateInMlPerSec = 8.0)
                            isNewPump = true
                        })
                    }
                }
            }

            // ── 4×2 visual grid ──────────────────────────────────────────────
            item {
                PumpGrid(
                    pumps = ui.pumps,
                    onPumpTap = { pump -> showPumpEditor = pump; isNewPump = false },
                )
            }

            // ── Pump list ────────────────────────────────────────────────────
            items(ui.pumps.sortedBy { it.port }, key = { it.port }) { pump ->
                PumpRow(
                    pump = pump,
                    isTesting = ui.testingPump == pump.port,
                    countdown = ui.testCountdown,
                    anyTesting = ui.testingPump != null,
                    onTest = { vm.triggerPump(pump.port) },
                    onEdit = { showPumpEditor = pump; isNewPump = false },
                    onDelete = { confirmDeletePump = pump.port },
                )
            }
        }
    }

    // ── Delete pump confirm ───────────────────────────────────────────────────
    confirmDeletePump?.let { num ->
        AlertDialog(
            onDismissRequest = { confirmDeletePump = null },
            containerColor = c.bg2,
            title = {
                Text(
                    "Delete Pump $num?",
                    color = c.cream,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            confirmButton = {
                TextButton(onClick = { vm.deletePump(num); confirmDeletePump = null }) {
                    Text("Delete", color = c.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDeletePump = null }) {
                    Text(
                        "Cancel",
                        color = c.cream3
                    )
                }
            },
        )
    }

    // ── Delete machine confirm ────────────────────────────────────────────────
    if (confirmDeleteMachine) {
        AlertDialog(
            onDismissRequest = { confirmDeleteMachine = false },
            containerColor = c.bg2,
            title = {
                Text(
                    "Delete \"${ui.activeMachine?.name}\"?",
                    color = c.cream,
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            text = {
                Text(
                    "This will remove the machine and all its configuration.",
                    color = c.cream2
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    ui.activeMachine?.id?.let { vm.deleteMachine(it) }
                    confirmDeleteMachine = false
                }) {
                    Text("Delete", color = c.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDeleteMachine = false }) {
                    Text(
                        "Cancel",
                        color = c.cream3
                    )
                }
            },
        )
    }

    // ── Pump editor ───────────────────────────────────────────────────────────
    showPumpEditor?.let { pump ->
        PumpEditorSheet(
            pump = pump,
            isNew = isNewPump,
            usedNumbers = if (isNewPump) usedNumbers else usedNumbers.filter { it != pump.port },
            onSave = { saved ->
                if (isNewPump) vm.createPump(saved) else vm.updatePump(saved)
                showPumpEditor = null
            },
            onDismiss = { showPumpEditor = null },
        )
    }

    // ── Rename machine ────────────────────────────────────────────────────────
    if (showRenameMachine && ui.activeMachine != null) {
        RenameMachineDialog(
            currentName = ui.activeMachine!!.name,
            onConfirm = { newName ->
                vm.updateMachine(ui.activeMachine!!.copy(name = newName))
                showRenameMachine = false
            },
            onDismiss = { showRenameMachine = false },
        )
    }
}

// ── Machine card — full-width card with action rows ───────────────────────────

@Composable
private fun MachineCard(
    vm: MachineViewModel,
    onRename: () -> Unit,
    onCollaborators: () -> Unit,
    onDelete: () -> Unit,
    onCreateNew: () -> Unit,
) {
    val c = DrinkeiroTheme.colors
    val ui by vm.ui.collectAsState()

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = c.bg2,
        border = BorderStroke(1.dp, c.border),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // ── Header row: status + name ────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                StatusDot(ui.activeMachine?.status == "online")
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        ui.activeMachine?.name ?: "No machine",
                        style = MaterialTheme.typography.titleLarge,
                        color = c.cream,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        buildString {
                            append(if (ui.activeMachine?.status == "online") "Online" else "Offline")
                            val colabs = ui.activeMachine?.collaborators?.size ?: 0
                            if (colabs > 0) append(" · $colabs collaborator${if (colabs != 1) "s" else ""}")
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = c.cream3,
                    )
                }
            }

            HorizontalDivider(color = c.border, thickness = 0.5.dp)

            // ── Action grid: 2×2 ────────────────────────────────────────────
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MachineActionButton(
                        label = "Rename",
                        icon = { Icon(Icons.Outlined.Edit, null, modifier = Modifier.size(16.dp)) },
                        onClick = onRename,
                        modifier = Modifier.weight(1f),
                    )
                    MachineActionButton(
                        label = "Collaborators",
                        icon = {
                            Icon(
                                Icons.Outlined.Group,
                                null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = onCollaborators,
                        modifier = Modifier.weight(1f),
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MachineActionButton(
                        label = "New Machine",
                        icon = { Icon(Icons.Outlined.Add, null, modifier = Modifier.size(16.dp)) },
                        onClick = onCreateNew,
                        modifier = Modifier.weight(1f),
                    )
                    MachineActionButton(
                        label = "Delete",
                        icon = {
                            Icon(
                                Icons.Outlined.Delete,
                                null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = onDelete,
                        isDestructive = true,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            // ── Other machines quick-switch ──────────────────────────────────
            if (ui.machines.size > 1) {
                HorizontalDivider(color = c.border, thickness = 0.5.dp)
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Text(
                        "Switch Machine",
                        style = MaterialTheme.typography.labelSmall,
                        color = c.cream3,
                        modifier = Modifier.padding(bottom = 6.dp),
                    )
                    ui.machines.filter { it.id != ui.activeMachine?.id }.forEach { machine ->
                        Surface(
                            onClick = { vm.selectMachine(machine) },
                            shape = RoundedCornerShape(10.dp),
                            color = c.bg3,
                            border = BorderStroke(1.dp, c.border),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                StatusDot(machine.status == "online")
                                Text(
                                    machine.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = c.cream2,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "Switch →",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = c.accent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MachineActionButton(
    label: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDestructive: Boolean = false,
) {
    val c = DrinkeiroTheme.colors
    val fgColor = if (isDestructive) c.error else c.cream2
    val bgColor = if (isDestructive) c.error.copy(alpha = 0.1f) else c.bg3
    val border = if (isDestructive) c.error.copy(alpha = 0.3f) else c.border

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        border = BorderStroke(1.dp, border),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            androidx.compose.runtime.CompositionLocalProvider(
                androidx.compose.material3.LocalContentColor provides fgColor
            ) { icon() }
            Spacer(Modifier.width(6.dp))
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = fgColor,
                fontWeight = FontWeight.SemiBold
            )
        }
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
                    val n = row * 4 + col
                    val pump = pumps.firstOrNull { it.port == n }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (pump != null) c.accentLo else c.bg2)
                            .border(
                                1.5.dp,
                                if (pump != null) c.accentMd else c.border,
                                RoundedCornerShape(14.dp)
                            )
                            .then(if (pump != null) Modifier.clickable { onPumpTap(pump) } else Modifier),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (pump != null) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Text(
                                    "P$n",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = c.accent,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    pump.name.split(" ").take(2).joinToString(" "),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = c.cream3,
                                    fontSize = 8.sp
                                )
                                Text(
                                    "${pump.flowRateInMlPerSec}ml/s",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = c.cream4,
                                    fontSize = 8.sp
                                )
                            }
                        } else {
                            Text(
                                "P$n",
                                style = MaterialTheme.typography.labelSmall,
                                color = c.cream4
                            )
                        }
                    }
                }
            }
        }
    }
}

// ── Pump row ──────────────────────────────────────────────────────────────────

@Composable
private fun PumpRow(
    pump: Pump, isTesting: Boolean, countdown: Int,
    anyTesting: Boolean, onTest: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit,
) {
    val c = DrinkeiroTheme.colors
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = c.bg2,
        border = BorderStroke(1.dp, c.border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = c.accentLo,
                    border = BorderStroke(1.5.dp, c.accentMd),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            "P${pump.port}",
                            style = MaterialTheme.typography.labelSmall,
                            color = c.accent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        pump.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = c.cream,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "${pump.flowRateInMlPerSec} ml/s",
                        style = MaterialTheme.typography.bodySmall,
                        color = c.cream3
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        onClick = { if (!anyTesting) onTest() },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isTesting) c.green.copy(alpha = 0.2f) else c.bg3,
                        border = BorderStroke(
                            1.5.dp,
                            if (isTesting) c.green.copy(alpha = 0.4f) else c.border
                        ),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                if (isTesting) "$countdown" else "▶",
                                color = if (isTesting) c.green else c.cream3,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Surface(
                        onClick = onEdit,
                        shape = RoundedCornerShape(10.dp),
                        color = c.bg3,
                        border = BorderStroke(1.5.dp, c.border),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                "✎",
                                color = c.cream3,
                                fontSize = 15.sp
                            )
                        }
                    }
                    Surface(
                        onClick = onDelete,
                        shape = RoundedCornerShape(10.dp),
                        color = c.error.copy(alpha = 0.1f),
                        border = BorderStroke(1.5.dp, c.error.copy(alpha = 0.3f)),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                "×",
                                color = c.error,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
            if (isTesting) {
                Spacer(Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { 1f - countdown / 10f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = c.green,
                    trackColor = c.bg3,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
                )
            }
        }
    }
}

// ── Machine Switcher Sheet ────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MachineSwitcherSheet(vm: MachineViewModel, onDismiss: () -> Unit) {
    val c = DrinkeiroTheme.colors
    val ui by vm.ui.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = c.bg2,
        dragHandle = { SheetHandle(modifier = Modifier.padding(top = 12.dp)) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("My Machines", style = MaterialTheme.typography.headlineSmall, color = c.cream)
            ui.machines.forEach { machine ->
                val isActive = machine.id == ui.activeMachine?.id
                Surface(
                    onClick = { vm.selectMachine(machine); onDismiss() },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isActive) c.accentLo else c.bg3,
                    border = BorderStroke(1.5.dp, if (isActive) c.accentMd else c.border)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        StatusDot(machine.status == "online")
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                machine.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = c.cream,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "${if (machine.status == "online") "Online" else "Offline"} · ${machine.collaborators.size} users",
                                style = MaterialTheme.typography.bodySmall,
                                color = c.cream3
                            )
                        }
                        if (isActive) Text("✓", color = c.accent, fontSize = 18.sp)
                    }
                }
            }
            Surface(
                onClick = { vm.showCreateMachineDialog(); onDismiss() },
                shape = RoundedCornerShape(16.dp),
                color = c.bg3,
                border = BorderStroke(1.5.dp, c.border)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "+ New Machine",
                        color = c.cream2,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ── Pump Editor Sheet ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PumpEditorSheet(
    pump: Pump,
    isNew: Boolean,
    usedNumbers: List<Int>,
    onSave: (Pump) -> Unit,
    onDismiss: () -> Unit
) {
    val c = DrinkeiroTheme.colors
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val available = (1..8).filter { it !in usedNumbers || it == pump.port }
    var pumpNumber by remember {
        mutableStateOf(
            if (isNew) available.firstOrNull() ?: 1 else pump.port
        )
    }
    var ingredient by remember { mutableStateOf(pump.ingredientId) }
    var flowRate by remember { mutableStateOf(pump.flowRateInMlPerSec.toFloat()) }
    var name by remember { mutableStateOf(pump.name) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = c.bg2,
        dragHandle = { SheetHandle(modifier = Modifier.padding(top = 12.dp)) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                if (isNew) "New Pump" else "Edit Pump",
                style = MaterialTheme.typography.headlineSmall,
                color = c.cream
            )
            Column {
                SectionLabel("Pump Number"); Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (1..8).forEach { n ->
                        val ok = n in available;
                        val sel = n == pumpNumber
                        Surface(
                            onClick = { if (ok) pumpNumber = n },
                            shape = RoundedCornerShape(12.dp),
                            color = when {
                                sel -> c.accentLo; ok -> c.bg3; else -> c.bg2
                            },
                            border = BorderStroke(1.5.dp, if (sel) c.accentMd else c.border),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "$n", color = when {
                                        sel -> c.accent; ok -> c.cream2; else -> c.cream4
                                    }, fontWeight = FontWeight.Bold, fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }

            // Nickname
            DrinkeiroTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name (Nickname)",
                placeholder = "e.g. Vodka, Lime juice…",
            )

            // Ingredient name
            DrinkeiroTextField(
                value = ingredient,
                onValueChange = { ingredient = it },
                label = "Ingredient (English name)",
                placeholder = "e.g. Vodka, Lime juice…"
            )
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SectionLabel("Flow Rate")
                    Text(
                        "${flowRate.toInt()} ml/s",
                        color = c.accent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                Slider(
                    value = flowRate,
                    onValueChange = { flowRate = it },
                    valueRange = 1f..30f,
                    steps = 28,
                    colors = SliderDefaults.colors(
                        thumbColor = c.accent,
                        activeTrackColor = c.accent
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("1 ml/s", style = MaterialTheme.typography.labelSmall, color = c.cream4)
                    Text("30 ml/s", style = MaterialTheme.typography.labelSmall, color = c.cream4)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GhostButton(text = "Cancel", onClick = onDismiss, modifier = Modifier.weight(1f))
                DrinkeiroButton(
                    text = if (isNew) "Create Pump" else "Save Changes",
                    enabled = ingredient.isNotBlank(),
                    onClick = {
                        onSave(
                            Pump(
                                port = pumpNumber,
                                name = name.trim(),
                                ingredientId = ingredient.trim(),
                                flowRateInMlPerSec = round(flowRate.toDouble())
                            )
                        )
                    },
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}

// ── Rename Machine Dialog ─────────────────────────────────────────────────────

@Composable
private fun RenameMachineDialog(
    currentName: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val c = DrinkeiroTheme.colors
    var name by remember { mutableStateOf(currentName) }
    AlertDialog(
        onDismissRequest = onDismiss, containerColor = c.bg2,
        title = {
            Text(
                "Rename Machine",
                color = c.cream,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            OutlinedTextField(
                value = name, onValueChange = { name = it }, singleLine = true,
                shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = c.accent,
                    unfocusedBorderColor = c.border,
                    focusedTextColor = c.cream,
                    unfocusedTextColor = c.cream,
                    cursorColor = c.accent,
                    focusedContainerColor = c.bg3,
                    unfocusedContainerColor = c.bg3
                ),
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank()) onConfirm(name.trim()) },
                enabled = name.isNotBlank() && name.trim() != currentName
            ) { Text("Save", color = c.accent, fontWeight = FontWeight.Bold) }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = c.cream3) } },
    )
}
