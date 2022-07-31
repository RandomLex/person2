package org.example.app.controllers;

import org.example.app.repositories.EmployeesRepository;
import org.example.app.repositories.EmployeesRepositoryInMemory;
import org.example.app.repositories.EmployeesRepositoryPostgres;
import org.example.app.repositories.RepositoryFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employees")
public class EmployeesController extends HttpServlet {

    private EmployeesRepository employeesRepository = RepositoryFactory.getEmployeesRepository();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employees", employeesRepository.findAll());
        req.getRequestDispatcher("employees.jsp").forward(req, resp);
    }

}
