package com.ericsson.commercehub.views.searchresult;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;

@JsModule("./views/search/image-card.ts")
@Tag("image-card")
public class ProductDetails extends LitTemplate {

    @Id
    private Image image;

    @Id
    private Span header;

    @Id
    private Span subtitle;

    @Id
    private Paragraph text;

    @Id
    private Span badge;

    @Id
    private Anchor anchor;


    public ProductDetails(String text, String url, String price, String siteName, String siteUrl) {
        this.image.setSrc(url);
        this.image.setAlt(text);
        this.header.setText(text);
        this.subtitle.setText("$" + price.toString());
        this.anchor.setHref(siteUrl);
        this.anchor.setTarget("_blank");
        this.badge.setText("Buy Now On " + siteName);
    }
}
