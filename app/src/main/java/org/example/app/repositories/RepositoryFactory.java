package org.example.app.repositories;

import java.io.IOException;
import java.util.Properties;

public final class RepositoryFactory {

    private RepositoryFactory() {
        // чтобы не создавали объекты этого класса
    }

    public static EmployeesRepository getEmployeesRepository() {
        Properties applicationProperties = new Properties();
        try {
            applicationProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String repositoryType = applicationProperties.getProperty("repository.type").toLowerCase();

        switch (repositoryType) {
            case "memory":
                return EmployeesRepositoryInMemory.getInstance();
            case "postgres":
                return EmployeesRepositoryPostgres.getInstance();
            default:
                throw new RuntimeException("Invalid repository type: " + repositoryType);
        }

//        if ("memory".equals(repositoryType)) {
//            return EmployeesRepositoryInMemory.getInstance();
//        } else if ("postgres".equals(repositoryType)) {
//            return EmployeesRepositoryPostgres.getInstance();
//        } else {
//            throw new RuntimeException("Invalid repository type: " + repositoryType);
//        }

    }
}
