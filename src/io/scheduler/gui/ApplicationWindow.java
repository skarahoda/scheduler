package io.scheduler.gui;

import io.scheduler.data.User;
import io.scheduler.data.handler.BannerParser;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class ApplicationWindow {

	private JFrame frameMain;
	private JMenuBar menuBar;
	private PanelScheduler panelSchedule;
	private PanelGraduation panelGradSummary;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ApplicationWindow window = new ApplicationWindow();
				window.frameMain.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initializeConfig();
		initializeMainFrame();
		initializeMenu();
		initializePanels();
	}

	private void initializeConfig() {
		if (User.getCurrentTerm() == -1) {
			while (!ApplicationWindow.config()) {
				int option = JOptionPane
						.showConfirmDialog(null, "Do you want to exit?",
								"Configurations", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE);
				if (option == JOptionPane.OK_OPTION) {
					System.exit(1);
				}
			}
		}
	}

	private void initializePanels() {
		panelGradSummary = new PanelGraduation(frameMain.getContentPane(),
				"graduation");
		panelSchedule = new PanelScheduler(frameMain.getContentPane(),
				"schedule");
		panelSchedule.setVisible();
	}

	private void initializeMainFrame() {
		frameMain = new JFrame();
		frameMain.setTitle("Scheduler");
		frameMain.setBounds(100, 100, 600, 600);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.getContentPane().setLayout(new CardLayout(0, 0));
	}

	private void initializeMenu() {
		menuBar = new JMenuBar();
		frameMain.setJMenuBar(menuBar);

		JMenu mnScheduler = new JMenu("Scheduler");
		mnScheduler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelSchedule.setVisible();
			}
		});
		menuBar.add(mnScheduler);
		mnScheduler.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				if (ApplicationWindow.config()) {
					panelSchedule.updateWithTerm();
				}
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}
		});

		JMenu mnGraduation = new JMenu("Graduation Summary");
		menuBar.add(mnGraduation);
		mnGraduation.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				if (ApplicationWindow.config()) {
					panelSchedule.updateWithTerm();
				}
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}
		});

		JMenu mnPreferences = new JMenu("Preferences");
		menuBar.add(mnPreferences);
		mnPreferences.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				if (ApplicationWindow.config()) {
					panelSchedule.updateWithTerm();
				}
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}
		});
	}

	public static boolean config() {
		int term = new OptionConfig().getTerm();
		if (term != 0) {
			try {
				BannerParser.parse(term);
			} catch (IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(null, "Term is invalid.",
						"Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return false;
			} catch (IOException e1) {
				JOptionPane
						.showMessageDialog(
								null,
								"We cannot get information from the website please try again.",
								"Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return false;
			} catch (SQLException e1) {
				JOptionPane
						.showMessageDialog(
								null,
								"Database is already in use, please close the database connection.",
								"Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

}
