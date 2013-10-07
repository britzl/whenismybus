package se.springworks.whenismybus;

import java.util.Random;

import se.springworks.android.utils.bundle.BundleUtil;
import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.logging.LoggerFactory;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WidgetService extends Service {

	public static final String EXTRA_WIDGETSIZE = "EXTRA_WIDGETSIZE";
	
	private Logger logger = LoggerFactory.getLogger(WidgetService.class);
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		final Context context = getApplicationContext();

		BundleUtil.toString(intent.getExtras());
		final int size = intent.getIntExtra(EXTRA_WIDGETSIZE, 1);
		logger.info("Called. Size = %d", size);

		final ComponentName thisWidget;
		switch(size) {
		case 4:
			thisWidget = new ComponentName(context, Widget4x1.class);
			break;
		case 2:
			thisWidget = new ComponentName(context, Widget2x1.class);
			break;
		case 1:
		default:
			thisWidget = new ComponentName(context, Widget1x1.class);
			break;
		}

		
		final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		final int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
		final int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
		logger.info("From Intent " + String.valueOf(allWidgetIds.length));
		logger.info("Direct " + String.valueOf(allWidgetIds2.length));

		for (int widgetId : allWidgetIds) {
			// Create some random data
			int number = (new Random().nextInt(100));
			logger.info(String.valueOf(number));

			
			final Intent clickIntent;
			final RemoteViews remoteViews;
			switch(size) {
			case 4:
				clickIntent = new Intent(getApplicationContext(), Widget4x1.class);
				remoteViews = update4x1();
				break;
				
			case 2:
				clickIntent = new Intent(getApplicationContext(), Widget2x1.class);
				remoteViews = update2x1();
				break;
				
			case 1:
			default:
				clickIntent = new Intent(getApplicationContext(), Widget1x1.class);
				remoteViews = update1x1();
				break;
			}

			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(EXTRA_WIDGETSIZE, size);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					getApplicationContext(), 0, clickIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);

			remoteViews.setOnClickPendingIntent(R.id.container, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		stopSelf();

		return super.onStartCommand(intent, 0, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	private RemoteViews update4x1() {
		logger.debug("update4x1()");
		RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout_4x1);
		remoteViews.setTextViewText(R.id.widgetfrom, "from: " + new Random().nextInt());
		remoteViews.setTextViewText(R.id.widgetto, "to: " + 2);
		remoteViews.setTextViewText(R.id.widgetwhen, "when: " + 3);
		return remoteViews;
	}
	
	private RemoteViews update2x1() {
		logger.debug("update2x1()");
		RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout_2x1);
		remoteViews.setTextViewText(R.id.widgetfrom, "from: " + new Random().nextInt());
		remoteViews.setTextViewText(R.id.widgetto, "to: " + 2);
		remoteViews.setTextViewText(R.id.widgetwhen, "when: " + 3);
		return remoteViews;
	}
	
	private RemoteViews update1x1() {
		logger.debug("update1x1()");
		RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout_1x1);
		remoteViews.setTextViewText(R.id.widgetwhen, "when: " + new Random().nextInt());
		return remoteViews;
	}

}
