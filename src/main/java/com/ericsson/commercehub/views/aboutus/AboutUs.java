package com.ericsson.commercehub.views.aboutus;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(value = "aboutus")
public class AboutUs extends VerticalLayout {


    private VerticalLayout verticalLayout;
    private Button button;

    public AboutUs() {
        setupLayout();
        headerLayout();
        addHeaders();
        addImages();
        verticalLayout.addClassName("about-page-bgcolor");
        add(verticalLayout);
    }

    private void setupLayout() {
        verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        verticalLayout.setSpacing(true);
    }

    private void headerLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        button = new Button("Go To Search Page",VaadinIcon.ANGLE_LEFT.create());
        button.addClassName("gotosearchpage");
        button.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().setLocation("search");
        });
        H1 header = new H1("About Us");
        header.addClassName("about-header");
        Image eric = new Image("EricssonLogo.png","EricssonImage");
        eric.addClassNames("about-eric");
        layout.add(button,header,eric);
        verticalLayout.add(layout);
    }

    private void addHeaders(){
        H1 h1 = new H1("Our mission is to ensure online ");
        h1.addClassName("h1");
        H1 h2 = new H1("shopping at one step. Shopping Hub");
        h2.addClassName("h2");
        H1 h3 = new H1("will make your search journey shorter.");
        h3.addClassName("h3");
        verticalLayout.add(h1,h2,h3);
    }

    private void addImages() {
        Image searchImage = new Image("OnlineShopping.png","searchImage");
        searchImage.addClassName("about-searchimage");
        Image personImage = new Image("PersonalShopping.png","personImage");
        personImage.addClassName("about-personimage");
        Image addToCartImage = new Image("AddToCartDay.png","personImage");
        personImage.addClassName("about-addToCartimage");
        verticalLayout.add(searchImage,personImage,addToCartImage);
    }

}
