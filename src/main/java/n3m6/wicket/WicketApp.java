package n3m6.wicket;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApp extends WebApplication {

	@Override
	public Class<HomePage> getHomePage() {

		return HomePage.class; // return default page
	}

	public void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		mountPage("/carro", CadastroPage.class);
		mountPage("/carros", ConsultaPage.class);
	}
}
