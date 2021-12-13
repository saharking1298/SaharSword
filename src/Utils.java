import java.util.ArrayList;
import java.util.Scanner;


public class Utils {
	static Scanner input = new Scanner(System.in);
	static String underlineEnd = "\033[0m";
	static String underline = "\033[4m";

	public static void clear(){
		try{
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
		catch (Exception ignored){ }
	}

	public static int randInt(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	public static String randChoice(String[] arr){
		if(arr.length <= 1){
			return arr[0];
		}
		return arr[randInt(0, arr.length-1)];
	}
	public static String randChoice(String[] arr, int limiter){
		if(arr.length <= 1){
			return arr[0];
		}
		return arr[randInt(0, limiter)];
	}
	public static int randChoice(int[] arr){
		if(arr.length <= 1){
			return arr[0];
		}
		return arr[randInt(0, arr.length-1)];
	}
	public static int randChoice(int[] arr, int limiter){
		if(arr.length <= 1){
			return arr[0];
		}
		return arr[randInt(0, limiter)];
	}
	public static void showArr(String[] arr, String name){
		System.out.println(name + ": " + String.join(", ", arr));
	}
	public static void arrPop(int[] arr, int index){
		for(int i = index; i < arr.length; i++){
			if(i != arr.length - 1){
				arr[i] = arr[i+1];
			}
		}
		arr[arr.length - 1] = -1;
	}
	public static String[] shuffleArr(String[] arr){
		String[] shuffled = new String[arr.length];
		int[] indexesRemaining = new int[arr.length];
		int limiter = arr.length - 1;
		int i, index;
		// Filling remaining indexes
		for(i = 0; i < arr.length; i++){
			indexesRemaining[i] = i;
		}
		for(i = 0; i < arr.length; i++){
			// Selecting a random index from remaining indexes array
			index = Utils.randInt(0, limiter);
			shuffled[i] = arr[indexesRemaining[index]];
			// Removing this index from remaining indexes
			arrPop(indexesRemaining, index);
			limiter--;
		}
		return shuffled;
	}
	public static int[] shuffleArr(int[] arr){
		int[] shuffled = new int[arr.length];
		int[] indexesRemaining = new int[arr.length];
		int limiter = arr.length - 1;
		int i, index;
		// Filling remaining indexes
		for(i = 0; i < arr.length; i++){
			indexesRemaining[i] = i;
		}
		for(i = 0; i < arr.length; i++){
			// Selecting a random index from remaining indexes array
			index = Utils.randInt(0, limiter);
			shuffled[i] = arr[indexesRemaining[index]];
			// Removing this index from remaining indexes
			arrPop(indexesRemaining, index);
			limiter--;
		}
		return shuffled;
	}

	public static String table(String[][] content){
		String table = "";
		int[] longestLengths = new int[content[0].length];
		for(int i = 0; i < longestLengths.length; i++){
			longestLengths[i] = 0;
		}
		for(int i = 0; i < content.length; i++){
			for(int j = 0; j < content[i].length; j++){
				int len = content[i][j].length();
				if(len > longestLengths[j]){
					longestLengths[j] = len;
				}
			}
		}

		for(int i = 0; i < content.length; i++){
			for(int j = 0; j < content[i].length; j++){
				String label = content[i][j];
				int len = label.length();
				if(i == 0){
					label = Utils.underline(label);
				}
				int limit = longestLengths[j] - len;
				for(int k = 0; k < limit; k++){
					label += " ";
				}
				table += label;
				if(j != content[i].length - 1){
					table += "    ";
				}
			}
			if(i != content.length - 1) {
				table += "\n";
			}
		}
		return table;
	}
	public static String table(String[] content){
		String[][] tableContent = new String[content.length][content[0].split("|").length];
		for(int i = 0; i < content.length; i++){
			tableContent[i] = content[i].split("\\|");
			for(int j = 0; j < tableContent[i].length; j++){
				tableContent[i][j] = tableContent[i][j].trim();
			}
		}
		return table(tableContent);
	}
	public static String table(String content){
		String[] tableContent = content.split("\n");
		for(int i = 0; i < tableContent.length; i++){
			tableContent[i] = tableContent[i].trim();
		}
		return table(tableContent);
	}
	public static String header(String text){
		return "---- [" + text.trim() + "] ----";
	}
	public static String underline(String text){
		return text;
		// return underline + text + underlineEnd;
	}
	
	public static int menu(String title, String[] options) {
		System.out.println(title);
		for(int i = 0; i < options.length; i++) {
			System.out.println(i+1 + ". " + options[i]);
		}
		String choice;
		while (true) {
			choice = input.nextLine().trim();
			try {
				int numericChoice = Integer.parseInt(choice);
				if (numericChoice > 0 && numericChoice <= options.length) {
					return numericChoice;
				}
				throw new Exception();
			}
			catch(Exception e){
				System.out.println("Please enter a valid choice.");
			}
		}
	}
	public static Integer[] multiSelect(String title, String[] options){
		System.out.println(title);
		for(int i = 0; i < options.length; i++){
			System.out.println(i+1 + ". " + options[i]);
		}
		String userInput;
		String[] choices;
		ArrayList<Integer> numericChoices = new ArrayList<Integer>();
		int num;
		while (numericChoices.size() == 0){
			numericChoices = new ArrayList<Integer>();
			userInput = input.nextLine().trim().replace(',', ' ').replace('.', ' ').toLowerCase();
			if(userInput.equals("")){
				break;
			}
			else if(userInput.equals("all")){
				for(int i = 0; i < options.length; i++){
					numericChoices.add(i);
				}
				break;
			}
			else{
				choices = userInput.split(" +");
				for(String choice: choices){
					try {
						num = Integer.parseInt(choice);
						if(num > 0 && num <= options.length) {
							numericChoices.add(num-1);
						}
					}
					catch (Exception ignored){

					}
				}
				if(numericChoices.size() == 0){
					System.out.println("Please enter at least one choice");
				}
			}
		}

		Integer[] arr = new Integer[numericChoices.size()];
		arr = numericChoices.toArray(arr);
		return arr;
	}
	public static boolean contains(String[] arr, String val) {
		for(String str: arr) {
			if(val.equals(str)) {
				return true;
			}
		}
		return false;
	}
	public static boolean contains(int[] arr, int val) {
		for(int num: arr) {
			if(val == num) {
				return true;
			}
		}
		return false;
	}
	public static int findIndex(String[] arr, String val) {
		for(int i = 0; i < arr.length; i++) {
			if(val.equals(arr[i])) {
				return i;
			}
		}
		return -1;
	}
	public static int findIndex(int[] arr, int val) {
		for(int i = 0; i < arr.length; i++) {
			if(val == arr[i]) {
				return i;
			}
		}
		return -1;
	}
}
