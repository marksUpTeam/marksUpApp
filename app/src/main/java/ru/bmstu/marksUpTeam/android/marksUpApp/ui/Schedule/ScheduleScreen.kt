package ru.bmstu.marksUpTeam.android.marksUpApp.ui.Schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    tint: Color = colorResource(id = R.color.black),
    backgroundColor: Color = colorResource(id = R.color.white)
) {
    val currentDate = LocalDate.now()
    val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE / 2) { Int.MAX_VALUE }
    var selectedDate by remember { mutableStateOf(currentDate) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Расписание",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = tint
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.coral))
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF7F4F4))
                .padding(8.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                val weekStart = calculateStartOfWeek(page)
                WeekTable(
                    tint = tint,
                    startOfWeek = weekStart,
                    currentDate = currentDate,
                    selectedDate = selectedDate,
                    onDayClick = { date -> selectedDate = date }
                )
            }
        }

        ScheduleForSelectedDay(
            selectedDate = selectedDate,
            tint = tint,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
    }

}

fun calculateStartOfWeek(page: Int): LocalDate {
    val today = LocalDate.now()
    var monday = today
    while (monday.dayOfWeek != DayOfWeek.MONDAY) {
        monday = monday.minusDays(1)
    }
    val daysOffset = (page - Int.MAX_VALUE / 2) * 7
    return monday.plusDays(daysOffset.toLong())
}

@Composable
fun WeekTable(
    tint: Color,
    startOfWeek: LocalDate,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0 until 7) {
                val currentDay = startOfWeek.plusDays(i.toLong())
                DayItem(
                    currentDay = currentDay,
                    currentDate = currentDate,
                    selectedDate = selectedDate,
                    onDayClick = onDayClick,
                    tint = tint
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 7 until 14) {
                val currentDay = startOfWeek.plusDays(i.toLong())
                DayItem(
                    currentDay = currentDay,
                    currentDate = currentDate,
                    selectedDate = selectedDate,
                    onDayClick = onDayClick,
                    tint = tint
                )
            }
        }
    }
}

@Composable
fun DayItem(
    currentDay: LocalDate,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    tint: Color
) {
    val date = currentDay.dayOfMonth
    val dayOfWeek = currentDay.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru")).capitalize()

    val isSelected = currentDay == selectedDate

    Box(
        modifier = Modifier
            .clickable { onDayClick(currentDay) }
            .background(
                color = if (isSelected) colorResource(id = R.color.lighter_blue) else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.toString(),
                color = when {
                    currentDay == currentDate -> colorResource(id = R.color.coral)
                    isSelected -> colorResource(id = R.color.black)
                    else -> tint
                },
                fontSize = if (isSelected) 24.sp else 22.sp,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
            )
            Text(
                text = dayOfWeek,
                color = when {
                    currentDay == currentDate -> colorResource(id = R.color.coral)
                    isSelected -> colorResource(id = R.color.black)
                    else -> tint.copy(alpha = 0.7f)
                },
                fontSize = if (isSelected) 18.sp else 16.sp,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
            )
        }
    }

}

@Composable
fun ScheduleForSelectedDay(
    selectedDate: LocalDate,
    tint: Color,
    modifier: Modifier = Modifier
) {
    val hours = (0..23).toList()
    val listState = rememberLazyListState()
    val currentTime = LocalTime.now()
    val currentHour = currentTime.hour

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Расписание на ${selectedDate.dayOfMonth} ${getMonthName(selectedDate)}",
            color = tint,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(state = listState) {
            items(hours) { hour ->
                HourRow(hour = hour, tint = tint)
                if (hour < 23) {
                    HorizontalDivider(thickness = 1.dp, color = colorResource(id = R.color.coral))
                }
            }
        }

        LaunchedEffect(Unit) {
            val targetIndex = currentHour.coerceIn(0, hours.size - 1)
            listState.scrollToItem(targetIndex)
        }
    }

}

@Composable
fun HourRow(hour: Int, tint: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                // Реализовать добавление занятия по клику
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = String.format("%02d:00", hour),
            color = tint,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(60.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "",
            color = tint,
            fontSize = 16.sp
        )
    }
}


fun getMonthName(date: LocalDate): String {
    val monthNames = arrayOf(
        "января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря"
    )
    return monthNames[date.monthValue - 1]
}
