package com.example.demo;

import com.example.demo.Layouts.RootLayout;
import com.example.demo.Mappers.ProductsMapper;
import com.example.demo.Mappers.SalesMapper;
import com.example.demo.Mappers.StoresMapper;
import com.example.demo.Models.Product;
import com.example.demo.Models.Sale;
import com.example.demo.Models.Store;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;


@Route("sales-register")
public class SalesRegister extends RootLayout {
    StoresMapper storesMapper;
    ProductsMapper productsMapper;
    SalesMapper salesMapper;
    InputStream is;
    SqlSessionFactory factory;
    SqlSession sqlSession;


    public SalesRegister() throws IOException {
        VerticalLayout components = new VerticalLayout();
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = factory.openSession();
        productsMapper = sqlSession.getMapper(ProductsMapper.class);
        storesMapper = sqlSession.getMapper(StoresMapper.class);
        salesMapper = sqlSession.getMapper(SalesMapper.class);

        Div header = new Div();
        H1 title = new H1("Registro de venta de productos");
        title.getStyle().set("text-align", "center");
        Paragraph description = new Paragraph();
        description.setText("El siguiente apartado registra ventas.");
        header.add(title);
        header.add(description);


        ComboBox storesComboBox = new ComboBox<Store>();
        storesComboBox.setLabel("Sucursal");
        storesComboBox.setPlaceholder("Seleccionar");
        storesComboBox.setItems(storesMapper.findAll());
        header.add(storesComboBox);


        ComboBox productComboBox = new ComboBox<Product>();
        productComboBox.setLabel("Producto");
        productComboBox.setPlaceholder("Seleccionar");
        productComboBox.setItems(productsMapper.findAll());
        header.add(productComboBox);


        Input amountProduct = new Input();
        amountProduct.setType("Text");
        amountProduct.setPlaceholder("Proporciona la cantidad del producto");
        amountProduct.setWidth("100%");
        amountProduct.setHeight("30px");
        header.add(amountProduct);


        Input priceProduct = new Input();
        priceProduct.setType("Text");
        priceProduct.setPlaceholder("Precio del producto");
        priceProduct.setWidth("100%");
        priceProduct.setHeight("30px");
        priceProduct.setEnabled(false);
        header.add(priceProduct);


        Input subTotalInput = new Input();
        subTotalInput.setType("Text");
        subTotalInput.setPlaceholder("Subtotal");
        subTotalInput.setWidth("100%");
        subTotalInput.setHeight("30px");
        subTotalInput.setEnabled(false);
        header.add(subTotalInput);

        Input ivaInput = new Input();
        ivaInput.setType("Text");
        ivaInput.setPlaceholder("IVA");
        ivaInput.setWidth("100%");
        ivaInput.setHeight("30px");
        ivaInput.setEnabled(false);
        header.add(ivaInput);

        Input totalInput = new Input();
        totalInput.setType("Text");
        totalInput.setPlaceholder("Total");
        totalInput.setWidth("100%");
        totalInput.setHeight("30px");
        totalInput.setEnabled(false);
        header.add(totalInput);


        Button saleButton = new Button();
        saleButton.setText("Vender");
        header.add(saleButton);

        header.getStyle().set("display", "block");
        components.add(header);

        this.add(components);
        components.getStyle().set("display", "block");

        productComboBox.addValueChangeListener(event -> {
            if(event.isFromClient()) {
                Product unProducto = (Product) event.getValue();
                priceProduct.setValue(unProducto.getSalePrice().toString());
            }
        });

        amountProduct.addValueChangeListener(inputBlurEvent -> {
            if (productComboBox.getValue() == null) {
                Notification notification = Notification.show("Falta proporcionar el producto!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                return;
            }
            try {
                BigDecimal amount = new BigDecimal(amountProduct.getValue());
                Product product = (Product) productComboBox.getValue();

                BigDecimal salesPrice = product.getSalePrice();

                BigDecimal subtotal = amount.multiply(salesPrice);
                subTotalInput.setValue(subtotal.toString());


                BigDecimal iva = subtotal.multiply(new BigDecimal("0.16"));
                iva = iva.setScale(2, RoundingMode.HALF_UP);
                ivaInput.setValue(iva.toString());

                BigDecimal total = subtotal.add(iva);
                total = total.setScale(2, RoundingMode.HALF_UP);
                totalInput.setValue(total.toString());

            } catch (NumberFormatException e) {

            }
        });


        saleButton.addClickListener(buttonClickEvent -> {
            if (productComboBox.getValue() == null || productComboBox.getValue() == null || amountProduct.getValue() == null) {
                Notification notification = Notification.show("Faltan campos por agregar!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                return;
            }

            Product product = (Product) productComboBox.getValue();
            Store store = (Store) storesComboBox.getValue();
            Sale sale = new Sale(new BigDecimal(amountProduct.getValue()), product.getId(), store.getId(), new Date().toString());
            salesMapper.insertSale(sale);
            sqlSession.commit();



            Notification notification = Notification.show("Se ha registrado la venta!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.MIDDLE);

            amountProduct.clear();
            storesComboBox.clear();
            productComboBox.clear();
            priceProduct.clear();
            subTotalInput.clear();
            ivaInput.clear();
            totalInput.clear();


        });

    }
}
