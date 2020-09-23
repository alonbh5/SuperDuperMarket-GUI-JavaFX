package course.java.sdm.gui.InputPane;


import javafx.animation.PathTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.shape.LineTo;
import javafx.util.Duration;


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
    private BooleanProperty DoAnimation = null;
    private SimpleDoubleProperty locationX = new SimpleDoubleProperty(1);
    private SimpleDoubleProperty locationY= new SimpleDoubleProperty(1);;




    public void OnCreation (boolean showDec,Runnable onFinished) {
        TextRight.setDisable(!showDec);
        this.Onfinish = onFinished;
        checkRight=showDec;
    }





    @FXML
    void OnOk(ActionEvent event) {
        if (TextLeft.getText() != null && TextLeft.getText().matches(regex))
            if (checkRight) {
                if (TextRight.getText().isEmpty() || TextRight.getText().matches(regex)) {
                    if (DoAnimation != null && DoAnimation.getValue())
                        MakeAnimation();
                    else
                        Onfinish.run();
                }
                else
                    ErrorLabel.setText("Please Enter Positive Number! (Right Box)");
            } else {
                if (DoAnimation != null && DoAnimation.getValue())
                    MakeAnimation();
                else
                Onfinish.run();
            }
        else
            ErrorLabel.setText("Please Enter Positive Number! (left Box)");
    }

    private void MakeAnimation () {

            Path path = new Path();
            path.getElements().add(new MoveTo(locationX.getValue(), locationY.getValue()));
            path.getElements().add(new LineTo(TextLeft.getLayoutX()-80, TextLeft.getLayoutY()+300));

            OkButton.setDisable(true);
            TextLeft.setDisable(true);
            TextRight.setDisable(true);
            PathTransition pl = new PathTransition();
            pl.setNode(TextLeft);
            pl.setPath(path);
            pl.setDuration(Duration.seconds(2));
            pl.setAutoReverse(false);
            pl.setCycleCount(1);
            pl.setOnFinished(r->{Onfinish.run();});
            pl.play();

    }


    public Double getAmount() {

        Double res = Double.parseDouble(TextLeft.getText());
        if (checkRight)
            if (!TextRight.getText().isEmpty())
                res += Double.parseDouble("0." + TextRight.getText());

        return res;
    }

    public void initAnimation(BooleanProperty doAnimation, DoubleProperty x, DoubleProperty y) {
        DoAnimation=doAnimation;
        locationX.bind(x);
        locationY.bind(y);
    }


    public void ChangeTitle (String str) {
        TitleLabel.setText(str);
    }
}
