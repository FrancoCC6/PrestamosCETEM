public class FakeResultSet {
	private final Object[][] DATASET;
	private int current_register = -1;

	// Solo crear FakeResultSets locales que seran usadas fuera
	private FakeResultSet(Object[][] dataset) {
		DATASET = dataset;
	}

	public boolean next() {
		return (++current_register < DATASET.length);
	}

	public Integer getInt(int field_index) {
		return (int)(DATASET[current_register][field_index-1]);
	}

	public String getString(int field_index) {
		return DATASET[current_register][field_index-1].toString();
	}
/*
	public Date getDate(int field_index) {
		// CAMBIAR A UNO APROPIADO
		return (Integer)(DATASET[current_register][field_index]);
	}

	public Integer getInt(int field_index) {
		return (Integer)(DATASET[current_register][field_index]);
	}
*/

	// NO SE AJUSTA AL MODELO DE DATOS PROPIAMENTE DICHO, REFLEJA LOS RESULTADOS DEL QUERY
	public static final FakeResultSet 
		QUERY_INVENTARIO = new FakeResultSet(new Object[][] {
			{"Calculadora", 4},
			{"Enchufe Zapatilla", 1},
			{"Proteccion Visual", 20},
			{"Proteccion Auditiva", 20},
			{"Casco", 15},
			{"Guardapolvo", 2}
		}),
		QUERY_PRESTAMOS = new FakeResultSet(new Object[][] {
			{"Sister", "Friede", "Calculadora", 1, "Mecha"},
			{"Demon", "in Pain", "Guardapolvo", 3, "Pablo"},
			{"Demon", "from Below", "Casco", 2, "Any"},
			{"Demon", "Prince", "Proteccion Auditiva", 4, "Fran"},
			{"Knight", "Artorias", "Enchufe Zapatilla", 1, "Pame"},
			{"Gwyn", "Lord of Cinder", "Proteccion Visual", 2, "Mecha"},
			{"Malenia", "Blade of Miquella", "Calculadora", 1, "Laure"}
		});
}
