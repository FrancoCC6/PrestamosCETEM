import javax.swing.table.*;

public class DataHandler {
	public static void fetchData() {
		while (FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.next()) {
			MODELO_PRESTAMOS_PENDIENTES.addRow(new Object[] {
				FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(1), 
				FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(2), 
				FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(3), 
				FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(4), 
				FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(5), 
				FakeResultSet.QUERY_PRESTAMOS_PENDIENTES.getString(6) 
			});
		}

		while (FakeResultSet.QUERY_INVENTARIO.next()) {
			MODELO_INVENTARIO.addRow(new Object[] {FakeResultSet.QUERY_INVENTARIO.getString(1), FakeResultSet.QUERY_INVENTARIO.getInt(2)});
		}

		while (FakeResultSet.QUERY_PRESTAMOS_TODOS.next()) {
			MODELO_PRESTAMOS_TODOS.addRow(new Object[] {
				FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(1), 
				FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(2), 
				FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(3), 
				FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(4), 
				FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(5), 
				FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(6), 
				FakeResultSet.QUERY_PRESTAMOS_TODOS.getString(7) 
			});
		}
	}

	public static final DefaultTableModel MODELO_INVENTARIO = new DefaultTableModel(new Object[]{
		"Elemento", 
		"Cantidad"
	}, 0);

	public static final DefaultTableModel MODELO_PRESTAMOS_PENDIENTES = new DefaultTableModel(new Object[]{
		"Fecha y Hora",
		"Nombre",
		"Apellido",
		"Elemento",
		"Cantidad",
		"Presta"
	}, 0);

	public static final DefaultTableModel MODELO_PRESTAMOS_TODOS = new DefaultTableModel(new Object[]{
		"Fecha y Hora",
		"Nombre",
		"Apellido",
		"Elemento",
		"Cantidad",
		"Presta",
		"Devolvio"
	}, 0);
}
