package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import model.Cost;

public class ChartPB extends ApplicationFrame {
	private XYPlot plot;
	private Cost[][] arr;

	public ChartPB(String title, Cost[][] arr) {
		super(title);
		this.arr = arr;
		final TimeSeriesCollection dataset1 = createBuyDataset("Buy CAD",0);
		final TimeSeriesCollection dataset2 = createSaleDataset("Sale CAD",0);
		
		final TimeSeriesCollection dataset3 = createBuyDataset("Buy CHF",1);
		final TimeSeriesCollection dataset4 = createSaleDataset("Sale CHF",1);
		
		final TimeSeriesCollection dataset5 = createBuyDataset("Buy EUR",2);
		final TimeSeriesCollection dataset6 = createSaleDataset("Sale EUR",2);
		
		final TimeSeriesCollection dataset7 = createBuyDataset("Buy GBP",3);
		final TimeSeriesCollection dataset8 = createSaleDataset("Sale GBP",3);
		
		final TimeSeriesCollection dataset9 = createBuyDataset("Buy PLZ",4);
		final TimeSeriesCollection dataset10 = createSaleDataset("Sale PLZ",4);
		
		final TimeSeriesCollection dataset11 = createBuyDataset("Buy USD",5);
		final TimeSeriesCollection dataset12 = createSaleDataset("Sale USD",5);
		
		final JFreeChart chart = ChartFactory.createTimeSeriesChart("Stat", "Time", "Cost", dataset1, true, true,
				true);
		chart.setBackgroundPaint(Color.white);
		this.plot = chart.getXYPlot();
		this.plot = chart.getXYPlot();
		this.plot.setBackgroundPaint(Color.lightGray);
		this.plot.setDomainGridlinePaint(Color.white);
		this.plot.setRangeGridlinePaint(Color.white);
		final ValueAxis axis = this.plot.getDomainAxis();
		axis.setAutoRange(true);

		final NumberAxis rangeAxis2 = new NumberAxis("Range Axis 2");
		rangeAxis2.setAutoRangeIncludesZero(false);

		final JPanel content = new JPanel(new BorderLayout());

		final ChartPanel chartPanel = new ChartPanel(chart);
		content.add(chartPanel);

		chartPanel.setPreferredSize(new java.awt.Dimension(700, 470));
		setContentPane(content);

		this.plot.setDataset(1, dataset2);
		this.plot.setRenderer(1, new StandardXYItemRenderer());
		
		this.plot.setDataset(2, dataset3);
		this.plot.setRenderer(2, new StandardXYItemRenderer());
		
		this.plot.setDataset(3, dataset4);
		this.plot.setRenderer(3, new StandardXYItemRenderer());
		
		this.plot.setDataset(4, dataset5);
		this.plot.setRenderer(4, new StandardXYItemRenderer());
		
		this.plot.setDataset(5, dataset6);
		this.plot.setRenderer(5, new StandardXYItemRenderer());
		
		this.plot.setDataset(6, dataset7);
		this.plot.setRenderer(6, new StandardXYItemRenderer());
		
		this.plot.setDataset(7, dataset8);
		this.plot.setRenderer(7, new StandardXYItemRenderer());
		
		this.plot.setDataset(8, dataset9);
		this.plot.setRenderer(8, new StandardXYItemRenderer());
		
		this.plot.setDataset(9, dataset10);
		this.plot.setRenderer(9, new StandardXYItemRenderer());
		
		this.plot.setDataset(10, dataset11);
		this.plot.setRenderer(10, new StandardXYItemRenderer());
		
		this.plot.setDataset(11, dataset12);
		this.plot.setRenderer(11, new StandardXYItemRenderer());

	}

	private TimeSeriesCollection createBuyDataset(final String name,int pos) {
		final TimeSeries series = new TimeSeries(name);
		RegularTimePeriod t = new Day(01, 01,2014);
		for (int i = 0; i < arr[pos].length; i++) {
			try{
			series.add(t, arr[pos][i].getBuy());
			}
			catch(NullPointerException npe){
				series.add(t, arr[pos][i-1].getBuy());
			}
			t = t.next();
		}
		return new TimeSeriesCollection(series);
	}

	private TimeSeriesCollection createSaleDataset(final String name, int pos) {
		final TimeSeries series = new TimeSeries(name);
		RegularTimePeriod t = new Day(01, 01, 2014);
		for (int i = 0; i < arr[pos].length; i++) {
			try {
				series.add(t, arr[pos][i].getSale());
			} catch (NullPointerException npe) {
				series.add(t, arr[pos][i-1].getBuy());
			}
			t = t.next();
		}
		return new TimeSeriesCollection(series);
	}

}
