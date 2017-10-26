package n3m6.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import n3m6.wicket.assets.css.CssAssets;
import n3m6.wicket.assets.js.JavascriptAssets;

public class HomePage extends WebPage {

	protected final PageParameters parameters;

	public HomePage(final PageParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		CssAssets.renderHead(response);
		JavascriptAssets.renderHead(response);
	}

	
}
