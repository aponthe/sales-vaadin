package com.example.demo;

import com.example.demo.Components.Flex;
import com.example.demo.Components.LabelInput;
import com.example.demo.Layouts.RootLayout;
import com.example.demo.Mappers.ProductsMapper;
import com.example.demo.Models.Product;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouteAlias;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Route("products")
@RouteAlias("")
@RouteAlias("products")
public class ProductsManage extends RootLayout {
    ProductsMapper productsMapper;
    InputStream is;
    SqlSessionFactory factory;
    SqlSession sqlSession;

    public ProductsManage() throws IOException {
        VerticalLayout components = new VerticalLayout();
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = factory.openSession();
        productsMapper = sqlSession.getMapper(ProductsMapper.class);


        Div header = new Div();
        H1 title = new H1("Administración de productos");
        title.getStyle().set("text-align", "center");
        Paragraph description = new Paragraph();
        description.setText("El siguiente apartado solicita datos para registrar productos.");
        header.add(title);
        header.add(description);

        Flex inputs = new Flex();
        header.add(inputs);


        Input nameProductInput = new Input();
        nameProductInput.setType("Text");
        nameProductInput.setPlaceholder("Proporciona el nombre del producto");
        nameProductInput.setHeight("30px");
        LabelInput labelInputNameProduct = new LabelInput("Nombre del producto");
        labelInputNameProduct.add(nameProductInput);
        inputs.add(labelInputNameProduct);


        Input descriptionProductInput = new Input();
        descriptionProductInput.setType("Text");
        descriptionProductInput.setPlaceholder("Proporciona la especificación del producto");
        descriptionProductInput.setHeight("30px");
        LabelInput labelInputDescriptionProduct = new LabelInput("Especificación");
        labelInputDescriptionProduct.add(descriptionProductInput);
        inputs.add(labelInputDescriptionProduct);


        Input purchasePriceProductInput = new Input();
        purchasePriceProductInput.setType("Text");
        purchasePriceProductInput.setPlaceholder("Proporciona el precio de compra del producto");
        purchasePriceProductInput.setHeight("30px");
        LabelInput labelInputPurchasePriceProduct = new LabelInput("Precio compra");
        labelInputPurchasePriceProduct.add(purchasePriceProductInput);
        inputs.add(labelInputPurchasePriceProduct);


        Input salePriceProductInput = new Input();
        salePriceProductInput.setType("Text");
        salePriceProductInput.setPlaceholder("Proporciona el precio de venta del producto");
        salePriceProductInput.setHeight("30px");
        LabelInput labelInputSalePriceProduct = new LabelInput("Precio venta");
        labelInputSalePriceProduct.add(salePriceProductInput);
        inputs.add(labelInputSalePriceProduct);


        Button addProductButton = new Button();
        addProductButton.setText("Agregar producto");
        addProductButton.addClickListener(buttonClickEvent -> {
            if (nameProductInput.getValue() == null || descriptionProductInput.getValue() == null || purchasePriceProductInput.getValue() == null || salePriceProductInput.getValue() == null) {
                Notification notification = Notification.show("Faltan campos por agregar!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                return ;
            }

            Product productToSearch = productsMapper.getByName(nameProductInput.getValue());
            if(productToSearch!=null){
                Notification notification = Notification.show("Ya existe un producto con el mismo nombre!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                return ;
            }

            Product product = new Product(nameProductInput.getValue(), descriptionProductInput.getValue(), new BigDecimal(purchasePriceProductInput.getValue()).setScale(2, RoundingMode.HALF_UP), new BigDecimal(salePriceProductInput.getValue()).setScale(2, RoundingMode.HALF_UP));
            productsMapper.insertProduct(product);

            sqlSession.commit();

            Notification notification = Notification.show("Se ha registrado el producto con exito!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.MIDDLE);

            nameProductInput.clear();
            descriptionProductInput.clear();
            purchasePriceProductInput.clear();
            salePriceProductInput.clear();

        });
        header.add(addProductButton);

        header.getStyle().set("display", "block");
        components.add(header);

        this.add(components);
        components.getStyle().set("display", "block");
    }
}
