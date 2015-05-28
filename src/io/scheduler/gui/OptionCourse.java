package io.scheduler.gui;

import io.scheduler.data.Course;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

public class OptionCourse {
	private int option;
	private DefaultListModel<Course> listModelCourse;
	private JList<Course> jListCourse;

	public OptionCourse(Collection<Course> courses) {
		ScrollPane scrollCourse = createScrollCourse(courses);
		Object[] message = { "Course:", scrollCourse };
		option = JOptionPane.showConfirmDialog(null, message, "Courses",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	private ScrollPane createScrollCourse(Collection<Course> courses) {
		ScrollPane returnVal = new ScrollPane();
		returnVal.setPreferredSize(new Dimension(300, 500));
		listModelCourse = new DefaultListModel<Course>();
		for (Course course : courses) {
			listModelCourse.addElement(course);
		}
		jListCourse = new JList<Course>(listModelCourse);
		jListCourse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnVal.add(jListCourse);
		return returnVal;
	}

	public Course get() {
		if (option != JOptionPane.OK_OPTION) {
			return null;
		}
		return jListCourse.getSelectedValue();
	}

}
