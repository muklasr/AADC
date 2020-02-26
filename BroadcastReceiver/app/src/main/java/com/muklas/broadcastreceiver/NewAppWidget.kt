package com.muklas.broadcastreceiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.DateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) { // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) { // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) { // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        private const val SHARED_PREF_FILE = "com.muklas.broadcastreceiver.PREF"
        private const val COUNT_KEY = "count"
        fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val prefs =
                context.getSharedPreferences(SHARED_PREF_FILE, 0)
            var count = prefs.getInt(COUNT_KEY + appWidgetId, 0)
            count++
            val dateString =
                DateFormat.getTimeInstance(DateFormat.LONG)
                    .format(Date())
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            views.setTextViewText(R.id.appWidgetId, appWidgetId.toString())
            views.setTextViewText(R.id.appWidgetUpdate, "$count @$dateString")

            val prefEditor = prefs.edit()
            prefEditor.putInt(COUNT_KEY + appWidgetId, count)
            prefEditor.apply()

            val intentUpdate = Intent(context, NewAppWidget::class.java)
            intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            val idArray = intArrayOf(appWidgetId)
            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)

            val pendingUpdate = PendingIntent.getBroadcast(
                context,
                appWidgetId,
                intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setOnClickPendingIntent(R.id.btnUpdate, pendingUpdate)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}