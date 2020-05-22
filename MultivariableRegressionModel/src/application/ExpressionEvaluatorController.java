package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/*
import app.Array;
import app.Expression;
import app.Variable;
*/

public class ExpressionEvaluatorController {
	
	@FXML
	private Label result;
	private boolean start = true;

	@FXML
	public void processExpression(ActionEvent event) {
		if (start) {
			result.setText("");
			start = false;
		}
		// curr = current character from button
		String curr = ((Button)event.getSource()).getText();
		if (curr.equals("=")) {
			result.setText(""+Expression.evaluate(result.getText()));
			start = true;
			return;
		}
		result.setText(result.getText() + curr);
	}
}