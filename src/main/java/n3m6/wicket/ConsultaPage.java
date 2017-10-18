package n3m6.wicket;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import n3m6.entity.Carro;
import n3m6.service.CarroService;
import n3m6.wicket.assets.css.CssAssets;

@SuppressWarnings("serial")
public class ConsultaPage extends WebPage {

	@Inject
	private CarroService carroService;

	@Override
	public void renderHead(IHeaderResponse response) {
		CssAssets.renderHead(response);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		List<Carro> carros = carroService.listar();
		ListModel<Carro> carrosModel = new ListModel<Carro>();
		carrosModel.setObject(carros);
		ListView<Carro> carroListView = new ListView<Carro>("carros", carrosModel) {

			@Override
			protected void populateItem(ListItem<Carro> item) {
				item.add(new Label("placa", new PropertyModel<>(item.getModel(), "placa")));

			}
		};
		add(carroListView);
	}
}
