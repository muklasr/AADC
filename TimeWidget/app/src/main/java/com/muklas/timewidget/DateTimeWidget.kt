package com.muklas.timewidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class DateTimeWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.date_time_widget)
    val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    val dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault())

    val timeString = timeFormat.format(Date())
    val dateString = dateFormat.format(Date())

    views.setTextViewText(R.id.tvTime, timeString)
    views.setTextViewText(R.id.tvTimeShadow, timeString)
    views.setTextViewText(R.id.tvDate, dateString)
    views.setTextViewText(R.id.tvDateShadow, dateString)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}