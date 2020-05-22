package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class LinearAlgebraController implements Initializable {

	@FXML
	TreeView<String> argumentTypeList;

	@FXML
	TextArea inputA;
	@FXML
	TextArea inputB;
	@FXML
	TextField inputS;
	@FXML
	TextArea output;

	public void processInputs() {
		String outputText = "";

		double[][] matrixA = null;
		double[] vectorA = null;
		// INPUT A
		if (inputA.getText().contains(";")) {
			matrixA = LinearAlgebra.convertStringToMatrix(inputA.getText());
			outputText += "Matrix A:" + "\n" + LinearAlgebra.displayMatrixAsString(matrixA);
		} else if (!inputA.getText().isEmpty() && !inputA.getText().contains(";")) {
			// make vector if not empty and not a matrix
			vectorA = LinearAlgebra.convertStringToVector(inputA.getText());
			outputText += "Vector A:" + "\n" + LinearAlgebra.displayVectorAsString(vectorA);
		} else {
			output.setText(outputText + " Input A is empty. Please try again.");
		}
		outputText += "\n";
		double[][] matrixB = null;
		double[] vectorB = null;
		// INPUT B
		if (inputB.getText().contains(";")) {
			matrixB = LinearAlgebra.convertStringToMatrix(inputB.getText());
			outputText += "Matrix B:" + "\n" + LinearAlgebra.displayMatrixAsString(matrixB) + "\n";
		} else if (!inputB.getText().isEmpty() && !inputB.getText().contains(";")) {
			// make vector if not empty and not a matrix
			vectorB = LinearAlgebra.convertStringToVector(inputB.getText());
			outputText += "Vector B:" + "\n" + LinearAlgebra.displayVectorAsString(vectorB) + "\n";
		} 
		// INPUT Scalar
		double scalar = inputS.getText().isEmpty() ? 1 : Double.parseDouble(inputS.getText());

		if (scalar != 1) {
			outputText += "Scalar: " + "\n" + scalar + "\n";
		}

		if (matrixA != null) {
			outputText += "RREF(A):" + "\n" + LinearAlgebra.displayMatrixAsString(LinearAlgebra.getRREF(matrixA))
					+ "\n";
			outputText += "REF(A):" + "\n" + LinearAlgebra.displayMatrixAsString(LinearAlgebra.getREF(matrixA)) + "\n";
			outputText += "isLinearlyIndependent(A):" + "\n" + LinearAlgebra.linearIndependent(matrixA) + "\n";
			// GO BACK AND FIX THIS!!!!!!!!!!!!!
			outputText += "Rank(A):" + "\n"
					+ LinearAlgebra.getRank(matrixA) + "\n";
			outputText += "Nullity(A):" + "\n"
					+ LinearAlgebra.getNullity(matrixA) + "\n";
			outputText += "Col(A):" + "\n"
					+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.getColumnSpaceBasis(matrixA)) + "\n";
			outputText += "Row(A):" + "\n"
					+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.getRowSpaceBasis(matrixA)) + "\n";
			/*
			outputText += "Null(A):" + "\n"
					+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.getNullSpaceBasis(matrixA)) + "\n";
			*/
			outputText += "Transpose(A):" + "\n"
					+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.getTranspose(matrixA)) + "\n";
			// conditions to show only if the matrix is invertible
			if (matrixA.length == matrixA[0].length) {
				outputText += "Determinant(A):" + "\n" + LinearAlgebra.cofactorExpansion(matrixA) + "\n";
				// only get inverse if the determinant isn't equal to 0
				if (LinearAlgebra.cofactorExpansion(matrixA) != 0) {
					outputText += "Inverse(A):" + "\n"
							+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.getInverse(matrixA)) + "\n";
				}
			}
		}
		if (scalar != 1 && matrixA != null) {
			outputText += "Scaled(A):" + "\n"
					+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.scaleMatrix(matrixA, scalar)) + "\n";
		}
		if (matrixA != null && matrixB != null && matrixA[0].length == matrixB.length) {
			outputText += "AB:" + "\n"
					+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.matrixMultiplication(matrixA, matrixB)) + "\n";
		}
		if (matrixA != null && vectorB != null) {
			if (vectorB.length == matrixA.length) {
				outputText += "A|b:" + "\n"
						+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.getAugmentedMatrix(matrixA, vectorB))
						+ "\n";
				outputText += "Gaussian Elimination:" + "\n" + LinearAlgebra
						.displayStringVectorAsSingleString(LinearAlgebra.gaussianElimation(matrixA, vectorB)) + "\n";
			}
			if (vectorB.length == matrixA[0].length) {
				outputText += "Ab:" + "\n" + LinearAlgebra
						.displayVectorAsString(LinearAlgebra.matrixVectorMultiplication(matrixA, vectorB)) + "\n";
			}
		}
		if (vectorA != null && matrixB != null) {
			if (vectorB.length == matrixA.length) {
				outputText += "B|a:" + "\n"
						+ LinearAlgebra.displayMatrixAsString(LinearAlgebra.getAugmentedMatrix(matrixB, vectorA))
						+ "\n";
				// MUST COMPLETE THIS
				outputText += "Gaussian Elimination:" + "\n" + LinearAlgebra
						.displayStringVectorAsSingleString(LinearAlgebra.gaussianElimation(matrixB, vectorA)) + "\n";
			}
			if (vectorB.length == matrixA[0].length) {
				outputText += "Ba:" + "\n" + LinearAlgebra
						.displayVectorAsString(LinearAlgebra.matrixVectorMultiplication(matrixB, vectorA)) + "\n";
			}
		}
		if (vectorA != null && vectorB != null) {
			outputText += "a-b:" + "\n"
					+ LinearAlgebra.displayVectorAsString(LinearAlgebra.vectorSubtraction(vectorA, vectorB)) + "\n";
			outputText += "a+b:" + "\n"
					+ LinearAlgebra.displayVectorAsString(LinearAlgebra.vectorAddition(vectorA, vectorB)) + "\n";
			outputText += "a.b:" + "\n" + LinearAlgebra.dotProduct(vectorA, vectorB) + "\n";
			outputText += "isOrthogonal:" + "\n" + LinearAlgebra.isOrthogonal(vectorA, vectorB) + "\n";
		}
		output.setText(outputText);
	}

	// Creates a data set to display for the TreeView
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		@SuppressWarnings("rawtypes")
		TreeItem<String> rootSM = new TreeItem("Single Matrix"), getRREF = new TreeItem("getRREF"),
				getREF = new TreeItem("getREF"), isLinearlyIndependent = new TreeItem("isLinearlyIndependent"),
				getColumnRowNullSpaceBasis = new TreeItem("getColumnRowNullSpaceBasis"),
				getInverse = new TreeItem("getInverse"), getDeterminant = new TreeItem("getDeterminant"),
				getTranspose = new TreeItem("getTranspose");

		rootSM.getChildren().addAll(getRREF, getREF, isLinearlyIndependent, getColumnRowNullSpaceBasis, getInverse,
				getDeterminant);

		TreeItem<String> rootDM = new TreeItem("Multiple Components"),
				rootsM = new TreeItem("Requires Scalar & Matrix"), scalarMultiplication = new TreeItem("scaleMatrix");
		rootsM.getChildren().add(scalarMultiplication);

		TreeItem<String> rootMM = new TreeItem("Requires Two Matrices"),
				MMmultiplication = new TreeItem("matrixMultiplication");
		rootMM.getChildren().add(MMmultiplication);

		TreeItem<String> rootMV = new TreeItem("Requires Matrix & Vector"),
				getAugmentedMatrix = new TreeItem("getAugmentedMatrix"),
				getSolutionsToSystems = new TreeItem("getSolutionsToSystems"),
				matrixVectorMultiplication = new TreeItem("matrixVectorMultiplication");
		rootMV.getChildren().addAll(getAugmentedMatrix, getSolutionsToSystems, matrixVectorMultiplication);

		TreeItem<String> rootVV = new TreeItem("Requires Two Vectors"),
				vectorSubstraction = new TreeItem("vectorSubstraction"),
				vectorAddition = new TreeItem("vectorAddition"), dotProduct = new TreeItem("dotProduct"),
				isOrthogonal = new TreeItem("isOrthogonal");
		rootVV.getChildren().addAll(vectorSubstraction, vectorAddition, dotProduct, isOrthogonal);

		rootDM.getChildren().addAll(rootsM, rootMM, rootMV, rootVV);

		TreeItem<String> root = new TreeItem("Requires...");
		root.getChildren().addAll(rootSM, rootDM);
		argumentTypeList.setRoot(root);
	}
}