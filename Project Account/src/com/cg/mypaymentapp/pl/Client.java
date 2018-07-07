package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.Scanner;

import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		String ans;
		WalletService service = new WalletServiceImpl();

		do {
			System.out.println("Enter your choice of operation:");
			System.out.println("1. Add new Account");
			System.out.println("2. Show Balance");
			System.out.println("3. Withdraw");
			System.out.println("4. Deposit");
			System.out.println("5. Fund transfer");

			int no = sc.nextInt();

			switch (no) {
			case 1:
				System.out.println("Enter your name: ");
				String name = sc.next();

				System.out.println("Enter your mobile number: ");
				String mob = sc.next();

				System.out.println("Enter balance: ");
				BigDecimal amount = sc.nextBigDecimal();

				System.out.println(service.createAccount(name, mob, amount));

				break;

			case 2:
				System.out.println("Enter mobile number for which you want to show balance: ");
				String mobBal = sc.next();
				System.out.println(service.showBalance(mobBal));

				break;

			case 3:
				System.out
						.println("Enter the mobile number from which you want to withdraw: ");
				String withdrawMobNo = sc.next();
				System.out.println("Enter the amount: ");
				BigDecimal cash1 = sc.nextBigDecimal();
				System.out
						.println(service.withdrawAmount(withdrawMobNo, cash1));

				break;

			case 4:
				System.out
						.println("Enter the mobile number in which you want to deposit: ");
				String depositMobNo = sc.next();
				System.out.println("Enter the amount: ");
				BigDecimal cash2 = sc.nextBigDecimal();
				System.out.println(service.depositAmount(depositMobNo, cash2));
				break;

			case 5:
				System.out
						.println("Enter mobile number from which cash is to be transferred: ");
				String phn1 = sc.next();

				System.out
						.println("Enter account number in which cash is to be transferred: ");
				String phn2 = sc.next();

				System.out
						.println("Enter the amount which is to be transfeered: ");
				BigDecimal cash3 = sc.nextBigDecimal();

				System.out.println(service.fundTransfer(phn1, phn2, cash3));
				break;

			default:
				System.out.println("Enter correct option.");
				break;

			}

			System.out.println("Do you want to continue? Yes/No");
			ans = sc.next();
		} while (ans.equals("Yes") || ans.equals("y") || ans.equals("yes"));

	}
}
