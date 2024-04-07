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
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TextUI;
import javax.swing.text.JTextComponent;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

// Simula objetos de SQLite

public class GUI {
	// TODO: Analizar aislar estas clases en un package
	// Update 7/4/24 00:52: No me acuerdo si eran las que ya pase a otros archivos o estas
	private static interface InputProcessor {
		public abstract boolean process(String query);
	}

	private static class CustomTable extends JTable {
		public CustomTable() {
			super();

			getTableHeader().setReorderingAllowed(false);

			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// this parece no funcionar, asi que se usa una referencia
					CustomTable THIS = (CustomTable)(e.getSource());
					
					int fila = THIS.rowAtPoint(e.getPoint());
					int columna = THIS.columnAtPoint(e.getPoint());

					if (fila >= 0 && columna >= 0) { // Mejorar condicion? Out of bounds da -1
						System.out.println(THIS.getValueAt(fila, columna));
					}
				}
			});
		}
	}

	// TODO: RECONSIDERAR, potencial BLOAT
	private static class CustomDateWidget extends JTextComponent {
		private final JTextField INPUTS[] = new JTextField[3];

		public CustomDateWidget() {
			final LocalDate HOY = LocalDate.now();
			setLayout(new FlowLayout(FlowLayout.LEADING));

			for (int i = 2; i >= 0; i--) {
				INPUTS[i] = new JTextField(5);
			}

			add(new JLabel("Dia:"));
			INPUTS[0].setText(HOY.getDayOfMonth() + "");
			add(INPUTS[0]);

			add(new JLabel("Mes:"));
			INPUTS[1].setText(HOY.getMonthValue() + "");
			add(INPUTS[1]);

			add(new JLabel("Año:"));
			INPUTS[2].setText(HOY.getYear() + "");
			add(INPUTS[2]);
		}

		@Override
		public String getText() {
			return INPUTS[0].getText() + "/" + INPUTS[1].getText() + "/" + INPUTS[2].getText();
		}

		@Override
		public void updateUI() {
			// Descomentar para probar, qcyo
			// super.updateUI();

			// WARNING: Esta implementacion vacia impide que tire un runtime exception,
			// pero implementar apropiadamente en el futuro.
			// O no, qcyo xdxdxd
		}

		public void addActionListener(ActionListener action_listener) {
			for (JTextField input : INPUTS) {
				input.addActionListener(action_listener);
			}
		}
	}

	private static final JFrame FRAME = new JFrame();
	//private static final CardLayout CARD_LAYOUT = new CardLayout();
	private static final JPanel 
		PANEL_INFERIOR = new JPanel(new GridBagLayout()),
		SUBPANEL_DATOS = new JPanel(),
		SUBPANEL_INF_PRINCIPAL = new JPanel(new BorderLayout());
	private static final JButton BOTON_VOLVER = new JButton("Volver");
	private static final CustomTable TABLA = new CustomTable();

	private static final boolean doQueryGetResponse(DataHandler.Query query_type, String query_hint) {
		DefaultTableModel mod_nuevo = DataHandler.getTableModelFromQuery(query_type, query_hint);

		if (mod_nuevo == null) {
			return false;
		}

		TABLA.setModel(mod_nuevo);
		BOTON_VOLVER.setVisible(true);
		return true;
	}

	private static final void displayInputFrame(
			String message,
		   	String button_message, 
			JTextComponent input_component,
			InputProcessor validator,
			InputProcessor query_executor
	) {
		JFrame frame = new JFrame();
		//JTextField input_box = new JTextField(15);
		JLabel 
			message_label = new JLabel(message),
			errmsg_label = new JLabel("Entrada incorrecta");
		JButton accept_button = new JButton(button_message);
		GridBagConstraints gbagc = new GridBagConstraints();

		errmsg_label.setForeground(Color.RED);
		errmsg_label.setVisible(false);

		ActionListener default_action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validator == null) {
					frame.dispose();
					return;
				}

				if (!validator.process(input_component.getText())) {
					errmsg_label.setVisible(true);
					return;
				}

				frame.dispose();

				if (!query_executor.process(input_component.getText())) {
					// Mensaje de query no exitosa
					JFrame msg_frame = new JFrame();
					msg_frame.setLayout(new FlowLayout(FlowLayout.CENTER));
					msg_frame.add(new JLabel("No se encontraron resultados"));
					msg_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					msg_frame.setSize(new Dimension(350, 100));
					msg_frame.setResizable(false);
					msg_frame.setLocationRelativeTo(FRAME);
					msg_frame.setVisible(true);
				}
			}
		};
		
		frame.setLayout(new GridBagLayout());
		gbagc.gridx = 0;
		gbagc.weighty = 1;

		accept_button.addActionListener(default_action);
		// Yolo
		try {
			((JTextField)input_component).addActionListener(default_action);
		}
		catch (ClassCastException e) {
			try {
				((CustomDateWidget)input_component).addActionListener(default_action);
			}
			catch (Exception ex) {System.err.println(ex);}
		}
		
		gbagc.gridy = 0;
		frame.add(message_label, gbagc);

		gbagc.gridy = 1;
		frame.add(input_component, gbagc);

		gbagc.gridy = 2;
		frame.add(errmsg_label, gbagc);

		gbagc.gridy = 3;
		frame.add(accept_button, gbagc);

		frame.setMinimumSize(new Dimension(400, 150));
		frame.setLocationRelativeTo(FRAME);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}	

	private static JMenuBar buildMenuBar() {
		final JMenuBar MENU_BAR = new JMenuBar();
		final JMenu 
			MENU_VER = new JMenu("Ver"),
			MENU_BUSCAR = new JMenu("Buscar prestamos por...");
		final JMenuItem
			MENUITEM_TODOS_PRESTAMOS = new JMenuItem("Todos los prestamos"),
			MENUITEM_INVENTARIO_FULL = new JMenuItem("Inventario completo"),
			MENUITEM_BUSCAR_ALUMNO = new JMenuItem("Alumno"),
			MENUITEM_BUSCAR_PRESTAMO_FECHA = new JMenuItem("Fecha"),
			MENUITEM_BUSCAR_ELEMENTO = new JMenuItem("Elemento");

		// TODO: Unificar este ActionListener con el siguiente
		MENUITEM_TODOS_PRESTAMOS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TABLA.setModel(DataHandler.getTableModelFromQuery(DataHandler.Query.PRESTAMOS_TODOS));
				BOTON_VOLVER.setVisible(true);
			}
		});

		MENUITEM_INVENTARIO_FULL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Cambiar por doQueryGetResponse?
				TABLA.setModel(DataHandler.getTableModelFromQuery(DataHandler.Query.INVENTARIO));
				BOTON_VOLVER.setVisible(true);
			}
		});

		MENUITEM_BUSCAR_ALUMNO.addActionListener(e -> displayInputFrame(
			"Ingrese nombre y/o apellido, o legajo",
			"Buscar",
			new JTextField(15),
			input -> 
				(	input.matches("\\d{5}")) // Legajo
				||	input.matches("[A-Za-z]+( [A-Za-z]+)*"), // Nombre y/o apellido
			q_hint -> doQueryGetResponse(DataHandler.Query.PRESTAMOS_ALUMNO, q_hint)
		));

		// TODO: Cambiar por combobox
		MENUITEM_BUSCAR_ELEMENTO.addActionListener(e -> displayInputFrame(
			"Ingrese denominacion del elemento (ej.: Calculadora)",
			"Buscar",
			new JTextField(15),
			input -> input.matches("[A-Za-z0-9]+( [A-Za-z0-9]+)*"), // Ej.: Calculadora, Zapatos 42, ...
			q_hint -> doQueryGetResponse(DataHandler.Query.PRESTAMOS_ELEMENTO, q_hint)
		));

		MENUITEM_BUSCAR_PRESTAMO_FECHA.addActionListener(e -> displayInputFrame(
			"Ingrese una fecha valida",
			"Buscar",
			new CustomDateWidget(),
			input -> {
				if (!input.matches("[0-3]?[0-9]/[01]?[0-9]/20[0-9]{2}")) {
					return false;
				}

				String[] parsed_raw_date = input.split("/");
				int 
					days_of_month[] = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
					parsed_date[] = new int[3];

				for (int i = 2; i >= 0; i--) {
					parsed_date[i] = Integer.parseInt(parsed_raw_date[i]);
				}

				// Para los años bisiestos
				if (Math.abs(parsed_date[2] - 2012) % 4 == 0) {
					days_of_month[1]++;
				}

				return
					parsed_date[0] > 0 
				&&	parsed_date[1] > 0
				&&	parsed_date[1] <= 12
				&&	parsed_date[0] <= days_of_month[parsed_date[1] - 1];
			},
			q_hint -> doQueryGetResponse(DataHandler.Query.PRESTAMOS_FECHA, q_hint)
		));

		MENU_VER.add(MENUITEM_TODOS_PRESTAMOS);
		MENU_VER.add(MENUITEM_INVENTARIO_FULL);

		MENU_BUSCAR.add(MENUITEM_BUSCAR_PRESTAMO_FECHA);
		MENU_BUSCAR.add(MENUITEM_BUSCAR_ALUMNO);
		MENU_BUSCAR.add(MENUITEM_BUSCAR_ELEMENTO);

		MENU_BAR.add(MENU_VER);
		MENU_BAR.add(MENU_BUSCAR);

		return MENU_BAR;
	}

	private static JPanel buildUpperPanel() {
		JLabel ICONO = new JLabel();
		ICONO.setIcon(new ImageIcon("res/miniatura_cetem_savio.png"));

		final JButton BOTON_NUEVO_PRESTAMO = new JButton("Nuevo Prestamo");

		final JPanel PANEL_SUPERIOR = new JPanel(new BorderLayout()) {
			// Straight up STOLEN code
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
				TABLA.setModel(DataHandler.getTableModelFromQuery(DataHandler.Query.PRESTAMOS_PENDIENTES));
				BOTON_VOLVER.setVisible(false);
			}
		});

		BOTON_NUEVO_PRESTAMO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//CARD_LAYOUT.show(SUBPANEL_INF_PRINCIPAL, "NuevoPrestamo");
				//BOTON_VOLVER.setVisible(true);
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

	/*
	// TODO: Completar
	// Update 6/4/24 20:57: Capaz no hago un panel y uso un frame considerando la refactorizacion de usar una sola tabla
	private static JPanel buildNewLoanPanel() {
		final JPanel PANEL_NUEVO_PRESTAMO = new JPanel();

		PANEL_NUEVO_PRESTAMO.add(new JLabel("Nuevo prestamo, WIP"));

		return PANEL_NUEVO_PRESTAMO;
	}
	*/

	public static void buildWindow() {
		// TODO: Pensar si esto realmente deberia estar aca
		TABLA.setModel(DataHandler.getTableModelFromQuery(DataHandler.Query.PRESTAMOS_PENDIENTES));

		final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
		final int ALTO = SCREENSIZE.height / 4 * 3;
		final int ANCHO = SCREENSIZE.width / 4 * 3;
		final GridBagConstraints GBAGC = new GridBagConstraints();

		GBAGC.fill = GridBagConstraints.BOTH;
		GBAGC.weighty = 1.0;

		GBAGC.gridx = 0;
		GBAGC.gridy = 0;
		GBAGC.weightx = 1.0;
		// TODO: Buscarle la vuelta para no armar el panel inferior en esta funcion
		PANEL_INFERIOR.add(SUBPANEL_INF_PRINCIPAL, GBAGC);

		GBAGC.gridx = 1;
		GBAGC.weightx = .5;
		PANEL_INFERIOR.add(SUBPANEL_DATOS, GBAGC);
		SUBPANEL_INF_PRINCIPAL.add(TABLA.getTableHeader(), BorderLayout.NORTH);
		SUBPANEL_INF_PRINCIPAL.add(TABLA, BorderLayout.CENTER);

		SUBPANEL_DATOS.add(new JLabel("Haga clic en cualquier dato para mostrar mas informacion"));
		SUBPANEL_DATOS.setBackground(new Color(220, 230, 240));

		FRAME.setMinimumSize(new Dimension(ANCHO, ALTO));
		FRAME.setLayout(new GridBagLayout());

		GBAGC.weightx = 1.0;

		GBAGC.weighty = .01;
		GBAGC.gridx = 0;
		GBAGC.gridy = 0;
		FRAME.add(buildMenuBar(), GBAGC);
		
		GBAGC.gridy = 1;
		FRAME.add(buildUpperPanel(), GBAGC);

		GBAGC.weighty = 1.0;
		GBAGC.gridy = 2;
		FRAME.add(PANEL_INFERIOR, GBAGC);

		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setTitle("Prestamos CETEM");
		FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
		FRAME.setVisible(true);
	}
}
