import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class BagOfWordsModel {

	public HashMap<String, Long> dictionaryMap;

	public static void main(String[] args) throws IOException {
		File filePointer = new File("phrases.txt");
		FileInputStream phrasesFileStream = new FileInputStream("phrases.txt");

		if (!filePointer.exists()) {
			extractPhrasesFromTrainingSet();
		} else {
			System.out.println("phrases.txt -- File exists!");

			// BagofWords
			System.out
					.println("Starting modelling Bag Of Words representation....");
			generateBagOfWords(phrasesFileStream);

		}

	}

	private static void generateBagOfWords(FileInputStream phrasesFileStream)
			throws IOException {
		HashMap<String, Long> tempDictMap = new HashMap<String, Long>();
		System.out.println("Starting generation of Dictionary......");
		tempDictMap = generateDictionary(phrasesFileStream);
		System.out.println("Dictionary Generation Completed!");
		int dictionarySize = tempDictMap.size();

	}

	private static HashMap<String, Long> generateDictionary(
			FileInputStream phrasesFileStream) throws IOException {
		HashMap<String, Long> tempMap = new HashMap<String, Long>();
		long value = 1;

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		FileWriter fwstream = new FileWriter("dictionary.txt");
		BufferedWriter out = new BufferedWriter(fwstream);

		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(phrasesFileStream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		String[] components;
		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			// System.out.println(strLine);
			components = strLine.split(" ");
			// System.out.println(components[2]);
			// out.write(components[2] + "\n");
			for (String words : components) {
				if (!cleanWord(words).equals("null")) {
					if (!tempMap.containsKey(words)) {
						tempMap.put(words, value);
						value++;
					}
				}
			}

		}

		return tempMap;

	}

	private static String cleanWord(String wordContent) {
		wordContent = wordContent.replace("\n", "null");
		wordContent = wordContent.replace(".", "null");
		wordContent = wordContent.replace("!", "null");
		wordContent = wordContent.replace("-", "null");
		wordContent = wordContent.replace("_", "null");
		wordContent = wordContent.replace(",", "null");
		wordContent = wordContent.replace("   ", "null");
		wordContent = wordContent.replace("  ", "null");
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
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String[] components;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// System.out.println(strLine);
				components = strLine.split("\t");
				System.out.println(components[2]);
				out.write(components[2] + "\n");
			}
			// Close the input stream
			in.close();
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
