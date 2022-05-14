package com.nullpointer.moviescompose.ui.share


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nullpointer.moviescompose.R

@Composable
fun ToolbarBack(
    title: String,
    iconNavigation: Int = R.drawable.ic_arrow_back,
    actionBack: (() -> Unit)? = null,
    actionSearch: (() -> Unit)? = null,
) {
    if (actionBack != null) {
        TopAppBar(title = { Text(title) },
            backgroundColor = MaterialTheme.colors.primaryVariant,
            contentColor = Color.White,
            navigationIcon = {
                IconButton(onClick = actionBack) {
                    Icon(painterResource(id = iconNavigation),
                        stringResource(id = R.string.description_arrow_back))
                }

            })
    } else {
        TopAppBar(title = { Text(title) },
            backgroundColor = MaterialTheme.colors.primaryVariant,
            contentColor = Color.White,actions = {
                if (actionSearch != null) {
                    IconButton(onClick = actionSearch) {
                        Icon(painterResource(R.drawable.ic_search),
                            stringResource(id = R.string.description_search))
                    }
                }
            })
    }
}

