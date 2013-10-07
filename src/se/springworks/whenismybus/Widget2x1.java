package se.springworks.whenismybus;


public class Widget2x1 extends WidgetProvider {

	@Override
	protected Class<? extends WidgetProvider> getWidgetClass() {
		return Widget2x1.class;
	}

	@Override
	protected int getWidgetSize() {
		return 2;
	}

	@Override
	protected int getWidgetLayoutId() {
		return R.layout.widget_layout_2x1;
	}

}
