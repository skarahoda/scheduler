package io.scheduler.gui;

import io.scheduler.data.SUClass;
import io.scheduler.data.User;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelScheduler extends CardPanel {
	private CustomTabbedPane tabbedPaneSchedule;
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
					OptionList<SUClass> option = new OptionList<SUClass>(
							timeTable.getSUClasses());
					List<SUClass> list = option.get();
					for (SUClass suClass : list) {
						timeTable.deleteClass(suClass);
					}
				} catch (InvalidParameterException e1) {
					JOptionPane.showMessageDialog(null,
							"You don't have any class.");
					e1.printStackTrace();
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
					PanelTimeTable timeTable = getTimeTable();
					OptionSUClass option = new OptionSUClass(SUClass.get(User
							.getCurrentTerm()));
					timeTable.addClass(option.get());
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (InvalidParameterException e1) {
					JOptionPane.showMessageDialog(null,
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
		ScheduleFactory scheduleFactory = new ScheduleFactory();
		tabbedPaneSchedule = new CustomTabbedPane(SwingConstants.TOP,
				scheduleFactory);
		tabbedPaneSchedule.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		add(tabbedPaneSchedule);
	}

	public void updateWithTerm() {
		tabbedPaneSchedule.removeAll();
		tabbedPaneSchedule.fill();
	}

}
