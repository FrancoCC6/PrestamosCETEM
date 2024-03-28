// TODO: CONECTAR CON TABLEMODELS DE LA GUI
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
}
