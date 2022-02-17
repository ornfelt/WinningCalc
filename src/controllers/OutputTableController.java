package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import state.StateStore;

public class OutputTableController implements Initializable, Observer {

    private Subject stateStore;

    public OutputTableController() {
    }
    
    @FXML
    private Label lblWinning;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stateStore = StateStore.getInstance();
        stateStore.registerObserver(this);
        update();
    }

    public void update() {
        int tempInt = StateStore.getInstance().getState().getOutWinningCalc();
        lblWinning.setText(Integer.toString(tempInt));
    }
}
