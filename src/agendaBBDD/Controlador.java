package agendaBBDD;

import java.util.HashMap;

/**
 * Esta clase sirve de control para el paso de informacion entre las vistas
 * (UIS) y el modelo
 * 
 * @author Miguel
 *
 */
public class Controlador {

	private Vista vista;
	private VistaTabla vistaTabla;
	private Modelo modelo;

	/**
	 * <strong>Constructor Controlador</strong> Contructor vacio de la clase
	 * Controlador
	 */
	public Controlador() {
	}

	/**
	 * Metodo que comprueba que el primer parametro cumpla la estrutura sql.Date
	 * lanzando el metodo <strong>comprobarFechaFormato(String)</strong> en caso de
	 * return false se lanza <strong>Exception()</strong>
	 * 
	 * @see comprobarFechaFormato(String)
	 * @param fecha
	 * @param nota
	 * @throws Exception
	 */
	public void nuevaNota(String fecha, String nota) throws Exception {
		if (comprobarFormatoFecha(fecha)) {
			modelo.crearNuevaNota(fecha, nota);
		} else {
			throw new Exception();
		}
	}

	/**
	 * Metodo que parte el String mediante un split("-"). Se compruba una longitud
	 * igual a 3 y que se cumpla el formato "AAAA-MM-DD", si esas dos condiciones se
	 * cumplen <strong> return true </strong>, sino <strong> return false </strong>
	 * 
	 * @param fecha
	 * @return Boolean
	 */
	private boolean comprobarFormatoFecha(String fecha) {
		String[] arrayFecha = fecha.split("-");
		boolean flag = false;
		if (arrayFecha.length == 3) {
			if (arrayFecha[0].matches("[0-9]{4}$") && arrayFecha[1].matches("[0-9]{2}$")
					&& arrayFecha[2].matches("[0-9]{2}$")) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Metodo que actualiza el objeto <strong> modelo.informacionNota </strong>
	 * 
	 * @param informacionNota
	 */
	public void actualizarNota(HashMap<String, String> informacionNota) {
		modelo.setInformacionNota(informacionNota);
	}

	/**
	 * Este metodo filtra el parametro "vistaAbrir" mediante un switch que segun el
	 * caso dado abre una vista y cierra otra.
	 * 
	 * @param vistaAbrir
	 */
	public void cargarVista(String vistaAbrir) {
		switch (vistaAbrir) {
		case "vista":
			vistaTabla.setVisible(false);
			vista.setVisible(true);
			modelo.actualizarNota();
			break;
		case "vistaTabla":
			vista.setVisible(false);
			vistaTabla.setVisible(true);
			break;
		default:
			System.err.println("<!> ERROR SWITCH: Rama default");
			vistaTabla.setVisible(false);
			vista.setVisible(true);
			break;
		}
	}

	/**
	 * Este metodo sirve de transporte para los strings de la clase Vista a Modelo
	 * 
	 * @param fecha
	 * @param contenido
	 */
	public void modificarNota(String fecha, String contenido) {
		modelo.modificarNota(fecha, contenido);
	}

	/**
	 * Este metodo com para vista.InformacionNota["codigo"] y
	 * modelo.InformacionNota["codigo"] En caso de coincidir se avisa a
	 * <strong>modelo.eliminarNota()</strong> En caso de que no coincida se produce
	 * una <strong>Exception()</strong>
	 * 
	 * @throws Exception
	 */
	public void eliminarNota() throws Exception {
		if (vista.getInformacionNota().get("codigo") == modelo.getInformacionNota().get("codigo")) {
			modelo.eliminarNota();
		} else {
			throw new Exception();
		}
	}

	/* SETTERS */
	public void setVista(Vista vista) {
		this.vista = vista;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public void setVistaTabla(VistaTabla vistaTabla) {
		this.vistaTabla = vistaTabla;
	}

}
