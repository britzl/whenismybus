package se.springworks.whenismybus;

import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.logging.LoggerFactory;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Widget2x1 extends AppWidgetProvider {

	private Logger logger = LoggerFactory.getLogger(Widget2x1.class);

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		logger.debug("onUpdate method called");
		// Get all ids
		ComponentName thisWidget = new ComponentName(context, Widget2x1.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		// Build the intent to call the service
		Intent intent = new Intent(context.getApplicationContext(), WidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
		intent.putExtra(WidgetService.EXTRA_WIDGETSIZE, 2);

		// Update the widgets via the service
		context.startService(intent);
	}

}
