package io.scheduler.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuBar extends JMenu {
	
	
	
	public MenuBar(JFrame frmScheduler, final JPanel panel_home, final JPanel panel_scheduler, final JPanel panel_gradfield,
			final JPanel panel_gradclasses, final JPanel panel_gradresult){
		
		JMenuBar menuBar = new JMenuBar();
         frmScheduler.setJMenuBar(menuBar);
		
		JMenu mnHome = new JMenu("Home");
		menuBar.add(mnHome);
		
		
		JMenuItem mntmHome = new JMenuItem("Home");
		mntmHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 panel_home.setVisible(true);
				 panel_scheduler.setVisible(false);
				 panel_gradfield.setVisible(false);
				 panel_gradclasses.setVisible(false);
				 panel_gradresult.setVisible(false);
				
			}
		});
		mnHome.add(mntmHome);
		
		JMenuItem mntmSignOut = new JMenuItem("Sign Out");
		mntmSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "In Progress");
			}
		});
		mnHome.add(mntmSignOut);
		
		JMenu mnNewMenu = new JMenu("Scheduler");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmSchedule = new JMenuItem("Schedule");
		mntmSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panel_home.setVisible(false);
				 panel_scheduler.setVisible(true);
				 panel_gradfield.setVisible(false);
				 panel_gradclasses.setVisible(false);
				 panel_gradresult.setVisible(false);
			}
		});
		mnNewMenu.add(mntmSchedule);
		
		JMenu mnNewMenu_1 = new JMenu("Graduation Summary");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmGraduationSummary = new JMenuItem("Graduation Summary");
		mntmGraduationSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_gradfield.setVisible(true);
				
			}
		});
		mnNewMenu_1.add(mntmGraduationSummary);
		
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
								JOptionPane.showMessageDialog(null, "In Progress");
			}
		});
		mnHelp.add(mntmAbout);
		
	}
	
	

}
