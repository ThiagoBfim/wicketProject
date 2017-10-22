package n3m6.wicket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.util.CollectionUtils;

import n3m6.entity.Carro;
import n3m6.entity.Fabricante;
import n3m6.entity.Modelo;
import n3m6.entity.enuns.Categoria;
import n3m6.entity.enuns.Tracao;
import n3m6.service.CarroService;
import n3m6.service.ModeloService;
import n3m6.wicket.jsr303validator.BeanPropertyValidator;

@SuppressWarnings("serial")
public class CadastroPage extends HomePage {

	// public CadastroPage(String id, IModel<Carro> model) {
	// super(id, model);
	// this.carro.setObject(model.getObject());
	// }
	//
	// public CadastroPage(String id) {
	// super(id);
	// this.carro.setObject(new Carro());
	// }

	@Inject
	private CarroService carroService;

	@Inject
	private ModeloService modeloService;

	private IModel<Carro> carro = new Model<>();

	public CadastroPage() {
		this.carro.setObject(new Carro());
	}

	public CadastroPage(Carro carro) {
		this.carro.setObject(carro);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Carro> form = new Form<>("form", new CompoundPropertyModel<>(carro));
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback") {
			protected void onConfigure() {
				setVisible(form.hasError());
			};
		};
		add(feedbackPanel);
		String msg = "Cadastrar";
		if (carro.getObject().getId() != null) {
			msg = "Salvar";
		}
		form.add(createButtonSubmit("salvar", msg));
		TextField<String> placaText = createTextWithValidator("placa");
		placaText.add(new AjaxFormComponentUpdatingBehavior("change") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				placaText.setModelObject(placaText.getModelObject().toUpperCase());
				target.add(placaText);
			}

		});
		placaText.add(new PlacaValidator());
		form.add(placaText);

		AutoCompleteTextField<String> autoCompleteTextField = new AutoCompleteTextField<String>("modelo",
				new PropertyModel<>(carro, "modelo.descricao")) {

			@Override
			protected Iterator<String> getChoices(String input) {
				List<Modelo> modelos = modeloService.retrieveStartsWith(input);
				List<String> descricaoModelos = new ArrayList<>();
				modelos.forEach(m -> {
					descricaoModelos.add(m.getDescricao());
				});
				if (CollectionUtils.isEmpty(modelos)) {
					descricaoModelos.add(input);
				}
				return descricaoModelos.iterator();
			}
		};

		autoCompleteTextField.add(new BeanPropertyValidator<String>(Carro.class, "modelo"));
		form.add(autoCompleteTextField);

		RadioChoice<Tracao> radioGroupTracao = new RadioChoice<>("tracao", Arrays.asList(Tracao.values()),
				new ChoiceRenderer<Tracao>("descricao"));
		radioGroupTracao.setSuffix("   ");
		radioGroupTracao.add(new BeanPropertyValidator<Tracao>(Carro.class, "tracao"));
		form.add(radioGroupTracao);

		DropDownChoice<Categoria> dropDownCategoria = new DropDownChoice<Categoria>("categoria",
				new LoadableDetachableModel<List<Categoria>>() {
					@Override
					protected List<Categoria> load() {
						return Arrays.asList(Categoria.values());
					}
				});
		dropDownCategoria.add(new BeanPropertyValidator<Categoria>(Carro.class, "categoria"));
		form.add(dropDownCategoria);

		IModel<Fabricante> fabricanteModel = new PropertyModel<>(carro, "fabricante");
		TextField<Fabricante> fabricanteText = new TextField<Fabricante>("fabricante",
				new PropertyModel<>(carro, "fabricanteFormat"));
		fabricanteText.add(new BeanPropertyValidator<Fabricante>(Carro.class, "fabricante"));
		fabricanteText.setOutputMarkupId(true);
		form.add(fabricanteText);

		ModalWindow modalWindow = new ModalWindow("modal");
		ManterFabricantePanel fabricantePanel = new ManterFabricantePanel(modalWindow.getContentId(), fabricanteModel) {
			@Override
			protected void onSelecionarCheck(AjaxRequestTarget target) {
				modalWindow.close(target);
				fabricanteText.modelChanged();
				target.add(fabricanteText);
			}
		};
		modalWindow.showUnloadConfirmation(false);
		modalWindow.setContent(fabricantePanel);

		add(modalWindow);
		form.add(new AjaxLink<String>("pesquisarFabricante") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.add(fabricantePanel);
				modalWindow.show(target);
			}

		});

		add(form);

	}

	private TextField<String> createTextWithValidator(String id) {
		TextField<String> textField = new TextField<String>(id);
		textField.add(new BeanPropertyValidator<String>(Carro.class, id));
		return textField;
	}

	private Button createButtonSubmit(String id, String msg) {
		return new Button(id, new Model<>(msg)) {
			@Override
			public void onSubmit() {
				Modelo modelo = modeloService.findByDescricao(carro.getObject().getModelo().getDescricao());
				if (modelo != null) {
					carro.getObject().setModelo(modelo);
				} else {
					modelo = carro.getObject().getModelo();
					modelo.setId(null);
					modeloService.salvar(modelo);
				}
				carroService.salvar(carro.getObject());
				this.setResponsePage(new ConsultaPage());
			}
		};
	}

	public class PlacaValidator implements IValidator<String> {

		@Override
		public void validate(IValidatable<String> validatable) {
			final String field = validatable.getValue();
			Carro carroRetrived = carroService.findByPlaca(field);
			if (carroRetrived != null && (carro.getObject().getId() == null
					|| (carro.getObject().getId() != null && !carroRetrived.getId().equals(carroRetrived.getId())))) {
				validatable.error(new ValidationError().setMessage("Placa já existente"));
			}

		}
	}

}
