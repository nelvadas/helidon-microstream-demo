
package com.oracle.graalvm.microstream.demo;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.oracle.graalvm.microstream.demo.dao.*;
import com.oracle.graalvm.microstream.demo.model.Employee;

/**
 * A simple JAX-RS CRUD resource:
 *
 * Get all employees in the database :
 * curl -X GET http://localhost:8080/employee
 *
 * Get employe with name Joe:
 * curl -X GET http://localhost:8080/employee/Joe
 *
 * Create a new employe
 * curl -X POST -H "Content-Type: application/json"  -d '{"firstname":"GraalVM","lastname":"MicroStream"}'  http://localhost:8080/employe
 *
 * The message is returned as a JSON object.
 */
@Path("/employee")
@RequestScoped
public class EmployeeResource {


    @Inject
    private EmployeDaoService employeDao;

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployee( Employee e) {
        long  id= -1;
        try {
            id = employeDao.insert(e);
            return Response.status(Response.Status.OK).entity(e).build();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }



    @Path("/{uid}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployeeByUID(@PathParam("uid") String uid) {

        try {
           Boolean deleted = employeDao.delete(uid);

            return Response.status(Response.Status.OK).entity(deleted).build();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }



    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEmployeeByName(@PathParam("name") String name) {

        try {
            List<Employee> emp = employeDao.findByFirstNameOrLastName(name);
            return Response.status(Response.Status.OK).entity(emp).build();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Set the greeting to use in future messages.
     *
     * @return {@link Response}
     */
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEmploye() {

        List<Employee> emps = employeDao.findAll();
        return Response.status(Response.Status.OK).entity(emps).build();
    }







    public EmployeDaoService getEmployeDao() {
        return employeDao;
    }

    public void setEmployeDao(EmployeDaoService employeDao) {
        this.employeDao = employeDao;
    }
}
