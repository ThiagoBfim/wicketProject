package n3m6.wicket.assets.css;

import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.PackageResourceReference;

import lombok.experimental.UtilityClass;
import n3m6.wicket.HomePage;

@UtilityClass
public class CssAssets {

	public static void renderHead(IHeaderResponse response) {
		response.render(CssContentHeaderItem
				.forReference(new PackageResourceReference(HomePage.class, "assets/css/bootstrap.min.css")));

	}
}
