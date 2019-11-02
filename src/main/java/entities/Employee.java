package entities;

import org.springframework.beans.support.ArgumentConvertingMethodInvoker;

import controller.input.EmployeeCreateData;
import controller.input.EmployeeUpdateData;

public class Employee {
	
	public static final String MALE = "male";
	public static final String FEMALE = "female";

	private long id;
	private String name;
	private int age;
	private String sex;
	private int perfomance;
	private int salary;
	private String description;
	private long companyId;
	
	public Employee() {
	}
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", perfomance=" + perfomance
				+ ", salary=" + salary + ", description=" + description + ", companyId=" + companyId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + (int) (companyId ^ (companyId >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + perfomance;
		result = prime * result + salary;
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (age != other.age)
			return false;
		if (companyId != other.companyId)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (perfomance != other.perfomance)
			return false;
		if (salary != other.salary)
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		return true;
	}
	
	
}
