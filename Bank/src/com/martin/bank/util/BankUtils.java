package com.martin.bank.util;

import java.util.Scanner;

public class BankUtils {
	public static int getValidInteger(Scanner input) {
		while (true) {
			try {
				return Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				System.err.println("Enter valid number");
			}
		}
	}
}
