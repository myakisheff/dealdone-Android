package com.example.dealdone.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dealdone.R
import com.example.dealdone.ui.theme.DealDoneTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    taskTitle: String,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(dimensionResource(R.dimen.extra_large_padding)),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
            ) {
                Text(
                    text = stringResource(R.string.delete_task, taskTitle),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.extra_large_padding)))
                Row {
                    Button(
                        onClick = onConfirm
                    ) {
                        Text(text = stringResource(R.string.ok))
                    }
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.large_padding)))
                    Button(
                        onClick = onCancel
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskDeleteConfirmationDialogPreview() {
    DealDoneTheme {
        TaskDeleteConfirmationDialog(
            onConfirm = {},
            onCancel = {},
            onDismiss = {},
            taskTitle = "Task test"
        )
    }
}