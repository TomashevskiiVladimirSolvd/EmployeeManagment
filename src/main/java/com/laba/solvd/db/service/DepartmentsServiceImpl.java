package com.laba.solvd.db.service;

import com.laba.solvd.db.dao.Interfaces.DepartmentRepository;
import com.laba.solvd.db.dao.Interfaces.EmployeeRepository;
import com.laba.solvd.db.dao.RepositoryImpl.DepartmentRepositoryImpl;
import com.laba.solvd.db.dao.RepositoryImpl.EmployeeRepositoryImpl;
import com.laba.solvd.db.model.Department;
import com.laba.solvd.db.model.Employee;
import com.laba.solvd.db.service.Interfaces.DepartmentService;
import com.laba.solvd.db.service.Interfaces.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentsServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public DepartmentsServiceImpl() {
        this.departmentRepository = new DepartmentRepositoryImpl();
        // this.departmentRepository= new DepartmentMapperImpl();
        this.employeeService = new EmployeesServiceImpl();
        this.employeeRepository = new EmployeeRepositoryImpl();

    }

    @Override
    public Department create(Department department) {
        department.setId(null);
        departmentRepository.create(department);

        if (department.getEmployee() != null) {
            List<Employee> employees = department.getEmployee().stream().
                    map(employee -> employeeService.create(employee))
                    .collect(Collectors.toList());
            for (Employee employee : employees) {
                employeeRepository.setEmployee(employee, department);
            }
        }
        return department;
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.getAll();
    }
}
