
module edu.esprit.crudoff{
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.web;
        requires org.controlsfx.controls;
        requires com.dlsc.formsfx;
        requires net.synedra.validatorfx;
        requires org.kordamp.ikonli.javafx;
        requires org.kordamp.bootstrapfx.core;
        requires eu.hansolo.tilesfx;
        requires java.sql;
    requires java.mail;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.jackson2;
    requires com.google.api.client.auth;
    requires java.desktop;
    requires com.google.gson;
    requires org.json;
    //requires java.mail;



    opens edu.esprit.crudoff.tests to javafx.fxml;
        opens edu.esprit.crudoff.services to javafx.fxml;
        opens edu.esprit.crudoff.utilis to javafx.fxml;
        opens edu.esprit.crudoff.entities to javafx.fxml;
        exports edu.esprit.crudoff.services;
        exports edu.esprit.crudoff.entities;
        exports edu.esprit.crudoff.utilis;
        exports edu.esprit.crudoff.tests;
        }