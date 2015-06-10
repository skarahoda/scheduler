package io.scheduler.gui;

import io.scheduler.data.Term;
import io.scheduler.data.Term.TermOfYear;
import io.scheduler.data.handler.BannerParser;

import java.io.IOException;
import java.sql.SQLException;
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

	public boolean isApplied() {
		if (option != JOptionPane.OK_OPTION) {
			return false;
		}
		int year;
		try {
			year = Integer.parseInt(textFieldYear.getText());
		} catch (NumberFormatException e) {
			return false;
		}
		try {
			Term term = new Term(year,
					(TermOfYear) comboBoxTerm.getSelectedItem());
			BannerParser.parse(term);
			return true;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Term is invalid.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"We cannot get information from the website please try again.",
							"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (SQLException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Database is already in use, please close the database connection.",
							"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

}
