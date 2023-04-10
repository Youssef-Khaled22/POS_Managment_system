package com.example.pos_system;

import com.example.pos_system.DAO.CashierDAOImp;
import com.example.pos_system.DAO.ProductDAOImp;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

public class StatisticsController {

    @FXML
    private BarChart<String, Integer> BarChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label BestSelling = new Label();

    @FXML
    private Label expire;

    @FXML
    void initialize() {
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Days left to expire");
        ProductDAOImp productDAOImp = new ProductDAOImp();
        int[] days = productDAOImp.getdaysLeftForExpire();
        String[] productName = productDAOImp.getNameOfNearest5ExpDate();
        series.getData().add(new XYChart.Data<>(productName[0], days[0]));
        series.getData().add(new XYChart.Data<>(productName[1], days[1]));
        series.getData().add(new XYChart.Data<>(productName[2], days[2]));
        series.getData().add(new XYChart.Data<>(productName[3], days[3]));
        series.getData().add(new XYChart.Data<>(productName[4], days[4]));
        BarChart.getData().addAll(series);
        BarChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        CashierDAOImp cashier = new CashierDAOImp();
        int[] sales = cashier.getMost5Selling();
        String[] products = cashier.getNameOfMost5Selling();
        ObservableList<PieChart.Data> pieChartDate = FXCollections.observableArrayList(
                new PieChart.Data(products[0], sales[0]),
                new PieChart.Data(products[1], sales[1]),
                new PieChart.Data(products[2], sales[2]),
                new PieChart.Data(products[3], sales[3]),
                new PieChart.Data(products[4], sales[4]),
                new PieChart.Data("Other", productDAOImp.allSales() - (sales[0] + sales[1] + sales[2] + sales[3] + sales[4])));
        pieChartDate.forEach(data -> data.nameProperty().bind(
                Bindings.concat(data.getName() + " ", (float) ((short) ((float) ((data.getPieValue() / productDAOImp.allSales()) * 100)) * 100) / 100 + "%")));
        pieChart.getData().addAll(pieChartDate);
        BestSelling.setText("Best selling product is " + products[0]);
        expire.setText("The closest product to expire is " + productName[0]);
    }

}
