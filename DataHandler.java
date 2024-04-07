import javax.swing.table.*;

public class DataHandler {
	public static enum Query {
		PRESTAMOS_PENDIENTES,
		PRESTAMOS_TODOS,
		INVENTARIO;
	}

	// TODO: Cambiar para hacer mas compatible con SQL
	// y posiblemente no hacer un query cada vez
	public static DefaultTableModel getTableModelFromQuery(Query q) {
		DefaultTableModel modelo;
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

			default: // placeholder para que compile
				modelo = new DefaultTableModel();
		}

		return modelo;
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
