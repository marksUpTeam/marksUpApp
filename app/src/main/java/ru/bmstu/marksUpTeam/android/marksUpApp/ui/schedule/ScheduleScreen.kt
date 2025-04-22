package ru.bmstu.marksUpTeam.android.marksUpApp.ui.schedule

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.NotificationReceiver
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.PersonType
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.mainActivity.Route
import java.time.format.TextStyle
import java.util.Locale
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Class as LessonClass

@Composable
fun ScheduleScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    tint: Color = colorResource(id = R.color.black),
    backgroundColor: Color = colorResource(id = R.color.white)
) {
    val context = LocalContext.current
    val state by viewModel.stateFlow.collectAsState()
    val isTeacher = state.profile.personType is PersonType.TeacherType
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE / 2) { Int.MAX_VALUE }
    var selectedDate by remember { mutableStateOf(currentDate) }
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    LaunchedEffect(state) {
        state.route?.let { route ->
            navController.navigate(route)
            viewModel.resetRoute()
        }
        state.classes.let {
            viewModel.updateFlow()
        }
    }

    LaunchedEffect(state.classes) {
        state.classes.forEach { classItem ->
            scheduleClassNotification(context, classItem)
        }
    }

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
            IconButton(onClick = { TODO() }) {
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
            viewModel = viewModel,
            selectedDate = selectedDate,
            isTeacher = isTeacher,
            tint = tint,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
    }
}

private fun scheduleClassNotification(context: Context, classItem: LessonClass) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val startInstant = classItem.datetimeStart.toInstant(TimeZone.currentSystemDefault())
    val timeBeforeClass = startInstant.minus(30, DateTimeUnit.MINUTE, TimeZone.currentSystemDefault())
    val now = Clock.System.now()

    if (timeBeforeClass > now) {
        createNotification(context, alarmManager, classItem, timeBeforeClass,
            "Через 30 минут: ${classItem.discipline.name}")
    }
    if (startInstant > now) {
        createNotification(context, alarmManager, classItem, startInstant,
            "Сейчас: ${classItem.discipline.name}")
    }
}

@SuppressLint("ScheduleExactAlarm")
private fun createNotification(
    context: Context,
    alarmManager: AlarmManager,
    classItem: LessonClass,
    triggerTime: Instant,
    message: String
) {
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("subject", classItem.discipline.name)
        putExtra("time", message)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        classItem.id.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        triggerTime.toEpochMilliseconds(),
        pendingIntent
    )
}

private fun calculateStartOfWeek(page: Int): LocalDate {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var monday = today
    while (monday.dayOfWeek != DayOfWeek.MONDAY) {
        monday = monday.minus(1, DateTimeUnit.DAY)
    }
    val daysOffset = (page - Int.MAX_VALUE / 2) * 7
    return monday.plus(daysOffset, DateTimeUnit.DAY)
}

@Composable
private fun WeekTable(
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
                val currentDay = startOfWeek.plus(i, DateTimeUnit.DAY)
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
private fun DayItem(
    currentDay: LocalDate,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    tint: Color
) {
    val date = currentDay.dayOfMonth
    val dayOfWeek = currentDay.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru"))
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

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
private fun ScheduleForSelectedDay(
    viewModel: ScheduleViewModel,
    selectedDate: LocalDate,
    isTeacher: Boolean,
    tint: Color,
    modifier: Modifier = Modifier
) {
    val hours = (0..23).toList()
    val listState = rememberLazyListState()
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
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
                HourRow(
                    viewModel = viewModel,
                    hour = hour,
                    tint = tint,
                    isTeacher = isTeacher
                )
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
private fun HourRow(
    viewModel: ScheduleViewModel,
    hour: Int,
    isTeacher: Boolean,
    tint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (isTeacher) {
                    viewModel.changeScreenTo(Route.Lesson.name)
                }
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

private fun getMonthName(date: LocalDate): String {
    val monthNames = arrayOf(
        "января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря"
    )
    return monthNames[date.monthNumber - 1]
}