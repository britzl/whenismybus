package se.springworks.whenismybus;

import java.util.Random;

import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.logging.LoggerFactory;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WidgetService extends Service {

	public static final String EXTRA_WIDGETSIZE = "EXTRA_WIDGETSIZE";
	
	private Logger logger = LoggerFactory.getLogger(WidgetService.class);
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		logger.info("Called");
		// Create some random data

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
		int size = intent.getIntExtra(EXTRA_WIDGETSIZE, 1);

		ComponentName thisWidget = new ComponentName(getApplicationContext(), Widget1x1.class);
		int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
		logger.info("From Intent" + String.valueOf(allWidgetIds.length));
		logger.info("Direct" + String.valueOf(allWidgetIds2.length));

		for (int widgetId : allWidgetIds) {
			// Create some random data
			int number = (new Random().nextInt(100));
			logger.info(String.valueOf(number));


			final RemoteViews remoteViews;
			switch(size) {
			case 2:
				remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout_2x1);
				update2x1(remoteViews);
				break;
				
			case 1:
			default:
				remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout_1x1);
				update1x1(remoteViews);
				break;
			}
			
			// Register an onClickListener
			Intent clickIntent = new Intent(getApplicationContext(), Widget1x1.class);

			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

//			PendingIntent pendingIntent = PendingIntent.getBroadcast(
//					getApplicationContext(), 0, clickIntent,
//					PendingIntent.FLAG_UPDATE_CURRENT);
//			remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		stopSelf();

		return super.onStartCommand(intent, 0, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	private void update2x1(RemoteViews remoteViews) {
		logger.debug("update2x1()");
		remoteViews.setTextViewText(R.id.widgetfrom, "from: " + 1);
		remoteViews.setTextViewText(R.id.widgetto, "to: " + 2);
		remoteViews.setTextViewText(R.id.widgetwhen, "when: " + 3);
	}
	
	private void update1x1(RemoteViews remoteViews) {
		logger.debug("update1x1()");
		remoteViews.setTextViewText(R.id.widgetwhen, "when: " + 3);
	}

}
