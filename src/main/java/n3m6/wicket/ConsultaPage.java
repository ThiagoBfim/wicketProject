package n3m6.wicket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import n3m6.entity.Carro;
import n3m6.entity.Fabricante;
import n3m6.entity.enuns.Categoria;
import n3m6.entity.enuns.Tracao;
import n3m6.service.CarroService;
import n3m6.wicket.assets.css.CssAssets;
import n3m6.wicket.jsr303validator.BeanPropertyValidator;

@SuppressWarnings("serial")
public class ConsultaPage extends WebPage {

	@Inject
	private CarroService carroService;
	private List<Carro> carros = new ArrayList<>();

	private WebMarkupContainer containerTable;

	@Override
	public void renderHead(IHeaderResponse response) {
		CssAssets.renderHead(response);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		IModel<Carro> carro = new Model<>();
		carro.setObject(new Carro());
		ListModel<Carro> carrosModel = new ListModel<Carro>();
		Form<Carro> form = new Form<>("form", new CompoundPropertyModel<>(carro));
		TextField<String> placaText = new TextField<>("placa");
		placaText.add(new AjaxFormComponentUpdatingBehavior("blur") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				carros = carroService.listar();
				carrosModel.setObject(carros);
				target.add(containerTable);
			}

		});
		form.add(placaText);

		TextField<String> modeloText = new TextField<>("modelo");
		form.add(modeloText);

		RadioChoice<Tracao> radioGroupTracao = new RadioChoice<>("tracao", Arrays.asList(Tracao.values()),
				new ChoiceRenderer<Tracao>("descricao"));
		radioGroupTracao.setSuffix("   ");
		form.add(radioGroupTracao);
		
		DropDownChoice<Categoria> dropDownCategoria = new DropDownChoice<Categoria>("categoria",
				new LoadableDetachableModel<List<Categoria>>() {
					@Override
					protected List<Categoria> load() {
						return Arrays.asList(Categoria.values());
					}
				});
		form.add(dropDownCategoria);
		
//		IModel<Fabricante> fabricanteModel = new PropertyModel<>(carro, "fabricante");
		TextField<Fabricante> fabricanteText = new TextField<Fabricante>("fabricante");
		form.add(fabricanteText);

		add(form);
		criarComponentsTabela(carrosModel);
	}

	private void criarComponentsTabela(ListModel<Carro> carrosModel) {
		containerTable = new WebMarkupContainer("containerTable") {
			@Override
			protected void onConfigure() {
				carros = carroService.listar();
				carrosModel.setObject(carros);
			}
		};
		containerTable.setOutputMarkupId(true);
		carros = carroService.listar();
		carrosModel.setObject(carros);

		WebMarkupContainer colunas = new WebMarkupContainer("colunas") {
			@Override
			protected void onConfigure() {
				setVisible(!carros.isEmpty());
			}
		};
		containerTable.add(colunas);
		ListView<Carro> carroListView = new ListView<Carro>("carros", carrosModel) {

			@Override
			protected void populateItem(ListItem<Carro> item) {
				item.add(new Label("placa", new PropertyModel<>(item.getModel(), "placa")));
				item.add(new Label("modelo", new PropertyModel<>(item.getModel(), "modelo.descricao")));
				item.add(new Label("tracao", new PropertyModel<>(item.getModel(), "tracao.descricao")));
				item.add(new Label("categoria", new PropertyModel<>(item.getModel(), "categoria.descricao")));
				item.add(new Label("fabricante", new PropertyModel<>(item.getModel(), "fabricante")));
				item.add(createButtonExcluir("excluir", item.getModelObject().getId()));
				item.add(createButtonEditar("editar", item.getModelObject()));
			}

		}.setReuseItems(true);
		containerTable.add(carroListView);
		carroListView.setVisible(!carros.isEmpty());
		WebMarkupContainer containerNoResult = new WebMarkupContainer("noResult") {
			protected void onConfigure() {
				setVisible(carros.isEmpty());
			};
		};
		containerTable.add(containerNoResult);
		add(containerTable);
	}

	private AjaxLink<String> createButtonExcluir(String id, Integer idCarro) {
		return new AjaxLink<String>(id) {

			@Override
			public void onClick(AjaxRequestTarget target) {
				carroService.remover(idCarro);
				target.add(containerTable);
			}
		};
	}

	private Component createButtonEditar(String id, Carro carro) {
		return new AjaxLink<String>(id) {

			@Override
			public void onClick(AjaxRequestTarget target) {
				this.setResponsePage(new CadastroPage(carro));
			}
		};
	}
}
