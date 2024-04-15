import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrameObservaciones1 {
	private static void buildFrame() {
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		JTextArea input_observacion = new JTextArea(5, 20);
		JButton 
			button_aceptar = new JButton("OK"),
			button_cancelar = new JButton("No");

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridwidth = 2;

		gbc.gridx = 0;
		gbc.gridy = 0;

		frame.add(new JLabel("Observacion:"), gbc);

		gbc.gridy = 1;
		frame.add(input_observacion, gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 1;

		gbc.gridy = 2;
		frame.add(button_aceptar, gbc);

		gbc.gridx = 1;
		frame.add(button_cancelar, gbc);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Agregar observacion?");
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> buildFrame());
	}
}
