package io.scheduler.gui;

import io.scheduler.data.User;
import io.scheduler.data.handler.BannerParser;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * @author skarahoda
 *
 */
public class PanelConfig extends CardPanel {

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = -8483298720242968924L;
	protected static String key;
	private JTextField textField;

	/**
	 * @param parent
	 *            : container that has cardlayout
	 * @param key
	 *            : key for cardlayout
	 */
	public PanelConfig(Container parent, String key) {
		super(parent, key);

	}

	@Override
	protected void initialize() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel lblTerm = new JLabel("Term:");
		add(lblTerm);

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 41,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, textField, 59,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblTerm, 3,
				SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, lblTerm, -11,
				SpringLayout.WEST, textField);
		add(textField);
		textField.setColumns(10);

		String[] petStrings = { "Fall", "Spring", "Summer" };
		final JComboBox<String> comboTerm = new JComboBox<String>(petStrings);
		springLayout.putConstraint(SpringLayout.NORTH, comboTerm, 0,
				SpringLayout.NORTH, lblTerm);
		springLayout.putConstraint(SpringLayout.WEST, comboTerm, 59,
				SpringLayout.EAST, textField);
		add(comboTerm);

		JButton btnSubmit = new JButton("Submit");
		springLayout.putConstraint(SpringLayout.NORTH, btnSubmit, 43,
				SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, btnSubmit, 0,
				SpringLayout.WEST, textField);
		add(btnSubmit);

		btnSubmit.setAction(new AbstractAction("Submit") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4581397962079405794L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					int year = Integer.parseInt(textField.getText());
					int term = comboTerm.getSelectedIndex();

					String currentTerm = Integer.toString(year) + "0"
							+ Integer.toString(term + 1);
					User.setCurrentTerm(currentTerm);
					BannerParser.getSUClasses(User.getCurrentTerm());
				} catch (IllegalArgumentException e1) {
					// TODO add logger
					JPanel panel = new JPanel(new GridLayout(0, 1));
					JOptionPane.showMessageDialog(panel,
							"Please enter a valid year.");
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}
}
