import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Image;
import java.awt.List;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultComboBoxModel;

public class Draw extends JFrame {

	private JPanel contentPane;
	private JTextField searchBar;
	private String path = "";
	private ArrayList<String> attendees = new ArrayList<String>();
	private Set<String> winners = new TreeSet<String>();
	private DefaultListModel model = new DefaultListModel();
	private int limit = 1 ;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Draw frame = new Draw();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public void drawLots(int limit) {
		
		winners.removeAll(attendees);
		model.removeAllElements();
		
		try( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"))){
		String name;
		
		while((name = reader.readLine()) != null) 
			attendees.add(name);
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		while(winners.size() != limit) {
			Random random = new Random();
			int index = random.nextInt(attendees.size());
			winners.add(attendees.get(index));
		}
	}
	
	public void letsCelebrate() throws LineUnavailableException {
		
		try {
			
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audio.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
			
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	public Draw() {
		
		Image img;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 1250, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 0, 1234, 861);
		contentPane.add(panel);
		panel.setLayout(null);
		
		searchBar = new JTextField();
		searchBar.setForeground(new Color(0, 0, 128));
		searchBar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		searchBar.setBounds(60, 35, 877, 61);
		panel.add(searchBar);
		searchBar.setColumns(10);
		
		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int i = fileChooser.showOpenDialog(null);
				
				if(i == JFileChooser.APPROVE_OPTION) {
					path = fileChooser.getSelectedFile().getPath();
					searchBar.setText(path);
				}
			}
		});
		browseButton.setForeground(new Color(0, 0, 128));
		browseButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		browseButton.setBounds(1018, 35, 146, 61);
		panel.add(browseButton);
		
		JList winnerList = new JList();
		winnerList.setForeground(new Color(0, 0, 128));
		winnerList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		winnerList.setBounds(60, 203, 877, 610);
		panel.add(winnerList);
		winnerList.setModel(model);
	
		
		JLabel lblNewLabel = new JLabel("Winners :");
		lblNewLabel.setForeground(new Color(25, 25, 112));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(60, 123, 146, 58);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Draw Lots");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(path.equals("")) 
					JOptionPane.showMessageDialog(null, "PLEASE CHOOSE A FILE FIRST!");
				
				else {
					drawLots(limit);
					
					for(String win : winners) {
						model.addElement(win);
					}
					
					try {
						letsCelebrate();
					} catch (LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 128));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(1018, 223, 146, 61);
		panel.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(new Color(0, 0, 128));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox.setMaximumRowCount(10);
		comboBox.setBounds(287, 135, 99, 32);
		panel.add(comboBox);
		
		JButton okButton = new JButton("");
		okButton.setBackground(SystemColor.activeCaption);
		img = new ImageIcon(this.getClass().getResource("/okay.png")).getImage();
		okButton.setIcon(new ImageIcon(img));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limit = comboBox.getSelectedIndex()+1;
			}
		});
		okButton.setBounds(407, 135, 32, 32);
		panel.add(okButton);
		
		
	}
}
