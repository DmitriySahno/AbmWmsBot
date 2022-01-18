package com.abmcloud.abmwmsbot.utils;

//import javafx.application.Application;

import com.abmcloud.abmwmsbot.dto.ReportData;
import com.abmcloud.abmwmsbot.dto.ReportRow;
import com.abmcloud.abmwmsbot.enums.ChartTypes;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class ChartJavaFXUtils extends Thread {

    private final SendPhoto sendPhoto;
    private final ChartTypes chartType;
    private final ReportData report;
    private String fileName = "";

    public ChartJavaFXUtils(SendPhoto sendPhoto, ReportData report, ChartTypes chartType) {
        this.sendPhoto = sendPhoto;
        this.report = report;
        this.chartType = chartType;
        PlatformImpl.startup(() -> {
        }); //init java fx application
    }

    @Override
    public void run() {

        Runnable reportRunnable = () -> {
            switch (chartType) {
                case BAR -> createBarChart(report);
                case LINE -> createLineChart(report);
                case PIE -> createPieChart(report);
            }
        };
        PlatformImpl.runLater(reportRunnable);

        try {
            Thread.currentThread().wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            sendPhoto.setPhoto(new InputFile(new FileInputStream(new File(fileName)), report.getName()));
        } catch (FileNotFoundException e) {
            log.error("Failed to set photo \"{}\" to photoMessage with error \"{}\"", fileName, e.getMessage());
        }

    }

    public void createBarChart(ReportData reportData) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);

        bc.setTitle(reportData.getName());
        xAxis.setLabel(reportData.getLegendName());
        yAxis.setLabel(reportData.getValuesName());

        //filling dataset
        XYChart.Series series1 = new XYChart.Series();
        //series1.setName("2003");
        series1.getData().addAll(reportData.getData());

        bc.getData().addAll(series1);
        Scene scene = new Scene(bc, 800, 600);
        fileName = saveChart(scene);
        notifyAll();
    }

    public void createLineChart(ReportData reportData) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String, Number> chart = new LineChart<String, Number>(xAxis, yAxis);

        chart.setTitle(reportData.getName());
        xAxis.setLabel(reportData.getLegendName());
        yAxis.setLabel(reportData.getValuesName());

        //filling dataset
        XYChart.Series series1 = new XYChart.Series();
        //series1.setName("2003");
        series1.getData().addAll(reportData.getData().stream()
                .map((ReportRow row) -> new XYChart.Data<>(row.getParam(), Double.parseDouble(row.getValue())))
                .collect(Collectors.toList()));


        chart.getData().addAll(series1);
        Scene scene = new Scene(chart, 800, 600);
        fileName = saveChart(scene);
        notifyAll();

    }

    public void createPieChart(ReportData reportData) {
        double sum = reportData.getData().stream().mapToDouble(rr -> Double.parseDouble(rr.getValue())).sum();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                reportData.getData()
                        .stream()
                        .map((row) -> new PieChart.Data(row.getParam(), Double.parseDouble(row.getValue()) / sum))
                        .collect(Collectors.toList()));


        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(reportData.getName());
        chart.setLegendSide(Side.LEFT);

        Scene scene = new Scene(chart, 800, 600);
        fileName = saveChart(scene);
        notifyAll();
    }

    private String saveChart(Scene scene) {
        String filePath = "src/main/resources/files/";
        String fileName = String.format("%s.%s", filePath + RandomStringUtils.randomAlphanumeric(8), "png"); //chart filename to save
//        Platform.startup(() -> {
        WritableImage image = scene.snapshot(null);
        File file = new File(fileName);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            log.info("File saved successfully \"{}\"", fileName);
        } catch (IOException e) {
            log.error("Failed to save file \"{}\" with error \"{}\"", fileName, e.getMessage());
        }
//        });
        return fileName;
    }

    /*public static void createBarChart() {
        Platform.startup(() -> {
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
            bc.setTitle("Country Summary");
            xAxis.setLabel("Country");
            yAxis.setLabel("Value");

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("2003");
            series1.getData().add(new XYChart.Data("austria", 25601.34));
            series1.getData().add(new XYChart.Data("brazil", 20148.82));
            series1.getData().add(new XYChart.Data("france", 10000));
            series1.getData().add(new XYChart.Data("italy", 35407.15));
            series1.getData().add(new XYChart.Data("usa", 12000));

            XYChart.Series series2 = new XYChart.Series();
            series2.setName("2004");
            series2.getData().add(new XYChart.Data("austria", 57401.85));
            series2.getData().add(new XYChart.Data("brazil", 41941.19));
            series2.getData().add(new XYChart.Data("france", 45263.37));
            series2.getData().add(new XYChart.Data("italy", 117320.16));
            series2.getData().add(new XYChart.Data("usa", 14845.27));

            XYChart.Series series3 = new XYChart.Series();
            series3.setName("2005");
            series3.getData().add(new XYChart.Data("austria", 45000.65));
            series3.getData().add(new XYChart.Data("brazil", 44835.76));
            series3.getData().add(new XYChart.Data("france", 18722.18));
            series3.getData().add(new XYChart.Data("italy", 17557.31));
            series3.getData().add(new XYChart.Data("usa", 92633.68));

            bc.getData().addAll(series1, series2, series3);
            Scene scene = new Scene(bc, 800, 600);
            saveChart(scene);
        });


    }*/
}
