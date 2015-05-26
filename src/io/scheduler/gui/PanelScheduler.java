package io.scheduler.gui;

import io.scheduler.data.SUClass;
import io.scheduler.data.Schedule;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class PanelScheduler extends CardPanel {
	private Schedule schedule;
	private PanelTimeTable timeTable;

	public PanelScheduler(Container parent, String key) {
		super(parent, key);
	}

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = 8525163294114489034L;

	@Override
	protected void initialize() {
		schedule = null;
		try {
			schedule = Schedule.get("deneme");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JTabbedPane tabbedPaneSchedule = new JTabbedPane(SwingConstants.TOP);
		tabbedPaneSchedule.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		tabbedPaneSchedule.addTab("+", null, null, "Add new schedule");
		timeTable = new PanelTimeTable();
		timeTable.fillTable(schedule);
		tabbedPaneSchedule.addTab(schedule.getName(), timeTable);
		add(tabbedPaneSchedule);

		JPanel panelButtons = new JPanel();
		panelButtons.setAlignmentY(Component.TOP_ALIGNMENT);
		add(panelButtons);
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelButtons.setPreferredSize(new Dimension(600, 40));
		panelButtons.setMaximumSize(panelButtons.getPreferredSize());
		panelButtons.setMinimumSize(panelButtons.getPreferredSize());
		JButton btnDeleteClass = new JButton("Delete Class");
		btnDeleteClass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OptionSUClass option = new OptionSUClass(schedule
						.getSUClasses(), true);
				try {
					schedule.deleteSUClass(option.get());
					timeTable.fillTable(schedule);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panelButtons.add(btnDeleteClass);

		JButton btnAddClass = new JButton("Add Class");
		btnAddClass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					OptionSUClass option = new OptionSUClass(SUClass.get(),
							false);
					schedule.addSUClass(option.get());
					timeTable.fillTable(schedule);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panelButtons.add(btnAddClass);
	}

}
