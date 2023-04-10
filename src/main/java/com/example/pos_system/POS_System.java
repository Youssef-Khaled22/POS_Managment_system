package com.example.pos_system;

import com.jfoenix.controls.JFXSnackbar;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class POS_System extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("pos.png"))));
        Scene scene = new Scene(new FXMLLoader(POS_System.class.getResource("StartPage.fxml")).load());
        Parent root= scene.getRoot();
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String doHashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");//"SHA"
            messageDigest.update(password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : resultByteArray) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void changeScene(ActionEvent actionEvent,String newScene){
        AtomicReference<Double> x= new AtomicReference<>(0.0);
        AtomicReference<Double> y= new AtomicReference<>(0.0);
        try {
            Scene scene = new Scene(new FXMLLoader(POS_System.class.getResource(newScene+".fxml")).load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = scene.getRoot();
            root.setOnMousePressed(event -> {
                x.set(event.getSceneX());
                y.set(event.getSceneY());
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - x.get());
                stage.setY(event.getScreenY() - y.get());
            });
            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void error(AnchorPane pane, String message, double time, int textSize, double barHeight) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        Label label = new Label(message);
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(label, Duration.seconds(time), null);
        snackbar.enqueue(snackbarEvent);
        label.setStyle("-fx-background-color: red ; -fx-text-fill: white ; -fx-alignment: center ; -fx-font-size: " + textSize + "px");
        label.setPrefWidth(pane.getPrefWidth());
        label.setPrefHeight(barHeight);
    }

    public static void information(AnchorPane pane, String message, double time, int textSize, double barHeight) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        Label label = new Label(message);
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(label, Duration.seconds(time), null);
        snackbar.enqueue(snackbarEvent);
        label.setStyle("-fx-background-color: blue ; -fx-text-fill: white ; -fx-alignment: center ; -fx-font-size: " + textSize + "px");
        label.setPrefWidth(pane.getPrefWidth());
        label.setPrefHeight(barHeight);
    }
}