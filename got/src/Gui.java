package src;

import java.io.File;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLabel;

public class Gui {
	private JFrame frame;
	private JList<String> listDir, listStats;
	private JButton btnLoadDir,btnStart,btnEnglish,btnSpanish;
	private JLabel lblNewLabel;
	private JPanel panel_buttons; 
	private Locale locale;
	private ResourceBundle resBundle;
	
	Gui(){
		
		locale = new Locale("en");
		resBundle = ResourceBundle.getBundle("src.bundle",locale);
		
		frame = new JFrame("got");
		frame.setSize(500, 350);
		
		frame.getContentPane().setLayout(null);
		
		panel_buttons = new JPanel();
		panel_buttons.setBounds(10, 190, 414, 60);
		frame.getContentPane().add(panel_buttons);
		panel_buttons.setLayout(null);
		
		btnLoadDir = new JButton(resBundle.getString("loadDir"));
		btnLoadDir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.selectDirectory();
			}
		});
		btnLoadDir.setBounds(10, 11, 115, 23);
		panel_buttons.add(btnLoadDir);
		
		btnStart = new JButton(resBundle.getString("start"));
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadStats(Main.calculateStats());
			}
		});
		btnStart.setBounds(315, 11, 89, 23);
		panel_buttons.add(btnStart);
		listDir = new JList<String>();
		listDir.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					try {
							Main.selectFile(listDir.getSelectedValue());
						
						} catch (NotDirectorySelectedException | NotFileExcepcion e1) {
							e1.printStackTrace();
						}
					}
				}
		});
		listDir.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPaneDir = new JScrollPane(listDir);
		scrollPaneDir.setBounds(10, 11, 196, 170);
		frame.getContentPane().add(scrollPaneDir);
		
		
		//scrollPaneDir.setViewportView(listDir);
		//scrollPaneDir.add(listDir);

		listStats = new JList<String>();
		JScrollPane scrollPaneStats = new JScrollPane(listStats);
		scrollPaneStats.setBounds(228, 11, 196, 168);
		frame.getContentPane().add(scrollPaneStats);
		
		JPanel panel_language = new JPanel();
		panel_language.setBounds(10, 254, 414, 46);
		frame.getContentPane().add(panel_language);
		panel_language.setLayout(null);
		
		btnEnglish = new JButton(resBundle.getString("en"));
		btnEnglish.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.changeLanguage("en");
			}
		});
		btnEnglish.setBounds(285, 11, 90, 23);
		panel_language.add(btnEnglish);
		
		btnSpanish = new JButton(resBundle.getString("es"));
		btnSpanish.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.changeLanguage("es");
			}
		});
		btnSpanish.setBounds(176, 11, 90, 23);
		panel_language.add(btnSpanish);
		
		lblNewLabel = new JLabel(resBundle.getString("language"));
		lblNewLabel.setBounds(10, 15, 156, 14);
		panel_language.add(lblNewLabel);
		
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
	

	public void loadDirectoryList(String[] strings) {
		listDir.setListData(strings);
	}


	public void loadStats(String[] calculateStats) {
		listStats.setListData(calculateStats);
		
	}
	
	public void changeLanguage(String language) {
		//repaint all locale sensitive components
		
		locale = new Locale(language);
		ResourceBundle.clearCache();
		resBundle = ResourceBundle.getBundle("src.bundle",locale);
		btnLoadDir.setText(resBundle.getString("loadDir"));
		btnStart.setText(resBundle.getString("start"));
		btnEnglish.setText(resBundle.getString("en"));
		btnSpanish.setText(resBundle.getString("es"));
		lblNewLabel.setText(resBundle.getString("language"));
	}
}
