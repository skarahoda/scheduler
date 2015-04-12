package io.scheduler.gui;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ApplicationWindow {


	private JFrame frmScheduler;
	private JMenuBar menuBar;
	private JPanel panelSchedule;
	private Container panelGradSummary;
	private Container panelConfig;
	

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
		
		frmScheduler = new JFrame();
		frmScheduler.setTitle("Scheduler");
		frmScheduler.setBounds(100, 100, 450, 300);
		frmScheduler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmScheduler.getContentPane().setLayout(null);
		
		initializeMenu();
		
		initializeSchedulePanel();
		initializeGradSummaryPanel();
		initializeConfigPanel();
		frmScheduler.getContentPane().add(panelConfig);
		//System.out.println(frmScheduler.getContentPane().getComponentCount());
	}

	private void initializeSchedulePanel() {
		
		panelSchedule = new JPanel();
		panelSchedule.setBounds(12, 12, 424, 230);
		
		JTextField txtSearchCourse = new JTextField();
		txtSearchCourse.setText("Search Class");
		txtSearchCourse.setBounds(263, 6, 144, 28);
		panelSchedule.add(txtSearchCourse);
		txtSearchCourse.setColumns(10);
		
		JButton btnAddClasses = new JButton("Add Classes");
		btnAddClasses.setBounds(263, 221, 134, 29);
		panelSchedule.add(btnAddClasses);
		
		JButton btnRemoveClasses = new JButton("Remove Classes");
		btnRemoveClasses.setBounds(47, 221, 134, 29);
		panelSchedule.add(btnRemoveClasses);
		
		String[] DayStrings = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		JComboBox<?> DayStrings1 = new JComboBox<Object>(DayStrings);
		DayStrings1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		
		DayStrings1.setBounds(263, 35, 93, 27);
		panelSchedule.add(DayStrings1);
		
		JList<?> list = new JList<Object>();
		list.setBounds(263, 74, 144, 120);
		panelSchedule.add(list);
	}

	private void initializeGradSummaryPanel() {
		// TODO Auto-generated method stub
		
		panelGradSummary = new JPanel();
		panelGradSummary.setBounds(12, 12, 424, 230);
		JLabel lblGradSummary = new JLabel("Grad Summary");
		panelGradSummary.add(lblGradSummary);
	}

	private void initializeConfigPanel() {
		// TODO Auto-generated method stub
		
		panelConfig = new JPanel();
		panelConfig.setBounds(12, 12, 424, 230);
		JLabel lblConfig = new JLabel("config");
		panelConfig.add(lblConfig);
	}

	private void initializeMenu() {
		menuBar = new JMenuBar();
		frmScheduler.setJMenuBar(menuBar);
		
		JMenu mnScheduler = new JMenu("Scheduler");
		menuBar.add(mnScheduler);
		
		JMenuItem mntmSchedule = new JMenuItem("Schedule");
		mntmSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMainContainer(panelSchedule);
			}
		});
		mnScheduler.add(mntmSchedule);
		
		JMenu mnGraduation = new JMenu("Graduation Summary");
		menuBar.add(mnGraduation);
		
		JMenuItem mntmGraduationSummary = new JMenuItem("Graduation Summary");
		mntmGraduationSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMainContainer(panelGradSummary);
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
				setMainContainer(panelConfig);
			}
		});
		mnHelp.add(mntmPreferences);
		
	}

	private void setMainContainer(Container container) {
		frmScheduler.getContentPane().removeAll();
		frmScheduler.getContentPane().add(container);
	}
	
}
