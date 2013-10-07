package se.springworks.whenismybus;


public class Widget1x1 extends WidgetProvider {

	@Override
	protected Class<? extends WidgetProvider> getWidgetClass() {
		return Widget1x1.class;
	}

	@Override
	protected int getWidgetSize() {
		return 1;
	}

	@Override
	protected int getWidgetLayoutId() {
		return R.layout.widget_layout_1x1;
	}

}
