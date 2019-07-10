package Forms;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.JFileChooser;

import javax.swing.JLabel;
import javax.swing.JTextField;

import Others.ImageParser;
import Others.Procesadores;
import Others.Utilities;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class formCompression extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JTextField textField;
	private JLabel lblNewLabel;
	private static String destination = "";
	private JFileChooser chooser;
	private File image;
	public static JProgressBar progressBar;
	private JButton btnGuarfarCompresion;
	private ArrayList<Byte> encode = null;
	JCheckBox chckbxUserParallelCompression = null;
	BufferedImage bi;
	JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					formCompression window = new formCompression();
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
	public formCompression(){
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
		frame.setBounds(20, 20, 524, 764);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSeleccioneImagen = new JLabel("Seleccione imagen a comprimir:");
		lblSeleccioneImagen.setBounds(16, 0, 237, 16);
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
				int seleccion = fileChooser.showOpenDialog(formCompression.this.lblNewLabel);
				if(seleccion == JFileChooser.APPROVE_OPTION) {
					image = fileChooser.getSelectedFile();
					ImageParser p = new ImageParser(image);
					formCompression.this.bi = p.getBI();
					
					BufferedImage bi = p.getBI();
					formCompression.this.textField.setText(image.getPath());
					formCompression.this.lblNewLabel.setIcon(new ImageIcon(formCompression.resize(bi, 500, 625)));
				}
			}
		});
		btnSeleccionar.setBounds(356, 26, 112, 29);
		frame.getContentPane().add(btnSeleccionar);
		
		
		progressBar = new JProgressBar();
		progressBar.setBounds(16, 573, 490, 29);
		frame.getContentPane().add(progressBar);
		
		lblNewLabel.setBounds(16, 59, 490, 502);
		frame.getContentPane().add(lblNewLabel);
		
		btnGuarfarCompresion = new JButton("Guardar compresion");
		btnGuarfarCompresion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(destination);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);    
			    if (chooser.showOpenDialog(formCompression.this) == JFileChooser.APPROVE_OPTION) { 
			    	Utilities.saveFile(encode, chooser.getSelectedFile().toString());
			    }
			}
		});
		btnGuarfarCompresion.setEnabled(false);
		btnGuarfarCompresion.setBounds(307, 695, 199, 25);
		frame.getContentPane().add(btnGuarfarCompresion);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(16, 657, 267, 68);
		frame.getContentPane().add(textPane);
		
		

		Double[] entropias = new Double[61];
		for(int i = 0; i < 61; i++) {
			entropias[i] = ((double)(i) / 10);
		}
		
		Procesadores[] proce = new Procesadores[201];
		proce[0] = new Procesadores("Disponibles", -1);
		for(int i = 1; i <= 200; i++) {
			proce[i] = new Procesadores(String.valueOf(i), i);
		}
		comboBox = new JComboBox(proce);
		comboBox.setBounds(182, 625, 71, 27);
		frame.getContentPane().add(comboBox);
		comboBox.setSelectedIndex(0);
		
		JButton btnCrearArchivos = new JButton("Crear compresion");
		btnCrearArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {  
				Thread parallel = new Thread(new Runnable() {
					@Override
					public void run() {
						long start = System.currentTimeMillis();
						ImageParser p = new ImageParser(bi);
						formCompression.progressBar.setValue(0);
					    progressBar.setValue(0);
					    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					    if(chckbxUserParallelCompression.isSelected()) {
					    	encode = Utilities.parallelEncoder(p, 20, ((Procesadores)comboBox.getSelectedItem()).getValue());
					    }else{
					    	encode = Utilities.encodeImageSequential(p, 20);
					    }
					    frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					    btnGuarfarCompresion.setEnabled(true);
					    long end = System.currentTimeMillis();
					    textPane.setText("Tiempo transcurrido: " + (end - start));
						//textPane.getStyledDocument().insertString(0,"La imagen se comprimio con exito\n"
								//+ "El tamano de la imagen comprimida es: "+encode.size()+" bytes", null);
					}
					
				});
			    parallel.start();
			}
		});
		btnCrearArchivos.setBounds(307, 658, 199, 25);
		frame.getContentPane().add(btnCrearArchivos);
		
		JLabel lblSeleccioneUnValor = new JLabel("Seleccione procesadores:");
		lblSeleccioneUnValor.setBounds(16, 629, 159, 16);
		frame.getContentPane().add(lblSeleccioneUnValor);
		
		chckbxUserParallelCompression = new JCheckBox("PARALLEL VERSION");
		chckbxUserParallelCompression.setBounds(307, 625, 199, 23);
		frame.getContentPane().add(chckbxUserParallelCompression);
		
		JButton btnRand = new JButton("RAND");
		btnRand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage nuevaImagen = new BufferedImage(5000, 8000, BufferedImage.TYPE_BYTE_GRAY);
				Random random = new Random();
				for(int x = 0; x < 5000; x++) {
					for(int y = 0; y < 8000; y++) {
						int color = random.nextInt(256);
						nuevaImagen.setRGB(x, y, new Color(color, color, color).getRGB());
					}
				}
				formCompression.this.bi = nuevaImagen;
				formCompression.this.lblNewLabel.setIcon(new ImageIcon(formCompression.resize(nuevaImagen, 500, 625)));
			}
		});
		btnRand.setBounds(459, 26, 65, 29);
		frame.getContentPane().add(btnRand);
		
		
	}
}
