package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidId;
import com.example.demo.exception.InvalidMobileNumber;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository cr;

	@Override
	public void add(Customer customer) {
		// TODO Auto-generated method stub
		String mob = customer.getMob();
		
		if(mob!=null) {
			mob.trim();
			if(mob.startsWith("+91")) {
				mob=mob.substring(3);
				
			}
				 
			if(mob.startsWith("91")) {
				mob=mob.substring(2);
				
			}
			
			
		}
       
        if(mob.length() == 10) {
			if(mob.charAt(0) == '0' || mob.charAt(0) == '1' || mob.charAt(0) == '2' ||mob.charAt(0) =='3' ||mob.charAt(0) == '4' ||mob.charAt(0) =='5')
				throw new InvalidMobileNumber("INVALID MOBILE NUMBER");
			
			for(int i=0; i<mob.length(); i++) {
				if(!Character.isDigit(mob.charAt(i)))
					throw new InvalidMobileNumber("INVALID MOBILE NUMBER");
				
			}
			List<Customer> list=cr.findAll();
			
			for(Customer  c:list) {
				if(mob.equals(c.getMob())) {
					throw new InvalidMobileNumber("Mobile Already Exists!!!");
					
				}
			}
		}else
			throw new InvalidMobileNumber("INVALID MOBILE NUMBER");
		
		customer.setMob(mob);
		 
        Integer id=customer.getId();
        
        if(id <= 0) {
        	throw new InvalidId("Invalid ID");//
        }
        List<Customer> list=cr.findAll();
		
		for(Customer  x : list) {
			if(id.equals(x.getId())) {
				throw new InvalidId("Id Already Exists!!!");
				
			}
			
			
		}
        cr.save(customer);
		
		
			
		
	}

	@Override
	public List<Customer> display() {
		// TODO Auto-generated method stub
		return cr.findAll();  //Select * from customer
	}

	@Override
	public Customer delete(Integer id) {
		// TODO Auto-generated method stub
		
		//search
		if(cr.findById(id).isPresent()) {
			Customer temp = cr.findById(id).get();
		    cr.deleteById(id); //Delete
		    return temp;
		}
		return null;
	}

	@Override
	public Customer update(Customer customer, Integer id) {
		// TODO Auto-generated method stub
		//customer.setId(id);
		//cr.save(customer);
		 if (id == null || id <= 0) {
		        throw new RuntimeException("Invalid ID");
		    }

		    if (!cr.existsById(id)) {
		        return null;   
		    }

		    customer.setId(id);     
		    return cr.save(customer);
		
	}

	@Override
	public Customer search(Integer id) {
		// TODO Auto-generated method stub
		
		if(cr.findById(id).isPresent()) {
			Customer temp = cr.findById(id).get();
	
		    return temp;
		}
		return null;
		
		
	}

	@Override
	public void addAll(List<Customer> list) {
		// TODO Auto-generated method stub
		cr.saveAll(list);
		
	}

	@Override
	public Customer findByMob(String mob) {
		// TODO Auto-generated method stub
		return cr.findByMob(mob);
		
		
	}

	@Override
	public List<Customer> findByName(String name) {
		// TODO Auto-generated method stub
		return cr.findByName(name);
		
	}

	@Override
	public List<Customer> findByAddress(String address) {
		// TODO Auto-generated method stub
		return cr.findByAddress(address);
	}
	
	
	

}
