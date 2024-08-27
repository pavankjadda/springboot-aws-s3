package com.pj.service;

import com.pj.dto.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

@Service
public class EmployeeService {
    public List<Employee> getEmployees() {
        // Create RestTemplate object with defaults
        var restTemplate = new RestTemplate();

        // Make request
        var response = restTemplate.exchange("https://my-json-server.typicode.com/pavankjadda/typicode-data/employees", GET, null, Employee[].class);

        // RestTemplate converts response to given object type
        return Arrays.stream(Objects.requireNonNull(response.getBody())).toList();
    }
}
