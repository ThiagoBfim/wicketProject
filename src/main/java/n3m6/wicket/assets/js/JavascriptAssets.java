package n3m6.wicket.assets.js;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

import lombok.experimental.UtilityClass;
import n3m6.wicket.HomePage;

@UtilityClass
public class JavascriptAssets {

	public static void renderHead(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem
				.forReference(new PackageResourceReference(HomePage.class, "assets/js/jquery.maskedinput.min.js")));
		response.render(JavaScriptHeaderItem
				.forReference(new PackageResourceReference(HomePage.class, "assets/js/mascara.js")));

	}
}
