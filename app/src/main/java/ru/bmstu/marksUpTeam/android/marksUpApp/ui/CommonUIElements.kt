package ru.bmstu.marksUpTeam.android.marksUpApp.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ru.bmstu.marksUpTeam.android.marksUpApp.R


@Preview()
@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.star),
    contentDescription: String = "Favourites",
    isSelected: Boolean = false,
    tint: Color = colorResource(id = R.color.white),
    selectedTint: Color = colorResource(id = R.color.purple_500),
    backgroundColor: Color = colorResource(id = R.color.black),
    onClick: () -> Unit = {},
){
    Column(
        modifier = modifier.padding(4.dp)
            .shadow(elevation = if (isSelected) 20.dp else 10.dp, clip = false,
                shape = RoundedCornerShape(10.dp), spotColor = if (isSelected) selectedTint else tint,
                ambientColor = if (isSelected) selectedTint else tint).background(backgroundColor).height(64.dp).width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        IconButton(onClick = {onClick()}, modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally).wrapContentHeight(Alignment.CenterVertically)
        ) {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = if (isSelected) selectedTint else tint,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(text=contentDescription, textAlign = TextAlign.Center, color = if (isSelected) selectedTint else tint)
    }
}

@Preview
@Composable
fun selectorTeacher(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,

){

}