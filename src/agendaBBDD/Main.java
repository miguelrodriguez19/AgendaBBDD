package agendaBBDD;
/**
 * Launch de la app, que aplica el MVC creando y haciendo setters e inicia la vista principal
 * @author Miguel
 *
 */
public class Main {
	public static void main(String[] args) {
		Controlador miControlador = new Controlador();
		Vista miVista = new Vista();
		VistaTabla mivistaTabla = new VistaTabla();
		Modelo miModelo = new Modelo();
		
		miControlador.setModelo(miModelo);
		miControlador.setVista(miVista);
		miControlador.setVistaTabla(mivistaTabla);
		miModelo.setVista(miVista);
		miModelo.setVistaTabla(mivistaTabla);
		miVista.setControlador(miControlador);
		miVista.setModelo(miModelo);
		mivistaTabla.setControlador(miControlador);
		mivistaTabla.setModelo(miModelo);
		
		miVista.setVisible(true);
	}
}
