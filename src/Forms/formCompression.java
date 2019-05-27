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

import Others.ImageParser;
import Others.Utilities;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.JComboBox;

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
	BufferedImage bi;

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
					formCompression.this.bi = bi;
					
					BufferedImage bi = p.getBI();
					formCompression.this.textField.setText(image.getPath());
					formCompression.this.lblNewLabel.setIcon(new ImageIcon(formCompression.resize(bi, 500, 625)));
				}
			}
		});
		btnSeleccionar.setBounds(378, 24, 128, 29);
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
		
		

		Double[] entropias = new Double[30];
		for(int i = 30; i < 60; i++) {
			entropias[i - 30] = ((double)(i + 1) / 10);
		}
		
		JComboBox comboBox = new JComboBox(entropias);
		comboBox.setBounds(182, 625, 71, 27);
		frame.getContentPane().add(comboBox);
		comboBox.setSelectedIndex(0);
		
		JButton btnCrearArchivos = new JButton("Crear compresion");
		btnCrearArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {  
				Thread parallel = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ImageParser p = new ImageParser(image);
					    progressBar.setValue(0);
					    encode = Utilities.encodeImage(p, 20, (Double) comboBox.getSelectedItem());
					    btnGuarfarCompresion.setEnabled(true);
					    try {
					    	textPane.setText("");
							textPane.getStyledDocument().insertString(0,"La imagen se comprimio con exito\n"
									+ "El tamano de la imagen comprimida es: "+encode.size()+" bytes", null);
							
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
			    parallel.start();
			}
		});
		btnCrearArchivos.setBounds(307, 658, 199, 25);
		frame.getContentPane().add(btnCrearArchivos);
		
		JLabel lblSeleccioneUnValor = new JLabel("Seleccione un valor de Ht");
		lblSeleccioneUnValor.setBounds(16, 629, 159, 16);
		frame.getContentPane().add(lblSeleccioneUnValor);
		
		
	}
}
