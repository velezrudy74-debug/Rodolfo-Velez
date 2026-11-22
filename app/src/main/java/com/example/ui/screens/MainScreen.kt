package com.example.ui.screens

import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.ProfileData
import com.example.ui.components.DigitalBusinessCardDialog
import com.example.ui.components.ProjectDetailsDialog
import com.example.ui.viewmodel.PortfolioViewModel
import kotlinx.coroutines.flow.collectLatest

data class NavTabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: PortfolioViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val showBusinessCard by viewModel.showBusinessCard.collectAsStateWithLifecycle()
    val selectedProjectDetails by viewModel.selectedProjectDetails.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.snackbarMessage.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Handle back button when not on Home/Profile
    BackHandler(enabled = currentTab != 0 || showBusinessCard || selectedProjectDetails != null) {
        when {
            selectedProjectDetails != null -> viewModel.closeProjectDetails()
            showBusinessCard -> viewModel.setBusinessCardVisible(false)
            currentTab != 0 -> viewModel.selectTab(0)
        }
    }

    val tabs = remember {
        listOf(
            NavTabItem("Profile", Icons.Filled.Person, Icons.Outlined.Person, "nav_tab_profile"),
            NavTabItem("Projects", Icons.Filled.Folder, Icons.Outlined.Folder, "nav_tab_projects"),
            NavTabItem("Experience", Icons.Filled.BusinessCenter, Icons.Outlined.BusinessCenter, "nav_tab_experience"),
            NavTabItem("Contact", Icons.Filled.Email, Icons.Outlined.Email, "nav_tab_contact")
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentTab) {
                            0 -> profile.preferredName
                            1 -> "Projects"
                            2 -> "Experience"
                            3 -> "Contact"
                            else -> profile.preferredName
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 8.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .clickable { viewModel.selectTab(0) },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_avatar),
                            contentDescription = "Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.setBusinessCardVisible(true) },
                        modifier = Modifier.testTag("topbar_qr_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = "Digital Business Card",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = { shareProfile(context, profile) },
                        modifier = Modifier.testTag("topbar_share_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Profile",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.testTag("main_navigation_bar"),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                tabs.forEachIndexed { index, tab ->
                    val isSelected = currentTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.selectTab(index) },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title
                            )
                        },
                        label = { Text(tab.title) },
                        modifier = Modifier.testTag(tab.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                0 -> ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToContact = { viewModel.selectTab(3) }
                )
                1 -> ProjectsScreen(viewModel = viewModel)
                2 -> ExperienceScreen(viewModel = viewModel)
                3 -> ContactScreen(viewModel = viewModel)
            }
        }
    }

    // Digital Business Card Dialog
    if (showBusinessCard) {
        DigitalBusinessCardDialog(
            onDismiss = { viewModel.setBusinessCardVisible(false) },
            onShare = {
                shareVCard(context, profile)
                viewModel.showFeedback("Sharing Rudy Velez Contact Card...")
            }
        )
    }

    // Project Details Dialog
    selectedProjectDetails?.let { project ->
        ProjectDetailsDialog(
            project = project,
            onDismiss = { viewModel.closeProjectDetails() },
            onOpenGithub = {
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(project.githubUrl))
                runCatching { context.startActivity(intent) }
            }
        )
    }
}

private fun shareProfile(context: Context, profile: ProfileData) {
    val shareText = """
        ${profile.fullName} (${profile.preferredName})
        ${profile.title}
        
        Email: ${profile.email}
        GitHub: ${profile.githubUrl}
        Repository: ${profile.repoUrl}
        
        ${profile.tagline}
    """.trimIndent()

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    val chooser = Intent.createChooser(sendIntent, "Share Rudy Velez Profile")
    context.startActivity(chooser)
}

private fun shareVCard(context: Context, profile: ProfileData) {
    val vCard = """
        BEGIN:VCARD
        VERSION:3.0
        N:Cortez Velez;Rodolfo;;;
        FN:${profile.fullName}
        TITLE:${profile.title}
        EMAIL;TYPE=INTERNET,PREF:${profile.email}
        URL:${profile.repoUrl}
        NOTE:${profile.tagline}
        END:VCARD
    """.trimIndent()

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, vCard)
        type = "text/x-vcard"
    }
    val chooser = Intent.createChooser(sendIntent, "Share Contact Card")
    context.startActivity(chooser)
}
