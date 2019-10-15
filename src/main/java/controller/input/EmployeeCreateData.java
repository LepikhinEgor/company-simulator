package controller.input;

public class EmployeeCreateData {
	private String name;
	private int age;
	private int perfomance;
	private int salary;
	private String sex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "EmployeeCreateData [name=" + name + ", age=" + age + ", perfomance=" + perfomance + ", salary=" + salary
				+ ", sex=" + sex + "]";
	}
	
}
