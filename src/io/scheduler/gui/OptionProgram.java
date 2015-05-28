package io.scheduler.gui;

import io.scheduler.data.Program;
import io.scheduler.data.handler.DegreeParser;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class OptionProgram {

	private JTextField textFieldYear;
	private JComboBox<String> comboBoxTerm;
	private int option;
	private JCheckBox checkBoxIsUG;
	private JTextField textFieldProgram;

	public OptionProgram() {

		MaskFormatter formatter;
		try {
			formatter = new MaskFormatter("####");
			textFieldYear = new JFormattedTextField(formatter);
		} catch (ParseException e) {
			textFieldYear = new JTextField();
		}
		String[] terms = { "Fall", "Spring" };
		comboBoxTerm = new JComboBox<String>(terms);
		checkBoxIsUG = new JCheckBox("I am undergraduate student");
		textFieldProgram = new JTextField();
		Object[] message = { "Year:", textFieldYear, "Term:", comboBoxTerm,
				checkBoxIsUG, "Program name:", textFieldProgram };
		option = JOptionPane.showConfirmDialog(null, message, "Configurations",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	public Program get() {
		if (option != JOptionPane.OK_OPTION) {
			return null;
		}
		int year;
		try {
			year = Integer.parseInt(textFieldYear.getText());
		} catch (NumberFormatException e) {
			year = 0;
		}
		int term = (year * 100) + (comboBoxTerm.getSelectedIndex() + 1);
		Program p = null;
		try {
			p = Program.get(term, textFieldProgram.getText(),
					checkBoxIsUG.isSelected());
			return DegreeParser.parse(term, checkBoxIsUG.isSelected(),
					textFieldProgram.getText());
		} catch (IllegalArgumentException | IOException | SQLException e) {
			try {
				p.removeFromDB();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

}
