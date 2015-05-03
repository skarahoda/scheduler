package io.scheduler.gui;

import io.scheduler.data.User;
import io.scheduler.data.handler.BannerParser;
import io.scheduler.data.handler.DatabaseConnector;

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

public class ApplicationWindow {


	private JFrame frameMain;
	private JMenuBar menuBar;
	private PanelSchedule panelSchedule;
	private PanelGraduation panelGradSummary;
	private PanelConfig panelConfig;
	private User mainUser;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
			this.mainUser = DatabaseConnector.getUser();
			if(this.mainUser == null){
				panelConfig.setVisible();
				this.mainUser = new User("201402");
				DatabaseConnector.setUser(this.mainUser);
			}else{
				panelSchedule.setVisible();
			}
			BannerParser.getSUClasses(mainUser.getCurrentTerm());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		initializeMainFrame();
		initializeMenu();
		
		panelGradSummary = new PanelGraduation(frameMain.getContentPane(),"graduation");
		panelSchedule = new PanelSchedule(frameMain.getContentPane(),"schedule");
		panelConfig = new PanelConfig(frameMain.getContentPane(),"schedule");
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
			public void actionPerformed(ActionEvent e) {
				panelSchedule.setVisible();
			}
		});
		mnScheduler.add(mntmSchedule);
		
		JMenu mnGraduation = new JMenu("Graduation Summary");
		menuBar.add(mnGraduation);
		
		JMenuItem mntmGraduationSummary = new JMenuItem("Graduation Summary");
		mntmGraduationSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelGradSummary.setVisible();
			}
		});
		mnGraduation.add(mntmGraduationSummary);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mntmPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelConfig.setVisible();
			}
		});
		mnHelp.add(mntmPreferences);
		
	}
	
}
