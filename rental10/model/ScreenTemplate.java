/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10.model;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import rental10.ScreenController;

/**
 *
 * @author song
 */
public class ScreenTemplate extends RentalAppScreenNames implements LabelTemplate {
    /**
     *display title. 
    */
    @FXML
    protected transient Label titleLabel;

    /**
     *dynamically set title text. 
     * @param screenName current screen page's name
    */
    @Override
    public void setTitleLabel (final String screenName)
    {
        titleLabel.setLayoutX(0);
        titleLabel.setLayoutY(0);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPrefSize(800, 100);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));       
        titleLabel.setText(screenName);
    }
}
