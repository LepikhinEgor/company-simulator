package entities;

public class Employee {
	
	public static final String MALE = "male";
	public static final String FEMALE = "female";

	private String name;
	private int age;
	private String sex;
	private int perfomance;
	private int salary;
	private String description;
	
	public Employee(String name, int age, String sex, int perfomanse, int salary, String description) {
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.perfomance = perfomanse;
		this.salary = salary;
		this.description = description;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPerfomance() {
		return perfomance;
	}

	public void setPerfomance(int perfomance) {
		this.perfomance = perfomance;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getDescription() {
		return description;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}