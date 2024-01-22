package com.crio.xcompany.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Company{
    private String companyName;
    private Employee founder;
    private Map<String,Employee> employeeBook;
    private Map<String,List<Employee>> managrBook;

    private Company(String companyName, Employee founder) {
        this.companyName = companyName;
        this.founder = founder;
        employeeBook = new HashMap<String,Employee>();
     managrBook=new HashMap<>();
        employeeBook.put(founder.getName(), founder);
    }
    

    public static Company create(String companyName, Employee founder){
        return new Company(companyName,founder);
    } 


    public String getCompanyName() {
        return companyName;
    }


    public void registerEmployee(String employeeName, Gender valueOf) {
        this.employeeBook.put(employeeName,new Employee(employeeName,valueOf));
    }


    public void assignManager(String employeeName, String managerName) {
        if (employeeBook.containsKey(employeeName)) {
            employeeBook.get(managerName).setManager(true);
            Employee employee = employeeBook.get(employeeName);
            employee.setManagerName(managerName);
            managrBook.putIfAbsent(managerName, new ArrayList<>());
            managrBook.get(managerName).add(employee);
        }
    }
    


    public Employee getEmployee(String employeeName) {
        if(employeeBook.containsKey(employeeName))
       return  employeeBook.get(employeeName);
        return null;
    }


    public List<Employee> getDirectReports(String managerName) {
        return managrBook.get(managerName);
    }


    public List<Employee> getTeamMates(String employeeName) {
        String managerName=employeeBook.get(employeeName).getManagerName();
        List<Employee>emp=new ArrayList<>();
        emp.add(employeeBook.get(managerName));
        emp.addAll(managrBook.get(managerName));
        return emp;
    }


    public void deleteEmployee(String employeeName) {
        if(employeeBook.containsKey(employeeName)) employeeBook.remove(employeeName);
    }


    public List<List<Employee>> getEmployeeHierarchy(String managerName) {
        List<List<Employee>>res=new ArrayList<>();
        res.add(Arrays.asList(employeeBook.get(managerName)));
        Queue<List<Employee>>q=new LinkedList<>();
        if(managrBook.containsKey(managerName)) q.add(managrBook.get(managerName));
        while(!q.isEmpty()){
            int n=q.size();
            while(n>0){
                List<Employee>emp=q.poll();
                res.add(emp);
                for(Employee e:emp){
                    if(e.isManager()) q.add(managrBook.get(e.getName()));
                }
                n--;
            }
        }
        return res;

    }

    // TODO: CRIO_TASK_MODULE_XCOMPANY
    // Please define all the methods required here as mentioned in the XCompany BuildOut Milestone for each functionality before implementing the logic.
    // This will ensure that the project can be compiled successfully.

}
