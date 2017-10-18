package n3m6.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {

	public HomePage(final PageParameters parameters) {
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(generateRedirectAjaxLink("cadastrar", new CadastroPage()));
		add(generateRedirectAjaxLink("consultar", new ConsultaPage()));
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
