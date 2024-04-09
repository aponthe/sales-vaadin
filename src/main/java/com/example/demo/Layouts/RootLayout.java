package com.example.demo.Layouts;

import com.example.demo.Components.Navbar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RootLayout extends VerticalLayout {
    /**
     * this is the base layout for views
     * v17
     */
    /**
     * pull request pr
     * 
     * test from testing-pull-request-dev
     * pull request test
     * v1
     */
    public RootLayout() {
        Navbar navbar = new Navbar();
        this.add(navbar);
    }
}
