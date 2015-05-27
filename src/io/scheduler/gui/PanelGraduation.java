package io.scheduler.gui;

import io.scheduler.data.DegreeReq;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PanelGraduation extends CardPanel {
	
	@SuppressWarnings("unused")
	private DegreeReq degree;

	public PanelGraduation(Container parent, String key) {
		super(parent, key);
	}

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = -7469090239906011796L;

	@Override
	protected void initialize() {
		
	
		degree = null;
		degree = DegreeReq.get("trial");
		
		this.setBounds(12, 12, 424, 230);
		JLabel lblGradSummary = new JLabel("Grad Summary");
		this.add(lblGradSummary);

	}

	public void TabbedPane() {
		

		setName("Tabbed Pane");
		setSize(300, 300);
		JTabbedPane jtp = new JTabbedPane();
		getRootPane().add(jtp);

		JPanel jp1 = new JPanel();

		JPanel jp2 = new JPanel();

		JLabel label1 = new JLabel();
		label1.setText("Tab 1");
		jp1.add(label1);

		jtp.addTab("Tab1", jp1);
		jtp.addTab("Tab2", jp2);

		JButton test = new JButton("Add");
		jp2.add(test);

		ButtonHandler phandler = new ButtonHandler();
		test.addActionListener(phandler);
		setVisible(true);
	}

	class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "tbt", "continued",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		TabbedPane tab = new TabbedPane();
	}

}
