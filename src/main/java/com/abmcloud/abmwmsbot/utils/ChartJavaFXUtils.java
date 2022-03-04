package com.abmcloud.abmwmsbot.utils;

//import javafx.application.Application;

import com.abmcloud.abmwmsbot.dto.ReportData;
import com.abmcloud.abmwmsbot.dto.ReportRow;
import com.abmcloud.abmwmsbot.enums.ChartTypes;
import com.abmcloud.abmwmsbot.service.BotService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
public class ChartJavaFXUtils implements Runnable {

    private final ChartTypes chartType;
    private final ReportData report;
    private String fileName = "";

    public ChartJavaFXUtils(ReportData report, ChartTypes chartType) {
        this.report = report;
        this.chartType = chartType;
    }

    @Override
    public void run() {
        switch (chartType) {
            case BAR -> createBarChart(report);
            case LINE -> createLineChart(report);
            case PIE -> createPieChart(report);
        }
    }

    public void createBarChart(ReportData reportData) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);

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
        saveChart(chart);
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
        series1.setName("Warehouse");
        series1.getData().addAll(reportData.getData().stream()
                .map((ReportRow row) -> new XYChart.Data<>(row.getParam(), Double.parseDouble(row.getValue())))
                .collect(Collectors.toList()));

        chart.getData().addAll(series1);
        saveChart(chart);
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

        saveChart(chart);
    }

    private void saveChart(Chart chart) {
        Scene scene = new Scene(chart, 800, 600);

        String filePath = "src/main/resources/files/";
        String fileName = String.format("%s.%s", filePath + RandomStringUtils.randomAlphanumeric(8), "png"); //chart filename to save
        WritableImage image = scene.snapshot(null);
        File file = new File(fileName);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            log.info("File saved successfully \"{}\"", fileName);
        } catch (IOException e) {
            log.error("Failed to save file \"{}\" with error \"{}\"", fileName, e.getMessage());
        }
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
