package Task_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsePhoneNumber {

	static ArrayList<String> phoneBook = new ArrayList<String>();

	public void parseNumber(String number) {
		// Розбиття на частини в яких лише цифри
		String[] partsOfNumber = number.split(" \\(?|-|\\)? ");
		int amountParts = partsOfNumber.length;
		System.out.println("Your number: \t\t" + number);
		if (amountParts < 3 || amountParts > 5) {
			System.out.println("The number is incorrect\n");
			return;
		}
		boolean part1 = true;
		boolean part2 = true;
		boolean part3 = true;
		// Перевірки на коректність всіх частин
		switch (amountParts) {
		case 5:
			part1 = describeInternationalOrServiceCode(partsOfNumber[0]);
		case 4:
			part2 = describeAreaCode(partsOfNumber[amountParts - 4]);
		case 3:
			String[] mainPart = new String[3];
			System.arraycopy(partsOfNumber, amountParts - 3, mainPart, 0, 3);
			part3 = describeMainPartOfThePhone(mainPart);
		}
		if (part1 && part2 && part3) {
			String correctNumber = "";
			for (int i = 0; i < partsOfNumber.length; i++)
				correctNumber += (partsOfNumber[i]);
			phoneBook.add(correctNumber);
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader bf;
		File file;
		if (args == null || args.length == 0)
			bf = new BufferedReader(new InputStreamReader(System.in));
		else {
			file = new File(args[0]);
			bf = new BufferedReader(new FileReader(file));
		}
		String number = bf.readLine();
		ParsePhoneNumber parseNumber = new ParsePhoneNumber();
		while (number != null && !number.trim().isEmpty()) {
			parseNumber.parseNumber(number);
			number = bf.readLine();
		}
		bf.close();
		System.out.println();
	}

	private boolean describeInternationalOrServiceCode(String code) {
		if (code == null || !test(code, "^\\+?[0-9]+$")) {
			System.out
					.println("International code or Service code is incorrect");
			return false;
		}
		if (code.charAt(0) == '+')
			System.out.println("Internatinanal code: \t" + code);
		else
			System.out.println("Service code: \t\t" + code);
		return true;
	}

	private boolean describeAreaCode(String code) {
		if (code == null || !test(code, "^[0-9]{3,4}$")) {
			System.out.println("Area code or Service code is incorrect");
			return false;
		}
		System.out.println("Area code: \t\t" + code);
		return true;
	}

	private boolean describeMainPartOfThePhone(String[] code) {
		for (int i = 0; i < 3; i++)
			if (code == null || !test(code[i], "^[0-9]{2,3}$")) {
				System.out.println("Main part of phone is incorrect");
				return false;
			}
		System.out.println("Main part of the phone: " + code[0] + " " + code[1]
				+ " " + code[2]);
		return true;
	}

	public boolean test(String testString, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(testString);
		return m.matches();
	}
}
