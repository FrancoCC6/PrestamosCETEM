import javax.swing.table.*;

public class DataHandler {
	public static enum Query {
		PRESTAMOS_PENDIENTES,
		PRESTAMOS_TODOS,
		PRESTAMOS_FECHA,
		PRESTAMOS_ELEMENTO,
		PRESTAMOS_ALUMNO,
		INVENTARIO;
	}

	// TODO: Cambiar para hacer mas compatible con SQL
	// y posiblemente no hacer un query cada vez
	public static DefaultTableModel getTableModelFromQuery(Query q, String q_hint) {
		DefaultTableModel modelo = null;
		switch (q) {
			case PRESTAMOS_PENDIENTES:
				modelo = new DefaultTableModel(COLUMNAS_PRESTAMOS_PENDIENTES, 0);
				while (FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.next()) {
					modelo.addRow(new Object[] {
						FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(1), 
						FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(2), 
						FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(3), 
						FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(4), 
						FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(5), 
						FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(6) 
					});
				}
				break;

			case PRESTAMOS_TODOS:
				modelo = new DefaultTableModel(COLUMNAS_PRESTAMOS_TODOS, 0);
				while (FakeResultSet.QUERY_PRESTAMOS_TODOS.next()) {
					modelo.addRow(new Object[] {
						FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(1), 
						FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(2), 
						FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(3), 
						FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(4), 
						FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(5), 
						FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(6), 
						FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(7) 
					});
				}
				break;

			case INVENTARIO:
				modelo = new DefaultTableModel(COLUMNAS_INVENTARIO, 0);
				while (FakeResultSet.QUERY_INVENTARIO.next()) {
					modelo.addRow(new Object[] {
						FakeResultSet.QUERY_INVENTARIO.getString(1), 
						FakeResultSet.QUERY_INVENTARIO.getInt(2)
					});
				}
				break;

			case PRESTAMOS_FECHA:
				if (q_hint.matches("14/0?3/2024")) {
					modelo = new DefaultTableModel(COLUMNAS_PRESTAMOS_TODOS, 0);
					while (FakeResultSet.QUERY_PRESTAMOS_FECHA.next()) {
						modelo.addRow(new Object[] {
							FakeResultSet.QUERY_PRESTAMOS_FECHA.getString(1), 
							FakeResultSet.QUERY_PRESTAMOS_FECHA.getString(2), 
							FakeResultSet.QUERY_PRESTAMOS_FECHA.getString(3), 
							FakeResultSet.QUERY_PRESTAMOS_FECHA.getString(4), 
							FakeResultSet.QUERY_PRESTAMOS_FECHA.getString(5), 
							FakeResultSet.QUERY_PRESTAMOS_FECHA.getString(6), 
							FakeResultSet.QUERY_PRESTAMOS_FECHA.getString(7) 
						});
					}
				}
				
				break;

			case PRESTAMOS_ELEMENTO:
				if (q_hint.toUpperCase().equals("CALCULADORA")) {
					modelo = new DefaultTableModel(COLUMNAS_PRESTAMOS_TODOS, 0);
					while (FakeResultSet.QUERY_PRESTAMOS_ELEMENTO.next()) {
						modelo.addRow(new Object[] {
							FakeResultSet.QUERY_PRESTAMOS_ELEMENTO.getString(1), 
							FakeResultSet.QUERY_PRESTAMOS_ELEMENTO.getString(2), 
							FakeResultSet.QUERY_PRESTAMOS_ELEMENTO.getString(3), 
							FakeResultSet.QUERY_PRESTAMOS_ELEMENTO.getString(4), 
							FakeResultSet.QUERY_PRESTAMOS_ELEMENTO.getString(5), 
							FakeResultSet.QUERY_PRESTAMOS_ELEMENTO.getString(6), 
							FakeResultSet.QUERY_PRESTAMOS_ELEMENTO.getString(7) 
						});
					}
				}

				break;

			case PRESTAMOS_ALUMNO:
				if (
						q_hint.toUpperCase().equals("LEA CROSSCODE")
					||	q_hint.equals("50704")
				) {
					modelo = new DefaultTableModel(COLUMNAS_PRESTAMOS_TODOS, 0);
					while (FakeResultSet.QUERY_PRESTAMOS_ALUMNO.next()) {
						modelo.addRow(new Object[] {
							FakeResultSet.QUERY_PRESTAMOS_ALUMNO.getString(1), 
							FakeResultSet.QUERY_PRESTAMOS_ALUMNO.getString(2), 
							FakeResultSet.QUERY_PRESTAMOS_ALUMNO.getString(3), 
							FakeResultSet.QUERY_PRESTAMOS_ALUMNO.getString(4), 
							FakeResultSet.QUERY_PRESTAMOS_ALUMNO.getString(5), 
							FakeResultSet.QUERY_PRESTAMOS_ALUMNO.getString(6), 
							FakeResultSet.QUERY_PRESTAMOS_ALUMNO.getString(7) 
						});
					}
				}
		}

		return modelo;
	}

	// Dubious placeholder
	public static DefaultTableModel getTableModelFromQuery(Query q) {
		return getTableModelFromQuery(q, "");
	}

	private static final String
		COLUMNAS_INVENTARIO[] = {
			"Elemento", 
			"Cantidad"
		},
		COLUMNAS_PRESTAMOS_PENDIENTES[] = {
			"Fecha y Hora",
			"Nombre",
			"Apellido",
			"Elemento",
			"Cantidad",
			"Presta"
		},
		COLUMNAS_PRESTAMOS_TODOS[] = {
			"Fecha y Hora",
			"Nombre",
			"Apellido",
			"Elemento",
			"Cantidad",
			"Presta",
			"Devolvio"
		};
}
