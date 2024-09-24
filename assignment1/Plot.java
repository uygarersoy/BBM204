import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.Arrays;

class Plot {
    public static void graph (long[] m, long[] i, long[] c, String title, String s1, String s2, String s3, String s4) throws IOException{
        double[] M = longToDouble(m);
        double[] I = longToDouble(i);
        double[] C = longToDouble(c);

        // X axis data
        int[] inputAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        // Create sample data for linear runtime
        double[][] yAxis = new double[3][10];
        yAxis[0] = M;
        yAxis[1] = I;
        yAxis[2] = C;
        // Save the char as .png and show it
        saveChart(title, inputAxis, yAxis, s1, s2, s3, s4);
    }

    public static void saveChart(String title, int[] xAxis, double[][] yAxis, String s1, String s2, String s3, String s4) throws IOException{
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title).yAxisTitle(s4).xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries(s1, doubleX, yAxis[0]);
        chart.addSeries(s2, doubleX, yAxis[1]);
        chart.addSeries(s3, doubleX, yAxis[2]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);
    }

    public static double[] longToDouble (long[] arr) {
        double[] res = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            double temp = arr[i];
            res[i] = temp;
        }
        return res;
    }
}
