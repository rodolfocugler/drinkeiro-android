package com.drinkeiro.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    cocktailVm: CocktailViewModel = hiltViewModel(),
    machineVm:  MachineViewModel  = hiltViewModel(),
) {
    val c          = DrinkeiroTheme.colors
    var activeTab  by remember { mutableStateOf(Tab.Favorites) }
    val machineUi  by machineVm.ui.collectAsState()
    val cocktailUi by cocktailVm.ui.collectAsState()

    Scaffold(
        containerColor = c.bg0,
        bottomBar = {
            Column {
                HorizontalDivider(color = c.border, thickness = 0.5.dp)
                NavigationBar(
                    containerColor    = c.bg0,
                    contentColor      = c.cream3,
                    tonalElevation    = 0.dp,
                    modifier          = Modifier.navigationBarsPadding(),
                ) {
                    Tab.entries.forEach { tab ->
                        val selected = tab == activeTab
                        NavigationBarItem(
                            selected = selected,
                            onClick  = { activeTab = tab },
                            icon     = {
                                Surface(
                                    shape = RoundedCornerShape(13.dp),
                                    color = if (selected) c.accentLo else c.bg0,
                                    modifier = Modifier.size(38.dp),
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(tab.icon, fontSize = 18.sp,
                                            color = if (selected) c.accent else c.cream4)
                                    }
                                }
                            },
                            label = {
                                Text(
                                    text  = tab.label.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (selected) c.accent else c.cream4,
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
                // ── App header ──────────────────────────────────────────────
                AppHeader(
                    machineVm  = machineVm,
                    activeTab  = activeTab,
                    cocktailVm = cocktailVm,
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

            // ── Global brew toast ───────────────────────────────────────────
            AnimatedVisibility(
                visible = machineUi.toastMessage != null,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = padding.calculateBottomPadding() + 12.dp),
                enter   = slideInVertically { it } + fadeIn(),
                exit    = slideOutVertically { it } + fadeOut(),
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
}

// ── App header ────────────────────────────────────────────────────────────────

@Composable
private fun AppHeader(
    machineVm:  MachineViewModel,
    activeTab:  Tab,
    cocktailVm: CocktailViewModel,
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Machine selector
            Column(
                modifier = Modifier.clickable { showMachinePicker = true }
            ) {
                Text(
                    text  = "ACTIVE MACHINE",
                    style = MaterialTheme.typography.labelSmall,
                    color = c.cream4,
                )
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusDot(machineUi.activeMachine?.status == "online")
                    Text(
                        text       = machineUi.activeMachine?.name ?: "No machine",
                        style      = MaterialTheme.typography.headlineSmall,
                        color      = c.cream,
                    )
                    Text("▾", color = c.cream4, fontSize = 11.sp)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (activeTab == Tab.Cocktails) {
                    AmberChip(text = "+ Recipe", onClick = { cocktailVm.requestCreateRecipe() })
                }
                Text(
                    text      = "Drinkeiro",
                    fontSize  = 15.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    color     = c.accent,
                )
            }
        }

        Spacer(Modifier.height(14.dp))
        HorizontalDivider(color = c.border, thickness = 0.5.dp)
    }

    if (showMachinePicker) {
        MachineSwitcherSheet(vm = machineVm, onDismiss = { showMachinePicker = false })
    }
}
