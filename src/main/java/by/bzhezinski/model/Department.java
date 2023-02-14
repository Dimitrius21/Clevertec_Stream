package by.bzhezinski.model;

import java.util.List;

public class Department {
    private int id;
    private String name;
    private String departmentType;
    private List<Person> personList;

    public Department() {
    }

    public Department(int id, String name, String departmentType, List<Person> personList) {
        this.id = id;
        this.name = name;
        this.departmentType = departmentType;
        this.personList = personList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", departmentName='" + name +", departmentType='" + departmentType + "', personList=" + personList + '}';
    }
}
