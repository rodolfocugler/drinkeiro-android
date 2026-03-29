package com.drinkeiro.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drinkeiro.ui.components.*
import com.drinkeiro.ui.theme.DrinkeiroTheme
import com.drinkeiro.viewmodel.CocktailViewModel
import com.drinkeiro.viewmodel.MachineViewModel

private enum class Tab(val icon: String, val label: String) {
    Favorites("★", "Favorites"),
    Cocktails("◈", "Cocktails"),
    History("⏱", "History"),
    Machine("⚙", "Machine"),
}

@Composable
fun MainScreen(
    onLogout:   () -> Unit,
    cocktailVm: CocktailViewModel = hiltViewModel(),
    machineVm:  MachineViewModel  = hiltViewModel(),
) {
    val c         = DrinkeiroTheme.colors
    var activeTab by remember { mutableStateOf(Tab.Favorites) }
    val machineUi by machineVm.ui.collectAsState()
    val cocktailUi by cocktailVm.ui.collectAsState()

    Scaffold(
        containerColor = c.bg0,
        bottomBar = {
            Column {
                HorizontalDivider(color = c.border, thickness = 0.5.dp)
                NavigationBar(
                    containerColor  = c.bg0,
                    contentColor    = c.cream3,
                    tonalElevation  = 0.dp,
                    modifier        = Modifier.navigationBarsPadding(),
                ) {
                    Tab.entries.forEach { tab ->
                        val selected = tab == activeTab
                        NavigationBarItem(
                            selected = selected,
                            onClick  = { activeTab = tab },
                            icon = {
                                Surface(
                                    shape    = RoundedCornerShape(13.dp),
                                    color    = if (selected) c.accentLo else c.bg0,
                                    modifier = Modifier.size(38.dp),
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            tab.icon,
                                            fontSize = 18.sp,
                                            color    = if (selected) c.accent else c.cream3,
                                        )
                                    }
                                }
                            },
                            label = {
                                Text(
                                    text  = tab.label.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (selected) c.accent else c.cream3,
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = c.bg0,
                            ),
                        )
                    }
                }
            }
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = padding.calculateBottomPadding())
            ) {
                // ── Header ──────────────────────────────────────────────────
                AppHeader(
                    machineVm  = machineVm,
                    activeTab  = activeTab,
                    cocktailVm = cocktailVm,
                    onLogout   = onLogout,
                )

                // ── Tab content ─────────────────────────────────────────────
                Box(modifier = Modifier.weight(1f)) {
                    when (activeTab) {
                        Tab.Favorites -> FavoritesTab(cocktailVm, machineVm)
                        Tab.Cocktails -> CocktailsTab(cocktailVm, machineVm)
                        Tab.History   -> HistoryTab(machineVm, cocktailVm)
                        Tab.Machine   -> MachineSettingsTab(machineVm)
                    }
                }
            }

            // ── Brew toast ───────────────────────────────────────────────────
            AnimatedVisibility(
                visible  = machineUi.toastMessage != null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = padding.calculateBottomPadding() + 12.dp),
                enter    = slideInVertically { it } + fadeIn(),
                exit     = slideOutVertically { it } + fadeOut(),
            ) {
                machineUi.toastMessage?.let { msg ->
                    LaunchedEffect(msg) {
                        kotlinx.coroutines.delay(3_000)
                        machineVm.dismissToast()
                    }
                    BrewToast(message = msg)
                }
            }
        }
    }

    // ── Dialogs driven by MachineViewModel ───────────────────────────────────
    if (machineUi.showCreateMachine) {
        CreateMachineDialog(
            onConfirm = { name -> machineVm.createMachine(name) },
            onDismiss = { machineVm.hideCreateMachineDialog() },
        )
    }

    if (machineUi.showCollaborators) {
        CollaboratorsSheet(
            machine          = machineUi.activeMachine,
            onAdd            = { email -> machineVm.addCollaborator(email) },
            onRemove         = { email -> machineVm.removeCollaborator(email) },
            onDismiss        = { machineVm.hideCollaboratorsDialog() },
        )
    }
}

// ── App Header ────────────────────────────────────────────────────────────────

@Composable
private fun AppHeader(
    machineVm:  MachineViewModel,
    activeTab:  Tab,
    cocktailVm: CocktailViewModel,
    onLogout:   () -> Unit,
) {
    val c         = DrinkeiroTheme.colors
    val machineUi by machineVm.ui.collectAsState()
    var showMachinePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(c.bg0)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 12.dp),
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            // Machine selector
            Column(modifier = Modifier.clickable { showMachinePicker = true }) {
                Text(
                    text  = "ACTIVE MACHINE",
                    style = MaterialTheme.typography.labelSmall,
                    color = c.cream3,
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    StatusDot(machineUi.activeMachine?.status == "online")
                    Text(
                        text  = machineUi.activeMachine?.name ?: "No machine",
                        style = MaterialTheme.typography.headlineSmall,
                        color = c.cream,
                    )
                    Text("▾", color = c.cream3, fontSize = 11.sp)
                }
            }

            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // + Recipe button on cocktails tab
                if (activeTab == Tab.Cocktails) {
                    AmberChip(
                        text    = "+ Recipe",
                        onClick = { cocktailVm.requestCreateRecipe() },
                    )
                }

                Text(
                    text       = "Drinkeiro",
                    fontSize   = 15.sp,
                    fontStyle  = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    color      = c.accent,
                )

                // Logout button
                IconButton(
                    onClick  = {
                        machineVm.logout()
                        onLogout()
                    },
                    modifier = Modifier.size(34.dp),
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.ExitToApp,
                        contentDescription = "Logout",
                        tint               = c.cream3,
                        modifier           = Modifier.size(20.dp),
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        HorizontalDivider(color = c.border, thickness = 0.5.dp)
    }

    if (showMachinePicker) {
        MachineSwitcherSheet(
            vm        = machineVm,
            onDismiss = { showMachinePicker = false },
        )
    }
}

// ── Create Machine Dialog ─────────────────────────────────────────────────────

@Composable
private fun CreateMachineDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val c    = DrinkeiroTheme.colors
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor   = c.bg2,
        title = {
            Text("New Machine", color = c.cream, style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            OutlinedTextField(
                value         = name,
                onValueChange = { name = it },
                placeholder   = { Text("e.g. Kitchen Bar", color = c.cream3) },
                singleLine    = true,
                shape         = RoundedCornerShape(12.dp),
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor      = c.accent,
                    unfocusedBorderColor    = c.border,
                    focusedTextColor        = c.cream,
                    unfocusedTextColor      = c.cream,
                    cursorColor             = c.accent,
                    focusedContainerColor   = c.bg3,
                    unfocusedContainerColor = c.bg3,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        confirmButton = {
            TextButton(
                onClick  = { if (name.isNotBlank()) onConfirm(name) },
                enabled  = name.isNotBlank(),
            ) {
                Text(
                    "Create",
                    color      = c.accent,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = c.cream3)
            }
        },
    )
}

// ── Collaborators Sheet ───────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollaboratorsSheet(
    machine:  com.drinkeiro.data.model.Machine?,
    onAdd:    (String) -> Unit,
    onRemove: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val c          = DrinkeiroTheme.colors
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var email      by remember { mutableStateOf("") }

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
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                "Collaborators",
                style = MaterialTheme.typography.headlineSmall,
                color = c.cream,
            )
            Text(
                text  = "${machine?.name ?: "Machine"} · anyone added can brew cocktails",
                style = MaterialTheme.typography.bodySmall,
                color = c.cream3,
            )

            // Existing collaborators
            val collabs = machine?.collaborators ?: emptyList()
            if (collabs.isEmpty()) {
                Text(
                    "No collaborators yet.",
                    style     = MaterialTheme.typography.bodyMedium,
                    color     = c.cream3,
                    fontStyle = FontStyle.Italic,
                )
            } else {
                collabs.forEach { collab ->
                    Row(
                        modifier              = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(c.bg3)
                            .border(1.dp, c.border, RoundedCornerShape(12.dp))
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment     = Alignment.CenterVertically,
                        ) {
                            Surface(
                                shape  = RoundedCornerShape(10.dp),
                                color  = c.accentLo,
                                modifier = Modifier.size(32.dp),
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("👤", fontSize = 16.sp)
                                }
                            }
                            Text(collab, style = MaterialTheme.typography.bodyMedium, color = c.cream)
                        }
                        IconButton(
                            onClick  = { onRemove(collab) },
                            modifier = Modifier.size(28.dp),
                        ) {
                            Text("×", color = c.error, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            HorizontalDivider(color = c.border)

            // Add new collaborator
            Text(
                "Add Collaborator",
                style      = MaterialTheme.typography.labelSmall,
                color      = c.cream3,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value         = email,
                    onValueChange = { email = it },
                    placeholder   = { Text("Email or name…", color = c.cream3) },
                    singleLine    = true,
                    shape         = RoundedCornerShape(12.dp),
                    modifier      = Modifier.weight(1f),
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = c.accent,
                        unfocusedBorderColor    = c.border,
                        focusedTextColor        = c.cream,
                        unfocusedTextColor      = c.cream,
                        cursorColor             = c.accent,
                        focusedContainerColor   = c.bg3,
                        unfocusedContainerColor = c.bg3,
                    ),
                )
                Button(
                    onClick  = {
                        if (email.isNotBlank()) {
                            onAdd(email.trim())
                            email = ""
                        }
                    },
                    enabled  = email.isNotBlank(),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = c.accent,
                        contentColor   = c.bg0,
                    ),
                ) {
                    Text("Add", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
