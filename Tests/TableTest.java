import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class TableTest {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			DefaultTableModel modelos[] = {
				new DefaultTableModel(
					new Object[] {
						"Columna 1",
						"Columna 2",
						"Columna 3"
					},
					0
				),
				new DefaultTableModel(
					new Object[] {
						"Columna 3",
						"Columna 4"
					},
					0
				)
			};

			modelos[0].addRow(new Object[] {"1", "2", "3"});
			modelos[0].addRow(new Object[] {"4", "5", "6"});
			modelos[0].addRow(new Object[] {"7", "8", "9"});

			modelos[1].addRow(new Object[] {"10", "11", "12"});
			modelos[1].addRow(new Object[] {"13", "14", "15"});
			
			int current_model = 0;
			JTable table = new JTable(modelos[current_model]);

			JButton boton_cambio = new JButton("Cambiar");
			boton_cambio.addActionListener(e -> {
				table.setModel(modelos[1]);
			});

			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout());

			frame.add(boton_cambio, BorderLayout.SOUTH);
			frame.add(table.getTableHeader(), BorderLayout.NORTH);
			frame.add(table, BorderLayout.CENTER);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(new Dimension(500, 500));
			frame.setResizable(false);
			frame.setVisible(true);
		});
	}
}
