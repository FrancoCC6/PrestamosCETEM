import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test2 {
	private static void buildWindow() {
		final JFrame FRAME = new JFrame();

		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setTitle("Test UwU");
		FRAME.setSize(500, 500);
		FRAME.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> buildWindow());
	}
}
