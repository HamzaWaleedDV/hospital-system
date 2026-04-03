package com.mycompany.testaty;

public class Person {

    private String id;
    private String name;
    private int age;
    private String gender;
    private String phone;


    public Person(String id, String name, int age, String gender, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public String getRole() {
        return "Regular Person";
    }

    public void printInfo() {
        System.out.println("ID: " + id + " | Name: " + name + " | Age: " + age);
    }
}