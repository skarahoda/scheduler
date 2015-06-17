package io.scheduler.gui;

import io.scheduler.data.Course;
import io.scheduler.data.Meeting;
import io.scheduler.data.SUClass;
import io.scheduler.data.handler.FiltersSUClass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
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
	private Collection<SUClass> filteredSuClasses;
	private Collection<SUClass> suClasses;
	private JCheckBox checkBoxCoReq;

	public OptionSUClass(Collection<SUClass> suClasses)
			throws InvalidParameterException {
		if (suClasses == null || suClasses.isEmpty())
			throw new InvalidParameterException();
		this.filteredSuClasses = new ArrayList<SUClass>(suClasses);
		this.suClasses = suClasses;
		JPanel optionPanel = initOptionPanel();
		checkBoxCoReq = new JCheckBox("Courses without corequisite");
		addEventListeners();
		Object[] message = { checkBoxCoReq, optionPanel };
		option = JOptionPane.showConfirmDialog(null, message, "Classes",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	private JPanel initOptionPanel() {
		JPanel returnVal = new JPanel();
		JPanel coursePanel = initCustomPanel(new JLabel("Course:"),
				createScrollCourse());
		JPanel suClassPanel = initCustomPanel(new JLabel("Class:"),
				createScrollSUClass());
		JPanel meetingPanel = initCustomPanel(new JLabel("Meeting:"),
				createScrollMeeting());
		returnVal.add(coursePanel);
		returnVal.add(suClassPanel);
		returnVal.add(meetingPanel);
		returnVal.setLayout(new BoxLayout(returnVal, BoxLayout.LINE_AXIS));
		return returnVal;
	}

	private JPanel initCustomPanel(JLabel label, JScrollPane scroll) {
		JPanel returnVal = new JPanel();
		returnVal.setLayout(new BoxLayout(returnVal, BoxLayout.PAGE_AXIS));
		returnVal.add(label);
		returnVal.add(scroll);
		return returnVal;
	}

	private void addEventListeners() {
		jListCourse.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				listModelSUClass.clear();
				Course course = jListCourse.getSelectedValue();
				for (SUClass suClass : filteredSuClasses) {
					if (suClass.getCourse().equals(course)) {
						listModelSUClass.addElement(suClass);
					}
				}
				jListSUClass.setSelectedIndex(0);
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
		ActionListener filterListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filter();
			}
		};
		checkBoxCoReq.addActionListener(filterListener);
	}

	protected void filter() {
		if (checkBoxCoReq.isSelected()) {
			filteredSuClasses = FiltersSUClass.filterForCoReq(suClasses);
		} else {
			filteredSuClasses = new ArrayList<SUClass>(suClasses);
		}
		fillScrollCourse();
	}

	private JScrollPane createScrollSUClass() {
		listModelSUClass = new DefaultListModel<SUClass>();
		jListSUClass = new JList<SUClass>(listModelSUClass);
		jListSUClass.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane returnVal = new JScrollPane(jListSUClass);
		return returnVal;
	}

	private JScrollPane createScrollMeeting() {
		listModelMeeting = new DefaultListModel<Meeting>();
		jListMeeting = new JList<Meeting>(listModelMeeting);
		jListMeeting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane returnVal = new JScrollPane(jListMeeting);
		return returnVal;
	}

	private JScrollPane createScrollCourse() {
		listModelCourse = new DefaultListModel<Course>();
		jListCourse = new JList<Course>(listModelCourse);
		jListCourse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fillScrollCourse();
		JScrollPane returnVal = new JScrollPane(jListCourse);
		return returnVal;
	}

	private void fillScrollCourse() {
		listModelCourse.clear();
		for (SUClass suClass : filteredSuClasses) {
			Course course = suClass.getCourse();
			if (course != null && !listModelCourse.contains(course)) {
				listModelCourse.addElement(course);
			}
		}
		if (!listModelCourse.isEmpty()) {
			jListCourse.setSelectedIndex(0);
		}
	}

	public SUClass get() {
		if (filteredSuClasses == null || option != JOptionPane.OK_OPTION) {
			return null;
		}
		return jListSUClass.getSelectedValue();
	}

}
