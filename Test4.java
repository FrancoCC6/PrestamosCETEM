/* 
	Bibliografia: 
		* https://stackoverflow.com/questions/6567870/what-does-swingutilities-invokelater-do
		* https://www.quora.com/Does-JVM-create-an-object-of-the-main-class-the-class-with-main
*/

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
}

public class Test4 {
	private static final JFrame FRAME = new JFrame();

	private static JPanel buildButtonsPanel() {
		final JButton
			BOTON_1 = new JButton("Boton 1"),
			BOTON_2 = new JButton("Boton 2"),
			BOTON_3 = new JButton("Boton 3"),
			BOTON_4 = new JButton("Boton 4");

		final JPanel PANEL_BOTONES = new JPanel(new FlowLayout(FlowLayout.LEADING));

		PANEL_BOTONES.add(BOTON_1);
		PANEL_BOTONES.add(BOTON_2);
		PANEL_BOTONES.add(BOTON_3);
		PANEL_BOTONES.add(BOTON_4);

		return PANEL_BOTONES;
	}

	private static void buildLowerPanel() {

	}

	private static void buildStockPanel() {

	}

	private static void buildLoansPanel() {

	}

	private static void buildWindow() {
		final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		final int ALTO = screensize.height / 4 * 3;
		final int ANCHO = screensize.width / 4 * 3;
		//final GridBagConstraints GBAGC = new GridBagConstraints();

		//FRAME.setLayout(new GridBagLayout());
		FRAME.add(buildButtonsPanel());
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setTitle("Prestamos CETEM");
		FRAME.setSize(ANCHO, ALTO);
		FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
		FRAME.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> buildWindow());
	}
}
