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
	// ����� ���� ���������� � ������� � �� ������ ������ ��������� (�������
	// ��������� � ������ �� ������������)
	private static String start = "^((?!\\/(\\*|\\/)|\").)*(\"[^\"]*\"((?!\\/(\\*|\\/)|\").)*)*";
	// ������������ �� start ��� ������ ����� �� ������� ����������
	private static String checkBlock = "(?=\\/\\*((?!\\*\\/).)*\\*\\/)";
	// ������������ �� start ��� ������ ����� �� ������� ����������, ���� �� ���
	// �������� �� ���� �����
	private static String checkOpenBlock = "(?=\\/\\*((?!\\*\\/).)*$)";
	// ������������ �� start ��� ������ ����� �� ������ ����������
	private static String checkLine = "(?=\\/\\/)";
	// ������������ �� start ��� ������ ����� �� ������� ����������, ���� ��� �
	// ����� �������� �� �� ��� ��������
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
		// ������� �� ������
		// ����� ����� ���������� �� �� ���������
		while (line != null) {
			p = Pattern.compile(start + checkLine);
			m = p.matcher(line);
			// ���� � ������ ��������, ��������� ���� � ���������� �� ������ �����
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
			// ���� � ������ ��������, �� ��������� �� ��� � �����, ��� �� ���������� �� ������ �����
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
			// ���� � �������� �������� ��������, ���� ��������� � ���������� �����
			if (m.find()) {
				currentLine += line.substring(m.start(), m.end());
				if ((currentLine.isEmpty() && !lines.get(lines.size() - 1)
						.isEmpty()) || !currentLine.trim().isEmpty())
					lines.add(currentLine);
				line = bf.readLine();
				p = Pattern.compile(checkCloseBlock);
				m = p.matcher(line);
				// ���� ����� �, �� ���������� �� ������ ���� �� ���� ������� ������������ ��������� ���������
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
