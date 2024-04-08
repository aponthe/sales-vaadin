package com.example.demo.Layouts;

import com.example.demo.Components.Navbar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RootLayout extends VerticalLayout {
    /**
     * this is the base layout for views
     * v15
     */
    public RootLayout() {
        Navbar navbar = new Navbar();
        this.add(navbar);
    }
}
