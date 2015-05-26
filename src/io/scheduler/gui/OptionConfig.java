package io.scheduler.gui;

import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class OptionConfig {

	private int option;
	private JTextField textFieldYear;
	private JComboBox<String> comboBoxTerm;

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
		String[] terms = { "Fall", "Spring" };
		comboBoxTerm = new JComboBox<String>(terms);
		Object[] message = { "Year:", textFieldYear, "Term:", comboBoxTerm };
		option = JOptionPane.showConfirmDialog(null, message, "Configurations",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	public int getTerm() {
		if (option != JOptionPane.OK_OPTION) {
			return 0;
		}
		int year;
		try {
			year = Integer.parseInt(textFieldYear.getText());
		} catch (NumberFormatException e) {
			year = 0;
		}
		int term = (year * 100) + (comboBoxTerm.getSelectedIndex() + 1);
		return term;
	}

}
