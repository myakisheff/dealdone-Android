package com.example.dealdone.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.example.dealdone.R
import com.example.dealdone.ui.theme.DealDoneTheme
import io.mhssn.colorpicker.ColorPickerDialog
import io.mhssn.colorpicker.ColorPickerType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    keyValue: String,
    onKeyValueChanged: (String) -> Unit,
    isKeyVisible: Boolean,
    changeKeyVisibility: () -> Unit,
    onChangeThemeColorClick: () -> Unit,
    changeThemeColor: (Color) -> Unit,
    isColorPickerVisible: Boolean,
    onColorPickerDismiss: () -> Unit,
    saveKey: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TextField(
            value = keyValue,
            onValueChange = onKeyValueChanged,
            visualTransformation = if (isKeyVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text(text = stringResource(R.string.enter_key)) },
            singleLine = true,
            trailingIcon = {
                val image = if (isKeyVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (isKeyVisible) stringResource(R.string.hide_key) else stringResource(
                    R.string.show_key
                )

                IconButton(onClick = changeKeyVisibility){
                    Icon(imageVector  = image, description)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = saveKey,
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.large_padding),
                    bottom = dimensionResource(R.dimen.large_padding)
                )
        ) {
            Text(
                text = stringResource(R.string.save_key),
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.large_padding),
                    end = dimensionResource(R.dimen.large_padding),
                )

            )
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
        )

        CurrentThemeColor(
            onClick = onChangeThemeColorClick,
        )
    }

    ColorPickerDialog(
        show = isColorPickerVisible,
        type = ColorPickerType.Circle(showAlphaBar = false),
        properties = DialogProperties(),
        onDismissRequest = onColorPickerDismiss,
        onPickedColor = changeThemeColor
    )

}

@Composable
fun CurrentThemeColor(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Text(
                text = stringResource(R.string.current_theme_color),
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .size(dimensionResource(R.dimen.color_preview_size))
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    DealDoneTheme {
        SettingsScreen(
            keyValue = "Key data sample",
            isKeyVisible = false,
            changeKeyVisibility = {},
            onKeyValueChanged = {},
            isColorPickerVisible = false,
            onColorPickerDismiss = {},
            changeThemeColor = {},
            saveKey = {},
            onChangeThemeColorClick = {}
        )
    }
}