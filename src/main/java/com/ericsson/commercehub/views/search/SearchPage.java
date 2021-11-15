package com.ericsson.commercehub.views.search;

import com.ericsson.commercehub.views.MainLayout;
import com.ericsson.commercehub.views.searchresult.SearchResult;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Search")
@Route(value = "search", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@JsModule("./views/search/search-view.ts")
@CssImport("./themes/myapp/styles.css")
public class SearchPage extends VerticalLayout {

    private TextField name;
    private Button button;
    private VerticalLayout verticalLayout;
    private SearchResult searchView;

    public SearchPage() {
        searchView = new SearchResult();
        setupLayout();
        addHeader();
        addForm();
        addFooter();
        add(verticalLayout);
    }


    private void addFooter() {
        Anchor anchor = new Anchor("http://localhost:9090/aboutus", "About Us");
        H2 footer = new H2();
        anchor.addClassName("glow");
        footer.setHeight("70px");
        verticalLayout.add(anchor,footer);
    }


    private void setupLayout() {
        verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        verticalLayout.addClassNames("bg-image");
        verticalLayout.setSpacing(true);
    }

    private void addHeader() {
        Image image = new Image("Shopping Hub-logos.jpeg", "Shopping Hub");
        image.addClassNames("logo-image");
        H2 header = new H2("Shopping Hub");
        header.addClassNames("header-font-size");
        verticalLayout.add(image, header);
    }

    private void addForm() {
        HorizontalLayout layout = new HorizontalLayout();
        name = new TextField();
        name.addClassName("box-color");
        name.setWidth("400px");
        name.setPlaceholder("Search by product...");
        name.focus();
        name.setAutofocus(true);
        button = new Button("Search", VaadinIcon.SEARCH.create());
        button.addClassName("button-color");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.setEnabled(false);
        button.setWidth("120px");
        name.addInputListener(inputEvent -> {
            button.setEnabled(true);
        });
        button.addClickListener(buttonClickEvent -> {
            String value = name.getValue();
            UI.getCurrent().getPage().setLocation("searchresult" + "?" + "search=" + value);
        });
        layout.add(name, button);
        verticalLayout.add(layout);
    }
}
