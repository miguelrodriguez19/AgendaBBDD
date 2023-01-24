package agendaBBDD;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToolBar;

/**
 * UI VistaTabla muestra el contenido de la tabla 'notas' en la BBDD mySQL
 * "agendaBBDD" y lanza eventos
 * 
 * @author Miguel
 *
 */
public class VistaTabla extends JFrame {
	private Modelo modelo;
	private Controlador controlador;
	private HashMap<String, String> informacionNota;

	private JPanel panel;
	private JLabel lblAgendaBBDD;
	private JTable table;
	private JButton btnConfirmar, btnCancelar, btnNewButton;
	private JScrollPane scrollPaneEventos;

	/**
	 * <strong>Constructor de la clase VistaTabla</strong> Este costructor genera
	 * todo el aspecto visual
	 */
	public VistaTabla() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Vista.class.getResource("/recursos/Icon32x32.png")));
		setResizable(false);
		setTitle("Cargar nota");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 442);

		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setForeground(new Color(102, 153, 153));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		lblAgendaBBDD = new JLabel("Cargar Nota");
		lblAgendaBBDD.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgendaBBDD.setFont(new Font("Bauhaus 93", Font.PLAIN, 30));
		lblAgendaBBDD.setForeground(Color.WHITE);
		lblAgendaBBDD.setBounds(10, 11, 570, 50);
		panel.add(lblAgendaBBDD);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Crea HashMap con la informacion de la nota recogida de la tabla
				informacionNota = new HashMap<>();
				informacionNota.put("codigo", table.getValueAt(table.getSelectedRow(), 0).toString());
				informacionNota.put("fecha", table.getValueAt(table.getSelectedRow(), 1).toString());
				informacionNota.put("contenido", table.getValueAt(table.getSelectedRow(), 2).toString());

				// Actualiza el HashMap de modelo y de vista
				controlador.actualizarNota(informacionNota);
				controlador.cargarVista("vista");
			}
		});
		btnConfirmar.setBorder(null);
		btnConfirmar.setBounds(481, 326, 89, 30);
		panel.add(btnConfirmar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Crea HashMap con la informacion en blanco
				informacionNota = new HashMap<>();
				informacionNota.put("codigo", "");
				informacionNota.put("fecha", "");
				informacionNota.put("contenido", "");

				// Actualiza el HashMap de modelo y de vista
				controlador.actualizarNota(informacionNota);
				controlador.cargarVista("vista");
			}
		});
		btnCancelar.setBorder(null);
		btnCancelar.setBounds(20, 326, 89, 30);
		panel.add(btnCancelar);

		table = new JTable();
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnConfirmar.setEnabled(true);
			}
		});
		table.setEditingColumn(0);
		table.setEditingRow(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSurrendersFocusOnKeystroke(true);
		table.setToolTipText("");
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table.setRowHeight(50);
		table.setBounds(96, 58, 809, 285);

		scrollPaneEventos = new JScrollPane();
		scrollPaneEventos.setBounds(20, 58, 550, 238);
		panel.add(scrollPaneEventos);
		scrollPaneEventos.setViewportView(table);

		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(0, 0, 0, 0);
		panel.add(btnNewButton);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				btnConfirmar.setEnabled(false);
				informacionNota = null;
				modelo.cargarTabla();
				table.setModel(modelo.getTabla());
			}
		});
	}

	/* GETTERS Y SETTERS */
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public HashMap<String, String> getInformacionNota() {
		return informacionNota;
	}

	public void setInformacionNota(HashMap<String, String> informacionNota) {
		this.informacionNota = informacionNota;
	}
}