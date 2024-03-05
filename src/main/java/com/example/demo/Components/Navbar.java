package com.example.demo.Components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

public class Navbar extends Div {
    public Navbar() {
        Button productRoute = new Button("Productos", buttonClickEvent -> {
            this.getUI().ifPresent(ui ->
                    ui.navigate("products"));
        });
        Button reportViewRoute = new Button("Reporte de ventas", buttonClickEvent -> {
            this.getUI().ifPresent(ui ->
                    ui.navigate("report"));
        });
        Button salesRegisterRoute = new Button("Ventas", buttonClickEvent -> {
            this.getUI().ifPresent(ui ->
                    ui.navigate("sales-register"));
        });
        Button storageRoute = new Button("Sucursales", buttonClickEvent -> {
            this.getUI().ifPresent(ui ->
                    ui.navigate("stores"));
        });

        Flex flex = new Flex();
        flex.add(productRoute);
        flex.add(storageRoute);
        flex.add(reportViewRoute);
        flex.add(salesRegisterRoute);

        this.add(flex);
    }
}
