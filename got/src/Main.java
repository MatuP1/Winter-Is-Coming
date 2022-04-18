package src;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	private static File selectedFile;
	private static File selectedDir;
	private static Gui gui;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gui = new Gui();
		
	}
	
	public static File selectFile() {
		selectedFile = gui.selectTextFile();
		
		return selectedFile;
	}
	
	public static File selectDirectory() {
		selectedDir = gui.selectDirectory();
		
		gui.loadDirectoryList(selectedDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".txt");// TODO Auto-generated method stub
			}
			
		}));
		return selectedDir;
	}
	
	public static long wordsIn(File f) {
		int amountWords  = 0;
		if(f.isDirectory()) {
			for(File file: f.listFiles()) {
			}
		}
		return amountWords;
	}
	
	

}
