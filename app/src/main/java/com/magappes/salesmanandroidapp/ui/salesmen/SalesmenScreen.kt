package com.magappes.salesmanandroidapp.ui.salesmen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magappes.salesmanandroidapp.R
import com.magappes.salesmanandroidapp.ui.components.SearchTextField
import com.magappes.salesmanandroidapp.ui.theme.SalesmanTheme


@Composable
internal fun SalesmenScreen(
    viewModel: SalesmenViewModel = hiltViewModel<SalesmenViewModel>()
) {
    val uiState: SalesmenUiState by viewModel.uiState.collectAsStateWithLifecycle()

    SalesmenScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SalesmenScreen(
    uiState: SalesmenUiState,
    onAction: (SalesmanUiAction) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopAppBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 24.dp)
        ) {
            SearchTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = uiState.searchQuery,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                maxLength = 5,
                onValueChange = {
                    onAction(SalesmanUiAction.OnSearchQueryChanged(it))
                }
            )
            when {
                uiState.isLoading -> LoadingState()
                uiState.salesmen.isEmpty() -> EmptyState()
                else -> {
                    SalesmenList(
                        salesmen = uiState.salesmen,
                        onAction = onAction
                    )
                }
            }
        }
    }
}

@Composable
private fun TopAppBar() {
    Box(
        modifier = Modifier
            .background(SalesmanTheme.colors.primary)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.addresses),
                color = SalesmanTheme.colors.white,
                style = SalesmanTheme.typography.roboto.bold.small
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                    tint = SalesmanTheme.colors.white
                )
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_results_found)
        )
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = SalesmanTheme.colors.primary
        )
    }
}

@Composable
private fun SalesmenList(
    salesmen: List<SalesmanUi>,
    onAction: (SalesmanUiAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(top = 24.dp, start = 16.dp)
    ) {
        items(salesmen) { salesman ->
            Column(
                modifier = Modifier
                    .padding(top = 17.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(41.dp)
                            .background(
                                color = SalesmanTheme.colors.grey200,
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                shape = CircleShape,
                                color = SalesmanTheme.colors.lightSteelBlueGray
                            )
                    ) {
                        Text(
                            text = salesman.firstLetterUppercase(),
                            fontSize = 20.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = salesman.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = SalesmanTheme.typography.roboto.regular.medium
                        )
                        if (salesman.isSelected) {
                            Text(
                                modifier = Modifier.padding(top = 3.dp),
                                text = salesman.areasWithCommas(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = SalesmanTheme.colors.grey400,
                                style = SalesmanTheme.typography.roboto.regular.small
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(end = 17.dp)
                    ) {
                        IconButton(
                            onClick = {
                                onAction(
                                    SalesmanUiAction.OnSalesmenItemClicked(
                                        index = salesman.index,
                                        isSelected = !salesman.isSelected
                                    )
                                )
                            }
                        ) {
                            Icon(
                                painter = painterResource(if (salesman.isSelected) R.drawable.icon_chevron_down else R.drawable.icon_chevron_right),
                                contentDescription = null,
                                tint = SalesmanTheme.colors.grey400
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 17.dp)
                        .height(1.dp)
                        .background(SalesmanTheme.colors.lightGrey)
                )
            }

        }
    }
}

internal class SalesmenScreenProvider : PreviewParameterProvider<SalesmenUiState> {
    override val values = sequenceOf(
        SalesmenUiState(
            salesmen = listOf(
                SalesmanUi(
                    index = 1,
                    name = "Artem Titarenko",
                    areas = listOf(
                        "76133"
                    )
                ),
                SalesmanUi(
                    index = 2,
                    name = "Bern Schmitt",
                    areas = listOf(
                        "76133", "76132"
                    ),
                    isSelected = true
                ),
            ),
            isLoading = false
        ),
        SalesmenUiState(
            salesmen = listOf(),
            isLoading = false
        ),
        SalesmenUiState(
            salesmen = listOf(),
            isLoading = true
        )
    )
}

@Preview(showBackground = true)
@Composable
internal fun SalesmenScreenPreview(
    @PreviewParameter(SalesmenScreenProvider::class) salesmenUiState: SalesmenUiState
) {
    SalesmenScreen(
        uiState = salesmenUiState,
        onAction = {}
    )
}