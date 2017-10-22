package n3m6.wicket;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
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
				item.add(new Label("modelo", new PropertyModel<>(item.getModel(), "modelo.descricao")));
				item.add(new Label("tracao", new PropertyModel<>(item.getModel(), "tracao.descricao")));
				item.add(new Label("categoria", new PropertyModel<>(item.getModel(), "categoria.descricao")));
				item.add(new Label("fabricante", new PropertyModel<>(item.getModel(), "fabricante")));

			}
		};
		add(carroListView);
		carroListView.setVisible(!carros.isEmpty());
		WebMarkupContainer containerNoResult = new WebMarkupContainer("noResult") {
			protected void onConfigure() {
				setVisible(carros.isEmpty());
			};
		};
		add(containerNoResult);
	}
}
