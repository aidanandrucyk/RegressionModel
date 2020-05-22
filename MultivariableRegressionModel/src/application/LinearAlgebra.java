package application;

import java.util.ArrayList;

class Node<item> {
	int data;
	Node<item> next;

	public Node(int data, Node<item> next) {
		this.data = data;
		this.next = next;
	}
}

public class LinearAlgebra {

	// prints every entry in matrix
	public static void printMatrix(double[][] matrix) {
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				System.out.print(" |" + matrix[row][col] + "|");
			}
			System.out.println();
		}
	}

	// prints every entry in vector
	public static void printVector(double[] vector) {
		for (int i = 0; i < vector.length; i++) {
			System.out.print(roundEntry(vector[i]));
			if (i != vector.length - 1) {
				System.out.print(vector[i] + ", ");
			}
		}
	}

	public static void printVector(String[] vector) {
		for (String curr : vector) {
			System.out.println(curr);
		}
	}

	public static double[][] convertStringToMatrix(String original) {
		// removes all spaces from the String
		original = original.replaceAll(" ", "");
		// finds size of matrix by finding difference between String with and without
		// the commas and semicolons which indicates the number of entries
		original.substring(0, original.indexOf(";"));
		double[][] matrix = new double[original.length() - original.replaceAll(";", "").length()][1
				+ original.substring(0, original.indexOf(";")).length()
				- original.substring(0, original.indexOf(";")).replaceAll(",", "").length()];
		int row = 0, col = 0;
		while (!original.isEmpty()) {
			// case 1: when going right through the columns on the same row
			if (original.indexOf(",") != -1 && original.indexOf(",") < original.indexOf(";")) {
				// put the parsed String from the start of the index to the end
				matrix[row][col] = Double.parseDouble(original.substring(0, original.indexOf(",")));
				original = original.substring(original.indexOf(",") + 1);
				col++;
				// case 2: when reached rightmost entry and moves on to the next row
			} else if ((original.indexOf(",") != -1 && original.indexOf(",") > original.indexOf(";"))
					|| (matrix[row].length == 1 && original.substring(original.indexOf(";")).indexOf(";") != -1)) {
				matrix[row][col] = Double.parseDouble(original.substring(0, original.indexOf(";")));
				original = original.substring(original.indexOf(";") + 1);
				col = 0;
				row++;
			}
			// case 3: only single String of digits and a semicolon left (last entry)
			else {
				matrix[row][col] = Double.parseDouble(original.substring(0, original.indexOf(";")));
				col++;
				row++;
				break;
			}
		}
		return matrix;
	}

	public static double[] convertStringToVector(String original) {
		// removes all spaces from the String
		original = original.replaceAll(" ", "");
		// finds size of vector by finding difference between String with and without
		// the commas which indicates the number of entries
		double[] vector = new double[1 + original.length() - original.replaceAll(",", "").length()];
		int index = 0;
		while (index < vector.length && !original.isEmpty()) {
			// -1 implies that there is no such character
			if (original.indexOf(",") != -1) {
				// put the parsed String from the start of the index to the end
				vector[index] = Double.parseDouble(original.substring(0, original.indexOf(",")));
				original = original.substring(original.indexOf(",") + 1);
				index++;
			} else {
				// all that should be left is the single String of digits
				vector[index] = Double.parseDouble(original);
				index++;
			}
		}
		return vector;
	}
	
	public static void main(String[]args) {
		printVector(convertStringToVector("2,3,5"));
	}

	public static String displayMatrixAsString(double[][] matrix) {
		String display = "";
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				// add entry shortened down to length 5
				display += (normalizeLength(matrix[row][col]));
				// if not the last entry in the row, then add a comma and a space
				if (col != matrix[row].length - 1) {
					display += ", ";
				}
				// if entry is the last entry in the row, then add semicolon
				else {
					display += ";";
				}
			}
			display += "\n";
		}
		return display;
	}

	//
	public static String displayVectorAsString(double[] vector) {
		String display = "";
		for (int i = 0; i < vector.length; i++) {
			display += roundEntry(vector[i]);
			// if not the last entry in the matrix, then add a comma and a space
			if (i != vector.length - 1) {
				display += ", ";
			}
		}
		return display;
	}

	public static String displayStringVectorAsSingleString(String[] vectorX) {
		String stringVector = "";
		for (int curr = 0; curr < vectorX.length; curr++) {
			stringVector += curr != vectorX.length - 1 ? vectorX[curr] + "\n" : vectorX[curr];
		}
		return stringVector;
	}

	// make length of the string equal to 5, filling empty space with spaces to
	// ensure equal length
	public static String normalizeLength(double entry) {
		// converts double to String
		String stringEntry = "" + roundEntry(entry);
		// if the entry is longer than 5 characters, than cut down to
		if (stringEntry.length() > 9) {
			// gets ride of "." if it is the last entry
			if (stringEntry.charAt(8) == '.') {
				return stringEntry.substring(0, 8) + " ";
			}
			return stringEntry.substring(0, 9);
		}
		// fills remaining chars with spaces
		while (stringEntry.length() != 9) {
			stringEntry += " ";
		}
		return stringEntry;
	}

	// make a copy of a matrix (prevents user from accidentally changing originally
	// matrix entries)
	public static double[][] createCopyOfMatrix(double[][] matrix) {
		double[][] copy = new double[matrix.length][matrix[0].length];
		for (int row = 0; row < copy.length; row++) {
			for (int column = 0; column < copy[row].length; column++) {
				copy[row][column] = matrix[row][column];
			}
		}
		return copy;
	}

	// creates an n by n identity matrix
	public static double[][] createIdentityMatrix(int size) throws IllegalArgumentException {
		if (size < 1) {
			throw new IllegalArgumentException("Invalid size for identity matrix.");
		}
		// must be an n by n matrix
		double[][] iMatrix = new double[size][size];
		// iterate down the diagonal
		for (int diagonal = 0; diagonal < size; diagonal++) {
			// fills diagonals with 1
			iMatrix[diagonal][diagonal] = 1;
		}
		return iMatrix;
	}

	// creates zero vector (used for homogenous solutions)
	public static double[] createZeroVector(int size) throws IllegalArgumentException {
		if (size < 1) {
			throw new IllegalArgumentException("Size must be at least 1.");
		}
		return new double[size];
	}

	// rounds every entry in the matrix to the thousandths place
	public static double[][] roundEntriesOfMatrix(double[][] matrix) {
		// go through every entry in the matrix
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				matrix[row][col] *= 1000;
				// integer rounds down
				matrix[row][col] = matrix[row][col] % 10 >= 5 ? (int) (matrix[row][col] + 1) : (int) (matrix[row][col]);
				matrix[row][col] /= 1000;
			}
		}
		return matrix;
	}

	// rounds every entry in the vector to the thousandths place
	public static double[] roundEntriesOfVector(double[] vector) {
		// go through every entry in the vector
		for (int i = 0; i < vector.length; i++) {
			vector[i] *= 1000;
			// integer rounds down
			vector[i] = vector[i] % 10 >= 5 ? (int) (vector[i] + 1) : (int) (vector[i]);
			vector[i] /= 1000;
		}
		return vector;
	}

	public static double roundEntry(double entry) {
		entry *= 1000;
		// integer data type rounds down
		entry = entry % 10 >= 5 ? (int) (entry + 1) : (int) (entry);
		return entry /= 1000;
	}

	// Performs a row interchange operation
	public static double[][] interchangeRows(double[][] matrix, int rowOld, int rowNew)
			throws IllegalArgumentException {
		double[] tempRow = new double[matrix[rowOld].length];
		for (int temp = 0; temp < matrix[rowOld].length; temp++) {
			// Makes a temporary row to prevent data loss
			tempRow[temp] = matrix[rowOld][temp];
			// Replacing the into the "old" entries with the "new" entries
			matrix[rowOld][temp] = matrix[rowNew][temp];
			// Replacing duplicated entries with the "old" entries from the
			// temporary array
			matrix[rowNew][temp] = tempRow[temp];
		}
		return matrix;
	}

	public static double[][] getRREF(double[][] original) {
		// initialize coordinates for the pivot position
		double[][] matrix = createCopyOfMatrix(original);

		int pivotRow = 0, pivotCol = 0;
		// creating REF form, setting entries below the leading entries equal to zero
		for (int row = 0; row < matrix.length && pivotRow < matrix.length
				&& pivotCol < matrix[pivotRow].length; row++, pivotRow++, pivotCol++) {
			/*
			 * The while loop runs until the current pivot position is not equal to zero. If
			 * it is equal to zero, a temporary row pointer will iterate down the rows
			 * checking for a non-zero entry such that the rows can be interchanged. If the
			 * row pointer checks every entry below the pivot position without finding a
			 * non-zero entry and it is not the last column in the matrix, the pivot
			 * position will move a column over, finding a non-zero entry or restarting the
			 * process of looping down the row in search of row interchange.
			 */
			while (matrix[pivotRow][pivotCol] == 0) {
				int rowSwitcher = pivotRow;
				// row pointer iterates down rows checking for a non-zero
				// entry such that the rows can be interchanged.
				while (rowSwitcher < matrix.length && pivotCol < matrix[rowSwitcher].length) {
					if (matrix[rowSwitcher][pivotCol] != 0) {
						// row interchange
						matrix = interchangeRows(matrix, pivotRow, rowSwitcher);
						break;
					}
					rowSwitcher++;
				}
				// move to the next column if row switcher reaches last row
				if (pivotCol < matrix[pivotRow].length - 1) {
					pivotCol++;
				} else {
					// if the pivot column is at the last column, then stop incrementing
					break;
				}

			}
			/*
			 * next few lines can be removed to make convert the matrix to REF instead of
			 * RREF by the end of this for loop
			 */
			// reciprocal of pivot position, assuring no division by zero
			double normalizer = matrix[pivotRow][pivotCol] != 0 ? (1 / matrix[pivotRow][pivotCol]) : 1;
			// this sets the pivot row such that the pivot = 1 and the other entries in the
			// row are multiplied by the normalizing, reciprocal constant
			for (int col = pivotCol; col < matrix[row].length; col++) {
				matrix[row][col] *= normalizer;
			}
			// Row operation c*ri+rj=rj, where ri is the pivot row
			for (int rowDown = pivotRow + 1; rowDown < matrix.length; rowDown++) {
				// Finds the constant that allows entries below the current pivot to equal zero
				double canceller = -matrix[rowDown][pivotCol] / matrix[pivotRow][pivotCol];
				for (int colDown = pivotCol; colDown < matrix[rowDown].length; colDown++) {
					// -= constant times the entry at the current location of column pointer and the
					// pivot row
					matrix[rowDown][colDown] += canceller * matrix[pivotRow][colDown];
				}
			}
		}
		/*
		 * Finished iterating down the matrix and is in REF form but with rows with
		 * normalized leading entries the for loop will iterate back up the matrix to
		 * ensure that entries above the pivot positions are 0, satisfying all
		 * requirements of RREF form.
		 */
		// set pivot indexes to most recent pivot position
		pivotRow--;
		pivotCol--;
		// traverse matrix in opposite direction, bottom right to top left, touching
		// the pivots
		while (pivotRow >= 0 && pivotCol >= 0) {
			// moves pivot position through matrix to find leading entry without going out
			// of bounds
			if (matrix[pivotRow][pivotCol] == 0) {
				for (int rowTemp = pivotRow; rowTemp >= 0; rowTemp--) {
					int newColPosition = -1;
					for (int colTemp = pivotCol; colTemp >= 0; colTemp--) {
						if (matrix[rowTemp][colTemp] != 0) {
							newColPosition = colTemp;
						}
					}
					if (newColPosition != -1) {
						pivotRow = rowTemp;
						pivotCol = newColPosition;
						break;
					}
				}
			}
			// row operation c*ri+rj -> rj, ensuring all entries above the pivot positions
			// are zero.
			for (int rowUp = pivotRow - 1; rowUp >= 0; rowUp--) {
				// finds constant in row operation
				double canceller = matrix[pivotRow][pivotCol] != 0
						? -matrix[rowUp][pivotCol] / matrix[pivotRow][pivotCol]
						: -matrix[rowUp][pivotCol];
				// performs row operation going down columns of the row
				for (int colUp = pivotCol; colUp < matrix[rowUp].length; colUp++) {
					matrix[rowUp][colUp] += canceller * matrix[pivotRow][colUp];
				}
			}
			pivotRow--;
			pivotCol--;
		}
		return matrix;
	}

	public static double[][] getREF(double[][] original) {
		double[][] matrix = createCopyOfMatrix(original);
		// initialize coordinates for the pivot position
		int pivotRow = 0, pivotCol = 0;
		// creating REF form, setting entries below the leading entries equal to zero
		for (int row = 0; row < matrix.length && pivotRow < matrix.length
				&& pivotCol < matrix[pivotRow].length; row++, pivotRow++, pivotCol++) {
			/*
			 * The while loop runs until the current pivot position is not equal to zero. If
			 * it is equal to zero, a temporary row pointer will iterate down the rows
			 * checking for a non-zero entry such that the rows can be interchanged. If the
			 * row pointer checks every entry below the pivot position without finding a
			 * non-zero entry and it is not the last column in the matrix, the pivot
			 * position will move a column over, finding a non-zero entry or restarting the
			 * process of looping down the row in search of row interchange.
			 */
			while (matrix[pivotRow][pivotCol] == 0) {
				int rowSwitcher = pivotRow;
				// row pointer iterates down rows checking for a non-zero
				// entry such that the rows can be interchanged.
				while (rowSwitcher < matrix.length && pivotCol < matrix[rowSwitcher].length) {
					if (matrix[rowSwitcher][pivotCol] != 0) {
						// row interchange
						matrix = interchangeRows(matrix, pivotRow, rowSwitcher);
						break;
					}
					rowSwitcher++;
				}
				// move to the next column if row switcher reaches last row
				if (pivotCol < matrix[pivotRow].length - 1) {
					pivotCol++;
				} else {
					// if the pivot column is at the last column, then stop incrementing
					break;
				}

			}
			// Row operation c*ri+rj=rj, where ri is the pivot row
			for (int rowDown = pivotRow + 1; rowDown < matrix.length; rowDown++) {
				// Finds the constant that allows entries below the current pivot to equal zero
				double canceller = -matrix[rowDown][pivotCol] / matrix[pivotRow][pivotCol];
				//System.out.println(canceller);
				for (int colDown = pivotCol; colDown < matrix[rowDown].length; colDown++) {
					// -= constant times the entry at the current location of column pointer and the
					// pivot row
					matrix[rowDown][colDown] += canceller * matrix[pivotRow][colDown];
				}
			}
			//System.out.println(pivotRow + "  " + pivotCol);
			printMatrix(matrix);
		}
		//System.out.println(pivotRow + "  " + pivotCol);
		return matrix;
	}

	public static boolean linearIndependent(double[][] matrix) {
		// if the number of rows < columns, then rank <= rows < columns, so nullity must
		// be at least 1, having at least one free variable
		if (matrix.length < matrix[0].length) {
			return false;
		}
		double[][] rref = getRREF(matrix);
		int row = 0, col = 0;
		/*
		 * Goes down the diagonal of the rref form of the matrix, checking if the
		 * entries are equal to one. If there is a value that is not one (such as zero),
		 * then that means there is a free variable, meaning there are infinitely many
		 * solutions to homogeneous solution beyond than the trivial solution.
		 */
		while (row < rref.length && col < rref[row].length) {
			if (rref[row][col] != 1) {
				return false;
			}
			row++;
			col++;
		}
		return true;
	}

	// RankA = dim(col(A))
	public static int getRank(double[][] matrix) {
		if (getColumnSpaceBasis(matrix) == null) {
			return 0;
		}
		return getColumnSpaceBasis(matrix)[0].length;
	}

	// Nullity = # Columns - Rank
	public static int getNullity(double[][] matrix) {
		// if dim(Col(A)) = 0 = rankA
		if (getColumnSpaceBasis(matrix) == null) {
			return matrix[0].length;
		}
		return matrix[0].length - getColumnSpaceBasis(matrix)[0].length;
	}

	// finds the span of a set of vectors (the linearly independent generating set)
	public static double[][] getColumnSpaceBasis(double[][] matrix) {
		// must create a new matrix so the matrix that the new matrix is taking entries
		// from is not in RREF (the same memory address as the parameter)
		double[][] reference = createCopyOfMatrix(matrix);
		// put matrix in rref
		double[][] rref = getRREF(matrix);
		/*
		 * Initializing queue to store pivot column locations. Choose linked list >
		 * array because of dynamic inputs/sizing; linked list > ArrayList because first
		 * pivot col can be 0, which is indistinguishable from unused elements (excess
		 * memory allocated) initialized to zero with.
		 */
		Node<Integer> front = null, back = null;
		int pivotRow = 0, pivotCol = 0;
		// locating and finding pivot columns
		while (pivotRow < rref.length && pivotCol < rref[pivotRow].length) {
			// if the current pivot position is zero, shift pivot right until pivot == 1 or
			// pivotCol goes out of bounds
			while (pivotRow < rref.length && pivotCol < rref[pivotRow].length && rref[pivotRow][pivotCol] != 1) {
				pivotCol++;
			}
			if (front == null) {
				front = new Node<Integer>(pivotCol, null);
				back = front;
			} else {
				back.next = new Node<Integer>(pivotCol, null);
				back = back.next;
			}
			pivotRow++;
			pivotCol++;
		}
		// pivot row must equal number of vectors in the basis (dimension) because it is
		// equal to the rank of the matrix
		pivotCol = 0;
		double[][] basis = new double[matrix.length][pivotRow];
		while (front != null && pivotCol < basis[0].length) {
			for (int row = 0; row < basis.length; row++) {
				basis[row][pivotCol] = reference[row][front.data];
			}
			pivotCol++;
			front = front.next;
		}
		return basis;
	}

	public static double[][] getRowSpaceBasis(double[][] matrix) {
		// Row(A) = Col(A^T)
		return getColumnSpaceBasis(getTranspose(matrix));
	}

	// Subspace of the homogeneous solution
	public static double[][] getNullSpaceBasis(double[][] matrix) {
		// Dim(Null(A) = nullity(A)
		if (getNullity(matrix) == 0) {
			return null;
		}
		double[][] basis = new double[matrix[0].length][getNullity(matrix)], rref = getRREF(matrix);
		int pivotRow = 0, pivotCol = 0;
		ArrayList<Integer> freeVariableColumns = new ArrayList<Integer>();
		// go down diagonal of matrix, looking for the columns containing free variables
		while (pivotRow < rref.length && pivotCol < rref[pivotRow].length) {
			// if the pivot position contains a zero, then move pivot col right and mark
			// that column as containing a free variable
			while (rref[pivotRow][pivotCol] == 0) {
				freeVariableColumns.add(pivotCol);
				pivotCol++;
			}
			pivotRow++;
			pivotCol++;
		}

		for (int freeVar = 0; freeVar < basis[0].length; freeVar++) {
			for (int rowDown = 0; rowDown < rref.length; rowDown++) {
				// if variable is @ same index as free variable, then set = 1, else the negative
				// of current entry
				basis[rowDown][freeVar] = rowDown == freeVariableColumns.get(freeVar) ? 1
						: -rref[rowDown][freeVariableColumns.get(freeVar)];
			}
		}
		return basis;
	}

	public static double[][] getAugmentedMatrix(double[][] A, double[] b) throws IllegalArgumentException {
		if (A.length != b.length) {
			throw new IllegalArgumentException("The matrix A and vector b are of incompatible sizes");
		}
		double[][] augmentedMatrix = new double[A.length][A[0].length + 1];
		for (int row = 0; row < augmentedMatrix.length; row++) {
			for (int col = 0; col < augmentedMatrix[row].length; col++) {
				augmentedMatrix[row][col] = col == A[0].length ? b[row] : A[row][col];
			}
		}
		return augmentedMatrix;
	}

	// finds the solution for Ax=b
	public static String[] gaussianElimation(double[][] A, double[] b) {
		double[][] augmentedMatrix = getRREF(getAugmentedMatrix(A, b));
		String[] vectorX = new String[A[0].length];
		int pivotRow = 0, pivotCol = 0;
		while (pivotRow < augmentedMatrix.length && pivotCol < augmentedMatrix[pivotRow].length - 1) {
			while (augmentedMatrix[pivotRow][pivotCol] == 0) {
				// free variable
				vectorX[pivotCol] = "x" + pivotCol + " = " + "x" + pivotCol;
				pivotCol++;
			}
			int count = 0;
			for (int colPtr = pivotCol; colPtr < augmentedMatrix[pivotRow].length; colPtr++) {

				// if non-zero pivot position
				if (colPtr == pivotCol) {
					vectorX[pivotCol] = "x" + pivotCol + " = ";
				}
				// if non-zero, non-pivot position, and NOT in the last column, then
				else if (colPtr != augmentedMatrix[pivotRow].length - 1 && augmentedMatrix[pivotRow][colPtr] != 0) {
					vectorX[pivotCol] += count > 0 ? " + " + (-augmentedMatrix[pivotRow][colPtr]) + "x" + colPtr
							: (-augmentedMatrix[pivotRow][colPtr]) + "x" + colPtr;
					count++;
				}
				// if non-zero, non-pivot position, and IS in the last column, then
				else if (colPtr == augmentedMatrix[pivotRow].length - 1 && augmentedMatrix[pivotRow][colPtr] != 0) {
					vectorX[pivotCol] += count > 0 ? " + " + augmentedMatrix[pivotRow][colPtr]
							: augmentedMatrix[pivotRow][colPtr];
					count++;
				}
			}

			pivotRow++;
			pivotCol++;
		}
		// checks if solutions is inconsistent
		if (pivotRow < augmentedMatrix.length && pivotCol == augmentedMatrix[pivotRow].length - 1
				&& augmentedMatrix[pivotRow][pivotCol] != 0) {
			return new String[] { "Inconsistent solution" };
		}
		return vectorX;
	}

	// Switches the row (i) and column (j) entries
	public static double[][] getTranspose(double[][] original) {
		// Sets size of new matrix such that the number of rows and columns are put in
		// irrespective positions.
		double[][] transpose = new double[original[0].length][original.length];
		// Iterates through the original matrix.
		for (int row = 0; row < original.length; row++) {
			for (int col = 0; col < original[row].length; col++) {
				// Switching the entries of i,j of the original matrix such that they are j,i in
				// the transpose of the matrix.
				transpose[col][row] = original[row][col];
			}
		}
		return transpose;
	}

	// Creates a submatrix of a square matrix, removing one column and row
	public static double[][] getSubMatrix(double[][] original, int rowIg, int colIg) {
		// rowIg and colIg are the respective rows and columns of the original matrix to
		// be "ignored" when performing a cofactor expansion.
		double[][] subMatrix = new double[original.length - 1][original.length - 1];
		// Initialize the index pointers for the submatrix.
		int subRow = 0, subCol = 0;
		// Iterates through the original matrix
		for (int row = 0; row < original.length; row++) {
			for (int col = 0; col < original.length; col++) {
				// Adds entry to the submatrix if the entry belongs to row and column that is
				// not to be ignored.
				if (rowIg != row && colIg != col) {
					subMatrix[subRow][subCol] = original[row][col];
					// Increments submatrix column pointer by one.
					subCol++;
					// Reset submatrix column pointer once it reaches the max length.
					if (subCol == subMatrix.length) {
						subCol = 0;
					}
				}
			}
			// Increments submatrix row pointer by one if current row of original matrix is
			// not one to be ignored.
			if (rowIg != row) {
				subRow++;
			}
		}
		return subMatrix;
	}

	public static double cofactorExpansion(double[][] matrix) throws IllegalArgumentException {
		// checks if there is a valid input
		if (matrix.length < 2 || matrix == null) {
			throw new IllegalArgumentException("Invalid Matrix");
		}
		// base case to use form determinant = ad-bc
		if (matrix.length == 2) {
			return ((matrix[0][0] * matrix[1][1]) - (matrix[1][0] * matrix[0][1]));
		}
		// Initialize cofactor for each call, which is to say the signed determinant of
		// a submatrix.
		double cofactor = 0;
		// Iterates down the rows.
		for (int currIndex = 0; currIndex < matrix.length; currIndex++) {
			/*
			 * The formula for this operation is the sum of
			 * ((-1)^(row+column))(expansionPathEntry)(determinantOfSubmatrix) for each
			 * entry down an expansion path. A pattern of (-1)^(row+column) == 1 when row
			 * and column indices are both even or odd values. Since we are using keeping
			 * column index 0 as a constant (which is always even by definition) for every
			 * expansion path, we only need to see if the current index of the row is also
			 * even.
			 */
			// Creates recursive stack until the the matrix size == 2.
			cofactor += currIndex % 2 == 0
					? matrix[currIndex][0] * cofactorExpansion(getSubMatrix(matrix, currIndex, 0))
					: -1 * matrix[currIndex][0] * cofactorExpansion(getSubMatrix(matrix, currIndex, 0));
		}
		// Sum of the the signed determinant of a submatrix.
		return cofactor;
	}

	public static double[][] getInverse(double[][] original) throws IllegalArgumentException {
		// checks if determinant of the original matrix is not equal to zero AND number
		// of rows == number of columns (must be square to take inverse)
		if (original == null || original.length < 1 || (original.length != original[0].length)) {
			throw new IllegalArgumentException("This matrix is not invertible because of invalid sizing");
		}
		double determinant = original.length == 1 ? 1 : cofactorExpansion(original);
		if (determinant == 0) {
			throw new IllegalArgumentException("This matrix is not invertible because the determinant == 0");
		}
		double[][] inverse = new double[original.length][original.length];
		switch (inverse.length) {
		case 1: {
			inverse[0][0] = original[0][0] != 0 ? 1 / original[0][0] : 0;
			break;
		}
		case 2: {
			// formula: matrix of {a, b}, {c, d} -> inverse = (1/ad-bc)({d, -b}, {-c, a})
			double constant = 1 / ((original[0][0] * original[1][1]) - (original[1][0] * original[0][1]));
			inverse[0][0] = constant * original[1][1];
			inverse[1][1] = constant * original[0][0];
			inverse[0][1] = -constant * original[0][1];
			inverse[1][0] = -constant * original[1][0];
			break;
		}
		// when size of the original matrix is > 2
		default: {
			// finding the adjugate (cofactor matrix)
			for (int row = 0; row < inverse.length; row++) {
				for (int col = 0; col < inverse[row].length; col++) {
					inverse[row][col] = ((row + col) % 2 == 0) ? cofactorExpansion(getSubMatrix(original, row, col))
							: -cofactorExpansion(getSubMatrix(original, row, col));
				}
			}
			// multiplying each entry of the adjugate by 1/determinant
			double recipricalOfOriginalDeterminant = 1 / cofactorExpansion(original);
			for (int row = 0; row < inverse.length; row++) {
				for (int col = 0; col < inverse[row].length; col++) {
					inverse[row][col] *= recipricalOfOriginalDeterminant;
				}
			}
			break;
		}
		}
		return inverse;
	}

	public static double[] vectorAddition(double[] firstVector, double[] secondVector) throws IllegalArgumentException {
		// checks if vectors are the same size, throw error if not
		if (firstVector.length != secondVector.length) {
			throw new IllegalArgumentException("Mismatched sizes");
		}
		// creates new vector of equal length
		double[] vectorSum = new double[firstVector.length];
		// adds corresponding entries
		for (int curr = 0; curr < vectorSum.length; curr++) {
			vectorSum[curr] = firstVector[curr] + secondVector[curr];
		}
		return vectorSum;
	}

	public static double[] vectorSubtraction(double[] firstVector, double[] secondVector)
			throws IllegalArgumentException {
		// checks if vectors are the same size, throw error if not
		if (firstVector.length != secondVector.length) {
			throw new IllegalArgumentException("Mismatched sizes");
		}
		// creates new vector of equal length
		double[] vectorDifference = new double[firstVector.length];
		// adds corresponding entries
		for (int curr = 0; curr < vectorDifference.length; curr++) {
			vectorDifference[curr] = firstVector[curr] - secondVector[curr];
		}
		return vectorDifference;
	}

	public static double[][] matrixAddition(double[][] firstMatrix, double[][] secondMatrix)
			throws IllegalArgumentException {
		// matrices must have equal number of respective rows and columns
		if (firstMatrix.length != secondMatrix.length || firstMatrix[0].length != secondMatrix[0].length) {
			throw new IllegalArgumentException("Mismatched sizes");
		}
		// creates new matrix of the same size
		double[][] newMatrix = new double[firstMatrix.length][firstMatrix[0].length];
		// adds corresponding entries
		for (int row = 0; row < newMatrix.length; row++) {
			for (int col = 0; col < newMatrix[row].length; col++) {
				newMatrix[row][col] = firstMatrix[row][col] + secondMatrix[row][col];
			}
		}
		return newMatrix;
	}

	public static double[][] matrixSubtraction(double[][] firstMatrix, double[][] secondMatrix)
			throws IllegalArgumentException {
		if (firstMatrix.length != secondMatrix.length || firstMatrix[0].length != secondMatrix[0].length) {
			throw new IllegalArgumentException("Mismatched sizes");
		}
		double[][] newMatrix = new double[firstMatrix.length][firstMatrix[0].length];
		for (int row = 0; row < newMatrix.length; row++) {
			for (int col = 0; col < newMatrix[row].length; col++) {
				newMatrix[row][col] = firstMatrix[row][col] - secondMatrix[row][col];
			}
		}
		return newMatrix;
	}

	public static double convert1by1MatrixToDouble(double[][] matrix) throws IllegalArgumentException {
		if (matrix.length != 1 && matrix[0].length != 1) {
			throw new IllegalArgumentException("Matrix is not 1 by 1");
		}
		return matrix[0][0];
	}

	// multiplies matrix by a scalar constant
	public static double[][] scaleMatrix(double[][] matrix, double scalar) {
		// multiplies every entry in the matrix by the scalar
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				matrix[row][col] *= scalar;
			}
		}
		return matrix;
	}

	// dot product or (u^T)(v)
	public static double dotProduct(double[] firstVector, double[] secondVector) throws IllegalArgumentException {
		if (firstVector.length != secondVector.length) {
			throw new IllegalArgumentException("vectors must be equal length");
		}
		// initialize running sum to 0
		double product = 0;
		// add the product of corresponding entries to the running sum
		for (int curr = 0; curr < firstVector.length; curr++) {
			product += firstVector[curr] * secondVector[curr];
		}
		return product;
	}

	public static boolean isOrthogonal(double[] firstVector, double[] secondVector) {
		// if the dot product of two vectors equal zero, then they are orthogonal
		// (perpendicular) to each other
		if (dotProduct(firstVector, secondVector) == 0) {
			return true;
		}
		return false;
	}

	public static double[][] matrixMultiplication(double[][] firstMatrix, double[][] secondMatrix)
			throws IllegalArgumentException {
		// check if matrix dimensions match
		if (firstMatrix[0].length != secondMatrix.length) {
			throw new IllegalArgumentException("These matrices have mismatched sizes");
		}
		// initializes matrix product with same number of rows as the first matrix and
		// same number of the column as the second matrix.
		double[][] product = new double[firstMatrix.length][secondMatrix[0].length];
		// two outermost loops will iterate through the rows of the first matrix and the
		// columns of the second matrix.
		for (int row = 0; row < product.length; row++) {
			for (int col = 0; col < product[row].length; col++) {
				// this operation is the dot product of the row vector of the first matrix and
				// the column vector of the second matrix.
				for (int mult = 0; mult < firstMatrix[row].length; mult++) {
					product[row][col] += firstMatrix[row][mult] * secondMatrix[mult][col];
				}
			}
		}
		return product;
	}

	public static double[] matrixVectorMultiplication(double[][] matrix, double[] vector)
			throws IllegalArgumentException {
		if (vector.length != matrix[0].length) {
			throw new IllegalArgumentException("The matrix and vector have incompatible sizes");
		}
		// initialize new vector
		double[] aVector = new double[matrix.length];
		// this operation is the dot product of the row vector of the first matrix and
		// the column vector.
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				aVector[row] += matrix[row][col] * vector[col];
			}
		}
		return aVector;
	}
	
	/*
	public static void main(String[] args) {
		double[][] cMatrix = { { 1, 1 }, { 1, 3 }, { 1, 9 }, { 1, 12 }, { 1, 15 } };
		double[] yVector = { 21, 32, 38, 41, 51 };
		RegressionModel bivariate = new RegressionModel(cMatrix, yVector);
		System.out.println(convertParameterVectorToString(bivariate.parameterVector));
		System.out.println();
		LinearAlgebra.printVector(bivariate.responseVector);
		System.out.println();
		LinearAlgebra.printVector(bivariate.parameterVector);
		System.out.println();
		LinearAlgebra.printVector(bivariate.yHatVector);
		System.out.println();
		System.out.println(bivariate.standardDeviation);
		LinearAlgebra.printMatrix(bivariate.covarianceMatrix);
		System.out.println(bivariate.rSquared);
		System.out.println(bivariate.r);
		System.out.println("_____");
		double[][] mcMatrix = { { 1, 1, 4 }, { 1, 3, 7 }, { 1, 9, 7 }, { 1, 12, 10 }, { 1, 15, 40 } };
		double[] myVector = { 21, 32, 38, 41, 51 };
		RegressionModel multivariate = new RegressionModel(mcMatrix, myVector);
		System.out.println(convertParameterVectorToString(multivariate.parameterVector));
		System.out.println();
		LinearAlgebra.printVector(multivariate.responseVector);
		System.out.println();
		LinearAlgebra.printVector(multivariate.parameterVector);
		System.out.println();
		LinearAlgebra.printVector(multivariate.yHatVector);
		System.out.println();
		System.out.println(multivariate.standardDeviation);
		LinearAlgebra.printMatrix(multivariate.covarianceMatrix);
		System.out.println(multivariate.rSquared);
		System.out.println(multivariate.r);
		System.out.println("_____");
	}
	 */
}