package se.springworks.whenismybus;


public class Widget4x1 extends WidgetProvider {

	@Override
	protected Class<? extends WidgetProvider> getWidgetClass() {
		return Widget4x1.class;
	}

	@Override
	protected int getWidgetSize() {
		return 4;
	}

	@Override
	protected int getWidgetLayoutId() {
		return R.layout.widget_layout_4x1;
	}

}
