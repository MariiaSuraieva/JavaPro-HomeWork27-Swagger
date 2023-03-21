package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.UserIsNotExistException;
import com.example.demowithtests.util.config.Mapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeController {

    private final EmployeeService employeeService;
    private final Mapper mapper;

    @Override
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = mapper.employeeDtoToEmployee(employeeDto);
        EmployeeDto dto = mapper.employeeToEmployeeDto(employeeService.create(employee));
        return dto;
    }

    @Override
    public List<EmployeeReadDto> getAllUsers() {
        List<Employee> employees = employeeService.getAll();
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        for (Employee employee : employees) {
            employeesReadDto.add(
                    mapper.employeeToEmployeeReadDto(employee)
            );
        }
        return employeesReadDto;
    }

    @Override
    public EmployeeReadDto getEmployeeById(@PathVariable String id) {
        return mapper.employeeToEmployeeReadDto(
                employeeService.getById(id)
        );
    }

    @Override
    public EmployeeReadDto refreshEmployee(@PathVariable("id") String id, @RequestBody EmployeeDto employeeDto) throws UserIsNotExistException {
        Integer parseId = Integer.parseInt(id);
        return mapper.employeeToEmployeeReadDto(
                employeeService.updateById(parseId, mapper.employeeDtoToEmployee(employeeDto)
                )
        );
    }

    @Override
    public void removeEmployeeById(@PathVariable String id) {
        Integer parseId = Integer.parseInt(id);
        employeeService.removeById(parseId);
    }

    @Override
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @Override
    public void sendEmailByCountry(@RequestParam String country, @RequestParam String text) {
        employeeService.sendEmailByCountry(country, text);
    }

    @Override
    public void sendEmailByCity(@RequestParam String cities, @RequestBody String text) {
        employeeService.sendEmailByCity(cities, text);
    }

    @Override
    public void fillingDataBase(@PathVariable String quantity) {
        employeeService.fillingDataBase(quantity);
    }

    @Override
    public void updateByCountryFully(@RequestParam String countries) {
        employeeService.updaterByCountryFully(countries);
    }

    @Override
    public void updateByCountrySmart(@RequestParam String countries) {
        employeeService.updaterByCountrySmart(countries);
    }

    @Override
    public void replaceNull() {
        employeeService.processor();
    }

    @Override
    public void emailSenderPhotoChange() {
        employeeService.emailSenderPhotoChange();
    }

    @Override
    public void emailSenderWhoMovedFromUkraine(@RequestParam String country) {
        employeeService.emailSenderWhoMovedFromUkraine(country);
    }
}