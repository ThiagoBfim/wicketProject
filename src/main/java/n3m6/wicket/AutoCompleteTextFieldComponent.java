package n3m6.wicket;

import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class AutoCompleteTextFieldComponent<T> extends AutoCompleteTextField {
	 
	public AutoCompleteTextFieldComponent(String id, PropertyModel propertyModel) {
		super(id, propertyModel);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@Override
	public MarkupContainer setDefaultModel(IModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Iterator getChoices(String input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
