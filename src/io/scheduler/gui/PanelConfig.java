package io.scheduler.gui;

import java.awt.Container;

import javax.swing.JLabel;

/**
 * @author skarahoda
 *
 */
public class PanelConfig extends CustomPanel {

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = -8483298720242968924L;

	/**
	 * @param parent: container that has cardlayout 
	 * @param key: key for cardlayout
	 */
	public PanelConfig(Container parent, String key) {
		super(parent, key);
	}
	
	protected void initialize(){
		JLabel lblConfig = new JLabel("config");
		this.add(lblConfig);
		super.initialize();
	}

}
