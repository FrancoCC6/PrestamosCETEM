import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> GUI.buildWindow());
		SwingUtilities.invokeLater(() -> DataHandler.fetchData());
	}
}
