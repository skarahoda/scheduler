package io.scheduler.gui;

import io.scheduler.data.BannerParser;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ApplicationWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
					window.frame.setVisible(true);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			BannerParser.getCourses("201402");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Scheduler");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmSchedule = new JMenuItem("Schedule");
		mntmSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JPanel newschedule = new JPanel();
				newschedule.setVisible(true);
				
				
				
			}
		});
		mnNewMenu.add(mntmSchedule);
		
		JMenu mnNewMenu_1 = new JMenu("Graduation Summary");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmGraduationSummary = new JMenuItem("Graduation Summary");
		mntmGraduationSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "In Progress");
			}
		});
		mnNewMenu_1.add(mntmGraduationSummary);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "In Progress");
			}
		});
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "In Progress");
			}
		});
		mnHelp.add(mntmAbout);
	}

}
