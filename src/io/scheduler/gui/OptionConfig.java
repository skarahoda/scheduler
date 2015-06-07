package io.scheduler.gui;

import io.scheduler.data.Term;
import io.scheduler.data.Term.TermOfYear;

import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class OptionConfig {

	private int option;
	private JTextField textFieldYear;
	private JComboBox<TermOfYear> comboBoxTerm;

	/**
	 * 
	 */
	public OptionConfig() {

		MaskFormatter formatter;
		try {
			formatter = new MaskFormatter("####");
			textFieldYear = new JFormattedTextField(formatter);
		} catch (ParseException e) {
			textFieldYear = new JTextField();
		}
		comboBoxTerm = new JComboBox<TermOfYear>(TermOfYear.values());
		Object[] message = { "Year:", textFieldYear, "Term:", comboBoxTerm };
		option = JOptionPane.showConfirmDialog(null, message, "Configurations",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	public Term getTerm() {
		if (option != JOptionPane.OK_OPTION) {
			return null;
		}
		int year;
		try {
			year = Integer.parseInt(textFieldYear.getText());
		} catch (NumberFormatException e) {
			return null;
		}
		return new Term(year, (TermOfYear) comboBoxTerm.getSelectedItem());
	}

}
