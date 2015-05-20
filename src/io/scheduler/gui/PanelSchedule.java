package io.scheduler.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JList;
import javax.swing.JTabbedPane;

public class PanelSchedule extends CardPanel {

	public PanelSchedule(Container parent, String key) {
		super(parent, key);
	}

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = 8525163294114489034L;

	@Override
	protected void initialize() {
		//this.setBounds(12, 12, 424, 230);
		
		this.setLayout(new BorderLayout());
		
		FormPanel frm = new FormPanel();
		
		this.add(frm,BorderLayout.LINE_END);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		TablePanel scheduleTable = new TablePanel();
		tabbedPane.addTab("Schedule", scheduleTable);
		this.add(tabbedPane,BorderLayout.WEST);
		
		JList<?> list = new JList<Object>();
		this.add(list);
	}

}
