package com.example.dealdone.ui.screen.newtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dealdone.R
import com.example.dealdone.ui.theme.DealDoneTheme

@Composable
fun NewTaskScreen(
    onCreatePressed: () -> Unit,
    onChangeModePressed: (Boolean) -> Unit,
    isFastCreation: Boolean,
    fastCreationText: String,
    onFastCreationTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ModeSwitcher(
            isFastCreation = isFastCreation,
            onChangeModePressed = onChangeModePressed
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_padding)))

        if(isFastCreation) {
            FastCreationTask(
                onCreatePressed = onCreatePressed,
                onTextChanged = onFastCreationTextChanged,
                text = fastCreationText
            )
        } else {
            DefaultCreationTask(
                onCreatePressed = onCreatePressed
            )
        }
    }
}

@Composable
fun ModeSwitcher(
    onChangeModePressed: (Boolean) -> Unit,
    isFastCreation: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        ModeItem(
            onClick = { onChangeModePressed(false) },
            isSelected = !isFastCreation,
            modeName = stringResource(R.string.default_mode),
            modifier = Modifier.weight(1f)
        )
        ModeItem(
            onClick = { onChangeModePressed(true) },
            isSelected = isFastCreation,
            modeName = stringResource(R.string.fast_mode),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ModeItem(
    modeName: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = modeName,
            style = MaterialTheme.typography.labelLarge
        )
        HorizontalDivider(
            thickness = dimensionResource(R.dimen.large_thickness),
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        )
    }
}

@Composable
fun FastCreationTask(
    onCreatePressed: () -> Unit,
    onTextChanged: (String) -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = text,
            onValueChange = onTextChanged,
            label = { Text(text = stringResource(R.string.fast_creation_task_label) ) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onCreatePressed) {
            Text(text = stringResource(R.string.create_task))
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.extra_large_padding)))
    }
}

@Composable
fun DefaultCreationTask(
    onCreatePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {


        Button(onClick = onCreatePressed) {
            Text(text = stringResource(R.string.create_task))
        }
    }
}

@Preview
@Composable
fun ChangeModeButtonsPreview() {
    DealDoneTheme {
        ModeSwitcher(
            isFastCreation = false,
            onChangeModePressed = {}
        )
    }
}

@Preview
@Composable
fun FastCreationTaskPreview() {
    DealDoneTheme {
        FastCreationTask(
            onCreatePressed = {},
            onTextChanged = {},
            text = "Sample of text",
        )
    }
}

@Preview
@Composable
fun DefaultCreationTaskPreview() {
    DealDoneTheme {
        DefaultCreationTask(
            onCreatePressed = {}
        )
    }
}