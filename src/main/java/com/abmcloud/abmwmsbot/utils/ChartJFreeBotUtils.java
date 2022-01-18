package com.abmcloud.abmwmsbot.utils;

import com.abmcloud.abmwmsbot.dto.ReportData;
import com.abmcloud.abmwmsbot.dto.ReportRow;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ChartJFreeBotUtils {

    public static String createLineChart(ReportData reportData) {
        String fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ".png"); //chart filename to save
        String chartTitle = reportData.getName();

        XYSeries series = new XYSeries("XYSeries name");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //filling dataset
        for (ReportRow row: reportData.getData()) {
            dataset.addValue(Double.valueOf(row.getValue()), "", row.getParam());
        }

        JFreeChart chart = ChartFactory.createLineChart(chartTitle, "Легенда", "Значения", dataset);
        chart.setPadding(new RectangleInsets(5,5,5,5));
        chart.setBackgroundPaint(Color.WHITE);
        chart.setElementHinting(true);

        try {
            ChartUtils.saveChartAsPNG(new File(fileName), chart, 640, 480);
            log.info("File saved successfully \"{}\"", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("Failed to save file \"{}\" with error \"{}\"", fileName, e.getMessage());
            return "";
        }
    }

    public static String createPieChart(ReportData reportData) {
        String fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ".png"); //chart filename to save
        String chartTitle = reportData.getName();


        DefaultPieDataset dataset = new DefaultPieDataset();
        //filling dataset
        for (ReportRow row: reportData.getData()) {
            dataset.setValue(row.getParam(), Double.valueOf(row.getValue()));
        }

        JFreeChart chart = ChartFactory.createPieChart(chartTitle,  dataset);

        try {
            ChartUtils.saveChartAsPNG(new File(fileName), chart, 640, 480);
            log.info("File saved successfully \"{}\"", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("Failed to save file \"{}\" with error \"{}\"", fileName, e.getMessage());
            return "";
        }
    }

    public static String createBarChart(ReportData reportData) {
        String fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ".png"); //chart filename to save
        String chartTitle = reportData.getName();

        XYSeries series = new XYSeries("XYSeries name");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //filling dataset
        for (ReportRow row: reportData.getData()) {
            dataset.addValue(Double.valueOf(row.getValue()), "", row.getParam());
        }

        JFreeChart chart = ChartFactory.createBarChart(chartTitle, "Легенда", "Значения", dataset);

        try {
            ChartUtils.saveChartAsPNG(new File(fileName), chart, 640, 480);
            log.info("File saved successfully \"{}\"", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("Failed to save file \"{}\" with error \"{}\"", fileName, e.getMessage());
            return "";
        }
    }

    /*public static String createLineChart(ReportData reportData) {
        StringBuffer legend = new StringBuffer(); //format "val|val|val"
        StringBuffer data = new StringBuffer("a:"); //format "a:val,val,val"

        List<ReportRow> reportRows = reportData.getData();
        for (int i = 0; i < reportRows.size(); i++) {
            ReportRow row = reportRows.get(i);
            legend.append(row.getParam());
            data.append(row.getValue());

            if (i < reportRows.size() - 1) { //if not last
                legend.append("|");
                data.append(",");
            }
        }

        ImageCharts chart = new ImageCharts()
                .chtt(reportData.getName() + new Date())
                .cht("lc") //p | p3 | pc | pd | ls | lc | lxy | ls:nda | lc:nda | lxy:nda | pa | bb | gv | gv:dot | gv:neato | gv:circo | gv:fdp | gv:osage | gv:twopi | qr | r
                .chl(legend.toString())
                .chd(data.toString());
//                .chs("700x300");

//        String filePath = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), "jpg");
        String filePath = "";

        boolean isFileCreated = false;
        try {
//            chart.toFile(filePath);
            filePath = chart.toURL();
            isFileCreated = true;
        } catch (Exception e) {
            log.error("Failed to save the file: \"{}\"", e.getMessage());
        }

        if (isFileCreated)
            return filePath;
        else
            return null;
    }*/
    
}
