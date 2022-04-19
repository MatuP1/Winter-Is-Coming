package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	private static File selectedFile;
	private static File selectedDir;
	private static String fileContent;
	private static FilenameFilter filter;
	private static Gui gui;
	private static Locale locale;
	private static ResourceBundle resBundle;
	public static void main(String[] args) {
		gui = new Gui();
		filter = new FileTxtFilter();
		fileContent = "";
		locale = new Locale("en");
		resBundle = ResourceBundle.getBundle("src.bundle",locale);
	}
	
	public static void selectFile(String s) throws NotDirectorySelectedException, NotFileExcepcion{
		//This is only called when selectedDir is initialized
		if(selectedDir.isDirectory()) {
			boolean isEqual = false;
			String dirPath = selectedDir.getPath() + "\\";
			File[] files = selectedDir.listFiles(filter);
			int i = 0;
			while(i!=files.length && !isEqual) {

				if((files[i].getPath().replace(dirPath, "")).equals(s)) {
					selectedFile = files[i];
					isEqual = true;
				}
				else {
					i++;
				}
			}
			if(isEqual) {
				readFile(selectedFile);
			}
			else throw new NotFileExcepcion(resBundle.getString("notFile"));
		
		}
		else throw new NotDirectorySelectedException(resBundle.getString("notDir"));
	}
	
	public static File selectDirectory() {
		selectedDir = gui.selectDirectory();
		readFile(selectedDir);
		calculateStats();
		gui.loadDirectoryList(selectedDir.list(filter));
		return selectedDir;
	}
	
	public static String[] calculateStats() {
		String[] statsString = new String[4];
		statsString[0] = resBundle.getString("words") + wordsIn();
		statsString[1] = resBundle.getString("characters") + charactersIn();
		statsString[2] = resBundle.getString("charactersWithoutSpace") + chractersWithoutSpaceIn();
		
		String[] mrw = mostRepeatedWords(10);
		statsString[3] = resBundle.getString("mrw");
		String[] stats;
		
		//La linea mas robada de stackoverflow de mi vida pero bueno concatena dos arreglos de string
		if(mrw!=null) {
			stats = Stream.of(statsString,mrw).flatMap(Stream::of).toArray(String[]::new);
		}
		else
			return statsString;
		
		return stats;
	}

	public static void changeLanguage(String language) {
		locale = new Locale(language);
		Locale.setDefault(new Locale(language));
		resBundle = ResourceBundle.getBundle("src.bundle",locale);
	}

	private static String[] mostRepeatedWords(int i) {
		
		String lowerContent = fileContent.toLowerCase();
		Scanner sc = new Scanner (lowerContent);
		
		//Create a map to keep count of occurrences for each word
	    Map<String, Integer> occurrences = new HashMap<String,Integer>();
		
	    //While is lines to read
	    while(sc.hasNextLine()) {
	        
	        //get all the words
	        String[] words = sc.nextLine().split(" ");
	        
	        //go word for word
	        for(String word: words) {
	            
	            //if the word is stored we add to the counter of that word
	            if(occurrences.containsKey(word)) {
	                occurrences.put(word,occurrences.get(word)+1);
	            }
	            //if it is his first occurrence put the counter in one
	            else {
	                occurrences.put(word,1);
	            }
	        }
	    }
	    if(occurrences.size()==0) {
	    	return null;
	    }
	    
	    List<Entry<String,Integer>> orderedOccurrences = new ArrayList<Entry<String, Integer>>();
	    orderedOccurrences.addAll(occurrences.entrySet());
	    orderedOccurrences.sort(Entry.comparingByValue());
	    
	    String[] mostRepeatedWords;
	    if(i>orderedOccurrences.size()) {
	    	mostRepeatedWords = new String[orderedOccurrences.size()];
	    	i = orderedOccurrences.size() + 1;
	    }
	    else
		    mostRepeatedWords = new String[i];
	    
	    int j = 0; 
	    while(j<i && j<orderedOccurrences.size()) {
	    	Entry<String,Integer> actualEntry = orderedOccurrences.get((orderedOccurrences.size()-1)-j);
	    	mostRepeatedWords[j] = resBundle.getString("mrw1") + " " +actualEntry.getKey() + resBundle.getString("mrw2")+ " " +
	    							+ actualEntry.getValue() + resBundle.getString("mrw3") +
	    							actualEntry.getValue()*100/wordsIn() + resBundle.getString("mrw4");
	    	j++;
	    }
		
	    return mostRepeatedWords;
	}

	private static int chractersWithoutSpaceIn() {
		if(fileContent == null || fileContent.equals("")) {
			fileContent = "";
			return 0;
		}
		return fileContent.replace(" ","").replace("\n","").length();
	}

	private static int charactersIn() {
		if(fileContent == null || fileContent.equals("")) {
			fileContent = "";
			return 0;
		}
		return fileContent.replace("\n","").length();
	}
	
	private static int wordsIn() {
		if(fileContent == null || fileContent.equals("")) {
			fileContent = "";
			return 0;
		}
		StringTokenizer st = new StringTokenizer(fileContent.replace("\n", " "));
		return st.countTokens();
	}

	private static void readFile(File f) {
		String linea = new String();
		fileContent = "";
		try {
			FileReader fr = null;
			BufferedReader b = null;
			if(f.isDirectory()) {
				for(File file: f.listFiles(filter)) {
					linea = new String(" ");
					fr = new FileReader(file);
					b = new BufferedReader(fr);
					while(linea!=null) {
						fileContent +=linea;
						linea = b.readLine();
					}
				}
			}
			else {
				fr = new FileReader(f);
				b = new BufferedReader(fr);
				while(linea!=null) {
					fileContent += linea;
					linea = b.readLine();
				}
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
