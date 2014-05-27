import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BagOfWordsModel {

	public HashMap<String, Long> dictionaryMap;

	public static void main(String[] args) throws IOException {
		File filePointer = new File("phrases.txt");

		if (!filePointer.exists()) {
			extractPhrasesFromTrainingSet();
			startModelling();
		} else {
			startModelling();
		}

	}

	private static void startModelling() throws FileNotFoundException,
			IOException {
		FileInputStream phrasesFileStream = new FileInputStream("phrases.txt");
		System.out.println("phrases.txt -- File exists!");
		// BagofWords
		System.out
				.println("Starting modelling Bag Of Words representation....");
		generateBagOfWords(phrasesFileStream);
	}

	private static void generateBagOfWords(FileInputStream phrasesFileStream)
			throws IOException {
		HashMap<String, Long> tempDictMap = new HashMap<String, Long>();
		FileWriter fwstream = new FileWriter("BagOfWordsRepresentation.txt");
		BufferedWriter out = new BufferedWriter(fwstream);

		System.out.println("Starting generation of Dictionary......");
		tempDictMap = generateDictionary(phrasesFileStream);
		System.out.println("Dictionary Generation Completed!");
		int dictionarySize = tempDictMap.size();
		System.out.println("Dictionary Size: " + dictionarySize);

		phrasesFileStream = new FileInputStream("phrases.txt");
		// Get the object of DataInputStream
		DataInputStream inStream = new DataInputStream(phrasesFileStream);
		BufferedReader brStream = new BufferedReader(new InputStreamReader(
				inStream));
		String strLine;
		String[] components;
		HashMap<String, Integer> bagOfWordsMap = new HashMap<String, Integer>();
		// Read File Line By Line
		while ((strLine = brStream.readLine()) != null) {
			// Print the content on the console
			components = strLine.split(" ");
			for (String words : components) {
				int frequencyCount = 0;
				if (!cleanWord(words).equals("null")) {
					for (String key : tempDictMap.keySet()) {
						if ((key.equals(words))) {
							if (!bagOfWordsMap.containsKey(words)) {
								bagOfWordsMap.put(words, frequencyCount++);
							} else {
								frequencyCount = bagOfWordsMap.get(words);
								bagOfWordsMap.put(key, frequencyCount++);
							}
						} else {
							if (!bagOfWordsMap.containsKey(words)) {
								bagOfWordsMap.put(words, frequencyCount);
							} else {
								frequencyCount = bagOfWordsMap.get(words);
								bagOfWordsMap.put(words, frequencyCount++);
							}
						}
					}

				}
			}
			String bagOfWordsLine = "";
			for (int value : bagOfWordsMap.values()) {
				bagOfWordsLine = bagOfWordsLine + value + ", ";
			}
			System.out.println(bagOfWordsLine);
			bagOfWordsLine = bagOfWordsLine.trim();
			if (bagOfWordsLine.length() != 0) {
				bagOfWordsLine = bagOfWordsLine.substring(0,
						bagOfWordsLine.length() - 1);
			}
			bagOfWordsLine = "[" + bagOfWordsLine + "]\n";

			// One line read
			out.write(bagOfWordsLine);
			// bagOfWordsMap.clear();

		}
		inStream.close();
		out.close();
		System.out.println("Bag of Words Representation Complete!");

	}

	private static HashMap<String, Long> generateDictionary(
			FileInputStream phrasesFileStream) throws IOException {
		HashMap<String, Long> tempMap = new HashMap<String, Long>();
		long value = 1;

		FileWriter fwstream = new FileWriter("GeneratedDictionary.txt");
		BufferedWriter outPtr = new BufferedWriter(fwstream);

		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(phrasesFileStream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		String[] components;
		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			components = strLine.split(" ");
			for (String words : components) {
				if (!cleanWord(words).equals("null")) {
					if (!tempMap.containsKey(words)) {
						tempMap.put(words, value);
						value++;
					}
				}
			}

		}
		in.close();
		String dictLine = "";
		for (Map.Entry<String, Long> entry : tempMap.entrySet()) {
			String keyDict = entry.getKey();
			Long valueDict = entry.getValue();
			dictLine = dictLine + "\"" + keyDict + "\": " + valueDict + ",\n";
		}

		outPtr.write("{\n" + dictLine + "}");
		outPtr.close();
		in.close();

		return tempMap;

	}

	private static String cleanWord(String wordContent) {
		// wordContent = wordContent.replace("\n", "null");
		// wordContent = wordContent.replace(".", "null");
		// wordContent = wordContent.replace("!", "null");
		// wordContent = wordContent.replace("-", "null");
		// wordContent = wordContent.replace("_", "null");
		// wordContent = wordContent.replace(",", "null");
		// wordContent = wordContent.replace("   ", "null");
		// wordContent = wordContent.replace("  ", "null");
		return wordContent;
	}

	private static void extractPhrasesFromTrainingSet() {
		// TODO Auto-generated method stub
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream("train.tsv");

			FileWriter fwstream = new FileWriter("phrases.txt");
			BufferedWriter out = new BufferedWriter(fwstream);

			// Get the object of DataInputStream
			DataInputStream instr = new DataInputStream(fstream);
			BufferedReader brstr = new BufferedReader(new InputStreamReader(
					instr));
			String strLine;
			String[] components;
			// Read File Line By Line
			while ((strLine = brstr.readLine()) != null) {
				// Print the content on the console
				// System.out.println(strLine);
				components = strLine.split("\t");
				// System.out.println(components[2]);
				out.write(components[2] + "\n");
			}
			// Close the input stream
			instr.close();
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
