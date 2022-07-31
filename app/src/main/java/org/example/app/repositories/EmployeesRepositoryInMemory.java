package org.example.app.repositories;

import org.example.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

//Singleton (double-check locking)
public class EmployeesRepositoryInMemory implements EmployeesRepository {

    private Map<Long, Employee> map = new ConcurrentHashMap<>();
    private static volatile EmployeesRepositoryInMemory instance;

    private EmployeesRepositoryInMemory() {
        map.put(1L, new Employee(1L, "Alex", 45, 100));
        map.put(2L, new Employee(2L, "Ivan", 30, 1100));
        map.put(3L, new Employee(3L, "Petr", 20, 500));
    }

    public static EmployeesRepositoryInMemory getInstance() {
        if (instance == null) {
            synchronized (EmployeesRepositoryInMemory.class) {
                if (instance == null) {
                    instance = new EmployeesRepositoryInMemory();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Employee> findAll() {
        return map.values().stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> find(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Employee save(Employee employee) {
        Long id = employee.getId();
        if (id == null) {
            id = generatedId();
            employee.setId(id);
        }
        map.put(id, employee);
        return employee;
    }

    @Override
    public Optional<Employee> remove(long id) {
        return Optional.ofNullable(map.remove(id));
    }

    private long generatedId() {
        long id;
        do {
            id = ThreadLocalRandom.current().nextLong(1, 1_000);
        } while (map.containsKey(id));
        return id;
    }
}
