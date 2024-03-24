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

public class Test7 {
	// NO SE AJUSTA AL MODELO DE DATOS PROPIAMENTE DICHO, REFLEJA LOS RESULTADOS DEL QUERY
	private static final FakeResultSet QUERY_INVENTARIO = new FakeResultSet(new Object[][] {
		{"Calculadora", 4},
		{"Enchufe Zapatilla", 1},
		{"Proteccion Visual", 20},
		{"Proteccion Auditiva", 20},
		{"Casco", 15},
		{"Guardapolvo", 2}
	});

	private static final FakeResultSet QUERY_PRESTAMOS = new FakeResultSet(new Object[][] {
		{"Sister", "Friede", "Calculadora", 1, "Mecha"},
		{"Demon", "in Pain", "Guardapolvo", 3, "Pablo"},
		{"Demon", "from Below", "Casco", 2, "Any"},
		{"Demon", "Prince", "Proteccion Auditiva", 4, "Fran"},
		{"Knight", "Artorias", "Enchufe Zapatilla", 1, "Pame"},
		{"Gwyn", "Lord of Cinder", "Proteccion Visual", 2, "Mecha"},
		{"Malenia", "Blade of Miquella", "Calculadora", 1, "Laure"}
	});

	private static final DefaultTableModel MODELO_INVENTARIO = new DefaultTableModel(new Object[]{
		"Elemento", 
		"Cantidad"
	}, 0);

	private static final DefaultTableModel MODELO_PRESTAMOS = new DefaultTableModel(new Object[]{
		"Nombre",
		"Apellido",
		"Elemento",
		"Cantidad",
		"Presta"
	}, 0);

	private static final JFrame FRAME = new JFrame();
	private static final CardLayout CARD_LAYOUT = new CardLayout();
	private static final JPanel PANEL_INFERIOR = new JPanel(CARD_LAYOUT);
	private static final JButton BOTON_VOLVER = new JButton("Volver");
	//private static final Container PANELINF_CONTENTPANE = PANEL_INFERIOR.getContentPane();

	/*
	private static final ActionListener SHOW_THIS_TAB = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			CARD_LAYOUT.show(PANEL_INFERIOR, ((JButton)e.getSource()).getText());
		}
	};
	*/

	private static final ActionListener NEW_LOAN_WINDOW = new ActionListener() {
		@Override
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
	};

	private static JMenuBar buildMenuBar() {
		final JMenuBar MENU_BAR = new JMenuBar();
		final JMenu MENU_VER = new JMenu("Ver");
		final JMenuItem
			MENUITEM_TODOS_PRESTAMOS = new JMenuItem("Todos los prestamos"),
			MENUITEM_INVENTARIO_FULL = new JMenuItem("Inventario completo");

		MENUITEM_INVENTARIO_FULL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CARD_LAYOUT.show(PANEL_INFERIOR, "Inventario");
				BOTON_VOLVER.setVisible(true);
			}
		});

		MENU_VER.add(MENUITEM_TODOS_PRESTAMOS);
		MENU_VER.add(MENUITEM_INVENTARIO_FULL);

		MENU_BAR.setMargin(new Insets(20, 20, 20, 20));
		MENU_BAR.add(MENU_VER);

		return MENU_BAR;
	}

	private static JPanel buildUpperPanel() {
		JLabel ICONO = new JLabel();
		ICONO.setIcon(new ImageIcon("miniatura_cetem_savio.png"));

		/*
		final String[] TAGS = {
			"Nuevo Prestamo",
			"Prestamos",
			"Inventario",
			"Boton 4"
		};

		final JButton[] BOTONES = new JButton[TAGS.length];
		for (int i = 0; i < TAGS.length; i++) {
			BOTONES[i] = new JButton(TAGS[i]);
		}
		BOTONES[0].addActionListener(NEW_LOAN_WINDOW);
		BOTONES[1].addActionListener(SHOW_THIS_TAB);
		BOTONES[2].addActionListener(SHOW_THIS_TAB);

		/*
		BOTON_1.setBackground(Color.CYAN);
		BOTON_2.setBackground(Color.YELLOW);
		BOTON_3.setBackground(Color.RED);
		BOTON_4.setBackground(Color.GREEN);
		*/
		final JButton BOTON_NUEVO_PRESTAMO = new JButton("Nuevo Prestamo");

		final JPanel PANEL_SUPERIOR = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				int w = getWidth(), h = getHeight();
				Color color1 = new Color(49, 150, 222, 0);
				Color color2 = new Color(86, 183, 252);
				GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		},
			  PANEL_BOTONES = new JPanel(new FlowLayout(FlowLayout.LEADING));
		PANEL_BOTONES.setOpaque(false);

		/*
		for (JButton btn : BOTONES) {
			PANEL_BOTONES.add(btn);
		}
		*/
		BOTON_VOLVER.setVisible(false);
		BOTON_VOLVER.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CARD_LAYOUT.show(PANEL_INFERIOR, "PrestamosActivos");
				BOTON_VOLVER.setVisible(false);
			}
		});

		PANEL_BOTONES.add(BOTON_NUEVO_PRESTAMO);
		PANEL_BOTONES.add(BOTON_VOLVER);

		PANEL_SUPERIOR.add(PANEL_BOTONES, BorderLayout.WEST);
		PANEL_SUPERIOR.add(ICONO, BorderLayout.EAST);
		return PANEL_SUPERIOR;
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

	private static JPanel buildActiveLoansPanel() {
		final JPanel PANEL_PRESTAMOS = new JPanel(new BorderLayout());
		final JTable TABLA = new JTable(MODELO_PRESTAMOS);

		// PANEL_PRESTAMOS.add(new JLabel("Prestamos"));
		PANEL_PRESTAMOS.add(TABLA.getTableHeader(), BorderLayout.NORTH);
		PANEL_PRESTAMOS.add(TABLA, BorderLayout.CENTER);

		/*
		PANEL_PRESTAMOS.setOpaque(true);
		PANEL_PRESTAMOS.setBackground(Color.WHITE);
		*/

		return PANEL_PRESTAMOS;
	}

	private static void buildWindow() {
		final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
		final int ALTO = SCREENSIZE.height / 4 * 3;
		final int ANCHO = SCREENSIZE.width / 4 * 3;
		final GridBagConstraints GBAGC = new GridBagConstraints();
		//final JPanel PANEL_INFERIOR = buildLowerPanel();
		PANEL_INFERIOR.add(buildActiveLoansPanel(), "PrestamosActivos");
		PANEL_INFERIOR.add(buildStockPanel(), "Inventario");

		FRAME.setMinimumSize(new Dimension(ANCHO, ALTO));
		FRAME.setLayout(new GridBagLayout());
		//FRAME.setLayout(new BorderLayout());

		//GBAGC.gridwidth = ANCHO;
		GBAGC.fill = GridBagConstraints.BOTH;
		GBAGC.weightx = 1.0;
		//GBAGC.gridheight = ALTO;

		GBAGC.weighty = .01;
		GBAGC.gridx = 0;
		GBAGC.gridy = 0;
		FRAME.add(buildMenuBar(), GBAGC);
		//FRAME.add(buildMenuBar(), BorderLayout.NORTH);
		
		GBAGC.weighty = .01;
		GBAGC.gridx = 0;
		GBAGC.gridy = 1;
		FRAME.add(buildUpperPanel(), GBAGC);
		//FRAME.add(buildUpperPanel(), BorderLayout.NORTH);
		
		//FRAME.add(PANEL_INFERIOR, BorderLayout.CENTER);
		GBAGC.weighty = 1.0;
		GBAGC.gridx = 0;
		GBAGC.gridy = 2;
		FRAME.add(PANEL_INFERIOR, GBAGC);

		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setTitle("Prestamos CETEM");
		FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
		FRAME.setVisible(true);
	}

	private static void fetchData() {
		while (QUERY_PRESTAMOS.next()) {
			MODELO_PRESTAMOS.addRow(new Object[] {
				QUERY_PRESTAMOS.getString(1), 
				QUERY_PRESTAMOS.getString(2), 
				QUERY_PRESTAMOS.getString(3), 
				QUERY_PRESTAMOS.getString(4), 
				QUERY_PRESTAMOS.getString(5) 
			});
		}

		while (QUERY_INVENTARIO.next()) {
			MODELO_INVENTARIO.addRow(new Object[] {QUERY_INVENTARIO.getString(1), QUERY_INVENTARIO.getInt(2)});
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> buildWindow());
		SwingUtilities.invokeLater(() -> fetchData());
	}
}
