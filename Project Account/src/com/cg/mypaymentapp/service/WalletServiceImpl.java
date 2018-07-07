package com.cg.mypaymentapp.service;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;
import com.cg.mypaymentsapp.exception.InsufficientBalanceException;
import com.cg.mypaymentsapp.exception.InvalidInputException;

public class WalletServiceImpl implements WalletService {
	public static Scanner sc = new Scanner(System.in);

	private WalletRepo repo;

	public WalletServiceImpl(Map<String, Customer> data) {
		repo = new WalletRepoImpl(data);
	}

	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	public WalletServiceImpl() {
		repo = new WalletRepoImpl(new HashMap<String, Customer>());
	}

	@Override
	public Customer createAccount(String name, String mobileno,
			BigDecimal amount) {
	
		while (true) {

			if (validateMobile(mobileno)) {
				break;
			} 
			else {
				System.err
						.println("Wrong mobile number. It should be 10 digits.");
				System.err.println("Please enter again: ");
				mobileno = sc.next();
			}
		}
		Customer customer = new Customer(name, mobileno, new Wallet(amount));

		boolean result = repo.save(customer);
		if (result == true) {
			return customer;
		}
		else {
			return null;
		}
		
}

	private boolean validateMobile(String mobileNo) {
		String pattern = "[1-9][0-9]{9}";
		if (mobileNo.matches(pattern)) {
			return true;
		} 
		else {
			return false;
		}
	}

	@Override
	public Customer showBalance(String mobileNo) {
		Customer customer = repo.findOne(mobileNo);
		if (customer != null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo,
			BigDecimal amount) {
		
		Customer sourceCustomer = repo.findOne(sourceMobileNo);
		Customer targetCustomer = repo.findOne(targetMobileNo);
		Wallet sourceWallet = sourceCustomer.getWallet();
		Wallet targetWallet = targetCustomer.getWallet();
		BigDecimal balance1 = sourceWallet.getBalance().subtract(amount);
		sourceWallet.setBalance(balance1);
		sourceCustomer.setWallet(sourceWallet);
		BigDecimal balance2 = targetWallet.getBalance().add(amount);
		targetWallet.setBalance(balance2);
		targetCustomer.setWallet(targetWallet);
		
		if (sourceWallet.getBalance().compareTo(amount)==-1)
		{
		throw new InsufficientBalanceException("Insufficient balance.");
		}
		else
		return sourceCustomer;
		}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) {

		Customer depCustomer = repo.findOne(mobileNo);

		Wallet depWallet = depCustomer.getWallet();

		BigDecimal balance1 = depWallet.getBalance().add(amount);
		depWallet.setBalance(balance1);
		depCustomer.setWallet(depWallet);

		return depCustomer;
		}
		

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		Customer withCustomer = repo.findOne(mobileNo);

		Wallet withWallet = withCustomer.getWallet();

		BigDecimal balance1 = withWallet.getBalance().subtract(amount);
		withWallet.setBalance(balance1);
		withCustomer.setWallet(withWallet);

		if (withWallet.getBalance().compareTo(amount)==-1)
		{
		throw new InsufficientBalanceException("Insufficient balance.");
		}
		else
			return withCustomer;
	}
		
}