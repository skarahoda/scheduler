package io.scheduler.gui;

import io.scheduler.data.Course;
import io.scheduler.data.Meeting;
import io.scheduler.data.SUClass;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.security.InvalidParameterException;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OptionSUClass {
	private int option;
	private DefaultListModel<SUClass> listModelSUClass;
	private DefaultListModel<Course> listModelCourse;
	private DefaultListModel<Meeting> listModelMeeting;
	private JList<Course> jListCourse;
	private JList<SUClass> jListSUClass;
	private JList<Meeting> jListMeeting;
	private Collection<SUClass> suClasses;
	private JLabel coursesLabel;
	private JLabel classesLabel;
	private JPanel optionPanel;
	private JPanel suClassPanel;
	private JPanel coursePanel;

	public OptionSUClass(Collection<SUClass> suClasses, boolean isSimple) {
		if (suClasses == null || suClasses.isEmpty())
			throw new InvalidParameterException();
		JPanel datePanel = new JPanel();
		JLabel dateLabel = new JLabel("Meeting");

		this.suClasses = suClasses;
		this.optionPanel = new JPanel();
		this.suClassPanel = new JPanel();
		this.coursePanel = new JPanel();

		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.LINE_AXIS));
		coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.PAGE_AXIS));
		suClassPanel
				.setLayout(new BoxLayout(suClassPanel, BoxLayout.PAGE_AXIS));
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.PAGE_AXIS));

		ScrollPane scrollSUClass = createScrollSUClass();
		if (!isSimple) {
			ScrollPane scrollCourse = createScrollCourse();
			ScrollPane scrollMeeting = createScrollMeeting();

			addEventListeners();

			coursesLabel = new JLabel("Course");
			classesLabel = new JLabel("Class");
			classesLabel.setAlignmentX(0);
			/*
			 * Object[] message = { "Course:", scrollCourse, "Class:",
			 * scrollSUClass };
			 */

			datePanel.add(dateLabel);
			datePanel.add(scrollMeeting);
			coursePanel.add(coursesLabel);
			coursePanel.add(scrollCourse);
			suClassPanel.add(classesLabel);
			suClassPanel.add(scrollSUClass);

			
			
			coursePanel.setPreferredSize(new Dimension(200, 100));
			suClassPanel.setPreferredSize(new Dimension(200, 100));
			datePanel.setPreferredSize(new Dimension(200, 100));

			optionPanel.add(coursePanel);
			optionPanel.add(suClassPanel);
			optionPanel.add(datePanel);

			optionPanel.setPreferredSize(new Dimension(1000, 500));

			option = JOptionPane.showConfirmDialog(null, optionPanel,
					"Classes", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		} else {
			for (SUClass suClass : suClasses) {
				listModelSUClass.addElement(suClass);
			}
			Object[] message = { "Class:", scrollSUClass };
			option = JOptionPane.showConfirmDialog(null, message, "Classes",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		}

	}

	private void addEventListeners() {
		jListCourse.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				listModelSUClass.clear();
				Course course = jListCourse.getSelectedValue();
				for (SUClass suClass : suClasses) {
					if (suClass.getCourse().equals(course)) {
						listModelSUClass.addElement(suClass);
					}
				}
			}
		});
		jListSUClass.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				listModelMeeting.clear();
				SUClass suClass = jListSUClass.getSelectedValue();

				if (suClass != null) {
					for (Meeting meeting : suClass.getMeetings()) {
						listModelMeeting.addElement(meeting);
					}
				}

			}
		});
	}

	private ScrollPane createScrollSUClass() {
		ScrollPane returnVal = new ScrollPane();
		listModelSUClass = new DefaultListModel<SUClass>();
		jListSUClass = new JList<SUClass>(listModelSUClass);
		jListSUClass.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnVal.add(jListSUClass);
		return returnVal;
	}

	private ScrollPane createScrollMeeting() {
		ScrollPane returnVal = new ScrollPane();
		listModelMeeting = new DefaultListModel<Meeting>();
		jListMeeting = new JList<Meeting>(listModelMeeting);
		jListMeeting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnVal.add(jListMeeting);

		return returnVal;
	}

	private ScrollPane createScrollCourse() {
		ScrollPane returnVal = new ScrollPane();
		listModelCourse = new DefaultListModel<Course>();
		for (SUClass suClass : suClasses) {
			Course course = suClass.getCourse();
			if (course != null && !listModelCourse.contains(course)) {
				listModelCourse.addElement(course);
			}
		}
		jListCourse = new JList<Course>(listModelCourse);
		jListCourse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnVal.add(jListCourse);
		return returnVal;
	}

	public SUClass get() {
		if (suClasses == null || option != JOptionPane.OK_OPTION) {
			return null;
		}
		return jListSUClass.getSelectedValue();
	}

}
