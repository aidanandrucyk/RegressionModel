package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RegressionController {

	@FXML
	LineChart<Number, Number> lineChart;
	@FXML
	private TextArea dMatrixRvect;
	@FXML
	private TextArea yHatParavect;
	@FXML
	private TextArea hatMatrix;
	@FXML
	private TextArea covarianceMatrix;
	@FXML
	private TextArea coefficents;

	@FXML
	private TextArea designMatrix;
	@FXML
	private TextArea responseVector;
	
	@FXML
	private TextField formula;

	// clears data on chart
	public void clear(ActionEvent event) {
		lineChart.getData().clear();
	}

	public void getOutputs() {
		// create model
		RegressionModel model = new RegressionModel(LinearAlgebra.convertStringToMatrix(designMatrix.getText()),
				LinearAlgebra.convertStringToVector(responseVector.getText()));
		// put attributes of model into GUI
		dMatrixRvect.setText("Design Matrix:" + "\n" + LinearAlgebra.displayMatrixAsString(model.getDesignMatrix())
				+ "Response Vector:" + "\n" + LinearAlgebra.displayVectorAsString(model.getResponseVector()));
		yHatParavect.setText("Y Hat Vector:" + "\n" + LinearAlgebra.displayVectorAsString(model.getyHatVector()) + "\n"
				+ "Parameter Vector:" + "\n" + LinearAlgebra.displayVectorAsString(model.getParameterVector()) + "\n"
						+ "Residual Vector:" + "\n" + LinearAlgebra.displayVectorAsString(model.getResidualVector()));
		hatMatrix.setText("Hat Matrix:" + "\n" + LinearAlgebra.displayMatrixAsString(model.getHatMatrix()));
		covarianceMatrix.setText(
				"Covariance Matrix:" + "\n" + LinearAlgebra.displayMatrixAsString(model.getCovarianceMatrix()));
		coefficents.setText("Correlation Coefficient:" + "\n" + LinearAlgebra.roundEntry(model.getR()) + "\n" + "Determination Coefficient:"
				+ "\n" + LinearAlgebra.roundEntry(model.getRSquared()) + "\n" + "Standard Deviation:" + "\n"
				+ LinearAlgebra.roundEntry(model.getSigma()));
		formula.setText(RegressionModel.convertParameterVectorToString(model.getParameterVector()));
		
		// make graph
		
		
		LinearAlgebra.printMatrix(model.getDesignMatrix());
		
		for (int explanVar = 1; explanVar < model.getDesignMatrix()[0].length; explanVar++) {
			// find max and min values of first explanatory variable
			double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
			for (int obs = 0; obs < model.getDesignMatrix().length; obs++) {
				if (model.getDesignMatrix()[obs][explanVar] < min) {
					min = model.getDesignMatrix()[obs][explanVar];
				}
				if (model.getDesignMatrix()[obs][explanVar] > max) {
					max = model.getDesignMatrix()[obs][explanVar];
				}				
			}
			System.out.println(min + " + " + max + " for " + explanVar);
			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
			// calculate yhat = model.getyHatVector()[0] + min*model.getyHatVector()[explanVar];
			// min of domain
			series.getData().add(new XYChart.Data<Number, Number>(min, model.getyHatVector()[0] + min*model.getyHatVector()[explanVar]));
			// max of domain
			series.getData().add(new XYChart.Data<Number, Number>(max, model.getyHatVector()[0] + max*model.getyHatVector()[explanVar]));
			series.setName("LSRL for x" + explanVar);
			lineChart.getData().add(series);
		}		
		
		


	}
}