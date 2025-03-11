package com.iacc.robert_morales_semana_8

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast

class AppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.app_cldndc)

            configureButton(context, views, appWidgetId, R.id.btn_sumar_decimal, "com.iacc.robert_morales_semana_8.SUMAR_DECIMAL")
            configureButton(context, views, appWidgetId, R.id.btn_restar_decimal, "com.iacc.robert_morales_semana_8.RESTA_DECIMAL")
            configureButton(context, views, appWidgetId, R.id.btn_sumar_binario, "com.iacc.robert_morales_semana_8.SUMAR_BINARIO")
            configureButton(context, views, appWidgetId, R.id.btn_restar_binario, "com.iacc.robert_morales_semana_8.RESTA_BINARIO")

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
    private fun configureButton( context: Context, views: RemoteViews, appWidgetId: Int, buttonId: Int, action: String) {

        val intent = Intent(context, AppWidgetProvider::class.java).apply {
            this.action = action
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(buttonId, pendingIntent)
    }
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val action = intent.action
        when (action) {
            "com.iacc.robert_morales_semana_8.SUMAR_DECIMAL" -> {
                Toast.makeText(context, "Operación: Sumar Decimal", Toast.LENGTH_SHORT).show()
            }
            "com.iacc.robert_morales_semana_8.RESTA_DECIMAL" -> {
                Toast.makeText(context, "Operación: Restar Decimal", Toast.LENGTH_SHORT).show()
            }
            "com.iacc.robert_morales_semana_8.SUMAR_BINARIO" -> {
                Toast.makeText(context, "Operación: Sumar Binario", Toast.LENGTH_SHORT).show()
            }
            "com.iacc.robert_morales_semana_8.RESTA_BINARIO" -> {
                Toast.makeText(context, "Operación: Restar Binario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
