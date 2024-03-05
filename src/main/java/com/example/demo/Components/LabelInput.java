package com.example.demo.Components;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;

public class LabelInput extends Div {
    private Html label;
    public LabelInput(String label){
        this.label = new Html("<b>"+label+"</b>");
        this.add(this.label);
        this.getStyle().set("display","flex");
        this.getStyle().set("flex-direction","column");
    }
}
