package io.scheduler.gui;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JPanel;

/**
 * @author skarahoda
 *
 */
public abstract class CardPanel extends JPanel {

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = -7303738305156470194L;
	private Container parent;
	private String key;
	private boolean isInitialized; 
	
	/**
	 * @param parent: container that has cardlayout 
	 * @param key: key for cardlayout
	 */
	public CardPanel(Container parent, String key) {
		super();
		this.isInitialized = false;
		this.parent = parent;
		this.key = key;
	}
	
	public void setVisible(){
		if(!this.isInitialized){
			this.initialize();
		}
		CardLayout layout = (CardLayout) this.parent.getLayout();
		layout.show(this.parent, this.key);
	}

	protected void initialize(){
		this.parent.add(this, this.key);
		this.isInitialized = true;
	}
}
