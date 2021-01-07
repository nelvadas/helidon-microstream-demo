package com.oracle.graalvm.microstream.demo.dao;

import com.oracle.graalvm.microstream.demo.model.Employee;
import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.List;

public class DataRoot {
     List<Employee> employeeList;

     DataRoot(){
          employeeList = new ArrayList<Employee>(1);
     }


}
