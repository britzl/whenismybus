package se.springworks.whenismybus.application;

import se.springworks.android.utils.application.BaseApplication;
import se.springworks.android.utils.inject.GrapeGuice;

public class Application extends BaseApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		GrapeGuice.getInjector(this);
	}

}
