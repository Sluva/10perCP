package Task_6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class FrequencyOfWords extends Thread {
	static volatile BufferedReader bf;
	HashMap<String, Integer> localMap = new HashMap<>();
	static HashMap<String, Integer> mainMap = new HashMap<>();

	@Override
	public void run() {
		String[] array;
		String line = "";
		try {
			line = bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (line != null) {
			array = line.split("([.,!? ;:]|( ')|(' ))+");
			for (int i = 0; i < array.length; i++) {
				array[i] = array[i].toLowerCase();
				if (localMap.containsKey(array[i]))
					localMap.put(array[i], localMap.get(array[i]) + 1);
				else
					localMap.put(array[i], 1);
			}
			try {
				line = bf.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		File file;
		int threads;
		if (args == null || args.length == 0) {
			file = new File("./src/Task_6/files/shakespeare.txt");
			threads = 4;
		} else {
			file = new File(args[0]);
			threads = Integer.parseInt(args[1]);
		}
		try {
			bf = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		FrequencyOfWords[] pool = new FrequencyOfWords[threads];
		for (int j = 0; j < threads; j++) {
			pool[j] = new FrequencyOfWords();
			pool[j].start();
		}
		String s;
		for (int j = 0; j < threads; j++) {
			try {
				pool[j].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Iterator<String> it = pool[j].localMap.keySet().iterator();
			while (it.hasNext()) {
				s = it.next();
				if (mainMap.containsKey(s))
					mainMap.put(s, pool[j].localMap.get(s) + mainMap.get(s));
				else
					mainMap.put(s, pool[j].localMap.get(s));
			}
		}
	}
}
