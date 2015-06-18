package io.scheduler.gui;

import io.scheduler.data.Term;
import io.scheduler.data.Term.TermOfYear;
import io.scheduler.data.User;
import io.scheduler.data.handler.BannerParser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.Callable;

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
			return parse(term);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Term is invalid.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private boolean parse(final Term term) {
		Callable<Void> mainExecution = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				BannerParser.parse(term);
				return null;
			}
		};
		final Term oldTerm = User.getCurrentTerm();
		Runnable cancelExecution = new Runnable() {

			@Override
			public void run() {
				try {
					User.setCurrentTerm(oldTerm);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		return new IndeterminateProgressDialog(
				"Classes are getting from internet...", mainExecution,
				cancelExecution).isApplied();

	}

}
