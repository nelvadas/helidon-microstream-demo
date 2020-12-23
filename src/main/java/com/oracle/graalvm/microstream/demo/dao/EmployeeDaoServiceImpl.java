package com.oracle.graalvm.microstream.demo.dao;

import com.oracle.graalvm.microstream.demo.model.Employee;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@ApplicationScoped
public class EmployeeDaoServiceImpl implements EmployeDaoService {
    private static final Logger logger = Logger.getLogger(EmployeDaoService.class.getName());
    private final AtomicReference<String> storageDirectory = new AtomicReference<>();
    private  DataRoot root= new DataRoot();

    private  EmbeddedStorageManager storageManager=null ;

    public EmployeeDaoServiceImpl(){
    }

    @Inject
    public EmployeeDaoServiceImpl(@ConfigProperty(name = "storage.directory") String storageDirectory){
        this.storageDirectory.set(storageDirectory);
        this.storageManager = EmbeddedStorage.start(root,Paths.get(storageDirectory));
    }

    @Override
    public Long insert(Employee e) throws Exception {
      int size = root.employeeList.size();
        root.employeeList.add(e);
        storageManager.store(root.employeeList);
        return Long.valueOf(size);
    }

    @Override
    public List<Employee> findAll() {

        root.employeeList.forEach(System.out::println);
        return root.employeeList;

    }
}
