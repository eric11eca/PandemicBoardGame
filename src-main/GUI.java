import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	JFrame frame;
	public static void main(String[] args){
		GUI gui = new GUI();
	}
	
	GUI(){
	frame = new JFrame();
	frame.setSize(3000, 3000);
	frame.show();
	}
	
	public void addPanel(JPanel panel){
		frame.add(panel);
	}
	
	public void removePanel(JPanel panel){
		panel.setVisible(false);
		frame.remove(panel);
	}
}
