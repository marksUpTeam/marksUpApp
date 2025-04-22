@file:Suppress("FunctionName")

package ru.bmstu.marksUpTeam.android.marksUpApp.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.bmstu.marksUpTeam.android.marksUpApp.R


@Preview()
@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.star),
    contentDescription: String = "Favourites",
    isSelected: Boolean = false,
    tint: Color = colorResource(id = R.color.grey),
    selectedTint: Color = colorResource(id = R.color.blue),
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .height(48.dp)
            .width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        IconButton(
            onClick = { onClick() },
            modifier = Modifier
                .size(30.dp)
                .background(Color.Transparent),
        ) {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = if (isSelected) selectedTint else tint,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = contentDescription,
            textAlign = TextAlign.Center,
            color = if (isSelected) selectedTint else tint,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 0.dp)
        )
    }
}




@Preview
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    tint: Color = colorResource(id = R.color.white),
    backgroundColor: Color = colorResource(id = R.color.grey),
) {
    Box(modifier = modifier.background(backgroundColor), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = tint)
    }
}


@Preview
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    context: Context = LocalContext.current,
    tint: Color = colorResource(id = R.color.white),
    backgroundColor: Color = colorResource(id = R.color.grey),
    errorMessage: String = "",
) {
    Column(
        modifier = modifier.background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { onRefresh() }) {
            Icon(
                painter = painterResource(R.drawable.error),
                contentDescription = context.getString(R.string.errorOccurred),
                modifier = Modifier.fillMaxSize(),
                tint = tint
            )
        }
        Text(
            text = errorMessage,
            color = tint,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList(
    currentItem: String,
    listItems: List<String>,
    modifier: Modifier,
    readonly: Boolean
) {
    var fieldText by remember { mutableStateOf(currentItem) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (!readonly) {
                expanded = !expanded
            }
        },

        ) {
        TextField(
            value = fieldText,
            onValueChange = { fieldText = it },
            modifier = modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            trailingIcon = {
                if (!readonly) {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                }
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listItems.forEach { item ->
                DropdownMenuItem(text = { Text(item) }, onClick = {
                    fieldText = item
                    expanded = false
                })
            }
        }
    }
}