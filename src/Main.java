import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		/** create a GUI */
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(5));
		pane.setHgap(5);
		pane.setVgap(5);
		ColumnConstraints column1 = new ColumnConstraints(200);
		ColumnConstraints column2 = new ColumnConstraints(100, 200, 300);
		column2.setHgrow(Priority.ALWAYS);
		pane.getColumnConstraints().addAll(column1, column2);

		// set background
		InputStream i = Files.newInputStream(Paths.get("Minions.jpg"));
		Image image = new Image(i);
		i.close();
		ImageView img = new ImageView(image);
		img.setFitHeight(550);
		img.setFitWidth(800);
		pane.getChildren().add(img);

		VBox vbox1 = new VBox(10);
		Label l1 = new Label("You Are In ...");
		Label l2 = new Label("You Are Going to ...");
		Label l3 = new Label("Optimal...");
		l1.setFont(Font.font("Times New Romans", FontWeight.BOLD, FontPosture.ITALIC, 16));
		l2.setFont(Font.font("Times New Romans", FontWeight.BOLD, FontPosture.ITALIC, 16));
		l3.setFont(Font.font("Times New Romans", FontWeight.BOLD, FontPosture.ITALIC, 16));
		l1.setTextFill(Color.FLORALWHITE);
		l2.setTextFill(Color.FLORALWHITE);
		l3.setTextFill(Color.FLORALWHITE);
		vbox1.getChildren().addAll(l1, l2, l3);
		vbox1.setPadding(new Insets(10, 10, 10, 10));
		GridPane.setHalignment(vbox1, HPos.LEFT);
		pane.add(vbox1, 0, 0);

		VBox vbox2 = new VBox(10);
		String[] countries = { "Safad", "Tabarya", "Bisan", "Al-Nasrah", "Jenin", "Nablus", "Tulkarem", "Jericho",
				"Ramallah", "Jerusalem", "Bethlehem", "Hebron", "Akka", "Heifa", "Qalqilya", "Yaffa", "Al-Lid",
				"Al-Ramleh", "Gaza" };
		String[] optimal = { "Shortest Path", "Faster Path", "Cost" };
		ComboBox<String> cbo1 = new ComboBox<>();
		ComboBox<String> cbo2 = new ComboBox<>();
		ComboBox<String> cbo3 = new ComboBox<>();
		cbo1.getItems().addAll(countries);
		cbo2.getItems().addAll(countries);
		cbo3.getItems().addAll(optimal);
		cbo1.setValue("Select one");
		cbo2.setValue("Select one");
		cbo3.setValue("Select one");
		vbox2.getChildren().addAll(cbo1, cbo2, cbo3);
		vbox2.setPadding(new Insets(10, 10, 10, 10));
		GridPane.setHalignment(vbox2, HPos.CENTER);
		pane.add(vbox2, 1, 0);

		VBox vbox0 = new VBox(10);
		Button btn1 = new Button("   Click   ");
		Button btn2 = new Button("   Test heuristic ");
		Button btn3 = new Button("   Test Algorithem  ");
		btn1.setFont(Font.font("Times New Romans", FontWeight.BOLD, FontPosture.ITALIC, 12));
		btn2.setFont(Font.font("Times New Romans", FontWeight.BOLD, FontPosture.ITALIC, 12));
		btn3.setFont(Font.font("Times New Romans", FontWeight.BOLD, FontPosture.ITALIC, 12));
		vbox0.getChildren().addAll(btn1, btn2, btn3);
		vbox0.setAlignment(Pos.CENTER);
		GridPane.setHalignment(vbox0, HPos.CENTER);
		pane.add(vbox0, 0, 0);

		/** ################################################################## */
		
		AStar a = new AStar();
		// create a graph according to a distance file 
		DirectedGraph distanceDE = readFile("distances.txt", a.getDistence());
		DirectedGraph timeDE = readFile("time.txt", a.getTime());
		DirectedGraph costDE = findCost(a.getDistence(), a.getTime(), a.getCost());
		cbo1.setOnAction((e) -> {});
		cbo2.setOnAction((e) -> {});
		cbo3.setOnAction((e) -> {});
		btn1.setOnAction((e) -> {
			Node.numOfExpandedNode = 0;
			Node.numOfVisitedNode = 0;
			//msg.setText("");
			ArrayList<Node> path = null;
			int op = 0;
			int flag1 = 0 , flag2 = 0 , flag3 = 0;
			if (cbo1.getSelectionModel().isEmpty()){
				System.out.println("Choose Where are you in !");
			    flag1 = 1;
			}
			if (cbo2.getSelectionModel().isEmpty()){
				System.out.println("Choose Where Are you going to !");
			    flag2 = 1;
			}
			if (cbo3.getSelectionModel().isEmpty()){
				System.out.println("Choose the optimal !");
			    flag3 = 1;
			}
			if ((flag1==0) && (flag2==0) && (flag3==0)) {
				String source = cbo1.getValue();
				int s = changeToIndexNumber(source);
				String destination = cbo2.getValue();
				int d = changeToIndexNumber(destination);
				String opt = cbo3.getValue();
				if (opt.equalsIgnoreCase("Shortest Path")) {
					op = 1;
					path = a.bestSolution(distanceDE, s, d, 1);
				} else if (opt.equalsIgnoreCase("Faster Path")) {
					op = 2;
					path = a.bestSolution(timeDE, s, d, 2);
				} else if (opt.equalsIgnoreCase("Cost")) {
					op = 3;
					path = a.bestSolution(costDE, s, d, 3);
				}
				//System.out.println("Number of expanded nodes : "+Node.numOfExpandedNode+"\n");
				//System.out.println("Number of visited nodes : "+Node.numOfVisitedNode+"\n");
				String msg1 = "";
				//msg1 += "We want to go from:  " + source + "\nWe want to go to: " + destination;
				msg1 += "\nNumber Of Expanded Node " + Node.numOfExpandedNode;
				msg1 += "\nNumber Of Visited Node " + Node.numOfVisitedNode;
				if (path != null) {
					//System.out.println("optimal path :\n");
					msg1 += "\nThe sugested path\t";
					for (int k = 0; k < path.size() - 1; k++){
						msg1 += path.get(k).changeToName() + " --> ";
						//System.out.println(path.get(k).changeToName() + " --> ");
				}
					msg1 += path.get(path.size() - 1).changeToName();
					msg1 += "\npath cost is " + (int) (path.get(path.size() - 1).getPathCost() * 100) / 100.0;
					switch (op) {
					case 1:
						msg1 += " Km\n" + "We use distance heuristic\n";
						break;
					case 2:
						msg1 += " min\n" + "We use time heuristic\n";
						break;
					case 3:
						msg1 += " shekel\n" + "We use Cost heuristic\n";
					}
				} else {
					msg1 += "\nCan not find path\n";
				}
				System.out.println(msg1);

			}
		});
		btn2.setOnAction((e) -> {
			boolean badHeuristic = false;
			String msg0 = "";
			for (int k = 0; k < 20; k++) {
				badHeuristic = a.testHeuristic(1, distanceDE);
				if (badHeuristic)
					break;
			}
			if (badHeuristic)
				msg0 += " distance heuristic not addmisable\n";
			else
				msg0 += " distance heuristic is addmisable\n";
			for (int k = 0; k < 20; k++) {
				badHeuristic = a.testHeuristic(2, timeDE);
				if (badHeuristic)
					break;
			}
			if (badHeuristic)
				msg0 += " time heuristic not addmisable\n";
			else
				msg0 += " time heuristic is addmisable\n";
			for (int k = 0; k < 20; k++) {
				badHeuristic = a.testHeuristic(3, costDE);
				if (badHeuristic)
					break;
			}
			if (badHeuristic)
				msg0 += " cost heuristic not addmisable\n";
			else
				msg0 += " cost heuristic is addmisable\n";
			System.out.println(msg0);

		});

		btn3.setOnAction((e) -> {
			boolean badAlgorithem = false;
			String msg0 = "";
			for (int k = 0; k < 20; k++) {
				badAlgorithem = a.testAlgorithem(1, distanceDE);
				if (badAlgorithem)
					break;
			}
			if (badAlgorithem)
				msg0 += " distance Algorithem not addmisable\n";
			else
				msg0 += " distance Algorithem is addmisable\n";
			for (int k = 0; k < 20; k++) {
				badAlgorithem = a.testAlgorithem(2, timeDE);
				if (badAlgorithem)
					break;
			}
			if (badAlgorithem)
				msg0 += " time Algorithem not addmisable\n";
			else
				msg0 += " time Algorithem is addmisable\n";
			for (int k = 0; k < 20; k++) {
				badAlgorithem = a.testAlgorithem(3, costDE);
				if (badAlgorithem)
					break;
			}
			if (badAlgorithem)
				msg0 += " cost Algorithem not addmisable\n";
			else
				msg0 += " cost Algorithem is addmisable\n";
			System.out.println(msg0);

		});

		Scene scene = new Scene(pane, 400, 350);
		img.fitWidthProperty().bind(scene.widthProperty());
		img.fitHeightProperty().bind(scene.heightProperty());
		stage.setTitle("Path Finding");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	// read a file and create a graph
	private static DirectedGraph readFile(String fileName, double[][] val) {
		DirectedGraph de = new DirectedGraph();
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				String[] s = line.split(":");
				String[] str = s[s.length - 1].split("#");
				int f = changeToIndexNumber(s[0]);
				for (int j = 0; j < str.length; j++) {
					String[] string = str[j].split(",");
					String country = string[0].trim();
					double dist = Double.parseDouble(string[1]);
					int t = changeToIndexNumber(country);
					DirectedEdge e = new DirectedEdge(f, t, dist);
					val[t][f] = dist;
					val[f][t] = dist;
					de.addCityToGraph(e);
				}
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return de;
	}

	private static DirectedGraph findCost(double[][] distance, double[][] time, double[][] cost) {
		DirectedGraph de = new DirectedGraph();
		int numOfCities = de.getNumberOfCities();
		double numL, f, cost0;
		for (int i = 0; i < numOfCities; i++) {
			for (int j = 0; j < numOfCities; j++) {
				if ((distance[i][j] == 0) || (time[i][j] == 0))
					continue;
				else {
					numL = distance[i][j] / 10.0;
					f = (2.0 / 3.0) / (distance[i][j] / time[i][j]);
					cost0 = numL * f * 7;
					DirectedEdge e = new DirectedEdge(i, j, cost0); // from  to  distance
					cost[i][j] = cost0;
					cost[j][i] = cost0;
					de.addCityToGraph(e);
				}
			}
		}
		return de;
	}

	private static int changeToIndexNumber(String head) {
		int h = 0;
		if (head.equalsIgnoreCase("Safad"))
			h = 0;
		else if (head.equalsIgnoreCase("Tabarya"))
			h = 1;
		else if (head.equalsIgnoreCase("Bisan"))
			h = 2;
		else if (head.equalsIgnoreCase("Al-Nasrah"))
			h = 3;
		else if (head.equalsIgnoreCase("Jenin"))
			h = 4;
		else if (head.equalsIgnoreCase("Nablus"))
			h = 5;
		else if (head.equalsIgnoreCase("Tulkarem"))
			h = 6;
		else if (head.equalsIgnoreCase("Jericho"))
			h = 7;
		else if (head.equalsIgnoreCase("Ramallah"))
			h = 8;
		else if (head.equalsIgnoreCase("Jerusalem"))
			h = 9;
		else if (head.equalsIgnoreCase("Bethlehem"))
			h = 10;
		else if (head.equalsIgnoreCase("Hebron"))
			h = 11;
		else if (head.equalsIgnoreCase("Akka"))
			h = 12;
		else if (head.equalsIgnoreCase("Heifa"))
			h = 13;
		else if (head.equalsIgnoreCase("Qalqilya"))
			h = 14;
		else if (head.equalsIgnoreCase("Yaffa"))
			h = 15;
		else if (head.equalsIgnoreCase("Al-Lid"))
			h = 16;
		else if (head.equalsIgnoreCase("Al-Ramleh"))
			h = 17;
		else if (head.equalsIgnoreCase("Gaza"))
			h = 18;
		return h;
	}
}
