package com.example.checking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.Text;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class HelloApplication extends Application {

    public static int customerID = -1;
    public static String customerName = "";
    public static String passWord = "";

    @Override
    public void start(Stage stage) throws IOException {


        stage.setTitle("AANT Store");

        stage.show();

        mainPage(stage);



    }

    public static void mainPage(Stage stage){
        Pane main = new Pane();

        Label emailPh = new Label("Email id / Phone no.");
        emailPh.setLayoutY(200);
        emailPh.setLayoutX(100);

        Label password = new Label("Password");
        password.setLayoutY(250);
        password.setLayoutX(100);

        TextField EmailPh = new TextField();
        EmailPh.setLayoutX(300);
        EmailPh.setLayoutY(200);

        PasswordField Pass = new PasswordField();
        Pass.setLayoutX(300);
        Pass.setLayoutY(250);

        Label askingToRegister = new Label("Don't have an account? Register here.");
        askingToRegister.setLayoutX(100);
        askingToRegister.setLayoutY(450);

        Button registerC = new Button("Customer Sign up ");
        registerC.setLayoutX(350);
        registerC.setLayoutY(450);

        Button registerS = new Button("Login Supplier");
        registerS.setLayoutX(500);
        registerS.setLayoutY(100);
        registerS.setOnAction(e->{
            supplierLogin.show(stage);
        });

        Button registerE = new Button("Login Employee");
        registerE.setLayoutX(600);
        registerE.setLayoutY(100);
        registerE.setOnAction(e->{
            employeeLogin.show(stage);
        });
        main.getChildren().add(registerE);


        Button login = new Button("Log in");
        login.setLayoutX(400);
        login.setLayoutY(300);

        registerC.setOnAction(e->{
            System.out.println("asdad");
            signUpPageC(stage);
        });


        login.setOnAction(e->{
            customerName = EmailPh.getText();
            passWord = Pass.getText();
            String phoneoremail = EmailPh.getText();
            String pass = Pass.getText();
            String query = "select * from customers where (emailID = \'"+phoneoremail+"\') and accountPassword = \'"+pass+"\'";
            ResultSet rs = retrieveData(query, 2);

            try {

                if(rs.next()){
                    customerID = rs.getInt("customerID");
                    System.out.println("ID: "+customerID);
                    System.out.println("Logged in");
                    HomePage.show(stage);

                }else{
                    showError("Invalid Credentials", "No such user found");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        });

        main.getChildren().addAll(registerC,registerS, login, password, emailPh, Pass, EmailPh, askingToRegister);
        Scene loginScren = new Scene(main, 800, 600);
        stage.setScene(loginScren);
    }

    public static Connection connectToDB() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore", "root", "ComeCode1608");
            System.out.println("Works");

            return conn;
        }catch(Exception e2){
            System.out.println("Something is wrong");
            System.out.println(e2);
            return null;
        }
    }
    public static void signUpPageC(Stage stage){

        Pane registrationForm = new Pane();
        Scene registerScene = new Scene(registrationForm, 800, 600);

        Label first_name = new Label("First Name:");
        first_name.setLayoutY(100);
        first_name.setLayoutX(200);
        registrationForm.getChildren().addAll(first_name);

        TextField firstName = new TextField();
        firstName.setLayoutX(400);
        firstName.setLayoutY(100);
        registrationForm.getChildren().add(firstName);

        Label last_name = new Label("Last Name:");
        last_name.setLayoutY(140);
        last_name.setLayoutX(200);
        registrationForm.getChildren().addAll(last_name);

        TextField lastName = new TextField();
        lastName.setLayoutX(400);
        lastName.setLayoutY(140);
        registrationForm.getChildren().add(lastName);

        Label phone_no = new Label("Phone no:");
        phone_no.setLayoutY(180);
        phone_no.setLayoutX(200);
        registrationForm.getChildren().addAll(phone_no);

        TextField phoneNo = new TextField();
        phoneNo.setLayoutX(400);
        phoneNo.setLayoutY(180);
        registrationForm.getChildren().add(phoneNo);

        Label email_id = new Label("Email ID:");
        email_id.setLayoutY(220);
        email_id.setLayoutX(200);
        registrationForm.getChildren().addAll(email_id);

        TextField emailID = new TextField();
        emailID.setLayoutX(400);
        emailID.setLayoutY(220);
        registrationForm.getChildren().add(emailID);



        Label password = new Label("Password: ");
        password.setLayoutY(260);
        password.setLayoutX(200);
        registrationForm.getChildren().addAll(password);

        PasswordField passwordText = new PasswordField();
        passwordText.setLayoutX(400);
        passwordText.setLayoutY(260);
        registrationForm.getChildren().add(passwordText);

        Button submit = new Button("Submit");
        submit.setLayoutX(250);
        submit.setLayoutY(500);
        registrationForm.getChildren().add(submit);

        submit.setOnAction(e->{
            String fName = firstName.getText();
            String lName = lastName.getText();
            String emailId = emailID.getText();
            String pass = passwordText.getText();
            String phone = phoneNo.getText();

            String query = "insert into customers (firstName, lastName, phoneNo, emailID, accountPassword) values (\""+fName+"\", \""+lName+"\", \""+phone+"\", \""+emailId+"\", \""+pass+"\")";
            System.out.println(query);

            if(sendData(query, 2)){
                mainPage(stage);
            }

        });

        stage.setScene(registerScene);



    }

    public static ResultSet retrieveData(String query, int level){
        System.out.println(query+ level);
        if (level == 4){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore",employeeLogin.employeeEmail, employeeLogin.passWord);
                java.sql.Statement st;
                st = (java.sql.Statement) conn.createStatement();
                ResultSet rs = st.executeQuery(query);

                return rs;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        }
        else if(level == 3){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore",supplierLogin.supplierEmail, supplierLogin.passWord);
                java.sql.Statement st;
                st = (java.sql.Statement) conn.createStatement();
                ResultSet rs = st.executeQuery(query);

                return rs;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }


        } else if(level == 2){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore","root", "ComeCode1608");
                java.sql.Statement st;
                st = (java.sql.Statement) conn.createStatement();
                ResultSet rs = st.executeQuery(query);

                return rs;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        }else{
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore", HelloApplication.customerName, HelloApplication.passWord);
                java.sql.Statement st;
                st = (java.sql.Statement) conn.createStatement();
                ResultSet rs = st.executeQuery(query);

                return rs;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }


        }
    }
    public static boolean sendData(String query, int level){
        if(level==4){

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println(query);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore", employeeLogin.employeeEmail, employeeLogin.passWord);
                PreparedStatement pst = conn.prepareStatement(query);
                pst.executeUpdate();

                return true;

            }catch(Exception e2){

                showError("Something went wrong!","Could not register user\nError: "+e2.getMessage() );
                e2.printStackTrace();
                return false;

            }



        }
        else if(level == 3){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println(query);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore", supplierLogin.supplierEmail, supplierLogin.passWord);
                PreparedStatement pst = conn.prepareStatement(query);
                pst.executeUpdate();

                return true;

            }catch(Exception e2){

                showError("Something went wrong!","Could not register user\nError: "+e2.getMessage() );
                e2.printStackTrace();
                return false;

            }



        }
        else if(level == 2){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println(query);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore", "root", "ComeCode1608");

                PreparedStatement pst = conn.prepareStatement(query);
                pst.executeUpdate();

                return true;

            }catch(Exception e2){

                showError("Something went wrong!","Could not register user\nError: "+e2.getMessage() );
                e2.printStackTrace();
                return false;

            }


        }
        else if(level == 1){


            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println(query);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore", HelloApplication.customerName, HelloApplication.passWord);

                PreparedStatement pst = conn.prepareStatement(query);
                pst.executeUpdate();

                return true;

            }catch(Exception e2){

                showError("Something went wrong!","Could not register user\nError: "+e2.getMessage() );
                return false;

            }

        }
        return false;
    }
    public static ResultSet callFunction(String query, int level){
        try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailstore",  HelloApplication.customerName, HelloApplication.passWord);

                CallableStatement cstmt = conn.prepareCall(query);

                //Retrieving the result
                ResultSet rs = cstmt.executeQuery();
                return rs;

            } catch (ClassNotFoundException ex) {

                ex.printStackTrace();
            } catch (SQLException ex) {

                ex.printStackTrace();
            }
        return null;
    }

    public static void showError(String title, String main){
        Dialog<String> dialog = new Dialog<String>();

        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setWidth(400);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        dialog.setContentText(main);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        connectToDB();
        launch();
    }
    
    public static void showPopup(Stage stage, ArrayList<String> texts){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.initOwner(stage);
        VBox dialogVbox = new VBox();
        for (int i = 0; i<texts.size(); i++){
            dialogVbox.getChildren().addAll(new Label(texts.get(i)));
        }

        Scene dialogScene = new Scene(dialogVbox, 500, 500);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
}