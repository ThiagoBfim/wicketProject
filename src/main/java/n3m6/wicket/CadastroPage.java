package n3m6.wicket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.util.CollectionUtils;

import n3m6.entity.Carro;
import n3m6.entity.Modelo;
import n3m6.entity.Tracao;
import n3m6.service.CarroService;
import n3m6.service.ModeloService;
import n3m6.wicket.assets.css.CssAssets;
import n3m6.wicket.assets.js.JavascriptAssets;
import n3m6.wicket.jsr303validator.BeanPropertyValidator;

@SuppressWarnings("serial")
public class CadastroPage extends WebPage {

	@Inject
	private CarroService carroService;

	@Inject
	private ModeloService modeloService;

	private IModel<Carro> carro = new Model<>();

	public CadastroPage() {
		carro.setObject(new Carro());
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		CssAssets.renderHead(response);
		JavascriptAssets.renderHead(response);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form form = new Form<>("form", new CompoundPropertyModel<>(carro));
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback") {
			protected void onConfigure() {
				setVisible(form.hasError());
			};
		};
		add(feedbackPanel);

		form.add(createButtonSubmit("salvar"));
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

			@SuppressWarnings("unchecked")
			@Override
			protected Iterator getChoices(String input) {
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

		RadioChoice<Tracao> group = new RadioChoice<>("tracao", Arrays.asList(Tracao.values()),
				new ChoiceRenderer<Tracao>("descricao"));
		group.setSuffix("   ");
		group.add(new BeanPropertyValidator<Tracao>(Carro.class, "tracao"));
		form.add(group);

		form.add(createTextWithValidator("categoria"));
		form.add(createTextWithValidator("fabricante"));
		add(form);

	}

	private TextField<String> createTextWithValidator(String id) {
		TextField<String> textField = new TextField<String>(id);
		textField.add(new BeanPropertyValidator<String>(Carro.class, id));
		return textField;
	}

	private Button createButtonSubmit(String id) {
		return new Button(id) {
			@Override
			public void onSubmit() {
				Modelo modelo = modeloService.findByDescricao(carro.getObject().getModelo().getDescricao());
				if (modelo != null) {
					carro.getObject().setModelo(modelo);
				} else {
					modeloService.salvar(carro.getObject().getModelo());
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
