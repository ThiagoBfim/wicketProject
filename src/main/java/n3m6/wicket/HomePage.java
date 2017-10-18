package n3m6.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import n3m6.wicket.assets.css.CssAssets;

public class HomePage extends WebPage {

	public HomePage(final PageParameters parameters) {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(generateRedirectAjaxLink("cadastrar", new CadastroPage()));
		add(generateRedirectAjaxLink("consultar", new ConsultaPage()));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		CssAssets.renderHead(response);
	}

	private AjaxLink<Void> generateRedirectAjaxLink(String id, WebPage webPage) {
		return new AjaxLink<Void>(id) {

			@Override
			public void onClick(AjaxRequestTarget target) {
				this.setResponsePage(webPage);
			}

		};
	}
}
