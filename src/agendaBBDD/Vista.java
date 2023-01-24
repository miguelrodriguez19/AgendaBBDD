package agendaBBDD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * UI Vista es la vista principal de la app. Esta UI permite al usuario
 * interaccionar y recoge los eventos que este lanza.
 * 
 * @author Miguel
 *
 */
public class Vista extends JFrame {
	private Modelo modelo;
	private Controlador controlador;
	private HashMap<String, String> informacionNota;

	private JPanel panel;
	private JLabel lblAgendaBBDD;
	private JLabel lblFecha;
	private JTextField txtFecha;
	private JLabel lblNota;
	private JScrollPane scrollPane;
	private JButton btnNuevaNota;
	private JButton btnModificar;
	private JButton btnMostar;
	private JButton btnEliminar;
	private JLabel lblFormatoFecha;
	private JLabel lblError;
	private JLabel lblMensajeUsuario;
	private JTextArea txtANota;

	/**
	 * <strong>Constructor de la clase Vista</strong> Este costructor genera todo el
	 * aspecto visual
	 * 
	 */
	public Vista() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Vista.class.getResource("/recursos/Icon32x32.png")));
		setResizable(false);
		setTitle("Agenda BBDD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 442);

		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setForeground(new Color(102, 153, 153));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.RIGHT);
		lblError.setForeground(new Color(255, 84, 84));
		lblError.setFont(new Font("Arial", Font.BOLD, 13));
		lblError.setBounds(46, 344, 394, 14);
		panel.add(lblError);

		lblAgendaBBDD = new JLabel("Agenda BBDD");
		lblAgendaBBDD.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgendaBBDD.setFont(new Font("Bauhaus 93", Font.PLAIN, 30));
		lblAgendaBBDD.setForeground(Color.WHITE);
		lblAgendaBBDD.setBounds(10, 11, 570, 50);
		panel.add(lblAgendaBBDD);

		lblFecha = new JLabel("Fecha:");
		lblFecha.setForeground(Color.WHITE);
		lblFecha.setBackground(Color.WHITE);
		lblFecha.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblFecha.setBounds(20, 65, 150, 25);
		panel.add(lblFecha);

		txtFecha = new JTextField();
		txtFecha.setMargin(new Insets(2, 8, 2, 2));
		txtFecha.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		txtFecha.setBackground(Color.WHITE);
		txtFecha.setBounds(20, 91, 200, 25);
		panel.add(txtFecha);
		txtFecha.setColumns(10);

		lblNota = new JLabel("Nota:");
		lblNota.setForeground(Color.WHITE);
		lblNota.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblNota.setBounds(20, 137, 150, 25);
		panel.add(lblNota);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 163, 420, 170);
		panel.add(scrollPane);

		txtANota = new JTextArea();
		txtANota.setLineWrap(true);
		txtANota.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		txtANota.setMargin(new Insets(2, 8, 2, 2));
		scrollPane.setViewportView(txtANota);

		btnNuevaNota = new JButton("Nueva");
		btnNuevaNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprueba que los campos de texto no esten vacios
				if (!(txtFecha.getText().equals("") || txtANota.getText().equals(""))) {
					try {
						// Avisa al controlador para que avise al modelo para hacer un sql.INSERT
						controlador.nuevaNota(txtFecha.getText(), txtANota.getText());
						lblMensajeUsuario.setText("Nota guardada.");
						limpiarCampos();
						informacionNota = null;
					} catch (Exception e2) {
						lblError.setText("Formato de fecha invalido.");
						lblMensajeUsuario.setText("");
						txtFecha.setText("");
					}
				} else {
					lblError.setText("Rellena todos los campos.");
					lblMensajeUsuario.setText("");
				}
			}
		});
		btnNuevaNota.setBorder(null);
		btnNuevaNota.setBounds(472, 161, 89, 30);
		panel.add(btnNuevaNota);

		btnMostar = new JButton("Mostar");
		btnMostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					/*
					 * Avisa al controlador para cambiar de pantalla y este avisa al modelo para
					 * hacer sql.SELECT y cargar los datos de la nueva vista
					 */
					limpiarCampos();
					controlador.cargarVista("vistaTabla");
				} catch (Exception e2) {
					lblError.setText("Ha habido un error interno");
					lblMensajeUsuario.setText("");
				}
			}
		});
		btnMostar.setBorder(null);
		btnMostar.setBounds(472, 210, 89, 30);
		panel.add(btnMostar);

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Comprueba si se ha modificado al menos un elemento respecto al HashMap
					if (!(txtFecha.getText().equals(informacionNota.get("fecha")))
							|| !(txtANota.getText().equals(informacionNota.get("contenido")))) {
						// Comprueba que los campos de texto no esten vacios
						if (!(txtFecha.getText().equals("") || txtANota.getText().equals(""))) {
							try {
								// Avisa al controlador para que avise al modelo de hacer un sql.UPDATE
								controlador.modificarNota(txtFecha.getText(), txtANota.getText());
								lblMensajeUsuario.setText("Nota actualizada con exito.");
								limpiarCampos();
								informacionNota = null;
							} catch (Exception e2) {
								lblError.setText("Formato de fecha invalido.");
								lblMensajeUsuario.setText("");
							}
						} else {
							lblError.setText("Rellena todos los campos.");

							lblMensajeUsuario.setText("");
						}
					} else {
						lblError.setText("Actualiza el contenido de la nota.");

						lblMensajeUsuario.setText("");
					}
				} catch (Exception e2) {
					lblError.setText("Necesitas cargar antes una nota.");
					lblMensajeUsuario.setText("");
				}
			}
		});
		btnModificar.setBorder(null);
		btnModificar.setBounds(472, 255, 89, 30);
		panel.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Comprueba que exista el HashMap informacionNota
					if (informacionNota != null) {
						try {
							// Avisa al controlador para que avise al modelo para hacer un sql.DELETE
							controlador.eliminarNota();
							informacionNota = null;
							lblMensajeUsuario.setText("Nota eliminada correctamente.");
							limpiarCampos();
						} catch (Exception e2) {
							lblError.setText("Necesitas cargar antes una nota.");
							lblMensajeUsuario.setText("");
						}
						informacionNota = null;
					} else {
						lblError.setText("Necesitas cargar antes una nota.");
						lblMensajeUsuario.setText("");
					}
				} catch (Exception e2) {
					lblError.setText("Ha habido un error interno");
					lblMensajeUsuario.setText("");
				}
			}
		});
		btnEliminar.setBorder(null);
		btnEliminar.setBounds(472, 303, 89, 30);
		panel.add(btnEliminar);

		lblFormatoFecha = new JLabel("AAAA-MM-DD");
		lblFormatoFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFormatoFecha.setForeground(Color.WHITE);
		lblFormatoFecha.setBounds(20, 121, 200, 14);
		panel.add(lblFormatoFecha);

		lblMensajeUsuario = new JLabel("");
		lblMensajeUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblMensajeUsuario.setForeground(new Color(124, 252, 0));
		lblMensajeUsuario.setFont(new Font("Arial", Font.BOLD, 13));
		lblMensajeUsuario.setBounds(22, 344, 295, 14);
		panel.add(lblMensajeUsuario);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				lblMensajeUsuario.setText("");
				lblError.setText("");
			}
		});
	}

	/**
	 * Metodo que limpia el texto de lblError, txtFecha y txtANota
	 */
	public void limpiarCampos() {
		lblError.setText("");
		txtANota.setText("");
		txtFecha.setText("");
	}

	/**
	 * Metodo que recoge la informacion del HashMap y se la añade al txtFecha y
	 * txtANota
	 */
	public void cargarNota() {
		txtFecha.setText(informacionNota.get("fecha"));
		txtANota.setText(informacionNota.get("contenido"));

	}

	/* GETTER Y SETTERS */
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void setInformacionNota(HashMap<String, String> informacionNota) {
		this.informacionNota = informacionNota;
	}

	public HashMap<String, String> getInformacionNota() {
		return informacionNota;
	}
}
