/* 
	Bibliografia: 
		* https://stackoverflow.com/questions/6567870/what-does-swingutilities-invokelater-do
		* https://www.quora.com/Does-JVM-create-an-object-of-the-main-class-the-class-with-main
		* https://stackoverflow.com/questions/15513380/how-to-open-a-new-window-by-clicking-a-button
		* https://old.chuidiang.org/java/tablas/tablamodelo/tablamodelo.php
		* https://www.geeksforgeeks.org/java-swing-jtable/
		* https://www.tutorialspoint.com/sqlite/sqlite_java.html

*/

import javax.swing.*;
import javax.swing.table.*;
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

public class Test6 {
	private static final FakeResultSet QUERY_INVENTARIO = new FakeResultSet(new Object[][] {
		{"Calculadoras", 4},
		{"Enchufes Zapatilla", 1},
		{"Proteccion Visual", 20},
		{"Proteccion Auditiva", 20},
		{"Cascos", 15},
		{"Guardapolvos", 2}
	});

	private static final DefaultTableModel MODELO_INVENTARIO = new DefaultTableModel(new Object[]{"Elemento", "Cantidad"}, 0);

	private static class TaggedJButton extends JButton {
		public final String TAG;

		public TaggedJButton(String tag) {
			setText(tag);
			TAG = tag;
		}
	}

	private static final JFrame FRAME = new JFrame();
	private static final JPanel PANEL_INFERIOR = new JPanel();
	//private static final Container PANELINF_CONTENTPANE = PANEL_INFERIOR.getContentPane();
	private static final CardLayout CARD_LAYOUT = new CardLayout();

	private static final ActionListener BTN_ACTION = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			CARD_LAYOUT.show(PANEL_INFERIOR, ((TaggedJButton)e.getSource()).TAG);
		}
	};
	private static JPanel buildButtonsPanel() {
		final String[] TAGS = {
			"Prestamos",
			"Inventario",
			"Boton 3",
			"Boton 4"
		};

		final TaggedJButton[] BOTONES = new TaggedJButton[TAGS.length];
		for (int i = 0; i < TAGS.length; i++) {
			BOTONES[i] = new TaggedJButton(TAGS[i]);
			if (i < 2) { // Por ahora solo está implementado para los dos primeros botones
				BOTONES[i].addActionListener(BTN_ACTION);
			}
		}

		/*
		BOTON_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(() -> {
					JFrame new_frame = new JFrame("Test");
					new_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					new_frame.setSize(500, 500);
					new_frame.setResizable(false);
					new_frame.setLocationByPlatform(true);
					new_frame.setVisible(true);
				});
			}
		});

		BOTON_1.setBackground(Color.CYAN);
		BOTON_2.setBackground(Color.YELLOW);
		BOTON_3.setBackground(Color.RED);
		BOTON_4.setBackground(Color.GREEN);
		*/

		final JPanel PANEL_BOTONES = new JPanel(new FlowLayout(FlowLayout.LEADING));

		for (TaggedJButton btn : BOTONES) {
			PANEL_BOTONES.add(btn);
		}

		return PANEL_BOTONES;
	}

	/*
	 *									IMPORTANTE
	 *
	 *
	 *	Para llenar las tablas de datos:
	 *
	 *		0. Las JTables se crean vacías
	 *
	 *	En un hilo aparte:
	 *		1. Se hace el query desde el programa
	 *		2. Por cada fila del ResultSet se crea una nueva que se agrega al JTable
	 *
	 *					***********   SUJETO A CAMBIOS   ************
	 */

	private static JPanel buildStockPanel() {
		final JPanel PANEL_INVENTARIO = new JPanel(new BorderLayout());
		/*
		final FakeResultSet INVENTARIO = new FakeResultSet(new Object[][]{
			{"Calculadoras", 5},
			{"Guardapolvos", 7}
		});
		final TableModel MODELO = new AbstractTableModel() {
			public int getColumnCount() {
				// ESTO NO ESTA BIEN PLANTEADO
				return INVENTARIO[0].length;
			}

			public int getRowCount() {
				// ESTO TAMPOCO ESTA BIEN PLANTEADO
				return INVENTARIO.length;
			}

			public Object getValueAt(int fila, int columna) {
				//System.out.println("Fila " + fila + ", columna " + columna);
				if (columna >= INVENTARIO[0].length) {
					INVENTARIO.next();
				}

				return INVENTARIO.getString(columna);

				return ":3";
			}
		};
		final JTable PLANILLA_INVENTARIO = new JTable(MODELO);
		*/
		// Buscar manera de NO TENER QUE HACER ESTO
		final JTable TABLA = new JTable(MODELO_INVENTARIO);
		//final JScrollPane SCROLLPANE = new JScrollPane(TABLA);

		PANEL_INVENTARIO.add(TABLA.getTableHeader(), BorderLayout.NORTH);
		PANEL_INVENTARIO.add(TABLA, BorderLayout.CENTER);

		PANEL_INVENTARIO.setOpaque(true);
		PANEL_INVENTARIO.setBackground(Color.WHITE);

		return PANEL_INVENTARIO;
	}

	private static JPanel buildLoansPanel() {
		final JPanel PANEL_PRESTAMOS = new JPanel();

		PANEL_PRESTAMOS.add(new JLabel("Prestamos"));

		PANEL_PRESTAMOS.setOpaque(true);
		PANEL_PRESTAMOS.setBackground(Color.WHITE);

		return PANEL_PRESTAMOS;
	}

	private static void buildWindow() {
		final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		final int ALTO = screensize.height / 4 * 3;
		final int ANCHO = screensize.width / 4 * 3;
		//final GridBagConstraints GBAGC = new GridBagConstraints();
		//final JPanel PANEL_INFERIOR = buildLowerPanel();
		PANEL_INFERIOR.setLayout(CARD_LAYOUT);
		PANEL_INFERIOR.add(buildLoansPanel(), "Prestamos");
		PANEL_INFERIOR.add(buildStockPanel(), "Inventario");

		//FRAME.setLayout(new GridBagLayout());
		FRAME.setLayout(new BorderLayout());
		FRAME.add(buildButtonsPanel(), BorderLayout.NORTH);
		FRAME.add(PANEL_INFERIOR, BorderLayout.CENTER);
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setTitle("Prestamos CETEM");
		FRAME.setSize(ANCHO, ALTO);
		FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
		FRAME.setVisible(true);
	}

	private static void fetchData() {
		while (QUERY_INVENTARIO.next()) {
			MODELO_INVENTARIO.addRow(new Object[] {QUERY_INVENTARIO.getString(1), QUERY_INVENTARIO.getInt(2)});
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> buildWindow());
		SwingUtilities.invokeLater(() -> fetchData());
	}
}
