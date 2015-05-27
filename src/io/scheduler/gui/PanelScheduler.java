package io.scheduler.gui;

import io.scheduler.data.SUClass;
import io.scheduler.data.Schedule;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class PanelScheduler extends CardPanel {
	private JTabbedPane tabbedPaneSchedule;
	private JPanel panelButtons;

	public PanelScheduler(Container parent, String key) {
		super(parent, key);
	}

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = 8525163294114489034L;

	@Override
	protected void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		initTabbedPane();
		initButtons();
	}

	private void initButtons() {
		panelButtons = new JPanel();
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
				try {
					PanelTimeTable timeTable = getTimeTable();
					OptionSUClass option = new OptionSUClass(timeTable
							.getSUClasses(), true);
					timeTable.deleteClass(option.get());
				} catch (InvalidParameterException e1) {
					JOptionPane.showMessageDialog(new JFrame(),
							"You don't have any class.");
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
					PanelTimeTable timeTable = getTimeTable();
					OptionSUClass option = new OptionSUClass(SUClass.get(),
							false);
					timeTable.addClass(option.get());
				} catch (SQLException e1) {

				} catch (InvalidParameterException e1) {
					JOptionPane.showMessageDialog(new JFrame(),
							"There is no class.");
					e1.printStackTrace();

				}
			}
		});
		panelButtons.add(btnAddClass);
	}

	protected PanelTimeTable getTimeTable() {
		return (PanelTimeTable) tabbedPaneSchedule.getSelectedComponent();
	}

	private void initTabbedPane() {
		tabbedPaneSchedule = new JTabbedPane(SwingConstants.TOP);
		tabbedPaneSchedule.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		add(tabbedPaneSchedule);

		List<Schedule> schedules;
		try {
			schedules = Schedule.get();
		} catch (SQLException e2) {
			schedules = null;
		}
		for (Schedule schedule : schedules) {
			tabbedPaneSchedule.addTab(schedule.getName(), new PanelTimeTable(
					schedule));
		}
		tabbedPaneSchedule.addTab("+", null, null, "Add new schedule");

		tabbedPaneSchedule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int lastTab = tabbedPaneSchedule.getTabCount() - 1;
				if (tabbedPaneSchedule.getSelectedIndex() == lastTab) {
					String name = JOptionPane
							.showInputDialog("Please enter schedule name");
					List<String> tabNames = getTabNames();
					if (name != null && !name.equals("")
							&& !tabNames.contains(name)) {
						try {
							Schedule schedule = Schedule.get(name);
							tabbedPaneSchedule.remove(lastTab);
							tabbedPaneSchedule.addTab(schedule.getName(),
									new PanelTimeTable(schedule));
							tabbedPaneSchedule.addTab("+", null, null,
									"Add new schedule");
							tabbedPaneSchedule.setSelectedIndex(lastTab);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
	}

	protected List<String> getTabNames() {
		List<String> returnVal = new ArrayList<String>();
		for (int i = 0; i < tabbedPaneSchedule.getTabCount(); i++) {
			returnVal.add(tabbedPaneSchedule.getTitleAt(i));
		}
		return returnVal;
	}

}
