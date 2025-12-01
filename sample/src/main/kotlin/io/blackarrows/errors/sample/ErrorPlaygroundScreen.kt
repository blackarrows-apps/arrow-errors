package io.blackarrows.errors.sample

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.blackarrows.errors.compose.components.ErrorPresenter

/**
 * Main screen for the Error Playground sample app.
 *
 * Provides a comprehensive UI for testing all error scenarios.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorPlaygroundScreen(viewModel: ErrorPlaygroundViewModel) {
    val error by viewModel.error.collectAsState()
    val locale by viewModel.locale.collectAsState()
    val lastAction by viewModel.lastAction.collectAsState()
    val lastNavigation by viewModel.lastNavigation.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Arrow Errors Playground") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Locale selector
                LocaleSelector(
                    currentLocale = locale,
                    onLocaleChange = { viewModel.setLocale(it) }
                )

                // Status display
                StatusDisplay(
                    lastAction = lastAction,
                    lastNavigation = lastNavigation
                )

                HorizontalDivider()

                // Error sections
                ErrorSection(
                    title = "Network Errors",
                    icon = Icons.Default.Wifi,
                    description = "Demonstrate network-related errors"
                ) {
                    PlaygroundButton(
                        text = "Network Error (Snackbar)",
                        icon = Icons.Default.Warning,
                        onClick = { viewModel.showNetworkErrorSnackbar() }
                    )
                    PlaygroundButton(
                        text = "Network Error (Dialog)",
                        icon = Icons.Default.ErrorOutline,
                        onClick = { viewModel.showNetworkErrorDialog() }
                    )
                    PlaygroundButton(
                        text = "Network Error (Full Screen)",
                        icon = Icons.Default.Error,
                        onClick = { viewModel.showNetworkErrorFullScreen() }
                    )
                }

                ErrorSection(
                    title = "Authentication Errors",
                    icon = Icons.Default.Lock,
                    description = "Demonstrate auth-related errors"
                ) {
                    PlaygroundButton(
                        text = "Token Expired",
                        icon = Icons.Default.AccessTime,
                        onClick = { viewModel.showAuthExpiredDialog() }
                    )
                    PlaygroundButton(
                        text = "Unauthorized",
                        icon = Icons.Default.Block,
                        onClick = { viewModel.showAuthUnauthorizedDialog() }
                    )
                    PlaygroundButton(
                        text = "Forbidden",
                        icon = Icons.Default.Lock,
                        onClick = { viewModel.showAuthForbiddenDialog() }
                    )
                }

                ErrorSection(
                    title = "Storage Errors",
                    icon = Icons.Default.Storage,
                    description = "Demonstrate storage-related errors"
                ) {
                    PlaygroundButton(
                        text = "Storage Error",
                        icon = Icons.Default.Error,
                        onClick = { viewModel.showStorageErrorDialog() }
                    )
                    PlaygroundButton(
                        text = "Storage Full",
                        icon = Icons.Default.Warning,
                        onClick = { viewModel.showStorageFullError() }
                    )
                }

                ErrorSection(
                    title = "Session Errors",
                    icon = Icons.Default.AccountCircle,
                    description = "Demonstrate session-related errors"
                ) {
                    PlaygroundButton(
                        text = "Session Failed",
                        icon = Icons.Default.ErrorOutline,
                        onClick = { viewModel.showSessionFailedError() }
                    )
                }

                ErrorSection(
                    title = "Severity Levels",
                    icon = Icons.Default.Tune,
                    description = "Demonstrate different severity levels"
                ) {
                    PlaygroundButton(
                        text = "Info",
                        icon = Icons.Default.Info,
                        onClick = { viewModel.showInfoMessage() }
                    )
                    PlaygroundButton(
                        text = "Warning",
                        icon = Icons.Default.Warning,
                        onClick = { viewModel.showWarningMessage() }
                    )
                    PlaygroundButton(
                        text = "Error",
                        icon = Icons.Default.Error,
                        onClick = { viewModel.showErrorMessage() }
                    )
                    PlaygroundButton(
                        text = "Critical",
                        icon = Icons.Default.ErrorOutline,
                        onClick = { viewModel.showCriticalError() }
                    )
                }

                ErrorSection(
                    title = "Custom Errors",
                    icon = Icons.Default.Build,
                    description = "Demonstrate custom error configurations"
                ) {
                    PlaygroundButton(
                        text = "Error with Navigation",
                        icon = Icons.Default.Navigation,
                        onClick = { viewModel.showCustomErrorWithNavigation() }
                    )
                    PlaygroundButton(
                        text = "Multiple Actions",
                        icon = Icons.Default.TouchApp,
                        onClick = { viewModel.showCustomErrorWithMultipleActions() }
                    )
                    PlaygroundButton(
                        text = "Destructive Action",
                        icon = Icons.Default.Delete,
                        onClick = { viewModel.showDestructiveActionError() }
                    )
                    PlaygroundButton(
                        text = "Silent Error",
                        icon = Icons.Default.VisibilityOff,
                        onClick = { viewModel.showSilentError() }
                    )
                }

                ErrorSection(
                    title = "Custom Actions & Navigation",
                    icon = Icons.Default.Extension,
                    description = "Demonstrate ErrorAction.Custom and ErrorNavigation.Custom"
                ) {
                    PlaygroundButton(
                        text = "Custom Action (Skip Video)",
                        icon = Icons.Default.SkipNext,
                        onClick = { viewModel.showCustomActionExample() }
                    )
                    PlaygroundButton(
                        text = "Custom Navigation (Payment)",
                        icon = Icons.Default.Payment,
                        onClick = { viewModel.showCustomNavigationExample() }
                    )
                    PlaygroundButton(
                        text = "Custom Action + Navigation",
                        icon = Icons.Default.Apps,
                        onClick = { viewModel.showCustomActionAndNavigationExample() }
                    )
                }

                ErrorSection(
                    title = "Test Custom Inputs",
                    icon = Icons.Default.Science,
                    description = "Test with your own custom action and navigation values"
                ) {
                    CustomInputFields(viewModel = viewModel)
                }
            }

            // Error presenter handles all error presentations
            ErrorPresenter(
                error = error,
                onDismiss = { viewModel.clearError() },
                onActionClick = { actionId ->
                    viewModel.handleAction(actionId)
                    viewModel.clearError()
                },
                onNavigate = { navigation ->
                    viewModel.handleNavigation(navigation)
                }
            )
        }
    }
}

@Composable
private fun LocaleSelector(
    currentLocale: String,
    onLocaleChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Language / Idioma / Langue",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LocaleButton(
                    locale = "en",
                    label = "English",
                    isSelected = currentLocale == "en",
                    onClick = { onLocaleChange("en") },
                    modifier = Modifier.weight(1f)
                )
                LocaleButton(
                    locale = "es",
                    label = "Español",
                    isSelected = currentLocale == "es",
                    onClick = { onLocaleChange("es") },
                    modifier = Modifier.weight(1f)
                )
                LocaleButton(
                    locale = "fr",
                    label = "Français",
                    isSelected = currentLocale == "fr",
                    onClick = { onLocaleChange("fr") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun LocaleButton(
    locale: String,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = if (isSelected) {
            ButtonDefaults.buttonColors()
        } else {
            ButtonDefaults.outlinedButtonColors()
        }
    ) {
        Text(label)
    }
}

@Composable
private fun StatusDisplay(
    lastAction: String?,
    lastNavigation: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Status",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            lastAction?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
            lastNavigation?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
            if (lastAction == null && lastNavigation == null) {
                Text(
                    text = "No actions performed yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun ErrorSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            content()
        }
    }
}

@Composable
private fun PlaygroundButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
private fun CustomInputFields(viewModel: ErrorPlaygroundViewModel) {
    val customActionId by viewModel.customActionId.collectAsState()
    val customActionLabel by viewModel.customActionLabel.collectAsState()
    val customNavigationRoute by viewModel.customNavigationRoute.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Custom Action ID Input
        OutlinedTextField(
            value = customActionId,
            onValueChange = { viewModel.updateCustomActionId(it) },
            label = { Text("Action ID") },
            placeholder = { Text("e.g., my_custom_action") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Custom Action Label Input
        OutlinedTextField(
            value = customActionLabel,
            onValueChange = { viewModel.updateCustomActionLabel(it) },
            label = { Text("Action Label") },
            placeholder = { Text("e.g., Custom Action") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Custom Navigation Route Input
        OutlinedTextField(
            value = customNavigationRoute,
            onValueChange = { viewModel.updateCustomNavigationRoute(it) },
            label = { Text("Navigation Route") },
            placeholder = { Text("e.g., app://custom/route") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Test Button
        Button(
            onClick = { viewModel.showTestCustomInputs() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Test Custom Inputs")
        }
    }
}
