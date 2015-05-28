package io.scheduler.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTabbedPane;

public class CustomTabbedPane extends JTabbedPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 668587681735470359L;
	private Factory factory;

	/**
	 * @param factory
	 */
	public CustomTabbedPane(int location, Factory factory) {
		super(location);
		this.factory = factory;
		initMouseListeners();
		fill();
	}

	private void initMouseListeners() {

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int lastTab = getTabCount() - 1;
				if (getSelectedIndex() == lastTab) {
					Object object = factory.generateObject();
					if (object != null) {
						remove(lastTab);
						Component component = factory.generateComponent(object);
						addTab(object.toString(), component);
						addTab("+", null, null, "Add new schedule");
						setSelectedIndex(lastTab);
					}
				} else {

					if (e.getButton() == 2) {
						Object o = ((CustomComponent) getSelectedComponent())
								.getObject();
						factory.removeFromDB(o);
						remove(getSelectedIndex());
					}
				}

			}
		});

	}

	public void fill() {
		List<?> objects = factory.get();
		for (Object object : objects) {
			addTab(object.toString(), factory.generateComponent(object));
		}
		addTab("+", null, null, "Add new schedule");
	}

}
