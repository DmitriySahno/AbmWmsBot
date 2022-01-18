package com.abmcloud.abmwmsbot.utils;


/*import com.aspose.html.HTMLDocument;
import com.aspose.html.converters.Converter;
import com.aspose.html.rendering.image.ImageFormat;
import com.aspose.html.saving.ImageSaveOptions;*/

import java.io.IOException;

public class ChartGoogleUtils /*implements EntryPoint*/ {

/*    private PieChart chart;

    private void initialize() {
        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(() -> {
                // Create and attach the chart
                chart = new PieChart();
                RootPanel.get().add(chart);
                draw();
        });

    }


    public void createPieChart(){

        PieChart chart = new PieChart();

        // Prepare the data
        DataTable data = DataTable.create();
        data.addColumn(ColumnType.STRING, "Browser");
        data.addColumn(ColumnType.NUMBER, "Percentage");
        data.addRow("Firefox", 45.0);
        data.addRow("IE", 26.8);
        data.addRow("Chrome", 12.8);
        data.addRow("Safari", 8.5);
        data.addRow("Opera", 6.2);
        data.addRow("Others", 0.7);

        // Draw the chart
        chart.draw(data);
        chart.setHeight("480");
        chart.setWidth("480");

    }

    @Override
    public void onModuleLoad() {
        initialize();
    }*/

    /*public static void save() throws IOException {

//        StringBuilder buffer = new StringBuilder();
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/views/pie_chart.html")));
//        buffer.append(br.readLine());

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "\n" +
                "        google.charts.load('current', {'packages':['corechart']});\n" +
                "\n" +
                "        google.charts.setOnLoadCallback(drawChart);\n" +
                "\n" +
                "        function drawChart() {\n" +
                "\n" +
                "            var data = new google.visualization.DataTable();\n" +
                "            data.addColumn('string', 'Topping');\n" +
                "            data.addColumn('number', 'Slices');\n" +
                "            data.addRows([\n" +
                "                ['Mushrooms', 3],\n" +
                "                ['Onions', 1],\n" +
                "                ['Olives', 1],\n" +
                "                ['Zucchini', 1],\n" +
                "                ['Pepperoni', 2]\n" +
                "            ]);\n" +
                "\n" +
                "            var options = {'title':'How Much Pizza I Ate Last Night',\n" +
                "                'width':400,\n" +
                "                'height':300};\n" +
                "\n" +
                "            var chart = new google.visualization.PieChart(document.getElementById('chart_div'));\n" +
                "            chart.draw(data, options);\n" +
                "            chart.getDefinitionUrl();\n" +
                "        }\n" +
                "    </script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<!--Div that will hold the pie chart-->\n" +
                "<div id=\"chart_div\"></div>\n" +
                "<h>HELLO WORLD</h>\n" +
                "</body>\n" +
                "</html>";

// Load input HTML document
        HTMLDocument document = new HTMLDocument("src/main/resources/views/pie_chart.html");
        try {
            // Initialize ImageSaveOptions
            ImageSaveOptions options = new ImageSaveOptions(ImageFormat.Png);

            // Convert HTML to output JPG image
            Converter.convertHTML(document, options, "src/main/resources/files/test.png");
        } finally {
            if (document != null) {
                document.dispose();
            }
        }
*//*        HtmlImageGenerator hig = new HtmlImageGenerator();
        hig.loadHtml(html);
        hig.setSize(new Dimension(640, 480));
        hig.saveAsImage(new File("src/main/resources/files/test.png"));*//*

        *//*        File file = new File("/views/pie_chart.html");
        int width = 1024;
        int height = 1024;
        Java2DRenderer renderer = new Java2DRenderer(file, width, height);
        BufferedImage image = renderer.getImage();

        // write it out full size png defaults to png.
        FSImageWriter imagWriter = new FSImageWriter();
        imagWriter.write(image, "/files/pie_chart.png");*//*
    }
*/
}
