package io.scheduler.gui;

import io.scheduler.data.Course;
import io.scheduler.data.SUClass;

import java.awt.ScrollPane;
import java.security.InvalidParameterException;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OptionSUClass {
	private int option;
	private DefaultListModel<SUClass> listModelSUClass;
	private DefaultListModel<Course> listModelCourse;
	private JList<Course> jListCourse;
	private JList<SUClass> jListSUClass;
	private Collection<SUClass> suClasses;

	public OptionSUClass(Collection<SUClass> suClasses, boolean isSimple) {
		if (suClasses == null || suClasses.isEmpty())
			throw new InvalidParameterException();
		this.suClasses = suClasses;
		ScrollPane scrollSUClass = createScrollSUClass();
		if (!isSimple) {
			ScrollPane scrollCourse = createScrollCourse();
			addEventListeners();
			Object[] message = { "Course:", scrollCourse, "Class:",
					scrollSUClass };
			option = JOptionPane.showConfirmDialog(null, message, "Classes",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
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
	}

	private ScrollPane createScrollSUClass() {
		ScrollPane returnVal = new ScrollPane();
		listModelSUClass = new DefaultListModel<SUClass>();
		jListSUClass = new JList<SUClass>(listModelSUClass);
		jListSUClass.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnVal.add(jListSUClass);
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
