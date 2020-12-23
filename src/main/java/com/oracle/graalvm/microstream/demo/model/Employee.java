package com.oracle.graalvm.microstream.demo.model;

import java.util.UUID;

public class Employee {

   private  String uid;
   private  String firstname;
   private  String lastname ;

      public Employee(String firstname, String lastname){
          this.firstname = firstname;
          this.lastname = lastname;
      }

    public Employee() {
          this.uid= UUID.randomUUID().toString();
    }

    public String getUid() {return uid;}
    public void setUid(String uid ){ this.setUid(uid);}

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "uid='" + uid + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
