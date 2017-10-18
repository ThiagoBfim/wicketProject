package n3m6.wicket.jsr303validator;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.model.AbstractPropertyModel;

/**
 * <p>
 * Listener that adds a {@link BeanPropertyValidator} on
 * {@link org.apache.wicket.markup.html.form.AbstractTextComponent
 * AbstractTextFormComponents} right after the {@link Component#onAfterRender()}
 * has been invoked.
 * 
 * <p>
 * Usage in {@link org.apache.wicket.protocol.http.WebApplication
 * WebApplication} overrides :
 * 
 * <pre>
 * addPostComponentOnBeforeRenderListener(new JSR303ValidationListener());
 * 
 * @see org.apache.wicket.application.IComponentOnAfterRenderListener
 * 
 * @author ophelie salm (zenika)
 * 
 */
public class JSR303ValidationListener implements IComponentOnBeforeRenderListener {

	/**
	 * {@inheritDoc}
	 */
	public void onBeforeRender(Component component) {
		if ((component instanceof AbstractTextComponent<?>) && !component.hasBeenRendered()) {
			processComponent((AbstractTextComponent<?>) component);
		}
	}

	/**
	 * @param component
	 */
	@SuppressWarnings("unchecked")
	private void processComponent(AbstractTextComponent<?> component) {

		if (component.getModel() instanceof AbstractPropertyModel<?>) {
			AbstractPropertyModel<?> model = (AbstractPropertyModel<?>) component.getModel();

			if ((model.getChainedModel() != null) && (model.getChainedModel().getObject() != null)) {
				Class<?> modelObjectClass = model.getChainedModel().getObject().getClass();

				component.add(new BeanPropertyValidator(modelObjectClass, model.getPropertyExpression()));
			}
		}

	}
}
