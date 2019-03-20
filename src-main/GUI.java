import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI {
	JFrame frame;
	JPanel buttonPanel;

	GUI() {
		frame = new JFrame();
		frame.setSize(3000, 3000);
		frame.show();
	}

	private void setPanels(JLabel label) {
		JPanel mainPanel = new JPanel();
		mainPanel.add(label);
		//frame.add(label, BorderLayout.EAST);
		mainPanel.add(buttonPanel);
		frame.add(mainPanel, BorderLayout.EAST);
	}

	public void addPanel(JPanel panel) {
		frame.add(panel);
	}

	public void removePanel(JPanel panel) {
		panel.setVisible(false);
		frame.remove(panel);
	}

	public void loadInitialGame() {
		// TODO Auto-generated method stub
		loadBoardImage();

	}

	private void loadBoardImage() {
		try {
			BufferedImage img = ImageIO.read(new File("Main Picture.png"));
			ImageIcon icon = new ImageIcon(img);
			JLabel label = new JLabel();
			label.setIcon(icon);
			setPanels(label);

		} catch (IOException e) {
			System.out.println("File not found");
		}

	}
	
	
	
	
	public void setButtonPanel(JPanel buttonPanel){
		this.buttonPanel = buttonPanel;
	}

}
