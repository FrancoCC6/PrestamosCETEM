import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FramePrestamos2 {
	private static void buildFrame() {
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		JTextField
			input_nombre = new JTextField(20),
			input_apellido = new JTextField(20),
			input_id = new JTextField(20),
			input_carrera = new JTextField(20),
			input_telefono = new JTextField(20),
			input_elemento = new JTextField(20),
			input_cantidad = new JTextField(20);
		JButton 
			button_confirmar = new JButton(),
			button_cancelar = new JButton("Cancelar");

		button_confirmar.setText("<html><center>Registrar Alumno y<br>Confirmar Prestamo</center></html>");

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 5);

		gbc.gridx = 0;
		gbc.gridy = 0;
		frame.add(new JLabel("Nombre"), gbc);

		gbc.gridx = 1;
		frame.add(new JLabel("Apellido"), gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.add(input_nombre, gbc);

		gbc.gridx = 1;
		frame.add(input_apellido, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(new JLabel("DNI"), gbc);

		gbc.gridx = 1;
		frame.add(new JLabel("Telefono de contacto"), gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		frame.add(input_id, gbc);

		gbc.gridx = 1;
		frame.add(input_telefono, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		frame.add(new JLabel("Carrera"), gbc);

		gbc.gridy = 5;
		frame.add(input_carrera, gbc);

		gbc.insets.set(20, 5, 5, 5);

		gbc.gridy = 6;
		frame.add(new JLabel("Elemento"), gbc);

		gbc.gridx = 1;
		frame.add(new JLabel("Cantidad"), gbc);

		gbc.insets.set(5, 5, 0, 5);

		gbc.gridx = 0;
		gbc.gridy = 7;
		frame.add(input_elemento, gbc);

		gbc.gridx = 1;
		frame.add(input_cantidad, gbc);

		gbc.insets.set(20, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;

		gbc.gridx = 0;
		gbc.gridy = 8;
		frame.add(button_confirmar, gbc);

		gbc.gridx = 1;
		frame.add(button_cancelar, gbc);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Registrar alumno para nuevo prestamo");
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> buildFrame());
	}
}
