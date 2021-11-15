package com.ericsson.commercehub.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "Shopping Hub", shortName = "Shopping Hub", enableInstallPrompt = false)
@Theme(themeFolder = "myapp")
@PageTitle("Shopping Hub")
public class MainLayout extends AppLayout {

    public MainLayout() {
    }
}

