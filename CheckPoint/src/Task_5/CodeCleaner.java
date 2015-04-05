package Task_5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeCleaner {
	// Рядок який починається з початку і не містить симолів коментару (символи
	// коментару в лапках не враховуються)
	private static String start = "^((?!\\/(\\*|\\/)|\").)*(\"[^\"]*\"((?!\\/(\\*|\\/)|\").)*)*";
	// Добавляється до start для пошуку рядка із блочним коментарем
	private static String checkBlock = "(?=\\/\\*((?!\\*\\/).)*\\*\\/)";
	// Добавляється до start для пошуку рядка із блочним коментарем, який не був
	// закритий до кінця рядка
	private static String checkOpenBlock = "(?=\\/\\*((?!\\*\\/).)*$)";
	// Добавляється до start для пошуку рядка із лінійним коментарем
	private static String checkLine = "(?=\\/\\/)";
	// Добавляється до start для пошуку рядка із блочним коментарем, який був у
	// рядку закритий та не був відкритий
	private static String checkCloseBlock = "^((?!\\*\\/).)*\\*\\/";
	private static Pattern p;
	private static Matcher m;

	public static void main(String[] args) throws IOException {
		BufferedReader bf;
		File file;
		if (args == null || args.length == 0)
			file = new File("./src//Task_5//files//src.java");
		else
			file = new File(args[0]);
		bf = new BufferedReader(new FileReader(file));
		ArrayList<String> lines = new ArrayList<>();
		String line = bf.readLine();
		String currentLine = "";
		// Зчитуємо по рядках
		// Кожен рядок починається не із коментару
		while (line != null) {
			p = Pattern.compile(start + checkLine);
			m = p.matcher(line);
			// Якщо є лінійний коментар, видаляємо його і переходимо до нового рядка
			if (m.find()) {
				if (!line.substring(m.start(), m.end()).trim().isEmpty()
						|| (line.substring(m.start(), m.end()).isEmpty() && !lines
								.get(lines.size() - 1).isEmpty()))
					lines.add(line.substring(m.start(), m.end()));
				line = bf.readLine();
				continue;
			}
			currentLine = "";
			p = Pattern.compile(start + checkBlock);
			m = p.matcher(line);
			// Якщо є блокові коментарі, то видаляємо їх всіх у рядку, але не переходимо до нового рядка
			while (m.find()) {
				currentLine += line.substring(m.start(), m.end());
				line = m.replaceFirst("");
				p = Pattern.compile(checkCloseBlock);
				m = p.matcher(line);
				line = m.replaceFirst("");
				p = Pattern.compile(start + checkBlock);
				m = p.matcher(line);
			}
			p = Pattern.compile(start + checkOpenBlock);
			m = p.matcher(line);
			// Якщо є відкритий блоковий коментар, який закриється у наступному рядку
			if (m.find()) {
				currentLine += line.substring(m.start(), m.end());
				if ((currentLine.isEmpty() && !lines.get(lines.size() - 1)
						.isEmpty()) || !currentLine.trim().isEmpty())
					lines.add(currentLine);
				line = bf.readLine();
				p = Pattern.compile(checkCloseBlock);
				m = p.matcher(line);
				// Якщо такий є, то пропускаємо усі строки поки не буде символів заквиваючого блокового коментару
				while (!m.find() && line != null) {
					line = bf.readLine();
					m = p.matcher(line);
				}
				line = m.replaceFirst("");
			} else {
				if (currentLine.trim().isEmpty()) {
					if (!line.isEmpty()
							|| (line.isEmpty() && !lines.get(lines.size() - 1)
									.isEmpty()))
						lines.add(line);
				} else
					lines.add(currentLine);
				line = bf.readLine();
			}
		}
		bf.close();
		FileWriter fww;
		if (args == null || args.length == 0)
			fww = new FileWriter("./src/Task_5/files/srcDone.java");
		else
			fww = new FileWriter(args[0].substring(0, args[0].length() - 5)
					+ "Done.java");
		for (int i = 0; i < lines.size(); i++) {
			fww.write(lines.get(i) + "\n");
		}
		fww.close();
	}
}
