package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import state.StateStore;


public class OutputDiagramController implements Initializable, Observer{
    
    private Subject stateStore;
    
    public OutputDiagramController(){}
    
    @FXML
    private Pane diagramPane;
    
    private LineChart<Number,Number> lineChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createChart();
        this.stateStore = StateStore.getInstance();
        stateStore.registerObserver(this);
    }
    
    private void createChart() {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        //creating the chart
        lineChart = new LineChart<>(xAxis,yAxis);
                
        lineChart.setTitle("Chance of winning in the comming months");
       
        diagramPane.getChildren().add(lineChart);
        lineChart.getData().add(getSeries());
    }
    
    
    private XYChart.Series getSeries() {
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Me");
        //populating the series with data
        List winningList = StateStore.getInstance().getState().getOutDigramCalc();
        
        for (int i = 0; i < winningList.size(); i++) {
            series.getData().add(new XYChart.Data(i+1, winningList.get(i)));
        }
        
        return series;
    }
    
    //Kan anropas för att uppdatera diagrammet.
    private void updateChartSeries() {
        lineChart.getData().remove(0);
        lineChart.getData().add(getSeries());
    }
    public void update(){
        updateChartSeries();
    }
}
