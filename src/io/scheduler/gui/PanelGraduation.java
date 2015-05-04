package io.scheduler.gui;

import java.awt.Container;

import javax.swing.JLabel;

public class PanelGraduation extends CardPanel {

	public PanelGraduation(Container parent, String key) {
		super(parent, key);
	}

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = -7469090239906011796L;
	
	protected void initialize(){
		this.setBounds(12, 12, 424, 230);
		JLabel lblGradSummary = new JLabel("Grad Summary");
		this.add(lblGradSummary);
	}
	

}
