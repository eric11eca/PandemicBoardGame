import javax.swing.JButton;
import javax.swing.JPanel;

public class Pandemic {
	static int players = 0;
	static int epidemicNumber = 0;
	private static GUI gui;
	public static void main(String[] args) {
		InitializeGame();
    }

	private static void InitializeGame() {
		//Build all the objects
		//Call the GUI to select the number of players
		JPanel pnl = new JPanel();
		JButton btn2p = new JButton("2 players");
		JButton btn3p = new JButton("3 players");
		JButton btn4p = new JButton("4 players");
		pnl.add(btn2p);
		pnl.add(btn3p);
		pnl.add(btn4p);
		PlayerListener action1 = new PlayerListener(2, pnl);
		PlayerListener action2 = new PlayerListener(3, pnl);
		PlayerListener action3 = new PlayerListener(4, pnl);
		btn2p.addActionListener(action1);
		btn3p.addActionListener(action2);
		btn4p.addActionListener(action3);
		gui = new GUI();
		gui.addPanel(pnl);
		
		
		
	}
	
	public static void SetPlayers(int playernum, JPanel panelToClose){
		players = playernum;
		gui.removePanel(panelToClose);
		Difficulty();
	}

	private static void Difficulty() {
		JPanel pnl = new JPanel();
		JButton btn2p = new JButton("Easy");
		JButton btn3p = new JButton("Normal");
		JButton btn4p = new JButton("Hard");
		pnl.add(btn2p);
		pnl.add(btn3p);
		pnl.add(btn4p);
		DListener action1 = new DListener(4, pnl);
		DListener action2 = new DListener(5, pnl);
		DListener action3 = new DListener(6, pnl);
		btn2p.addActionListener(action1);
		btn3p.addActionListener(action2);
		btn4p.addActionListener(action3);
		gui.addPanel(pnl);
		
	}

	public static void SetDifficulty(int epidemics, JPanel panelToClose) {
		epidemicNumber = epidemics;
		gui.removePanel(panelToClose);
		StartGame();
		
	}

	private static void StartGame() {
		// TODO Auto-generated method stub
		
	}
	
		

}
