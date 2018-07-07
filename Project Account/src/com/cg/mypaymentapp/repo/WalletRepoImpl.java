package com.cg.mypaymentapp.repo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cg.mypaymentapp.beans.Customer;

public class WalletRepoImpl implements WalletRepo {

	private Map<String, Customer> data = new HashMap<>();

	public WalletRepoImpl(Map<String, Customer> data) {
		super();

	}

	@Override
	public boolean save(Customer customer) {
		if (customer == null) {
			return false;
		} else {
			data.put(customer.getMobileNo(), customer);
			return true;
		}
	}

	@Override
	public Customer findOne(String mobileNo) {

		Set set = data.entrySet();
		Customer customer = null;
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) 
		{
			Map.Entry me = (Map.Entry) iterator.next();
			if (me.getKey().equals(mobileNo)) 
			{
				customer = (Customer) me.getValue();
			}
		}
		return customer;
	}

}
