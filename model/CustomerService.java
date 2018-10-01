/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.model;

import java.util.List;
import uuu.resort.domain.Customer;
import uuu.resort.domain.ResortException;

/**
 *
 * @author PattyTai
 */
public class CustomerService {
    private RDBCustomersDAO dao = new RDBCustomersDAO();

    public Customer login(String id, String pwd)throws ResortException{
        if((id==null || ((id=id.trim()).length()==0)) || 
                ((pwd==null)|| ((pwd=pwd.trim()).length()==0))){
            throw new ResortException("必須輸入帳號密碼!");
        }
        
        Customer c = dao.get(id);
        if(c!=null && pwd.equals(c.getPassword())){
            return c;
        }
        
        throw new ResortException("帳號或密碼不正確，登入失敗!");
    }
    
    
    public void register(Customer c) throws ResortException {
        dao.insert(c);
    }

    public void update(Customer c) throws ResortException {
        dao.update(c);
    }

    public void delete(Customer c) throws ResortException {
        dao.delete(c);
    }

    public Customer get(String id) throws ResortException {
        return dao.get(id);
    }

    public List<Customer> getAll() throws ResortException {
        return dao.getAll();
    }
}
