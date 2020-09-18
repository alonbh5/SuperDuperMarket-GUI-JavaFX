package course.java.sdm.gui.InputPane;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

import javafx.scene.layout.Pane;


public class GetInputPaneController {

    @FXML
    private Pane MainPane;

    @FXML
    private TextField TextLeft;

    @FXML
    private TextField TextRight;

    @FXML
    private Button OkButton;

    @FXML    private Label ErrorLabel;

    @FXML    private Label TitleLabel;

    private Runnable Onfinish;


    private String regex = "[0-9]+";
    private boolean checkRight;





    public void OnCreation (boolean showDec,Runnable onFinished) {
        TextRight.setDisable(!showDec);
        this.Onfinish = onFinished;
        checkRight=showDec;
    }





    @FXML
    void OnOk(ActionEvent event) {
        if (TextLeft.getText() != null && TextLeft.getText().matches(regex))
            if (checkRight) {
                if (TextRight.getText().isEmpty() || TextRight.getText().matches(regex))
                    Onfinish.run();
                else
                    ErrorLabel.setText("Please Enter Positive Number! (Right Box)");
            } else
                Onfinish.run();
        else
            ErrorLabel.setText("Please Enter Positive Number! (left Box)");
    }


    public Double getAmount() {

        Double res = Double.parseDouble(TextLeft.getText());
        if (checkRight)
            if (!TextRight.getText().isEmpty())
                res += Double.parseDouble("0." + TextRight.getText());

        return res;
    }


    public void ChangeTitle (String str) {
        TitleLabel.setText(str);
    }
}
