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

public class GUI {
	private static class CustomTable extends JTable {
		public CustomTable(AbstractTableModel modelo) {
			super(modelo);

			getTableHeader().setReorderingAllowed(false);

			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// this parece no funcionar, asi que se usa una referencia
					CustomTable THIS = (CustomTable)(e.getSource());
					
					int fila = THIS.rowAtPoint(e.getPoint());
					int columna = THIS.columnAtPoint(e.getPoint());

					System.out.println(THIS.getValueAt(fila, columna));
				}
			});
		}
	}


	private static final JFrame FRAME = new JFrame();
	private static final CardLayout CARD_LAYOUT = new CardLayout();
	private static final JPanel 
		PANEL_INFERIOR = new JPanel(new GridBagLayout()),
		SUBPANEL_DATOS = new JPanel(),
		SUBPANEL_INF_PRINCIPAL = new JPanel(CARD_LAYOUT);
	private static final JButton BOTON_VOLVER = new JButton("Volver");

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
		final JMenu 
			MENU_VER = new JMenu("Ver"),
			MENU_BUSCAR = new JMenu("Buscar (N/I)");
		final JMenuItem
			MENUITEM_TODOS_PRESTAMOS = new JMenuItem("Todos los prestamos"),
			MENUITEM_INVENTARIO_FULL = new JMenuItem("Inventario completo"),
			MENUITEM_BUSCAR_ALUMNO = new JMenuItem("Alumno"),
			MENUITEM_BUSCAR_PRESTAMO_FECHA = new JMenuItem("Prestamos por fecha"),
			MENUITEM_BUSCAR_ELEMENTO = new JMenuItem("Elemento");

		MENUITEM_TODOS_PRESTAMOS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CARD_LAYOUT.show(SUBPANEL_INF_PRINCIPAL, "TodosPrestamos");
				BOTON_VOLVER.setVisible(true);
			}
		});

		MENUITEM_INVENTARIO_FULL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CARD_LAYOUT.show(SUBPANEL_INF_PRINCIPAL, "Inventario");
				BOTON_VOLVER.setVisible(true);
			}
		});

		MENU_VER.add(MENUITEM_TODOS_PRESTAMOS);
		MENU_VER.add(MENUITEM_INVENTARIO_FULL);

		MENU_BUSCAR.add(MENUITEM_BUSCAR_PRESTAMO_FECHA);
		MENU_BUSCAR.add(MENUITEM_BUSCAR_ALUMNO);
		MENU_BUSCAR.add(MENUITEM_BUSCAR_ELEMENTO);

		//MENU_BAR.setMargin(new Insets(20, 20, 20, 20));
		MENU_BAR.add(MENU_VER);
		MENU_BAR.add(MENU_BUSCAR);

		return MENU_BAR;
	}

	private static JPanel buildUpperPanel() {
		JLabel ICONO = new JLabel();
		ICONO.setIcon(new ImageIcon("res/miniatura_cetem_savio.png"));

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

		BOTON_VOLVER.setVisible(false);
		BOTON_VOLVER.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CARD_LAYOUT.show(SUBPANEL_INF_PRINCIPAL, "PrestamosActivos");
				BOTON_VOLVER.setVisible(false);
			}
		});

		BOTON_NUEVO_PRESTAMO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CARD_LAYOUT.show(SUBPANEL_INF_PRINCIPAL, "NuevoPrestamo");
				BOTON_VOLVER.setVisible(true);
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
		
		// Buscar manera de NO TENER QUE HACER ESTO
		final CustomTable TABLA = new CustomTable(DataHandler.MODELO_INVENTARIO);

		PANEL_INVENTARIO.add(TABLA.getTableHeader(), BorderLayout.NORTH);
		PANEL_INVENTARIO.add(TABLA, BorderLayout.CENTER);

		PANEL_INVENTARIO.setOpaque(true);
		PANEL_INVENTARIO.setBackground(Color.WHITE);

		return PANEL_INVENTARIO;
	}

	private static JPanel buildActiveLoansPanel() {
		final JPanel PANEL_PRESTAMOS = new JPanel(new BorderLayout());
		final CustomTable TABLA = new CustomTable(DataHandler.MODELO_PRESTAMOS);

		// PANEL_PRESTAMOS.add(new JLabel("Prestamos"));
		PANEL_PRESTAMOS.add(TABLA.getTableHeader(), BorderLayout.NORTH);
		PANEL_PRESTAMOS.add(TABLA, BorderLayout.CENTER);

		/*
		PANEL_PRESTAMOS.setOpaque(true);
		PANEL_PRESTAMOS.setBackground(Color.WHITE);
		*/

		return PANEL_PRESTAMOS;
	}

	private static JPanel buildNewLoanPanel() {
		final JPanel PANEL_NUEVO_PRESTAMO = new JPanel();

		PANEL_NUEVO_PRESTAMO.add(new JLabel("Nuevo prestamo, WIP"));

		return PANEL_NUEVO_PRESTAMO;
	}

	private static JPanel buildAllLoansPanel() {
		final JPanel PANEL_TODOS_PRESTAMOS = new JPanel();

		PANEL_TODOS_PRESTAMOS.add(new JLabel("Todos los prestamos, WIP"));

		return PANEL_TODOS_PRESTAMOS;
	}
	

	public static void buildWindow() {
		final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
		final int ALTO = SCREENSIZE.height / 4 * 3;
		final int ANCHO = SCREENSIZE.width / 4 * 3;
		final GridBagConstraints GBAGC = new GridBagConstraints();

		GBAGC.fill = GridBagConstraints.BOTH;
		GBAGC.weighty = 1.0;

		GBAGC.gridx = 0;
		GBAGC.gridy = 0;
		GBAGC.weightx = 1.0;
		PANEL_INFERIOR.add(SUBPANEL_INF_PRINCIPAL, GBAGC);

		GBAGC.gridx = 1;
		GBAGC.weightx = .5;
		PANEL_INFERIOR.add(SUBPANEL_DATOS, GBAGC);
		SUBPANEL_INF_PRINCIPAL.add(buildActiveLoansPanel(), "PrestamosActivos");
		SUBPANEL_INF_PRINCIPAL.add(buildStockPanel(), "Inventario");
		SUBPANEL_INF_PRINCIPAL.add(buildNewLoanPanel(), "NuevoPrestamo");
		SUBPANEL_INF_PRINCIPAL.add(buildAllLoansPanel(), "TodosPrestamos");

		SUBPANEL_DATOS.add(new JLabel("Haga clic en cualquier dato para mostrar mas informacion"));
		SUBPANEL_DATOS.setBackground(new Color(220, 230, 240));

		FRAME.setMinimumSize(new Dimension(ANCHO, ALTO));
		FRAME.setLayout(new GridBagLayout());
		//FRAME.setLayout(new BorderLayout());

		//GBAGC.gridwidth = ANCHO;
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
}
