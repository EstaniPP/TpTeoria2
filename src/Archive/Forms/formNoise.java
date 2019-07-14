package Forms;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.JFileChooser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Others.ImageParser;
import Others.Utilities;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class formNoise extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JLabel lblSeleccioneLaImagen;
	private JTextField textField_1;
	private JButton button;
	private JLabel label_1;


	BufferedImage b1= null,b2 = null;
	ImageParser p1 = null,p2 = null;
	
	public formNoise(){
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
		lblNewLabel.setForeground(Color.WHITE);
		
		frame = new JFrame();
		frame.setBounds(20, 20, 1072, 684);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSeleccioneImagen = new JLabel("Seleccione la imagen enviada");
		lblSeleccioneImagen.setBounds(12, 27, 216, 16);
		frame.getContentPane().add(lblSeleccioneImagen);
		
		textField = new JTextField();
		textField.setBounds(12, 55, 360, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSeleccionar = new JButton("SELECCIONAR");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int seleccion = fileChooser.showOpenDialog(formNoise.this.lblNewLabel);
				if(seleccion == JFileChooser.APPROVE_OPTION) {
					File image = fileChooser.getSelectedFile();
					p1 = new ImageParser(image);
					b1 = p1.getBI();
					formNoise.this.textField.setText(image.getPath());
					formNoise.this.lblNewLabel.setIcon(new ImageIcon(formNoise.resize(b1, 500, 500)));
				}
			}
		});
		btnSeleccionar.setBounds(384, 53, 128, 29);
		frame.getContentPane().add(btnSeleccionar);
		
		
		lblNewLabel.setBounds(12, 93, 500, 500);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnCrearArchivos = new JButton("Calcular ruido");
		btnCrearArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {                                       
				JOptionPane.showMessageDialog(frame, "El ruido entre la imagen "
						+ "enviada y la recibida es de:" + Utilities.getRuido(p1, p2));
			}
		});
		btnCrearArchivos.setBounds(915, 617, 137, 25);
		frame.getContentPane().add(btnCrearArchivos);
		
		lblSeleccioneLaImagen = new JLabel("Seleccione la imagen recibida");
		lblSeleccioneLaImagen.setBounds(552, 27, 216, 16);
		frame.getContentPane().add(lblSeleccioneLaImagen);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(552, 55, 360, 26);
		frame.getContentPane().add(textField_1);
		
		button = new JButton("SELECCIONAR");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int seleccion = fileChooser.showOpenDialog(formNoise.this.lblNewLabel);
				if(seleccion == JFileChooser.APPROVE_OPTION) {
					File image = fileChooser.getSelectedFile();
					p2 = new ImageParser(image);
					b2 = p2.getBI();
					formNoise.this.textField_1.setText(image.getPath());
					formNoise.this.label_1.setIcon(new ImageIcon(formNoise.resize(b2, 500, 500)));
				}				
			}
		});
		button.setBounds(924, 53, 128, 29);
		frame.getContentPane().add(button);		
		
		
		label_1 = new JLabel("");
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(552, 93, 500, 500);
		frame.getContentPane().add(label_1);
		
		JButton btnCalcularPerdida = new JButton("Calcular perdida");
		btnCalcularPerdida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {                                       
				JOptionPane.showMessageDialog(frame, "El ruido entre la imagen "
						+ "enviada y la recibida es de:" + Utilities.getRuido(p2, p1));				}
		});
		btnCalcularPerdida.setBounds(757, 617, 146, 25);
		frame.getContentPane().add(btnCalcularPerdida);
		
	}	
}

