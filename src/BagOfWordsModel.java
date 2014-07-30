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

	public HashMap<String, Integer> dictionaryMap;

	public static void main(String[] args) throws IOException {
		File filePointer = new File("phrases_all.txt");

		if (!filePointer.exists()) {
			extractPhrasesFromTrainingSet();
			startModelling();
		} else {
			startModelling();
		}

	}

	private static void startModelling() throws FileNotFoundException,
			IOException {
		FileInputStream phrasesFileStream = new FileInputStream(
				"phrases_all.txt");
		System.out.println("phrases.txt -- File exists!");
		// BagofWords
		System.out
				.println("Starting modelling Bag Of Words representation....");
		generateBagOfWords(phrasesFileStream);
	}

	private static void generateBagOfWords(FileInputStream phrasesFileStream)
			throws IOException {
		HashMap<String, Integer> tempDictMap = new HashMap<String, Integer>();
		FileWriter fwstream = new FileWriter("bow_model.txt");
		BufferedWriter out = new BufferedWriter(fwstream);

		System.out.println("Starting generation of Dictionary......");
		tempDictMap = generateDictionary(phrasesFileStream);
		System.out.println("Dictionary Generation Completed!");
		int dictionarySize = tempDictMap.size();
		System.out.println("Dictionary Size: " + dictionarySize);
		System.out.println("=====================");

		phrasesFileStream = new FileInputStream("phrases_all.txt");
		// Get the object of DataInputStream
		DataInputStream inStream = new DataInputStream(phrasesFileStream);
		BufferedReader brStream = new BufferedReader(new InputStreamReader(
				inStream));
		String strLine;
		String[] components;

		HashMap<String, Integer> bagOfWordsMap = new HashMap<String, Integer>(
				tempDictMap.size());
		int count = 1;
		// Read File Line By Line
		while ((strLine = brStream.readLine()) != null) {

			for (String tempKey : tempDictMap.keySet()) {
				bagOfWordsMap.put(tempKey, 0);
			}
			// Print the content on the console
			components = strLine.split(" ");
			for (String word : components) {
				bagOfWordsMap.put(word, bagOfWordsMap.get(word) + 1);
			}
			String bagOfWordsLine = "";
			for (int value : bagOfWordsMap.values()) {
				bagOfWordsLine = bagOfWordsLine + value + " ";
			}
			bagOfWordsLine = bagOfWordsLine.trim();
			if (bagOfWordsLine.length() != 0) {
				bagOfWordsLine = bagOfWordsLine.substring(0,
						bagOfWordsLine.length() - 1);
			}
			// bagOfWordsLine = "[" + bagOfWordsLine + "]\n";
			System.out.println("Line Number: " + count);
			count++;

			// One line read
			out.write(bagOfWordsLine + "\n");
			bagOfWordsMap.clear();
		}
		inStream.close();
		out.close();
		System.out.println("Bag of Words Representation Complete!");

	}

	private static HashMap<String, Integer> generateDictionary(
			FileInputStream phrasesFileStream) throws IOException {
		HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
		int value = 1;

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
				// if (!cleanWord(words).equals("null")) {
				if (!tempMap.containsKey(words)) {
					tempMap.put(words, value);
					value++;
				}
				// }
			}

		}
		in.close();
		String dictLine = "";
		for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
			String keyDict = entry.getKey();
			int valueDict = entry.getValue();
			dictLine = dictLine + " " + keyDict + " " + valueDict + "\n";
		}

		outPtr.write(dictLine);
		outPtr.close();
		in.close();

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
			FileInputStream fstream = new FileInputStream(
					"train_and_test_data.tsv");

			FileWriter fwstream = new FileWriter("phrases_all.txt");
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
