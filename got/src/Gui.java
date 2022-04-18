package src;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Gui {
	private JFrame frame;
	private JList<String> listDir;
	Gui(){
		frame = new JFrame("got");
		frame.setSize(500, 350);
		
		frame.getContentPane().setLayout(null);
		
		JPanel panel_buttons = new JPanel();
		panel_buttons.setBounds(10, 190, 414, 60);
		frame.getContentPane().add(panel_buttons);
		panel_buttons.setLayout(null);
		/**
		 * TODO 
		 * Apply translatable text
		 */
		JButton btnLoadDir = new JButton("Cargar Directorio");
		btnLoadDir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.selectDirectory();
			}
		});
		btnLoadDir.setBounds(10, 11, 115, 23);
		panel_buttons.add(btnLoadDir);
		
		JButton btnStart = new JButton("Comenzar");
		btnStart.setBounds(315, 11, 89, 23);
		panel_buttons.add(btnStart);
		
		listDir = new JList<String>();
		listDir.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting())
					Main.selectFile(listDir.getSelectedValue());
					System.out.print(listDir.getSelectedValue());
			}
		});
		listDir.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPaneDir = new JScrollPane(listDir);
		scrollPaneDir.setBounds(10, 11, 196, 170);
		frame.getContentPane().add(scrollPaneDir);
		
		
		//scrollPaneDir.setViewportView(listDir);
		//scrollPaneDir.add(listDir);
		
		JScrollPane scrollPaneStats = new JScrollPane();
		scrollPaneStats.setBounds(228, 11, 196, 168);
		frame.getContentPane().add(scrollPaneStats);
		frame.setVisible(true);
	}

	public File selectDirectory() {
		File dir = new File("c:/");
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int respuesta = fc.showOpenDialog(frame);
		
		if(respuesta == JFileChooser.APPROVE_OPTION) {
			dir = fc.getSelectedFile();
		}
		
		return dir;
	}
	public File selectTextFile(String s) {
		/**
		 * TODO 
		 * Apply translatable text
		 */
		FileNameExtensionFilter filter = new FileNameExtensionFilter("","txt");
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(filter);
		File file = null;
		int respuesta = fc.showOpenDialog(frame);
		
		if(respuesta == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}
		
		
		return file;
	}

	public void loadDirectoryList(String[] strings) {
		// TODO Auto-generated method stub
		listDir.setListData(strings);
	}
}
