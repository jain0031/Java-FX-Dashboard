/** A class that will store values for Data objects (what you read in from lines of the file).
	 * 
	 * @author VAIBHAV JAIN
	 * STUDENT NUMBER:- 040884087
	 *
	 */
package dashboard;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class Assignment1_Start extends Application {
	private int one,commercial,multi;
	
	private double Value;
	
	private double avgValue;
	
	private double standardDevi ;
	
	private double ageSdeviation;
	
	private double totalD;
	
	private double tAge;
	
	private double tValChange;
	
	private double aAge;
	
	private double avgValueChg;
	
	private double maximum;
	
	private double minimum;
	
	private int maxAge;
	
	private int minAge;

	private VBox vbox;
	
	private ArrayList<PropertyData> data = new ArrayList<>();
	
	private HashMap<String, ArrayList< PropertyData >> hashMapData;
	
	public void setStreetview(HashMap<String, ArrayList< PropertyData >> streetHous) {
		
		hashMapData = streetHous;
	}
	
	public static void main(String[] args) {
	
		launch(args);
	
	}
	public class PropertyData{

		private String street;
	
		private String postal;
		
		private int year;
		
		private String category;
		
		private int currentV;
		
		private int previousV;

		

		
		public PropertyData(String street, String postal, String category, int year, int currentV, int previousV)
		{
		
			setStreet(street);
			
			setPl(postal);
			
			setTe(category);
			
			setBt(year);
			
			setV1(currentV);
			
			setV2(previousV);
		}
		


		public String getStreet() { 
			return street; 
		}

		public void setStreet(String value1) { 
			this.street = value1; 
		}

		public String getPostal() { 
			return category; 
		}

		public void setPl(String value2) {
			this.category = value2; 
		}

		public String getCategory() { 
			return postal; 
		}

		public void setTe(String value3) { 
			this.postal = value3;
		}

		public int getBt() { 
			return year; 
		}

		public void setBt(int value4) { 
			this.year = value4;
		}

		public int getCurrent() { 
			return currentV;
		}

		public void setV1(int value5) { 
			this.currentV = value5;
		}

		public int getPrevious() {
			return previousV; 
		}

		public void setV2(int value6) { 
			this.previousV = value6;
		}

		public double difference() { 
			return getCurrent() - getPrevious();
		}

		public double getTD2() {
			return totalD;
		}

		public void setTD2(double tD2) {
			totalD = tD2;
		}

	}



	public class MyData
	{
		String data ;
	
		
		double value;
		
		public MyData(String y, double x)
		{
			data = y;
		
			value = x;
		}
		public String getName() {
			
			return data;
		}
		public double getValue() { 
			
			return value; 
		}

	}


	@Override
	public void start(Stage primaryStage) throws Exception {


		Button newButton = new Button("Go!");
		newButton.setOnMouseClicked(e -> {

			FileChooser filechooser = new FileChooser();
			
			FileChooser.ExtensionFilter Extension = new FileChooser.ExtensionFilter("file (*.csv)", "*.csv");
			
			filechooser.getExtensionFilters().add(Extension);
			
			File f = filechooser.showOpenDialog(primaryStage);
			
			if(f != null)
				try {
			
					setStreetview(readCSVFile(f.getCanonicalPath()));

					GridPane grdPane = new GridPane();

					vbox.getChildren().add( grdPane );
					
					Text text = new Text(" Average House Price:"+this.avgValue );
					
					text.setOnMouseClicked(cl->{
					
						ObservableList<MyData> data = FXCollections.observableArrayList();
						
						for(String stName : hashMapData.keySet())
						{
							double sumValues = 0.0;
	
							ArrayList<PropertyData> houseOnSt = hashMapData.get(stName);

							for(PropertyData aHouse : houseOnSt)
							{
								sumValues  += aHouse.getCurrent();
							}
							data.add(new MyData(stName, sumValues / houseOnSt.size()));
						}

						this.openOtherStage(data, "value");
					});
					
					grdPane.add(text, 0, 0);
				
					text.setFont(new Font(20));

					text = new Text("Standard Deviation of House Price : " +this.standardDevi);					
					
					grdPane.add(text, 0,2);
					
					text.setFont(new Font(20));	

					text = new Text("Average house age: "+this.aAge);	
					
					text.setOnMouseClicked(cl->{

						double sumValues = 0.0;
					
						ObservableList<MyData> data = FXCollections.observableArrayList();
						
						for(String streetName : hashMapData.keySet())
						{
							ArrayList<PropertyData> housonst = hashMapData.get(streetName);
						
							for(PropertyData aHouse : housonst)
							{
								sumValues  += aHouse.getCurrent();
							}
							
							data.add(new MyData(streetName, sumValues / housonst.size()));
						}
			
						this.openOtherStage(data, "age");

					});

					grdPane.add(text, 0,3);
					
					text.setFont(new Font(20));

					CategoryAxis xAxi    = new CategoryAxis();
			        
					xAxi.setLabel("Family Category");

					NumberAxis yAxi = new NumberAxis();

					yAxi.setLabel("Number of Families");

			        BarChart chart = new BarChart(xAxi, yAxi);

			        XYChart.Series dataSeries1 = new XYChart.Series();
			        
			        dataSeries1.setName("Property Types");

			        dataSeries1.getData().add(new XYChart.Data(" Single Family ", one));
			        
			        dataSeries1.getData().add(new XYChart.Data(" Multi Family "  , multi));
			        
			        dataSeries1.getData().add(new XYChart.Data(" Commercial  "  , commercial));

			        
			        chart.getData().add(dataSeries1);

					grdPane.add(chart, 3, 0, 1, 4);


					text = new Text("House age Standard Deviation :"+this.ageSdeviation);
					
					grdPane.add(text, 0,4);
					
					text.setFont(new Font(20));
					
					text = new Text("Total value change:"+this.tValChange);
					
					text.setFont(new Font(20));
					
					grdPane.add(text, 0, 5);

					text = new Text("Max  : " + this.maximum);

					grdPane.add(text, 2,5);
					
					text.setFont(new Font(20));
					
					text = new Text("Min : " + this.minimum);
					
					grdPane.add(text, 2, 6);
					
					text.setFont(new Font(20));

					text = new Text("Max Age  : " + this.maxAge);
					
					grdPane.add(text, 2,7);
					
					text.setFont(new Font(20));
					
					text = new Text("Min Age : " + this.minAge);
					
					grdPane.add(text, 2, 8);
					
					text.setFont(new Font(20));


					int value1 = (int) ((maximum-minimum)/25000);
					
					int value2[] = new int[value1+1];	

					int count = value1;

					for(int i = 0; i < value1; i++)
					{
						value2[i] = 0;
					}
					for(PropertyData p : this.data)
					{
						try{
							int xz = (int)(p.getCurrent()  - minimum )/25000;
					
							value2[xz]++;
						}
						catch (Exception ex)
						{
							System.out.println(ex.getMessage() );
						}
					}

					for(int i = 0; i < value2.length; i++)
					{
						if(value2[i] == 0)
						{
							count = i;
							
							i = value2.length;
						}
					}


					final NumberAxis xAxis = new NumberAxis();
					
					final NumberAxis yAxis = new NumberAxis();
					
					xAxis.setLabel("House values");

					LineChart<Number, Number> li = new LineChart<>(xAxis, yAxis);
					
					li.setTitle("House values as  per $25000");
					
					XYChart.Series series = new XYChart.Series();
					
					series.setName("Property tax of 2016");

					for(int i = 0; i < count; i++){
					
						series.getData().add(new XYChart.Data<Integer, Integer>(i, value2[i]));
					
					}
					li.getData().add(series);
					
					vbox.getChildren().add(li);

				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
		});
		primaryStage.setScene(new Scene(vbox = new VBox(newButton), 1000, 768));
		
		primaryStage.show();
	}

	
	protected void openOtherStage(ObservableList<MyData> aList, String string)
	{
		//Create a second stage object
		Stage newStage = new Stage();

		BorderPane bp = new BorderPane();
		//Create a scene object to show objects
		Scene newScene = new Scene(bp,800,600);

		TableView<MyData> tbl = new TableView<>();
		
		TableColumn<MyData, String> nameCol = new TableColumn<>("Name");	
		TableColumn<MyData, String> valCol = new TableColumn<>("Average "+ string);

		tbl.getColumns().addAll(nameCol, valCol);

		nameCol.setCellValueFactory(
				new PropertyValueFactory<MyData,String>("Name") );

		valCol.setCellValueFactory(
				new PropertyValueFactory<MyData,String>("Value" ) );
		tbl.setItems(aList);


		tbl.setOnMouseClicked(cl -> {

			
			int indx= tbl.getSelectionModel().getSelectedIndex();
			if(indx >= 0)
			{
				MyData selected = aList.get( indx );
		
				String streetName = selected.getName();

				HashMap<String, ArrayList<PropertyData> > postalCodes = new HashMap<>();

				ArrayList<PropertyData> numOfHous;
				
				ArrayList<PropertyData> housesOnThisStreet = hashMapData.get(streetName);
				
				for(PropertyData aProperty : housesOnThisStreet)
				{
					numOfHous = postalCodes.get(aProperty.getPostal());
				
					if(numOfHous == null)
					{
						numOfHous = new ArrayList<>();
					
						numOfHous.add(aProperty);
						
						postalCodes.put(aProperty.getPostal(), numOfHous);
					}
					else numOfHous.add(aProperty);
				}

				ObservableList<MyData> data = FXCollections.observableArrayList();

				for(String pc : postalCodes.keySet())
				{
					double sumValues = 0.0;
				
					ArrayList<PropertyData> numOfHous1 = postalCodes.get(pc);
					
					for(PropertyData aHouse : numOfHous1)
					
					{
						if(string == "value")
				
							sumValues  += aHouse.getCurrent();
									
						else if(string == "age")
							
							sumValues += aHouse.getBt();
					}
					data.add(new MyData(pc, sumValues));
					
				}

								TableView<MyData> myDataTabl = new TableView<>();
				
								TableColumn<MyData, String> pcCol = new TableColumn<>("Postal Code");	
				
								TableColumn<MyData, String> pcvalCol = new TableColumn<>("Average "+ string);
				
								myDataTabl.getColumns().addAll(pcCol, pcvalCol);

				            	pcCol.setCellValueFactory(new PropertyValueFactory<MyData,String>("Name") 
						);

				pcvalCol.setCellValueFactory(
						
						new PropertyValueFactory<MyData,String>("Value")
						);

				//create the table:
				myDataTabl.setItems(data);				
				
				bp.setRight(myDataTabl);
				
				bp.setCenter(new Text("Houses on "+ streetName + " by postal code:"));
			}
		});

		bp.setLeft(tbl);

		//Tell the stage which scene to display
		newStage.setScene(newScene);

		//make the stage visible
		newStage.show();

	}

	public HashMap<String, ArrayList<PropertyData> > readCSVFile(String hashmap)
	{
		HashMap<String, ArrayList<PropertyData>> sortedByStreet = null;
		int thr = Calendar.getInstance().get(Calendar.YEAR);
		double sumCalc1 = 0,sumCalc2 = 0,sumCalc3 = 0;
		commercial=multi = one = 0;
		minAge=2015;
		try(BufferedReader reader = Files.newBufferedReader(Paths.get(hashmap)))
		{   String [] pe;
		String lie = "";

		tAge = Value = ageSdeviation = standardDevi = tValChange = 0.0;
		while(lie != null){
			lie = reader.readLine();
			if(lie != null)
			{

				pe = lie.split(",");

				try{
					PropertyData prooper = new PropertyData( pe[12], pe[13], pe[5], Integer.parseInt(pe[24]),Integer.parseInt(pe[19]), Integer.parseInt(pe[22]));

					data.add( prooper );
				
					tAge += thr - prooper.getBt();
					
					Value += prooper.getCurrent();
					
					tValChange += prooper.difference();

				}
				
				catch(NumberFormatException nfe)
				{

				}
			}

			sortedByStreet = new HashMap<>();
		}
		aAge = this.tAge/data.size();
		
		maximum = minimum = avgValue = this.Value/data.size();
		
		avgValueChg = this.tValChange/data.size();
		
		for(PropertyData pc: data)
		{

			sumCalc1 += Math.pow(pc.getBt() - aAge,2);
		
			sumCalc3 += Math.pow(pc.difference() - avgValueChg, 2); 
			
			sumCalc2 += Math.pow(pc.getCurrent()-avgValue, 	2);

			if(pc.getCurrent() < minimum) minimum = pc.getCurrent();	
			
			if(pc.getPrevious() > maximum) maximum = pc.getPrevious();
			
			

			if(pc.getBt() < minAge) minAge = pc.getBt();	
			
			if(pc.getBt() > maxAge) maxAge = pc.getBt();


			if(pc.getCategory().equals("Commercial")) 	
			{
				this.commercial++;
			}
			else if(pc.getCategory().equals("One Family Dwelling")) 	{
				this.one++;
			}
			else if(pc.getCategory().equals("Multiple Family Dwelling")) 	{
				this.multi++;
			}
			ArrayList<PropertyData> newArrayList = sortedByStreet.get(  pc.getStreet()  );
			if(newArrayList == null)
			{
				newArrayList = new ArrayList< > ();
		
				newArrayList.add(pc);
				
				sortedByStreet.put(pc.getStreet(), newArrayList);
			}
			else
			{
			
				newArrayList.add(pc);
			}
		
		}

		}catch(IOException ioe)
		{
			System.out.println("Problem reading csv : " + ioe.getMessage());
		
		}

		standardDevi = Math.sqrt( sumCalc2/data.size() );
		
		ageSdeviation= Math.sqrt( sumCalc1/data.size() );
		
		totalD = (Math.sqrt( sumCalc3/data.size()));
	
		
		
		return sortedByStreet;
	}

	
}