package Forms;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.JFileChooser;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import Others.ImageParser;
import Others.Procesadores;
import Others.Utilities;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;

public class formDecompression extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JTextField textField;
	private JLabel lblNewLabel;
	private static String destination = "";
	private static String origen = "";
	private JFileChooser chooser;
	public static JProgressBar progressBar;
	private JButton btnGuarfarCompresion;
	private ArrayList<Byte> decode = null;
	public static JLabel label;
	BufferedImage bi;
	JCheckBox checkBox;
	JComboBox comboBox;
	JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					formDecompression window = new formDecompression();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public formDecompression(){
		initialize();
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	} 
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		lblNewLabel = new JLabel("");
		
		frame = new JFrame();
		frame.setBounds(20, 20, 524, 752);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSeleccioneImagen = new JLabel("Seleccione binario a descomprimir:");
		lblSeleccioneImagen.setBounds(16, 0, 267, 16);
		frame.getContentPane().add(lblSeleccioneImagen);
		
		textField = new JTextField();
		textField.setBounds(16, 26, 340, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSeleccionar = new JButton("SELECCIONAR");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int seleccion = fileChooser.showOpenDialog(formDecompression.this.lblNewLabel);
				if(seleccion == JFileChooser.APPROVE_OPTION) {
					origen = fileChooser.getSelectedFile().toString();
					formDecompression.this.textField.setText(origen);
				}
			}
		});
		btnSeleccionar.setBounds(378, 24, 128, 29);
		frame.getContentPane().add(btnSeleccionar);
		
		
		progressBar = new JProgressBar();
		progressBar.setBounds(16, 64, 490, 29);
		frame.getContentPane().add(progressBar);
		
		lblNewLabel.setBounds(16, 93, 490, 502);
		frame.getContentPane().add(lblNewLabel);
		
		btnGuarfarCompresion = new JButton("Guardar imagen");
		btnGuarfarCompresion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(destination);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);    
			    if (chooser.showOpenDialog(formDecompression.this) == JFileChooser.APPROVE_OPTION) { 
			    	Utilities.saveImage(bi,chooser.getSelectedFile().toString());
			    }
			}
		});
		btnGuarfarCompresion.setEnabled(false);
		btnGuarfarCompresion.setBounds(307, 699, 199, 25);
		frame.getContentPane().add(btnGuarfarCompresion);
		
		
		
		JButton btnCrearArchivos = new JButton("Descomprimir");
		btnCrearArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {    
				Thread parallel = new Thread(new Runnable() {

					@Override
					public void run() {
						long start = System.currentTimeMillis();
						decode = Utilities.getFileByteCode(origen);
					    progressBar.repaint();
					    
					    if(formDecompression.this.checkBox.isSelected()) {
					    	int pro = ((Procesadores) formDecompression.this.comboBox.getSelectedItem()).getValue();
					    	if(pro == -1) {
					    		pro = Runtime.getRuntime().availableProcessors();
					    	}
					    	bi = Utilities.parallelDecoder(decode, pro);
					    	long end = System.currentTimeMillis();
					    	textPane.setText("Tiempo transcurrido (PARALELA CON " + pro + " PROCESADORES): " + (end - start));
					    }else {
					    	bi = Utilities.decodeImage(decode);
					    	long end = System.currentTimeMillis();
					    	textPane.setText("Tiempo transcurrido: " + (end - start));
					    }					    
					    
						formDecompression.this.lblNewLabel.setIcon(new ImageIcon(formDecompression.resize(bi, 500, 625)));
						btnGuarfarCompresion.setEnabled(true);
					}
					
				});
			    parallel.start();
				
			}
		});
		btnCrearArchivos.setBounds(27, 699, 199, 25);
		frame.getContentPane().add(btnCrearArchivos);
		
		JLabel lblEnProceso = new JLabel("En proceso");
		lblEnProceso.setBounds(16, 50, 78, 16);
		frame.getContentPane().add(lblEnProceso);
		
		label = new JLabel("- - - -");
		label.setBounds(97, 50, 259, 16);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Seleccione procesadores:");
		label_1.setBounds(16, 607, 159, 16);
		frame.getContentPane().add(label_1);
		
		Procesadores[] proce = new Procesadores[201];
		proce[0] = new Procesadores("Disponibles", -1);
		for(int i = 1; i <= 200; i++) {
			proce[i] = new Procesadores(String.valueOf(i), i);
		}
		
		comboBox = new JComboBox(proce);
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(187, 603, 71, 27);
		frame.getContentPane().add(comboBox);
		
		checkBox = new JCheckBox("PARALLEL VERSION");
		checkBox.setBounds(355, 603, 151, 23);
		frame.getContentPane().add(checkBox);
		
		textPane = new JTextPane();
		textPane.setBounds(16, 635, 490, 57);
		frame.getContentPane().add(textPane);
	
	}
}