package boundry;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


import util.Consts;

public class ProductsInFlightReport extends JFrame{

	/*
	 * How to call the report from the code :
	 * 	ProductsInFlightReport demo = new ProductsInFlightReport(airportCode);
		demo.setSize(700,700);    
		RefineryUtilities.centerFrameOnScreen( demo );    
		demo.setVisible( true );
	 */

		private static String applicationTitle = "Products precentage by flights from/to city in the last month";
		private static String chartTitle = "Products precentage by flights from/to city in the last month";
		private String airportCode;
		
		public ProductsInFlightReport(String code) {
			   super(applicationTitle); 
			   this.airportCode = code;
			    setContentPane(createDemoPanel( ));
			   }
			   
		   private LinkedHashMap<String,Integer> countProducts()
			{
				LinkedHashMap<String,Integer> res = new LinkedHashMap<String,Integer>();
				try {
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					try { Connection conn = DriverManager.getConnection(Consts.CONN_STR);
							PreparedStatement stmt = conn.prepareStatement(Consts.SQL_PRODUCTS_REPORT);
							stmt.setString(1, airportCode);
							stmt.setString(2, airportCode);
							ResultSet rs = stmt.executeQuery(); 
						while (rs.next()) {
							int i = 1;
							res.put(rs.getString(i++), rs.getInt(i++));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return res;
			}
		   
		   private PieDataset createDataset( ) {
			  LinkedHashMap<String,Integer> data = countProducts();
		      DefaultPieDataset dataset = new DefaultPieDataset( );
		      ArrayList<String> keys = new ArrayList<String>(data.keySet());
		      for(int i=0; i<keys.size(); i++)
		      {
		    	  dataset.setValue(keys.get(i), data.get(keys.get(i)));
		      }
		      return dataset;         
		   }
		   
		   private JFreeChart createChart( PieDataset dataset ) {
		      JFreeChart chart = ChartFactory.createPieChart(      
		         chartTitle,   // chart title 
		         dataset,          // data    
		         true,             // include legend   
		         true, 
		         false);
		      chart.getPlot().setBackgroundPaint(Color.WHITE);
		      PiePlot plot = (PiePlot) chart.getPlot();
		      PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
		              "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		          plot.setLabelGenerator(gen);
		          plot.setLabelFont(new Font("Tahoma", Font.PLAIN, 16));
		      return chart;
		   }
		   
		   public JPanel createDemoPanel( ) {
		      JFreeChart chart = createChart(createDataset( ) );  
		      return new ChartPanel( chart ); 
		   }
}
