package com.example.streamapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class StreamApiTutorialApplication {

    static List<Employee> employees = new ArrayList<>();
    static {
        employees.add(new Employee("John", "Doe", 1000.0, List.of("Project 1", "Project 2")));
        employees.add(new Employee("Jane", "Doe", 2000.0, List.of("Project 3", "Project 4")));
        employees.add(new Employee("Aaron", "Smith", 3000.0, List.of("Project 2", "Project 5")));
        employees.add(new Employee("Carrie", "Smith", 4000.0, List.of("Project 4", "Project 5")));
    }



    public static void main(String[] args) {
        //SpringApplication.run(StreamApiTutorialApplication.class, args);

        // foreach
        employees.stream()
                .forEach(employee -> System.out.println(employee.getFirstName()));

        // map
        // collect() is used to convert a stream to a collection.
        List<Employee> increasedSalaryList = employees.stream()
                .map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.2,
                        employee.getProjects()))
                .collect(Collectors.toList());
        System.out.println(increasedSalaryList);

        Set<Employee> increasedSalarySet = employees.stream()
                .map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.2,
                        employee.getProjects()))
                .collect(Collectors.toSet());
        System.out.println(increasedSalarySet);

        // filter
        List<Employee> filteredEmployees = employees.stream()
                .filter(employee -> employee.getSalary() < 3000)
                .map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.25,
                        employee.getProjects()
                )).toList();
        System.out.println(filteredEmployees);

        // filter
        // findFirst() is used to find the first element in a stream.
        Employee filterEmployeesAndFindFirst = employees.stream()
                .filter(employee -> employee.getSalary() < 3000)
                .map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.25,
                        employee.getProjects()
                )).findFirst()
                .orElse(null);

        System.out.println(filterEmployeesAndFindFirst);

        // flatMap
        List<String> projects = employees.stream()
                .flatMap(employee -> employee.getProjects().stream())
                .collect(Collectors.toList());
        System.out.println(projects);

        // flatMap
        List<String> projects2 = employees.stream()
                .flatMap(employee -> employee.getProjects().stream()).toList();
        System.out.println(projects2);

        // flatMap
        String projects3 = employees.stream()
                .map(employee -> employee.getProjects())
                .flatMap(strings -> strings.stream())
                .collect(Collectors.joining(","));
        System.out.println(projects3);

        // flatMap
        Set<String> projects4 = employees.stream()
                .map(Employee::getProjects)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        System.out.println(projects4);

        // short Circuit operations
        List<Employee> shortCircuit = employees
                .stream()
                .skip(1)
                .limit(2)
                .collect(Collectors.toList());
        System.out.println(shortCircuit);

        // finite data
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        // sorting
        List<Employee> sortedEmployees = employees
                .stream()
                .sorted((o1, o2) -> o1.getFirstName().compareToIgnoreCase(o2.getFirstName()))
                .collect(Collectors.toList());
        System.out.println(sortedEmployees);

        // min and max
        Employee minEmployee = employees
                .stream()
                .min((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary()))
                .orElse(null);
        System.out.println(minEmployee);

        Employee maxEmployee = employees
                .stream()
                .max((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary()))
                .orElse(null);
        System.out.println(maxEmployee);

        Employee minEmployee2 = employees
                .stream()
                .skip(1)
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow(NoSuchElementException::new);
        System.out.println(minEmployee2);

        // reduce
        Double sum = employees
                .stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println(sum);

        Double total = employees
                .stream()
                .map(Employee::getSalary)
                .reduce(0.0, Double::sum);
        System.out.println(total);
    }
}
