import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrameConfirmacion {
	private static void buildFrame(
			String nombre_completo,
			String carrera,
			String telefono,
			String dni,
			String elemento,
			String cantidad,
			int prestamos_pendientes
	) {
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		JButton 
			button_confirmar = new JButton("Confirmar Prestamo"),
			button_cancelar = new JButton("Cancelar");
		JLabel label_pendientes;

		GridBagConstraints gbc = new GridBagConstraints();
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(20, 20, 0, 20);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		frame.add(new JLabel("Nombre y apellido;", SwingConstants.LEFT), gbc);

		gbc.gridx = 1;
		frame.add(new JLabel(nombre_completo, SwingConstants.LEFT), gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.add(new JLabel("Carrera:", SwingConstants.LEFT), gbc);

		gbc.gridx = 1;
		frame.add(new JLabel(carrera, SwingConstants.LEFT), gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(new JLabel("Telefono de contacto:", SwingConstants.LEFT), gbc);

		gbc.gridx = 1;
		frame.add(new JLabel(telefono, SwingConstants.LEFT), gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		frame.add(new JLabel("DNI:", SwingConstants.LEFT), gbc);

		gbc.gridx = 1;
		frame.add(new JLabel(dni, SwingConstants.LEFT), gbc);

		gbc.gridwidth = 2;
		gbc.insets.set(20, 20, 20, 20);
		gbc.gridx = 0;
		gbc.gridy = 4;
		if (prestamos_pendientes == 0) {
			label_pendientes = new JLabel("NO HAY PRESTAMOS PENDIENTES DE DEVOLUCION", SwingConstants.LEFT);
			label_pendientes.setForeground(Color.GREEN);
		}
		else {
			label_pendientes = new JLabel(prestamos_pendientes + " PRESTAMOS PENDIENTES DE DEVOLUCION", SwingConstants.LEFT);
			label_pendientes.setForeground(Color.RED);
		}
		frame.add(label_pendientes, gbc);

		gbc.insets.set(20, 20, 0, 20);
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 0;
		frame.add(new JLabel("Elemento:", SwingConstants.LEFT), gbc);

		gbc.gridx = 3;
		frame.add(new JLabel(elemento, SwingConstants.LEFT), gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		frame.add(new JLabel("Cantidad:", SwingConstants.LEFT), gbc);

		gbc.gridx = 3;
		frame.add(new JLabel(cantidad, SwingConstants.LEFT), gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 4;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets.set(20, 20, 10, 20);

		frame.add(new JPanel(new GridBagLayout()) {
			@Override
			public void paintComponent(Graphics g) {
				// Ya que no puedo editar el constructor
				// Hago inicializaciones aca xd
				// Cry about it
				super.paintComponent(g);

				if (getComponentCount() == 0) {
					GridBagConstraints gbc_ = new GridBagConstraints(
						0, 0, 1, 1, // gridx, gridy, gridwidth, gridheight
						1.0, 1.0, // weightx, weighty
						GridBagConstraints.NORTH, // anchor
						GridBagConstraints.BOTH, // fill
						new Insets(0, 0, 10, 0), // insets
						0, 0 // ipadx, ipady
					);

					add(button_confirmar, gbc_);

					gbc_.gridy = 1;
					add(button_cancelar, gbc_);
				}
			}
		}, gbc);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Confirmar prestamo");
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> buildFrame(
			"Juan Perez",
			"Sistemas",
			"2622123456",
			"42123456",
			"Casco",
			"2",
			2
		));
	}
}
