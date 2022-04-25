package Exception;

public class NumberOfSeedsException extends Exception {
	public NumberOfSeedsException() {
		super("Error The number of seeds must be less than or equal to the number of cells");
	}
}
