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
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;


public class ApplicationWindow {


	private JFrame frmScheduler;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JTextField txtSearchField;
	private JTextField txtSearchField_1;
	private JTextField txtSearchClasses;
	private JPanel panel_home;
	private JPanel panel_scheduler;
	private JPanel panel_gradfield;
	private JPanel panel_gradclasses;
	private JPanel panel_gradresult;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ApplicationWindow window = new ApplicationWindow();
					window.frmScheduler.setVisible(true);
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
		
		panel_gradresult = new JPanel(); // instantiations
	    panel_home = new JPanel();
	    panel_scheduler = new JPanel();
	    panel_gradfield = new JPanel();
	    panel_gradclasses = new JPanel();
		
		
		frmScheduler = new JFrame();
		
		
		frmScheduler.setTitle("Scheduler");
		frmScheduler.setBounds(100, 100, 450, 300);
		frmScheduler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		frmScheduler.getContentPane().setLayout(new CardLayout(0, 0));
		
		
		panel_home.setBackground(Color.GRAY);
		frmScheduler.getContentPane().add(panel_home, "name_1427149448872557000");
		panel_home.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setText("username");
		txtUsername.setBounds(159, 108, 134, 28);
		panel_home.add(txtUsername);
		txtUsername.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("password");
		pwdPassword.setBounds(159, 148, 134, 28);
		panel_home.add(pwdPassword);
		
		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.setBounds(159, 188, 134, 29);
		panel_home.add(btnSignIn);
		
		JLabel lblWelcomeToScheduler = new JLabel("Welcome to Scheduler");
		lblWelcomeToScheduler.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblWelcomeToScheduler.setBounds(144, 39, 199, 16);
		panel_home.add(lblWelcomeToScheduler);
		
		
		frmScheduler.getContentPane().add(panel_scheduler, "name_1427149452860129000");
		panel_scheduler.setLayout(null);
		
		txtSearchField = new JTextField();
		txtSearchField.setText("Search Class");
		txtSearchField.setBounds(263, 6, 144, 28);
		panel_scheduler.add(txtSearchField);
		txtSearchField.setColumns(10);
		
		JButton btnAddClasses = new JButton("Add Classes");
		btnAddClasses.setBounds(263, 221, 134, 29);
		panel_scheduler.add(btnAddClasses);
		
		JButton btnRemoveClasses = new JButton("Remove Classes");
		btnRemoveClasses.setBounds(47, 221, 134, 29);
		panel_scheduler.add(btnRemoveClasses);
		
		String[] DayStrings = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
		JComboBox<?> DayStrings1 = new JComboBox<Object>(DayStrings);
		DayStrings1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		
		DayStrings1.setBounds(263, 35, 93, 27);
		panel_scheduler.add(DayStrings1);
		
		JList<?> list = new JList<Object>();
		list.setBounds(263, 74, 144, 120);
		panel_scheduler.add(list);
		
		
		
		frmScheduler.getContentPane().add(panel_gradfield, "name_1427149454943753000");
		panel_gradfield.setLayout(null);
		
		txtSearchField_1 = new JTextField();
		txtSearchField_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtSearchField_1.setText("Search Field");
		txtSearchField_1.setBounds(124, 41, 192, 28);
		panel_gradfield.add(txtSearchField_1);
		txtSearchField_1.setColumns(10);
		
		JList<?> list_1 = new JList<Object>();
		list_1.setBounds(124, 81, 192, 97);
		panel_gradfield.add(list_1);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(301, 82, 15, 96);
		panel_gradfield.add(scrollBar);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panel_home.setVisible(false);
				 panel_scheduler.setVisible(false);
				 panel_gradfield.setVisible(false);
				 panel_gradclasses.setVisible(true);
				 panel_gradresult.setVisible(false);
			}
		});
		btnNext.setBounds(163, 190, 117, 29);
		panel_gradfield.add(btnNext);
		
		
		frmScheduler.getContentPane().add(panel_gradclasses, "name_1427149457111841000");
		panel_gradclasses.setLayout(null);
		
		txtSearchClasses = new JTextField();
		txtSearchClasses.setText("Search Classes");
		txtSearchClasses.setBounds(32, 22, 134, 28);
		panel_gradclasses.add(txtSearchClasses);
		txtSearchClasses.setColumns(10);
		
		JList<?> list_2 = new JList<Object>();
		list_2.setBounds(32, 62, 134, 139);
		panel_gradclasses.add(list_2);
		
		JLabel lblCoursesThatHave = new JLabel("Courses that have been taken");
		lblCoursesThatHave.setBounds(218, 28, 198, 16);
		panel_gradclasses.add(lblCoursesThatHave);
		
		JButton btnNext_1 = new JButton("Next");
		btnNext_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_home.setVisible(false);
				 panel_scheduler.setVisible(false);
				 panel_gradfield.setVisible(false);
				 panel_gradclasses.setVisible(false);
				 panel_gradresult.setVisible(true);
			}
		});
		btnNext_1.setBounds(327, 221, 117, 29);
		panel_gradclasses.add(btnNext_1);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					panel_home.setVisible(false);
				 panel_scheduler.setVisible(false);
				 panel_gradfield.setVisible(true);
				 panel_gradclasses.setVisible(false);
				 panel_gradresult.setVisible(false);
			}
		});
		btnBack.setBounds(6, 221, 117, 29);
		panel_gradclasses.add(btnBack);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(151, 62, 15, 139);
		panel_gradclasses.add(scrollBar_1);
		
		
		frmScheduler.getContentPane().add(panel_gradresult, "name_1427150508354750000");
		panel_gradresult.setLayout(null);
	}
}
