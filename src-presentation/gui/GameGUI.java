package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameGUI {
	private JFrame frame;
	private JPanel panelTop;
	private JPanel panelMiddle;
	private JPanel panelBottom;

	private LogoUI logoUI;
	private OutbreakUI outbreakUI;
	private DiseaseUI diseaseUI;
	private EventUI eventUI;
	private DeckUI deckUI;
	private ActionUI actionUI;
	private BoardUI boardUI;

	public GameGUI(LogoUI logoUI, OutbreakUI outbreakUI, DiseaseUI diseaseUI, EventUI eventUI, DeckUI deckUI,
			ActionUI actionUI, BoardUI boardUI) {
		super();
		this.logoUI = logoUI;
		this.outbreakUI = outbreakUI;
		this.diseaseUI = diseaseUI;
		this.eventUI = eventUI;
		this.deckUI = deckUI;
		this.actionUI = actionUI;
		this.boardUI = boardUI;

		loadFrame();
		loadTopPanel();
		loadMiddlePanel();
		loadBottomPanel();

		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void showGUI() {
		frame.setVisible(true);
	}

	private void loadFrame() {
		frame = new JFrame();
		frame.setTitle("PANDEMIC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
	}

	private void loadTopPanel() {
		panelTop = new JPanel();
		panelTop.setPreferredSize(new Dimension(1000, 120));
		panelTop.setLayout(new BorderLayout());

		logoUI.setPreferredSize(new Dimension(200, 120));
		outbreakUI.setPreferredSize(new Dimension(500, 50));
		JPanel mid = new JPanel();
		mid.setLayout(new BorderLayout());
		mid.add(outbreakUI, BorderLayout.NORTH);
		mid.add(diseaseUI, BorderLayout.CENTER);
		eventUI = new EventUI();
		eventUI.setPreferredSize(new Dimension(300, 120));

		panelTop.add(logoUI, BorderLayout.WEST);
		panelTop.add(eventUI, BorderLayout.EAST);
		panelTop.add(mid, BorderLayout.CENTER);

		frame.add(panelTop, BorderLayout.NORTH);

	}

	private void loadBottomPanel() {
		panelBottom = new JPanel();
		panelBottom.setPreferredSize(new Dimension(1000, 120));
		panelBottom.setLayout(new BorderLayout());

		deckUI.setPreferredSize(new Dimension(330, 120));

		panelBottom.add(deckUI, BorderLayout.WEST);
		panelBottom.add(actionUI, BorderLayout.CENTER);
		frame.add(panelBottom, BorderLayout.SOUTH);

	}

	private void loadMiddlePanel() {
		panelMiddle = new JPanel();
		panelMiddle.setPreferredSize(new Dimension(1000, 700));
		panelMiddle.setLayout(new BorderLayout());

		panelMiddle.add(boardUI);
		frame.add(panelMiddle, BorderLayout.CENTER);

	}
}
