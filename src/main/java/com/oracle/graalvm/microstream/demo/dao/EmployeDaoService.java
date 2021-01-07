package com.oracle.graalvm.microstream.demo.dao;

import com.oracle.graalvm.microstream.demo.model.Employee;

import java.util.List;

public interface EmployeDaoService {

    public Long insert(Employee e ) throws  Exception;
    public List<Employee> findAll();
    public List<Employee> findByFirstNameOrLastName(String firstOrLastName);
    Boolean delete(String uid);
}
