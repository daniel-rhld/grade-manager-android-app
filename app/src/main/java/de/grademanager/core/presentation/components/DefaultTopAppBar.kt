package de.grademanager.core.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    withNavigationIcon: Boolean = false,
    onNavigationIconClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (withNavigationIcon) {
                IconButton(
                    onClick = onNavigationIconClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        actions = actions,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun PreviewDefaultTopAppBar() {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        DefaultTopAppBar(
            title = "Toolbar title"
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewDefaultTopAppBarWithNavigationIcon() {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        DefaultTopAppBar(
            title = "Toolbar title",
            withNavigationIcon = true
        )
    }
}