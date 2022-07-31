package org.example.app.repositories;

import org.example.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

//Singleton (double-check locking)
public class EmployeesRepositoryPostgres implements EmployeesRepository {

    private Map<Long, Employee> map = new ConcurrentHashMap<>();
    private static volatile EmployeesRepositoryPostgres instance;

    private EmployeesRepositoryPostgres() {
        map.put(1L, new Employee(1L, "Inna", 45, 100));
        map.put(2L, new Employee(2L, "Anna", 30, 1100));
        map.put(3L, new Employee(3L, "Leila", 20, 500));
    }

    public static EmployeesRepositoryPostgres getInstance() {
        if (instance == null) {
            synchronized (EmployeesRepositoryPostgres.class) {
                if (instance == null) {
                    instance = new EmployeesRepositoryPostgres();
                }
            }
        }
        return instance;
    }

    public List<Employee> findAll() {
        //select * from employees;
        return map.values().stream()
                .collect(Collectors.toList());
    }

    public Optional<Employee> find(long id) {
        return Optional.ofNullable(map.get(id));
    }

    public Employee save(Employee employee) {
        Long id = employee.getId();
        if (id == null) {
            id = generatedId();
            employee.setId(id);
        }
        map.put(id, employee);
        return employee;
    }

    private long generatedId() {
        long id;
        do {
            id = ThreadLocalRandom.current().nextLong(1, 1_000);
        } while (map.containsKey(id));
        return id;
    }

    public Optional<Employee> remove(long id) {
        return Optional.ofNullable(map.remove(id));
    }
}
