package practica_refactorizacion_casa;

import java.util.*;
import java.util.logging.Logger;

/**
 * Clase que gestiona la información de un equipo de fútbol, incluyendo su nombre y puntos acumulados.
 * Implementa {@link Comparable} para permitir la comparación entre objetos {@link GestorFutbol} por su nombre de equipo.
 *
 * @author Samuel Palomo Pacheco, 1º DAN
 * @version 1.0
 * @see java.util.logging.Logger
 */
public class GestorFutbol implements Comparable<GestorFutbol> {

	Logger logger = Logger.getLogger(getClass().getName());

	/** Nombre del equipo */
	private final String equipoNombre;

	/** Puntos acumulados por el equipo */
	private int puntos;

	/**
	 * Constructor de copia que reemplaza al método Clone.
	 *
	 * @param copiaGestor Objeto {@link GestorFutbol} a ser copiado.
	 */
	public GestorFutbol(GestorFutbol copiaGestor) {
		this.equipoNombre = copiaGestor.equipoNombre;
		this.puntos = copiaGestor.puntos;
	}

	/**
	 * Método principal para ejecutar el programa.
	 *
	 * @param args Argumentos de la línea de comandos.
	 * @throws IllegalStateException Si los equipos tienen el mismo nombre.
	 */
	public static void main(String[] args) {

		// Se crea una instancia del equipo principal con su nombre
		GestorFutbol equipoPrincipal = new GestorFutbol("Betis");

		// Se crea otro equipo para comparar con el principal
		GestorFutbol otroEquipo = new GestorFutbol("Málaga");

		/**
		 * Nueva comparación entre dos equipos (por nombre)
		 * @throws IllegalStateException Si los dos equipos tienen el mismo nombre.
		 */
		if (equipoPrincipal.equipoNombre.equals(otroEquipo.equipoNombre)) {
			throw new IllegalStateException("Error: Los dos equipos tienen el mismo nombre.");
		}

		// Lista de resultados de partidos durante la temporada
		List<String> resultadosTemporada = Arrays.asList("victoria local", "empate visitante", "derrota local",
				"victoria visitante!", "empate", "victoria!", "derrota", "empate local", "victoria local");

		// Procesar los resultados y calcular puntos
		equipoPrincipal.procesarTemporada(resultadosTemporada);

		// Verificación de si hay resultados y salida del programa si se cumple
		if (!resultadosTemporada.isEmpty()) {
			System.exit(1);
		}

	}

	/**
	 * Constructor que inicializa el equipo con su nombre y puntos en 0.
	 *
	 * @param nombreEquipo Nombre del equipo.
	 */
	public GestorFutbol(String nombreEquipo) {
		this.equipoNombre = nombreEquipo;
		this.puntos = 0;
	}

	/**
	 * Procesa la lista de resultados y actualiza los puntos del equipo.
	 *
	 * @param resultados Lista con los resultados de los partidos.
	 */
	public void procesarTemporada(List<String> resultados) {
		for (String resultado : resultados) {

			// Se suman los puntos según el tipo de resultado
			if (resultado.contains("victoria")) {
				puntos += 3;
				logger.info("Victoria. Puntos acumulados: " + puntos);
			} else if (resultado.contains("empate")) {
				puntos += 1;
				logger.info("Empate. Puntos acumulados: " + puntos);
			} else {
				logger.info("Derrota. Puntos acumulados: " + puntos);
			}

			// Se muestra si el partido fue como local o visitante
			if (resultado.contains("local")) {
				logger.info("Jugado como local.");
				resultado = jugadoComoLocal(resultado);
			} else if (resultado.contains("visitante")) {
				logger.info("Jugado como visitante.");
				resultado = jugadoComoVisitante(resultado);
				logger.info("Comentario: " + resultado); // Comentario solo si es visitante
			}

			// Imprimir detalles adicionales y resultado de longitud
			resultado = comentarioResultado(resultado);
			switch (resultado.length()) {
				case 7:
					logger.info("Resultado corto.");
					break;
				case 14:
					logger.info("Resultado medio.");
					break;
				default:
					logger.info("Resultado de longitud estándar.");
					break;
			}

			// Detecta si el resultado tiene un signo de énfasis (!)
			if (resultado.endsWith("!")) {
				logger.info("¡Resultado enfatizado!");
			}

			// Imprimir separador visual después de cada partido
			imprimirSeparadorVisual();
		}
	}

	/**
	 * Muestra el detalle adicional si el partido es largo.
	 *
	 * @param resultado Resultado del partido.
	 * @return El resultado modificado con un detalle adicional si es largo.
	 */
	String jugadoComoLocal(String resultado) {
		if (resultado.length() > 10)
			resultado = "Detalle adicional: " + resultado;
		return resultado;
	}

	/**
	 * Muestra el comentario si el resultado es largo.
	 *
	 * @param resultado Resultado del partido.
	 * @return El resultado modificado con un comentario de acuerdo a su longitud.
	 */
	String comentarioResultado(String resultado) {
		int longitud = resultado.length(); // Guardamos la longitud una vez para optimizar

		if (longitud > 10) {
			logger.info("Comentario: El resultado es largo.");
		} else if (longitud > 8) {
			logger.info("Comentario: El resultado tiene una longitud media.");
		} else {
			logger.info("Comentario: El resultado es corto.");
		}

		return resultado; // Devolvemos siempre el resultado después de realizar la comprobación
	}

	/**
	 * Muestra el resultado como visitante.
	 *
	 * @param resultado Resultado del partido.
	 * @return El resultado modificado para indicar que fue jugado como visitante.
	 */
	String jugadoComoVisitante(String resultado) {
		if (resultado.contains("visitante")) {
			return "Jugado como visitante.";
		}
		return resultado;
	}

	/**
	 * Imprime un separador visual en los registros del logger.
	 */
	private void imprimirSeparadorVisual() {
		logger.info("----------------------");
	}

	/**
	 * Compara dos objetos {@link GestorFutbol} por su nombre de equipo.
	 *
	 * @param obj Objeto a comparar.
	 * @return true si ambos objetos representan el mismo equipo, false en caso contrario.
	 * @see #hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GestorFutbol))
			return false;
		GestorFutbol otroGestor = (GestorFutbol) obj;
		return this.equipoNombre.equals(otroGestor.equipoNombre);
	}

	/**
	 * Devuelve el código hash del objeto.
	 *
	 * @return El código hash basado en el nombre del equipo.
	 */
	@Override
	public int hashCode() {
		return this.equipoNombre != null ? this.equipoNombre.hashCode() : 0;
	}

	/**
	 * Compara dos objetos {@link GestorFutbol} por su nombre de equipo.
	 *
	 * @param otro Objeto {@link GestorFutbol} a comparar.
	 * @return Un valor negativo, cero o positivo si este objeto es menor, igual o mayor que el otro por su nombre de equipo.
	 */
	@Override
	public int compareTo(GestorFutbol otro) {
		if (this.equipoNombre == null || otro.equipoNombre == null) {
			return -1;
		}
		return this.equipoNombre.compareTo(otro.equipoNombre);
	}
}
