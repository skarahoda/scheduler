package io.scheduler.gui;

import io.scheduler.data.Course;
import io.scheduler.data.Schedule;
import io.scheduler.data.TakenCourse;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelGraduation extends CardPanel {

	private CustomTabbedPane tabbedPaneDegree;

	public PanelGraduation(Container parent, String key) {
		super(parent, key);
	}

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = -7469090239906011796L;

	@Override
	protected void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		initTabbedPane();
		initButtons();

	}

	private void initButtons() {

		JPanel panelButtons = new JPanel();
		panelButtons.setAlignmentY(Component.TOP_ALIGNMENT);
		add(panelButtons);
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelButtons.setPreferredSize(new Dimension(600, 40));
		panelButtons.setMaximumSize(panelButtons.getPreferredSize());
		panelButtons.setMinimumSize(panelButtons.getPreferredSize());
		JButton btnDeleteClass = new JButton("Delete Course");
		btnDeleteClass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					OptionList<Course> option = new OptionList<Course>(
							TakenCourse.getAll());
					TakenCourse.deleteCourses(option.get());
					updateTable();
				} catch (InvalidParameterException e1) {
					JOptionPane.showMessageDialog(null,
							"You don't have any course to delete.");
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panelButtons.add(btnDeleteClass);

		JButton btnAddClass = new JButton("Add Course");
		btnAddClass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					OptionList<Course> option = new OptionList<Course>(Course
							.getAll());
					TakenCourse.addCourses(option.get());
					updateTable();
				} catch (InvalidParameterException e1) {
					JOptionPane.showMessageDialog(null,
							"You don't have any course to add.");
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panelButtons.add(btnAddClass);

		JButton btnImport = new JButton("Import From Schedule");
		btnImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					OptionList<Schedule> option = new OptionList<Schedule>(
							Schedule.get());

					for (Schedule schedule : option.get()) {
						TakenCourse.addCourses(schedule);
					}
					updateTable();
				} catch (InvalidParameterException e1) {
					JOptionPane.showMessageDialog(null,
							"You don't have any schedule");
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panelButtons.add(btnImport);

	}

	protected void updateTable() {
		PanelGraduationTable table = (PanelGraduationTable) tabbedPaneDegree
				.getSelectedComponent();
		table.update();
	}

	private void initTabbedPane() {
		DegreeFactory degreeFactory = new DegreeFactory();
		tabbedPaneDegree = new CustomTabbedPane(SwingConstants.TOP,
				degreeFactory);
		tabbedPaneDegree.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		add(tabbedPaneDegree);
	}
}
