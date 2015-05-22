package io.scheduler.gui;

import io.scheduler.data.User;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ApplicationWindow {

	private JFrame frameMain;
	private JMenuBar menuBar;
	private PanelSchedule panelSchedule;
	private PanelGraduation panelGradSummary;
	private PanelConfig panelConfig;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		initialize();
		try {
			if (User.getCurrentTerm() == null) {
				panelConfig.setVisible();
			} else {
				panelSchedule.setVisible();
			}
		} catch (SQLException e) {
			// TODO add logger
			JPanel panel = new JPanel(new GridLayout(0, 1));
			JOptionPane
					.showMessageDialog(panel,
							"Database is already in use, please close the database connection.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		initializeMainFrame();
		initializeMenu();

		panelGradSummary = new PanelGraduation(frameMain.getContentPane(),
				"graduation");
		panelSchedule = new PanelSchedule(frameMain.getContentPane(),
				"schedule");
		panelConfig = new PanelConfig(frameMain.getContentPane(), "config");
	}

	private void initializeMainFrame() {
		frameMain = new JFrame();
		frameMain.setTitle("Scheduler");
		frameMain.setBounds(100, 100, 450, 300);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.getContentPane().setLayout(new CardLayout(0, 0));
	}

	private void initializeMenu() {
		menuBar = new JMenuBar();
		frameMain.setJMenuBar(menuBar);

		JMenu mnScheduler = new JMenu("Scheduler");
		menuBar.add(mnScheduler);

		JMenuItem mntmSchedule = new JMenuItem("Schedule");
		mntmSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelSchedule.setVisible();
			}
		});
		mnScheduler.add(mntmSchedule);

		JMenu mnGraduation = new JMenu("Graduation Summary");
		menuBar.add(mnGraduation);

		JMenuItem mntmGraduationSummary = new JMenuItem("Graduation Summary");
		mntmGraduationSummary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelGradSummary.setVisible();
			}
		});
		mnGraduation.add(mntmGraduationSummary);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnHelp.add(mntmHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnHelp.add(mntmAbout);

		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mntmPreferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelConfig.setVisible();
			}
		});
		mnHelp.add(mntmPreferences);

	}

}
