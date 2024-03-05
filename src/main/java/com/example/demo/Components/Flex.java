package com.example.demo.Components;

import com.vaadin.flow.component.html.Div;

public class Flex extends Div {
    public Flex(){
        this.getStyle().set("display", "flex");
        this.getStyle().set("justify-content", "space-between");
        this.setWidth("100%");
    }
}
