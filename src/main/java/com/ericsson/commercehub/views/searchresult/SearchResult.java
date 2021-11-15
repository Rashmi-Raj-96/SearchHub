package com.ericsson.commercehub.views.searchresult;

import com.ericsson.commercehub.controller.ProductSearchController;
import com.ericsson.commercehub.dto.Product;
import com.ericsson.commercehub.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@PageTitle("Search Result")
@Route(value = "searchresult", layout = MainLayout.class)
@Tag("super-search-view")
@JsModule("./views/search/search-result.ts")
public class SearchResult extends LitTemplate implements HasComponents, HasStyle {

    @Autowired
    private ProductSearchController productSearchController;

    @Id
    private H2 header;

    @Id
    private Select<String> sortBy;

    private List<Product> productList = new ArrayList<Product>();

    public SearchResult() {
        productList.clear();
        addClassNames("super-search-view", "flex", "flex-col", "h-full");
        UI.getCurrent().getPage().fetchCurrentURL(url -> {getUrl(url);});
        sortBy.setEnabled(false);
        sortBy.setItems("Price Low to High","Price High to Low");
        sortBy.addValueChangeListener(selectStringComponentValueChangeEvent -> {
            sortDataByValue(sortBy.getValue());
        });
    }

    private void sortDataByValue(String sortValue) {
        if (sortValue.equalsIgnoreCase("Price Low to High")) {
            Collections.sort(productList, Comparator.comparing(Product::getPrice));
            displayProductList(productList);
        } else if (sortValue.equalsIgnoreCase("Price High to Low")) {
            productList.sort(Comparator.comparing(Product::getPrice).reversed());
            displayProductList(productList);
        }
    }

    public void getUrl(URL url){
        if(url.getQuery() != null) {
            String[] queryArray = url.getQuery().split("=");
            if (queryArray.length != 0) {
                getProducts(queryArray[1]);
            }
        }
    }

    public void getProducts(String productName){
        productList = productSearchController.searchProduct(productName);
        if (!productList.isEmpty()) {
            this.header.setText("No of Products Found: "+ productList.size());
            displayProductList(productList);
        }
    }

    public void displayProductList(List<Product> productList){
        removeAll();
        sortBy.setEnabled(true);
        productList.stream().filter(Objects::nonNull).filter(product -> product.getImageURL() != null).forEach(product -> {
            add (new ProductDetails(product.getName(),product.getImageURL(),String.valueOf(product.getPrice()),product.getSiteName(),product.getProductURL()));
        });
    }
}