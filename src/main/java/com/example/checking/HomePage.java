package com.example.checking;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePage {
    public static void show(Stage stage){
        Pane main = new Pane();
        Button back = new Button("Back");
        back.setLayoutY(20);
        back.setLayoutX(20);
        back.setOnAction(e-> {
            HelloApplication a = new HelloApplication();
            try {
                a.start(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        TextField searchBar = new TextField();
        searchBar.setPrefWidth(500);
        searchBar.setLayoutX(20);
        searchBar.setLayoutY(50);

        Image search = new Image("file:images/search_icon.png");
        ImageView searchIcon = new ImageView(search);
        searchIcon.setLayoutY(50);
        searchIcon.setLayoutX(525);
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        searchIcon.setOnMouseClicked(e->{
            String input = searchBar.getText().strip();
            String query = "select * from products where products.productName = \'"+input+"\'";
            ResultSet rs = HelloApplication.retrieveData(query, 1);
            try{

                searchResult.show(stage, rs);

            }catch(Exception ex){
                HelloApplication.showError("Error", "Some Error Occured"+ex.getMessage());
            }


        });

        Image profile = new Image("file:images/profile.jpg");
        ImageView profileImg = new ImageView(profile);
        profileImg.setLayoutY(50);
        profileImg.setLayoutX(650);
        profileImg.setFitHeight(50);
        profileImg.setFitWidth(50);


        Image eatableImg = new Image("file:images/eatables.jpg");
        Image apparelsImg = new Image("file:images/apparels.jpg");
        Image electronicsImg = new Image("file:images/electronics.jpg");
        Image furnitureImg = new Image("file:images/furniture.jpg");

        ImageView Eatables = new ImageView(eatableImg);
        Eatables.setLayoutX(100);
        Eatables.setLayoutY(100);
        Eatables.setFitHeight(200);
        Eatables.setFitWidth(200);
        main.getChildren().add(Eatables);

        Label eat = new Label("Eatables");
        eat.setLayoutX(150);
        eat.setLayoutY(320);


        ImageView Apparels = new ImageView(apparelsImg);
        Apparels.setLayoutX(400);
        Apparels.setLayoutY(100);
        Apparels.setFitHeight(200);
        Apparels.setFitWidth(200);
        main.getChildren().add(Apparels);
        Label appr = new Label("Apparels");
        appr.setLayoutX(450);
        appr.setLayoutY(320);

        ImageView Electronics = new ImageView(electronicsImg);
        Electronics.setLayoutX(100);
        Electronics.setLayoutY(350);
        Electronics.setFitHeight(200);
        Electronics.setFitWidth(200);
        main.getChildren().add(Electronics);
        Label elect = new Label("Electronics");
        elect.setLayoutX(140);
        elect.setLayoutY(555);



        ImageView Furniture = new ImageView(furnitureImg);
        Furniture.setLayoutX(400);
        Furniture.setLayoutY(350);
        Furniture.setFitHeight(200);
        Furniture.setFitWidth(240);
        main.getChildren().add(Furniture);
        Label furni = new Label("Furniture");
        furni.setLayoutX(440);
        furni.setLayoutY(555);
        main.getChildren().addAll(back, furni, elect, appr, eat, searchBar, searchIcon, profileImg);
        Scene homeScene = new Scene(main, 800, 600);
        stage.setScene(homeScene);

        profileImg.setOnMouseClicked(e->{
            try {
                showProfile(stage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Eatables.setOnMouseClicked(e->{
            try {
                showProdsInCategory(stage, 1);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Apparels.setOnMouseClicked(e->{
            try {
                showProdsInCategory(stage, 2);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Furniture.setOnMouseClicked(e->{
            try {
                showProdsInCategory(stage, 3);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Electronics.setOnMouseClicked(e->{
            try {
                showProdsInCategory(stage, 4);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }


    public static void showProfile(Stage stage) throws SQLException {
        profilePage.show(stage);
    }

    public static void showProdsInCategory(Stage stage, int category) throws SQLException {
        if(category == 1){
            allProductsPage.show(stage, category);
        }else if(category == 2){
            allProductsPage.show(stage, category);
        }else if(category == 3){
            allProductsPage.show(stage, category);
        }else if(category == 4){
            allProductsPage.show(stage, category);
        }
    }

}
