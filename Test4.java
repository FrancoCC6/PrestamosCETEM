import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Simula objetos de SQLite
class FakeResultSet {
	private final Object[][] DATASET;
	private int current_register = -1;

	public FakeResultSet(Object[][] dataset) {
		DATASET = dataset;
	}

	public boolean next() {
		return (++current_register < DATASET.length);
	}

	public Integer getInt(int field_index) {
		return (Integer)(DATASET[current_register][field_index-1]);
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
}

public class Test4 {
	private static void buildWindow() {
		final JFrame FRAME = new JFrame();

		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setTitle("Niggers");
		FRAME.setSize(500, 500);
		FRAME.setVisible(true);
	}

	public static void main(String[] args) {
		//SwingUtilities.invokeLater(() -> buildWindow());
		FakeResultSet rs = new FakeResultSet(new Object[][] {
			{1, "Puto"},
			{2, "Cagon"}
		});

		while(rs.next()) {
			System.out.println(rs.getInt(1) + " " + rs.getString(2));
		}
	}
}
