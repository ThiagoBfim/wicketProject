package n3m6.wicket;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import n3m6.entity.Fabricante;
import n3m6.service.FabricanteService;
import n3m6.wicket.assets.css.CssAssets;
import n3m6.wicket.jsr303validator.BeanPropertyValidator;

@SuppressWarnings("serial")
public class ManterFabricantePanel extends Panel {

	private IModel<Fabricante> model = new Model<>();
	private IModel<Fabricante> modelInterno = new Model<>();
	private IModel<Fabricante> modelSelecionado = new Model<>();
	private Form<Fabricante> form;
	private List<Fabricante> fabricantes;
	private ListModel<Fabricante> fabricantesModel = new ListModel<Fabricante>();
	@Inject
	private FabricanteService fabricanteService;

	public ManterFabricantePanel(String id, IModel<Fabricante> model) {
		super(id, model);
		this.model = model;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		CssAssets.renderHead(response);
	}

	@Override
	protected void onConfigure() {
		modelInterno.setObject(new Fabricante());
		fabricantes = fabricanteService.listar();
		fabricantes.forEach(f -> {
			if (f.equals(model.getObject())) {
				f.setSelecionado(Boolean.TRUE);
				return;
			}
		});
		fabricantesModel.setObject(fabricantes);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		modelInterno = new Model<>();
		modelInterno.setObject(new Fabricante());

		
		RadioGroup<Fabricante> group = new RadioGroup<Fabricante>("group", new PropertyModel<>(modelInterno, "selecionado")) {
			@Override
			protected void onConfigure() {
				setVisible(!fabricantes.isEmpty());
			}
		};

		form = new Form<>("form", new CompoundPropertyModel<>(modelInterno));
		TextField<String> nomeText = new TextField<>("nome");
		nomeText.add(new BeanPropertyValidator<String>(Fabricante.class, "nome"));
		nomeText.add(new AjaxFormComponentUpdatingBehavior("blur") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				fabricantes = fabricanteService.findByNomeIgnoreCaseContaining(nomeText.getModelObject());
				onChangeNameFabricante();
				target.add(form);

			}
			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e) {
				super.onError(target, e);
				fabricantes = fabricanteService.listar();
				onChangeNameFabricante();
				form.getFeedbackMessages().clear();
				target.add(form);
			}
			
			private void onChangeNameFabricante() {
				
				fabricantes.forEach(f -> {
					if (f.equals(model.getObject())) {
						f.setSelecionado(Boolean.TRUE);
						return;
					}
				});
				fabricantesModel.setObject(fabricantes);
			}
		});
		form.add(nomeText);
		Label paisLabel = new Label("labelPais", "País:") {
			@Override
			protected void onConfigure() {
				setVisible(fabricantes.isEmpty());
			}
		};
		form.add(paisLabel);
		TextField<String> paisText = new TextField<>("pais");

		paisText.add(new BeanPropertyValidator<String>(Fabricante.class, "pais"));
		paisText.add(new Behavior() {
			@Override
			public void onConfigure(Component component) {
				paisText.setVisible(fabricantes.isEmpty());
			}
		});
		form.add(paisText);

		ListView<Fabricante> fabricanteListView = new ListView<Fabricante>("fabricantes", fabricantesModel) {

			@Override
			protected void populateItem(ListItem<Fabricante> item) {
				item.add(new Label("nome", new PropertyModel<>(item.getModel(), "nome")));
				item.add(new Label("pais", new PropertyModel<>(item.getModel(), "pais")));
				Radio<Fabricante> fabricanteSelecionado = new Radio<Fabricante>("selecionado", item.getModel());
				fabricanteSelecionado.add(new AjaxEventBehavior("change") {

					@Override
					protected void onEvent(AjaxRequestTarget target) {
						modelSelecionado.setObject(item.getModelObject());

					}
				});
				item.add(fabricanteSelecionado);

			}
		}.setReuseItems(false);

		WebMarkupContainer containerNoResult = new WebMarkupContainer("noResult") {
			protected void onConfigure() {
				setVisible(fabricantes.isEmpty());
			};
		};
		form.add(containerNoResult);
		group.add(fabricanteListView);	
		form.add(group);
		form.add(createButtonSubmit("salvar"));

		group.add(createButtonSelecionar("selecionar"));

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback") {
			protected void onConfigure() {
				setVisible(form.hasError());
			};
		};
		add(feedbackPanel);

		add(form);
	}

	private AjaxLink<String> createButtonSelecionar(String id) {
		return new AjaxLink<String>(id) {

			@Override
			public void onClick(AjaxRequestTarget target) {
				model.setObject(modelSelecionado.getObject());
				onSelecionarCheck(target);
			}
		};
	}

	private Button createButtonSubmit(String id) {
		return new Button(id) {

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(fabricantes.isEmpty());
			}

			@Override
			public void onSubmit() {
				this.getParent().modelChanged();
				model.setObject(modelInterno.getObject());
				fabricanteService.salvar(model.getObject());
			}

			@Override
			public void onError() {
				// target.add(form);
				System.out.println("teste");
			}
		};
	}

	protected void onSelecionarCheck(AjaxRequestTarget target) {
		/*
		 * Método para ser sobrescrito caso a opção de selecioanr seja acioanda.
		 */

	}
}
