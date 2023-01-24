package agendaBBDD;

import java.sql.*;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

/**
 * Este objeto contiene la conexion y todas las consultas con una base de datos
 * mySQL llamada "agendaBBDD".
 * 
 * @author Miguel
 *
 */

public class Modelo {

	private Vista vista;
	private VistaTabla vistaTabla;
	private HashMap<String, String> informacionNota;

	private String bd = "agendaBBDD";
	private String login = "root";
	private String pwd = "";
	private String url = "jdbc:mysql://localhost/" + bd;
	private Connection conexion;
	private Statement stmt;
	private DefaultTableModel miTabla;

	/**
	 * Constructor generico de la clase Modelo. Por defecto se crea una conexion con
	 * una base de datos en local de mySQL mediante JDBC
	 */
	public Modelo() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, login, pwd);
			stmt = conexion.createStatement();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Driver JDBC no encontrado");
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("Error al conectarse a la BBDD");
			sqle.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error general");
			e.printStackTrace();
		}

	}

	/**
	 * Ejecuta una query sql.Update introduciendo <strong>fecha</strong> y
	 * <strong>nota</strong> en la tabla 'notas'.
	 * 
	 * @param fecha
	 * @param nota
	 */
	public void modificarNota(String fecha, String nota) {
		String query = "UPDATE `notas` SET `fecha` = ?, `nota` = ? WHERE `notas`.`cod` = ?";
		PreparedStatement pstmt;
		try {
			pstmt = conexion.prepareStatement(query);
			pstmt.setDate(1, Date.valueOf(fecha));
			pstmt.setString(2, nota);
			pstmt.setString(3, informacionNota.get("codigo"));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	/**
	 * Ejecuta una query sql.Insert introduciendo <strong>fecha</strong> y
	 * <strong>nota</strong> en la tabla 'notas'.
	 * 
	 * @param fecha
	 * @param nota
	 */

	public void crearNuevaNota(String fecha, String nota) {
		String query = "INSERT INTO `notas` (`cod`, `fecha`, `nota`) VALUES (NULL, ?, ?)";
		PreparedStatement pstmt;
		try {
			pstmt = conexion.prepareStatement(query);
			pstmt.setDate(1, Date.valueOf(fecha));
			pstmt.setString(2, nota);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		informacionNota = null;
	}

	/**
	 * Ejecuta una query sql.Select generando un <strong>DefaultTableModel</strong>
	 * con la informacion de la tabla 'notas'.
	 * 
	 * @return DefaultTableModel
	 */
	public DefaultTableModel cargarTabla() {
		String query = "SELECT `cod` AS Codigo,`fecha` AS Fecha,`nota` AS Nota FROM `notas`";
		miTabla = new DefaultTableModel();
		int numColumnas = getNumColumnas(query);
		Object[] contenido = new Object[numColumnas];
		PreparedStatement pstmt;
		try {
			pstmt = conexion.prepareStatement(query);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			for (int i = 0; i < numColumnas; i++) {
				miTabla.addColumn(rsmd.getColumnName(i + 1));
			}
			while (rset.next()) {
				for (int col = 1; col <= numColumnas; col++) {
					contenido[col - 1] = rset.getString(col);
				}
				miTabla.addRow(contenido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return miTabla;
	}

	/**
	 * Metodo auxiliar del metodo <strong>cargarTabla()</strong> que cuenta la
	 * cantidad que columas que tenga la tabla
	 * 
	 * @param sql
	 * @return int
	 */
	private int getNumColumnas(String sql) {
		int num = 0;

		try {
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			num = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * Metodo auxiliar del metodo <strong>cargarTabla()</strong> que cuenta la
	 * cantidad que filas que tenga la tabla
	 * 
	 * @param sql
	 * @return int
	 */
	private int getNumFilas(String sql) {
		int numFilas = 0;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next())
				numFilas++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numFilas;
	}

	/**
	 * Ejecuta una query sql.Delete donde la condicion es
	 * modelo.informacionNota["codigo"]
	 */
	public void eliminarNota() {
		String query = "DELETE FROM `notas` WHERE `notas`.`cod` = ?";
		PreparedStatement pstmt;
		Integer cod = Integer.parseInt(informacionNota.get("codigo"));
		try {
			pstmt = conexion.prepareStatement(query);
			pstmt.setInt(1, cod);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	/**
	 * Metodo que avisa a <strong>vista.cargarNota()</strong>
	 */
	public void actualizarNota() {
		vista.cargarNota();
	}

	/* GETTERS Y SETTERS */
	public DefaultTableModel getTabla() {
		return miTabla;
	}

	public Vista getVista() {
		return vista;
	}

	public void setVista(Vista vista) {
		this.vista = vista;
	}

	public void setVistaTabla(VistaTabla vistaTabla) {
		this.vistaTabla = vistaTabla;
	}

	public HashMap<String, String> getInformacionNota() {
		return informacionNota;
	}

	public void setInformacionNota(HashMap<String, String> informacionNota) {
		this.informacionNota = informacionNota;
		vista.setInformacionNota(informacionNota);
	}

}
