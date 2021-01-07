package com.oracle.graalvm.microstream.demo.dao;

import com.oracle.graalvm.microstream.demo.model.Employee;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class EmployeeDaoServiceImpl implements EmployeDaoService {
    private static final Logger logger = Logger.getLogger(EmployeDaoService.class.getName());
    private final AtomicReference<String> storageDirectory = new AtomicReference<>();
    private  DataRoot root;

    private  EmbeddedStorageManager storage=null ;

    public EmployeeDaoServiceImpl(){
    }

    @Inject
    public EmployeeDaoServiceImpl(@ConfigProperty(name = "storage.directory") String storageDirectory){
        this.storageDirectory.set(storageDirectory);
        storage = EmbeddedStorage.start(root,Paths.get(storageDirectory));
        if(storage.root() == null){
            root = new DataRoot();
            storage.setRoot(root);
            storage.storeRoot();
            logger.info("No Storage found");
        }else{
            root = (DataRoot)storage.root();
            logger.info(" Storage found "+root);

        }

    }

    public Long insert(Employee e) throws Exception {

      int size = root.employeeList.size();
        root.employeeList.add(e);
        storage.store(root.employeeList);
        return Long.valueOf(size);
    }


    public List<Employee> findAll() {

        root.employeeList.forEach(System.out::println);
        return root.employeeList;

    }

    public List<Employee> findByFirstNameOrLastName(String firstOrLastName) {

        return root.employeeList
                .stream()
                .filter( e -> (firstOrLastName.equals(e.getFirstname()) || firstOrLastName.equals(e.getLastname())))
                .collect(Collectors.toList());
    }


    @Override
    public Boolean delete(String uid) {

        boolean deleted =root.employeeList.removeIf(e -> (uid.equals(e.getUid())));
        storage.store(root.employeeList);
        return deleted;
    }
}
