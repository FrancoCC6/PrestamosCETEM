import javax.swing.table.*;

public class DataHandler {
	public static void fetchData() {
		while (FakeResultSet.QUERY_PRESTAMOS.next()) {
			MODELO_PRESTAMOS.addRow(new Object[] {
				FakeResultSet.QUERY_PRESTAMOS.getString(1), 
				FakeResultSet.QUERY_PRESTAMOS.getString(2), 
				FakeResultSet.QUERY_PRESTAMOS.getString(3), 
				FakeResultSet.QUERY_PRESTAMOS.getString(4), 
				FakeResultSet.QUERY_PRESTAMOS.getString(5) 
			});
		}

		while (FakeResultSet.QUERY_INVENTARIO.next()) {
			MODELO_INVENTARIO.addRow(new Object[] {FakeResultSet.QUERY_INVENTARIO.getString(1), FakeResultSet.QUERY_INVENTARIO.getInt(2)});
		}
	}

	public static final DefaultTableModel MODELO_INVENTARIO = new DefaultTableModel(new Object[]{
		"Elemento", 
		"Cantidad"
	}, 0);

	public static final DefaultTableModel MODELO_PRESTAMOS = new DefaultTableModel(new Object[]{
		"Nombre",
		"Apellido",
		"Elemento",
		"Cantidad",
		"Presta"
	}, 0);
}
