package io.scheduler.gui;

import io.scheduler.data.Program;
import io.scheduler.data.Term;
import io.scheduler.data.Term.TermOfYear;
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
	private JComboBox<TermOfYear> comboBoxTerm;
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
		comboBoxTerm = new JComboBox<TermOfYear>(TermOfYear.values());
		checkBoxIsUG = new JCheckBox("I am undergraduate student");
		textFieldProgram = new JTextField();
		Object[] message = { "Year:", textFieldYear, "Term:", comboBoxTerm,
				checkBoxIsUG, "Program name:", textFieldProgram };
		option = JOptionPane.showConfirmDialog(null, message,
				"Choose Degree Program", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
	}

	public Program get() {
		if (option != JOptionPane.OK_OPTION) {
			return null;
		}
		int year;
		try {
			year = Integer.parseInt(textFieldYear.getText());
		} catch (NumberFormatException e) {
			return null;
		}
		Program p = null;
		Term term = new Term(year, (TermOfYear) comboBoxTerm.getSelectedItem());
		try {
			p = Program.get(term, textFieldProgram.getText(),
					checkBoxIsUG.isSelected());
			if (p == null)
				return DegreeParser.parse(term, checkBoxIsUG.isSelected(),
						textFieldProgram.getText());
			return null;
		} catch (IllegalArgumentException | IOException | SQLException e) {
			return null;
		}
	}

}
