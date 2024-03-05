package com.example.demo;

import com.example.demo.Components.Flex;
import com.example.demo.Layouts.RootLayout;
import com.example.demo.Mappers.ProductsMapper;
import com.example.demo.Mappers.SalesMapper;
import com.example.demo.Mappers.StoresMapper;
import com.example.demo.Models.Product;
import com.example.demo.Models.Report;
import com.example.demo.Models.Store;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Div;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Route("report")
public class Reportview extends RootLayout {
    SalesMapper salesMapper;
    ProductsMapper productsMapper;
    StoresMapper storesMapper;
    InputStream is;
    SqlSessionFactory factory;
    SqlSession sqlSession;

    public Reportview() throws IOException {
        VerticalLayout components = new VerticalLayout();
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = factory.openSession();
        salesMapper = sqlSession.getMapper(SalesMapper.class);
        storesMapper = sqlSession.getMapper(StoresMapper.class);
        productsMapper = sqlSession.getMapper(ProductsMapper.class);


        Div header = new Div();
        H1 title = new H1("Reporte de ventas");
        title.getStyle().set("text-align", "center");
        Paragraph description = new Paragraph();
        description.setText("El siguiente apartado genera el reporte de ventas");
        header.add(title);
        header.add(description);

        Flex inputs = new Flex();
        header.add(inputs);


        DatePicker firstDate = new DatePicker();
        firstDate.setPlaceholder("Proporciona fecha inicial");
        firstDate.setWidth("30%");
        firstDate.setLabel("Fecha inicial");
        inputs.add(firstDate);

        DatePicker lastDate = new DatePicker();
        lastDate.setPlaceholder("Proporciona fecha final");
        lastDate.setWidth("30%");
        lastDate.setLabel("Fecha final");
        inputs.add(lastDate);


        ComboBox storesComboBox = new ComboBox<Store>();
        storesComboBox.setPlaceholder("Seleccionar sucursal");
        storesComboBox.setItems(storesMapper.findAll());
        storesComboBox.setLabel("Sucursal");
        storesComboBox.getStyle().setWidth("30%");
        inputs.add(storesComboBox);

        Button generateButton = new Button();
        generateButton.setText("Buscar");
        header.add(generateButton);

        Grid<Report> reportGrid = new Grid();

        reportGrid.addColumn(column -> {
            return column.getProduct().getName();
        }).setHeader("Producto");
        reportGrid.addColumn(column -> {
            return column.getAmountProduct().setScale(2, RoundingMode.HALF_UP);
        }).setHeader("Cantidad");
        reportGrid.addColumn(column -> {
            return  column.getSubtotal().setScale(2, RoundingMode.HALF_UP);
        }).setHeader("Subtotal");
        reportGrid.addColumn(column -> {
            return column.getTotalIVA().setScale(2, RoundingMode.HALF_UP);
        }).setHeader("Total IVA");
        reportGrid.addColumn(column -> {
            return column.getTotal().setScale(2, RoundingMode.HALF_UP);
        }).setHeader("Total");
        header.add(reportGrid);


        generateButton.addClickListener(buttonClickEvent -> {
            if (firstDate.getValue() != null && lastDate.getValue() != null && storesComboBox.getValue() != null) {

                Store store = (Store) storesComboBox.getValue();
                List<Report> reportSales = salesMapper.getByFilter(firstDate.getValue().toString(),lastDate.getValue().toString(),store);

                for (Report report : reportSales) {
                    Product product = productsMapper.getById(report.getProductId());
                    report.setProduct(product);

                    BigDecimal subTotal = report.getAmountProduct().multiply(product.getSalePrice());
                    subTotal = subTotal.setScale(2,RoundingMode.HALF_UP);
                    report.setSubtotal(subTotal);

                    BigDecimal totalIVA = report.getSubtotal().multiply(new BigDecimal("0.16"));
                    totalIVA = totalIVA.setScale(2,RoundingMode.HALF_UP);
                    report.setTotalIVA(totalIVA);

                    BigDecimal total = subTotal.add(totalIVA);
                    total = total.setScale(2,RoundingMode.HALF_UP);
                    report.setTotal(total);
                }

                Notification notification = Notification.show("Reporte generado!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.MIDDLE);

                reportGrid.setItems(reportSales);
            }
        });

        header.getStyle().set("display", "block");
        components.add(header);

        this.add(components);
        components.getStyle().set("display", "block");
    }
}
