package application;

public class RegressionModel {
	// alternatively "C Matrix"
	private double[][] designMatrix;
	// alternatively "y Vector"
	private double[] responseVector;
	// alternatively "b Matrix"
	private double[] parameterVector;
	// called "Hat Matrix" because it turns Y's to Y hat's.
	private double[][] hatMatrix;
	// alternatively "predicted y Vector"
	private double[] yHatVector;
	// alternatively "Sigma"
	private double standardDeviation;
	// alternatively, "Error Vector"
	private double[] residualVector;
	// alternatively, "Covariance Matrix of Residuals"
	private double[][] covarianceMatrix;
	// alternatively, "Coefficient of Multiple Determination"
	private double rSquared;
	// alternatively, "Correlation Coefficient"
	private double r;

	// see stylistic choices (II) in formal write-up
	public RegressionModel(double[][] designMatrix, double[] responseVector) {
		this.designMatrix = designMatrix;
		this.responseVector = responseVector;
		/*
		 * Formula: b = ((C^T*C)^(-1))*y, where a = vector of length equal to the number
		 * of predictive variables+1; y = vector of length equal to the number of data
		 * points / observations; C = matrix with rows = the number of number of data
		 * points / observations, columns = predictive variables+1.
		 */
		this.parameterVector = (LinearAlgebra.matrixVectorMultiplication(LinearAlgebra.matrixMultiplication(
				LinearAlgebra.getInverse(LinearAlgebra.matrixMultiplication(LinearAlgebra.getTranspose(designMatrix), designMatrix)),
				LinearAlgebra.getTranspose(designMatrix)), responseVector));
		this.hatMatrix = makeHatMatrix(designMatrix);
		this.yHatVector = makeYHatVector(hatMatrix, responseVector);
		// Formula: residual vector = yVector - yHatVector
		this.residualVector = LinearAlgebra.vectorSubtraction(responseVector,yHatVector);
		this.standardDeviation = makeSigma(designMatrix, residualVector);
		// rounded to the thousandths place
		this.covarianceMatrix = LinearAlgebra.roundEntriesOfMatrix(makeCovarianceMatrix(hatMatrix, standardDeviation));
		this.rSquared = makeRSquared(responseVector, yHatVector);
		this.r = Math.sqrt(rSquared);
	}
	public double[] getResidualVector() {
		return residualVector;
	}
	
	public double getR(){
		return r;
	}
	
	public double getRSquared(){
		return rSquared;
	}
	
	public double[][] getDesignMatrix() {
		return designMatrix;
	}

	public void setDesignMatrix(double[][] newMatrix) {
		designMatrix = newMatrix;
	}

	public double[] getResponseVector() {
		return responseVector;
	}

	public void setResponseVector(double[] newResponseVector) {
		responseVector = newResponseVector;
	}

	public double[] getyHatVector() {
		return yHatVector;
	}
	
	public double[] getParameterVector() {
		return parameterVector;
	}

	public static double[] makeParameterVector(double[][] hatMatrix, double[] yVector) {
		// formula: Y hat = HY. This is the rationale for calling Hat Matrix a Hat
		// Matrix (converts Y's to Y Hat's)
		return (LinearAlgebra.matrixVectorMultiplication(hatMatrix, yVector));
	}

	// creating readable String form of the vector a
	public static String convertParameterVectorToString(double[] bVector) {
		// rounds each entry to the thousandths place
		bVector = LinearAlgebra.roundEntriesOfVector(bVector);
		String formula = "y = " + bVector[0];
		/*
		 * The current predictor variable starts at index 1 instead of 0 because least
		 * squares approximation requires that every entry in the first column be set to
		 * 1. See notes for further explanation.
		 */
		int predVar = 1;
		while (predVar < bVector.length) {
			// cannot make predVar value into subscript format due to limitations of ascii
			// characters
			formula += " + " + bVector[predVar] + "x" + predVar;
			predVar++;
		}
		return formula;
	}
	
	public void printApproximation() {
		System.out.println(convertParameterVectorToString(parameterVector));
	}

	public double[][] getHatMatrix() {
		return hatMatrix;
	}

	public static double[][] makeHatMatrix(double[][] designMatrix) {
		return LinearAlgebra.matrixMultiplication(
				LinearAlgebra.matrixMultiplication(designMatrix,
						LinearAlgebra.getInverse(
								LinearAlgebra.matrixMultiplication(LinearAlgebra.getTranspose(designMatrix), designMatrix))),
				LinearAlgebra.getTranspose(designMatrix));
	}

	public static double[] makeYHatVector(double[][] hatMatrix, double[] yVector) {
		return LinearAlgebra.matrixVectorMultiplication(hatMatrix, yVector);
	}

	public double getSigma() {
		return standardDeviation;
	}

	public void setSigma(double newSigma) {
		standardDeviation = newSigma;
	}

	// Mean Squared Error (MSE)
	public static double makeSigma(double[][] cMatrix, double[] residualVector) {
		// Formula: standardDeviation = sqrt(SSE/dfE) =
		// sqrt(((Y-Cb)^T(Y-Xb))/(degreesOfFreedom))
		return Math.sqrt(
				(LinearAlgebra.dotProduct(residualVector, residualVector)) / (residualVector.length - cMatrix[0].length));
	}

	public double[][] getCovarianceMatrix() {
		return covarianceMatrix;
	}

	public static double[][] makeCovarianceMatrix(double[][] hatMatrix, double standardDeviation) {
		// get Covariance of the residuals
		double[][] covarMatrix = LinearAlgebra.scaleMatrix(
				LinearAlgebra.matrixSubtraction(LinearAlgebra.createIdentityMatrix(hatMatrix.length), hatMatrix),
				Math.pow(standardDeviation, 2));
		// get Variance of each residual
		for (int diagonal = 0; diagonal < covarMatrix.length; diagonal++) {
			covarMatrix[diagonal][diagonal] = Math.pow(standardDeviation, 2) * (1 - hatMatrix[diagonal][diagonal]);
		}
		return covarMatrix;
	}

	public static double makeRSquared(double[] y, double[] yHat) {
		// calculates mean of y values
		double yBar = 0, SSM = 0, SSTO = 0;
		for (int entry = 0; entry < y.length; entry++) {
			yBar += y[entry];
		}
		yBar /= y.length;
		// calculating sum of squares
		for (int entry = 0; entry < y.length; entry++) {
			// sum of Squares for Model (SSM) Formula = sum of squared (y hat entry - y bar)
			SSM += Math.pow(yHat[entry] - yBar, 2);
			// total Sum of Squares (SSTO) Formula = sum of squared (y entry - y bar)
			SSTO += Math.pow(y[entry] - yBar, 2);
		}
		// r^2 Formula: SSM/SSTO
		return SSM / SSTO;
	}

	public static double[][] createNewBiDesignMatrix(double[][] matrix, int columnNum)throws IllegalArgumentException {
		if (columnNum >= matrix[0].length) {
			throw new IllegalArgumentException("No such column exist in the matrix");
		}
		double[][] newMatrix = new double[matrix.length][2];
		for (int row = 0; row < newMatrix.length; row++) {
			for (int col = 0; col < newMatrix[row].length; col++) {
				newMatrix[row][col] = col == 0 ? 1 : matrix[row][columnNum];
			}
		}
		return newMatrix;
	}
}
