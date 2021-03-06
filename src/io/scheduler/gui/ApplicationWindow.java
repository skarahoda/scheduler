package io.scheduler.gui;

import io.scheduler.data.User;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

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
		while (!User.canGet()) {
			JOptionPane
					.showMessageDialog(
							null,
							"Database is already in use, please close the database connection.",
							"Error", JOptionPane.ERROR_MESSAGE);
			exitOption();
		}
		if (User.getCurrentTerm() == null) {
			while (!new OptionConfig().isApplied()) {
				exitOption();
			}
		}
	}

	private void exitOption() {
		int option = JOptionPane.showConfirmDialog(null,
				"Do you want to exit?", "Configurations",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.OK_OPTION) {
			System.exit(1);
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

		mnScheduler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					panelSchedule.setVisible();
				}

			};
		});
		menuBar.add(mnScheduler);

		JMenu mnGraduation = new JMenu("Graduation Summary");
		menuBar.add(mnGraduation);

		mnGraduation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					panelGradSummary.setVisible();
				}

			};
		});

		JMenu mnPreferences = new JMenu("Preferences");
		menuBar.add(mnPreferences);

		mnPreferences.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1
						&& new OptionConfig().isApplied()) {
					new SwingWorker<Void, Void>() {

						@Override
						protected Void doInBackground() throws Exception {
							panelSchedule.updateWithTerm();
							return null;
						}
					}.execute();
				}
			};
		});
	}
}
