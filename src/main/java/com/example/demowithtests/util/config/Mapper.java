package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.AddressDto;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    @Mapping(target = "name", source = "employeeDto.name")
    @Mapping(target = "country", source = "employeeDto.country")
    @Mapping(target = "email", source = "employeeDto.email")
    @Mapping(target = "addresses", source = "employeeDto.addresses")
    @Mapping(target = "photos", source = "employeeDto.photos")
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);

    @Mapping(target = "country", source = "country")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "street", source = "street")
    Address map(AddressDto addressDto);

    @Mapping(target = "linkToPhoto", source = "linkToPhoto")
    @Mapping(target = "x", source = "x")
    @Mapping(target = "y", source = "y")
    @Mapping(target = "createdDate", source = "createdDate")
    Photo map(PhotoDto value);

    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "country", source = "employee.country")
    @Mapping(target = "email", source = "employee.email")
    @Mapping(target = "addresses", source = "employee.addresses")
    @Mapping(target = "photos", source = "employee.photos")
    EmployeeDto employeeToEmployeeDto(Employee employee);

    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "email", source = "employee.email")
    EmployeeReadDto employeeToEmployeeReadDto(Employee employee);

    @Mapping(target = "name", source = "employeeReadDto.name")
    @Mapping(target = "email", source = "employeeReadDto.email")
    Employee employeeReadDtoToEmployee(EmployeeReadDto employeeReadDto);
}
