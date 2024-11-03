package com.nayaz.ems.service.impl;

import com.nayaz.ems.dto.EmployeeDto;
import com.nayaz.ems.entity.Employee;
import com.nayaz.ems.exception.ResourceNotFoundException;
import com.nayaz.ems.mapper.Employeemapper;
import com.nayaz.ems.repository.EmployeeRepository;
import com.nayaz.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = Employeemapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return Employeemapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee is not exists with given id : " +employeeId));
        return Employeemapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee)-> Employeemapper.mapToEmployeeDto(employee)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
       Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                    new ResourceNotFoundException("Employee is not exists with given id : " +employeeId));

       employee.setFirstName(updatedEmployee.getFirstName());
       employee.setLastName(updatedEmployee.getLastName());
       employee.setEmail(updatedEmployee.getEmail());
       Employee updatedEmployeeObj =  employeeRepository.save(employee);
        return Employeemapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee is not exists with given id : " +employeeId));

                employeeRepository.deleteById(employeeId);

    }
}
