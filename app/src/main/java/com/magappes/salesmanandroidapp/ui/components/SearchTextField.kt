package com.magappes.salesmanandroidapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.magappes.salesmanandroidapp.R
import com.magappes.salesmanandroidapp.ui.theme.SalesmanTheme


@Composable
internal fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int? = null,
    hint: String = stringResource(R.string.search_text_field_hint),
) {
    var internalSearchQuery by remember { mutableStateOf(TextFieldValue(value)) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
            )
            .border(
                width = 0.5.dp,
                color = SalesmanTheme.colors.grey200,
            )
            .background(
                color = SalesmanTheme.colors.white,
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_action_search),
                contentDescription = null,
                tint = SalesmanTheme.colors.grey400
            )
            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.weight(1f)) {
                if (internalSearchQuery.text.isEmpty()) {
                    Text(
                        text = hint,
                        style = SalesmanTheme.typography.roboto.regular.medium,
                        color = SalesmanTheme.colors.grey400
                    )
                }
                BasicTextField(
                    value = internalSearchQuery,
                    onValueChange = {
                        if (isValidSearchInput(it, maxLength)) {
                            internalSearchQuery = it
                            onValueChange(it.text)
                        }
                    },
                    singleLine = true,
                    textStyle = SalesmanTheme.typography.roboto.regular.medium.copy(
                        color = SalesmanTheme.colors.textPrimary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = keyboardOptions,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(R.drawable.icon_mic),
                contentDescription = null,
                tint = SalesmanTheme.colors.grey400
            )
        }
    }
}

private fun isValidSearchInput(input: TextFieldValue, maxLength: Int?): Boolean {
    val text = input.text
    return maxLength != null &&
            text.length <= maxLength &&
            (text.isEmpty() || text.matches(Regex("^\\d{0,5}\\*?\$")))
}

internal class SearchTextFieldProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "",
        "76133"
    )
}

@Preview(showBackground = true)
@Composable
internal fun SearchTextFieldPreview(
    @PreviewParameter(SearchTextFieldProvider::class) element: String
) {
    SearchTextField(
        value = element,
        onValueChange = {}
    )
}