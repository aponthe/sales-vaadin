package com.example.demo.Layouts;

import com.example.demo.Components.Navbar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RootLayout extends VerticalLayout {
    /**
     * this is the base layout for views
     * v17
     */
    /**
     * another pull request
     * pull request pr
     * 
     * test from testing-pull-request-dev
     * pull request test
     * v1
     */
    /**
     * this is incorrect, otro cambio desde QA
     * dev4
     */
    public RootLayout() {
        Navbar navbar = new Navbar();
        this.add(navbar);
    }
}
