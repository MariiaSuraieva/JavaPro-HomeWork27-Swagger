package com.example.demowithtests.service;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.util.*;
import org.springframework.transaction.annotation.Transactional;

import java.lang.System.Logger.Level;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//@AllArgsConstructor
//@Slf4j
@org.springframework.stereotype.Service
public class EmployeeServiceBean implements EmployeeService {

    private static final Logger log = Logger.getLogger(EmployeeServiceBean.class.getName());
    private final Repository repository;

    public EmployeeServiceBean(Repository repository) {
        this.repository = repository;
    }

    // @SneakyThrows
    @Override
    public Employee create(Employee employee) {
        if (repository.findEmployeeByEmail(employee.getEmail()) == null) {
            if (employee.getEmail() == null) {
                throw new EmailAbsentException();
            }
            return repository.save(employee);
        }
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        if (repository.findAll().size() > 0) {
            if (repository.findAll().size() == repository.findEmployeeByIsDeletedIsTrue().size()) {
                throw new ListWasDeletedException();
            }
            return repository.findAll();
        }
        throw new ListHasNoAnyElementsException();

    }

    @Override
    public Employee getById(String id) {
        try {
            Integer employeeId = Integer.parseInt(id);
            Employee employee = repository.findById(employeeId)
                    .orElseThrow(IdIsNotExistException::new);
            if (employee.getIsDeleted()) {
                throw new ResourceWasDeletedException();
            }
            return employee;
        } catch (NumberFormatException exception) {
            throw new WrongDataException();
        }
    }

    //@SneakyThrows
    @Override
    public Employee updateById(Integer id, Employee employee) throws UserIsNotExistException {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    return repository.save(entity);
                })
                .orElseThrow(UserIsNotExistException::new);
    }

    @Override
    public void removeById(Integer id) {
        Employee employee = repository.findById(id)
                .orElseThrow(IdIsNotExistException::new);
        if (employee.getDeleted()) throw new UserAlreadyDeletedException();
        employee.setIsDeleted(true);
        repository.save(employee);
    }

    @Override
    public void removeAll() {

        if (repository.findAll().size() > 0) {
            if (repository.findAll().size() == repository.findEmployeeByIsDeletedIsTrue().size()) {
                throw new ListWasDeletedException();
            }
            List<Employee> base = repository.findAll();
            for (Employee employee : base) {
                employee.setIsDeleted(true);
            }
        }
        throw new ListHasNoAnyElementsException();


    }


    public void mailSender(List<String> emails, String text) {
        log.info("Emails sended");
    }

    @Override
    public List<Employee> sendEmailByCountry(String country, String text) {
        List<Employee> employees = repository.findEmployeeByCountry(country);
        mailSender(getterEmailsOfEmployees(employees), text);
        return employees;
    }

    public List<Employee> sendEmailByCity(String citiesString, String text) {
        String[] citiesArray = citiesString.split(",");
        List<String> citiesList = Arrays.asList(citiesArray);
        List<Employee> employees = new ArrayList<>();
        for (String city : citiesList) {
            List<Employee> employeesByCity = repository.findEmployeeByAddresses(city);
            employees.addAll(employeesByCity);
        }
        mailSender(getterEmailsOfEmployees(employees), text);
        log.info("hello");
        return employees;

    }

    @Override
    public void fillingDataBase(String quantityString) {
        int quantity = Integer.parseInt(quantityString);
        for (int i = 0; i <= quantity; i++) {
            repository.save(createrEmployee("name", "country", "email"));
        }
    }


    @Override
    public void updaterByCountryFully(String countries) {
        List<Employee> employees = repository.findAll();
        for (Employee employee : employees) {
            employee.setCountry(randomCountry(countries));
            repository.save(employee);
        }
    }

    @Override
    @Transactional
    public void updaterByCountrySmart(String countries) {
        List<Employee> employees = repository.findAll();
        for (Employee employee : employees) {
            String newCountry = randomCountry(countries);
            if (!employee.getCountry().equals(newCountry)) {
                employee.setCountry(newCountry);
                repository.save(employee);
            }
        }
    }

    @Override
    public List<Employee> processor() {
        log.info("replace null  - start");
        List<Employee> replaceNull = repository.findEmployeeByIsDeletedNull();
        log.info("replace null after replace: " + replaceNull);
        for (Employee emp : replaceNull) {
            emp.setIsDeleted(Boolean.FALSE);
        }
        // log.info("replaceNull = {} ", replaceNull);
        log.info("replace null  - end:");
        return repository.saveAll(replaceNull);
    }

    @Override
    public String randomCountry(String countriesString) {
        /*List<String> countries = List.of(countriesString.split(","));
        int randomIndex = (int) (Math.random() * countries.size());
        return countries.get(randomIndex);*/
        return countriesString;
    }

    private static List<String> getterEmailsOfEmployees(List<Employee> employees) {
        List<String> emails = new ArrayList<>();
        for (Employee employee : employees) {
            emails.add(employee.getEmail());
        }
        return emails;
    }

    @Override
    public Employee createrEmployee(String name, String country, String email) {
        return new Employee(name, country, email);
    }

    @Override
    public void emailSenderPhotoChange() {
        Duration interval = Duration.ofDays(365);
        List<Employee> employees = repository.findAll();
        String text = "You should update your employee photo";
        List<Employee> employeesForChanges = new ArrayList<>();
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getPhotos().isEmpty()) {
                employeesForChanges.add(employee);
                iterator.remove();
            } else {
                Set<Photo> photos = employee.getPhotos();
                Set<Photo> oldPhotos = photos.stream()
                        .filter(photo -> Optional.ofNullable(photo.getCreatedDate())
                                .map(createdDate -> Duration.between
                                        (createdDate.toInstant(), Instant.now()).compareTo(interval) >= 0)
                                .orElse(false))
                        .collect(Collectors.toSet());
                if (!oldPhotos.isEmpty()) {
                    employeesForChanges.add(employee);
                }
            }
        }
        mailSender(getterEmailsOfEmployees(employeesForChanges), text);
    }

    @Override
    public void emailSenderWhoMovedFromUkraine(String country) {
        String text = "We won the war, please, come back home";
        List<Employee> employeesFromUkraine =
                repository.findEmployeeByCountry(country);
        List<Employee> employeesMovedFromUkraine = employeesFromUkraine.stream()
                .filter(employee -> employee.getAddresses().stream()
                        .anyMatch(address -> address.getCountry().equals(country)
                                && !address.getAddressHasActive()))
                .collect(Collectors.toList());
        mailSender(getterEmailsOfEmployees(employeesMovedFromUkraine), text);
        System.out.println("Sent emails " + employeesMovedFromUkraine.size());
    }
}