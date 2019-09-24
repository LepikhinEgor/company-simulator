package exceptions.employees;

public class EmployeesListException extends Exception{
	public EmployeesListException( ) {
		super();
	}
	
	public EmployeesListException(String message) {
		super(message);
	}
}
