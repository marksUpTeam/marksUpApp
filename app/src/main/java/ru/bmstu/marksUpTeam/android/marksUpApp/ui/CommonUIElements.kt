@file:Suppress("FunctionName")

package ru.bmstu.marksUpTeam.android.marksUpApp.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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


@Preview
@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.star),
    contentDescription: String = "Favourites",
    isSelected: Boolean = false,
    tint: Color = colorResource(id = R.color.white),
    selectedTint: Color = colorResource(id = R.color.purple_500),
    backgroundColor: Color = colorResource(id = R.color.lighter_black),
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .shadow(
                elevation = if (isSelected) 20.dp else 10.dp,
                clip = false,
                shape = RoundedCornerShape(10.dp),
                spotColor = if (isSelected) selectedTint else tint,
                ambientColor = if (isSelected) selectedTint else tint
            )
            .background(backgroundColor)
            .height(64.dp)
            .width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { onClick() },
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
        ) {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = if (isSelected) selectedTint else tint,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = contentDescription,
            textAlign = TextAlign.Center,
            color = if (isSelected) selectedTint else tint,
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
fun Selector(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    setCurrentScreen: (Int) -> Unit = {},
    backgroundColor: Color = colorResource(id = R.color.black),
    tint: Color = colorResource(id = R.color.white),
    selectedTint: Color = colorResource(id = R.color.purple_500),
    isForTeacher: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var buttonClicked by rememberSaveable { mutableIntStateOf(1) }
        BaseButton(
            painter = painterResource(R.drawable.timetable),
            onClick = {
                setCurrentScreen(1)
                buttonClicked = 1
            },
            contentDescription = context.getString(R.string.classes),
            tint = tint,
            selectedTint = selectedTint,
            isSelected = buttonClicked == 1
        )
        BaseButton(
            onClick = {
                setCurrentScreen(2)
                buttonClicked = 2
            },
            contentDescription = context.getString(R.string.favourites),
            tint = tint,
            selectedTint = selectedTint,
            isSelected = buttonClicked == 2
        )
        if (isForTeacher) {
            BaseButton(
                painter = painterResource(R.drawable.manager),
                onClick = {
                    setCurrentScreen(3)
                    buttonClicked = 3
                },
                contentDescription = context.getString(R.string.classesManager),
                tint = tint,
                selectedTint = selectedTint,
                isSelected = buttonClicked == 3
            )
        } else {
            BaseButton(
                painter = painterResource(R.drawable.five),
                onClick = {
                    setCurrentScreen(3)
                    buttonClicked = 3
                },
                contentDescription = context.getString(R.string.marks),
                tint = tint,
                selectedTint = selectedTint,
                isSelected = buttonClicked == 3
            )
        }
        BaseButton(
            painter = painterResource(R.drawable.profile),
            onClick = {
                setCurrentScreen(4)
                buttonClicked = 4
            },
            contentDescription = context.getString(R.string.profile),
            tint = tint,
            selectedTint = selectedTint,
            isSelected = buttonClicked == 4
        )

    }
}


@Preview
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    tint: Color = colorResource(id = R.color.white),
    backgroundColor: Color = colorResource(id = R.color.lighter_black),
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
    backgroundColor: Color = colorResource(id = R.color.lighter_black),
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

@Composable
fun DropDownList(currentItem: String, listItems: List<String>, modifier: Modifier) {
    var fieldText by remember { mutableStateOf(currentItem) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = fieldText, onValueChange = {
                fieldText = it
            },
            modifier = modifier,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            }
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listItems.forEach { item ->
                DropdownMenuItem(text = { Text(item) }, onClick = {
                    fieldText = item
                    expanded = false
                })
            }
        }

    }
}





