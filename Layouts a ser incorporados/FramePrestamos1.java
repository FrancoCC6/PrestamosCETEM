import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FramePrestamos1 {
	//private static ActionListener validate_and_
	private static void buildFrame() {
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		JTextField
			input_identifier = new JTextField(20),
			input_element = new JTextField(15),
			input_quantity = new JTextField(5);
		JButton button_aceptar = new JButton("Aceptar");

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridwidth = 2;

		gbc.gridx = 0;
		gbc.gridy = 0;

		frame.add(new JLabel("Nombre y Apellido/DNI"), gbc);

		gbc.gridy = 1;
		frame.add(input_identifier, gbc);

		gbc.gridwidth = 1;

		gbc.gridy = 2;
		frame.add(new JLabel("Elemento"), gbc);

		gbc.gridy = 3;
		frame.add(input_element, gbc);

		gbc.gridy = 2;
		gbc.gridx = 1;
		frame.add(new JLabel("Cdad."), gbc);

		gbc.gridy = 3;
		frame.add(input_quantity, gbc);

		gbc.weighty = .5;
		gbc.gridwidth = 2;

		gbc.fill = GridBagConstraints.NONE;
		gbc.insets.set(15, 5, 5, 5);
		gbc.gridy = 4;
		gbc.gridx = 0;
		frame.add(button_aceptar, gbc);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Nuevo Prestamo");
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> buildFrame());
	}
}
