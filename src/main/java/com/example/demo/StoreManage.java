package com.example.demo;

import com.example.demo.Layouts.RootLayout;
import com.example.demo.Mappers.StoresMapper;
import com.example.demo.Models.Store;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Input;
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

@Route("stores")
public class StoreManage extends RootLayout {
    StoresMapper storesMapper;
    InputStream is;
    SqlSessionFactory factory;
    SqlSession sqlSession;

    public StoreManage() throws IOException {
        VerticalLayout components = new VerticalLayout();
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = factory.openSession();
        storesMapper = sqlSession.getMapper(StoresMapper.class);


        Div header = new Div();
        H1 title = new H1("Administración de sucursales");
        title.getStyle().set("text-align","center");
        Paragraph description = new Paragraph();
        description.setText("El siguiente apartado solicita datos para registrar sucursales.");
        header.add(title);
        header.add(description);


        Input nameStore = new Input();
        nameStore.setType("Text");
        nameStore.setPlaceholder("Proporciona el nombre de la sucursal a crear");
        nameStore.setWidth("100%");
        nameStore.setHeight("30px");
        header.add(nameStore);

        Button addStoreButton = new Button();
        addStoreButton.setText("Agregar sucursal");
        addStoreButton.addClickListener(buttonClickEvent -> {

            if (nameStore.getValue() == null) {
                Notification notification = Notification.show("Faltan campos por agregar!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                return ;
            }

            Store storeToSearch = storesMapper.getByName(nameStore.getValue());
            if(storeToSearch!=null){
                Notification notification = Notification.show("Ya existe una sucursal con el mismo nombre!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                return ;
            }

            Store newStore = new Store(nameStore.getValue());
            storesMapper.insertStore(newStore);

            sqlSession.commit();

            Notification notification = Notification.show("Se ha registrado la sucursal con exito!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.MIDDLE);

            nameStore.clear();
        });
        header.add(addStoreButton);

        header.getStyle().set("display", "block");
        components.add(header);

        this.add(components);
        components.getStyle().set("display", "block");
    }
}
