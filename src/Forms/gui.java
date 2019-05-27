package Forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			try {
				gui frame = new gui();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * Create the frame.
	 */
	public gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnComprimirImagen = new JButton("Comprimir imagen");
		btnComprimirImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formCompression window = new formCompression();
				window.frame.setVisible(true);
			}
		});
		btnComprimirImagen.setBounds(12, 77, 183, 62);
		contentPane.add(btnComprimirImagen);
		
		JButton btnDescomprimirImagen = new JButton("Descomprimir imagen");
		btnDescomprimirImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formDecompression window = new formDecompression();
				window.frame.setVisible(true);
			}
		});
		btnDescomprimirImagen.setBounds(205, 77, 196, 62);
		contentPane.add(btnDescomprimirImagen);
		
		JButton btnCalcularRuido = new JButton("Calcular ruido");
		btnCalcularRuido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formNoise window = new formNoise();
				window.frame.setVisible(true);
			}
		});
		btnCalcularRuido.setBounds(413, 77, 183, 62);
		contentPane.add(btnCalcularRuido);
		
		JButton btnCerrar = new JButton("cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui.this.dispose();
			}
		});
		btnCerrar.setBounds(479, 179, 117, 25);
		contentPane.add(btnCerrar);
		
		JLabel lblBienvenido = new JLabel("Bienvenido");
		lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenido.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 28));
		lblBienvenido.setBounds(205, 12, 196, 42);
		contentPane.add(lblBienvenido);
	}
}
