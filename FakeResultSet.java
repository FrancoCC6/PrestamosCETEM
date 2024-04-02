import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javax.swing.SwingUtilities;

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
	public Calendar getCalendar(int field_index) {
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
		QUERY_PRESTAMOS_PENDIENTES = new FakeResultSet(new Object[][] {
			{"15/03 15:00", "Sister", "Friede", "Calculadora", 1, "Mecha"},
			{"15/03 14:00", "Demon", "in Pain", "Guardapolvo", 3, "Pablo"},
			{"15/03 13:30", "Demon", "from Below", "Casco", 2, "Any"},
			{"15/03 12:00", "Demon", "Prince", "Proteccion Auditiva", 4, "Fran"},
			{"15/03 11:00", "Knight", "Artorias", "Enchufe Zapatilla", 1, "Pame"},
			{"15/03 10:00", "Gwyn", "Lord of Cinder", "Proteccion Visual", 2, "Mecha"},
			{"15/03 9:00", "Malenia", "Blade of Miquella", "Calculadora", 1, "Laure"}
		}),
		QUERY_PRESTAMOS_TODOS = new FakeResultSet(new Object[][] {
			{"15/03 15:00", "Sister", "Friede", "Calculadora", 1, "Mecha", "No"},
			{"15/03 14:00", "Demon", "in Pain", "Guardapolvo", 3, "Pablo", "No"},
			{"15/03 13:30", "Demon", "from Below", "Casco", 2, "Any", "No"},
			{"15/03 12:00", "Demon", "Prince", "Proteccion Auditiva", 4, "Fran", "No"},
			{"15/03 11:00", "Knight", "Artorias", "Enchufe Zapatilla", 1, "Pame", "No"},
			{"15/03 10:00", "Gwyn", "Lord of Cinder", "Proteccion Visual", 2, "Mecha", "No"},
			{"15/03 9:00", "Malenia", "Blade of Miquella", "Calculadora", 1, "Laure", "No"},
			{"14/03 15:00", "The Great", "Mighty Poo", "Calculadora", 2, "Mecha", "Si"},
			{"14/03 14:00", "Lea", "Crosscode", "Guardapolvo", 2, "Mecha", "Si"},
			{"14/03 13:00", "Agony", "OST", "Casco", 2, "Mecha", "Si"},
			{"14/03 9:00", "Suzuha", "Amane", "Proteccion Visual", 2, "Mecha", "Si"}
		}),
		QUERY_PRESTAMOS_FECHA = new FakeResultSet(new Object[][] {
			{"14/03 15:00", "The Great", "Mighty Poo", "Calculadora", 2, "Mecha", "Si"},
			{"14/03 14:00", "Lea", "Crosscode", "Guardapolvo", 2, "Mecha", "Si"},
			{"14/03 13:00", "Agony", "OST", "Casco", 2, "Mecha", "Si"},
			{"14/03 9:00", "Suzuha", "Amane", "Proteccion Visual", 2, "Mecha", "Si"}
		});
		/*
		QUERY_ALUMNOS = new FakeResultSet(new Object[][] {
			{"Sister", "Friede", "Calculadora", 1, "Mecha"},
			{"Demon", "in Pain", "Guardapolvo", 3, "Pablo"},
			{"Demon", "from Below", "Casco", 2, "Any"},
			{"Demon", "Prince", "Proteccion Auditiva", 4, "Fran"},
			{"Knight", "Artorias", "Enchufe Zapatilla", 1, "Pame"},
			{"Gwyn", "Lord of Cinder", "Proteccion Visual", 2, "Mecha"},
			{"Malenia", "Blade of Miquella", "Calculadora", 1, "Laure"}
		});
		*/
}
