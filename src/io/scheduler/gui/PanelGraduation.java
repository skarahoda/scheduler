package io.scheduler.gui;

import java.awt.Container;

import javax.swing.*;

import java.awt.event.*;


public class PanelGraduation extends CardPanel {

	public PanelGraduation(Container parent, String key) {
		super(parent, key);
	}

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = -7469090239906011796L;

	@Override
	

	protected void initialize() {
		this.setBounds(12, 12, 424, 230);
		JLabel lblGradSummary = new JLabel("Grad Summary");
		this.add(lblGradSummary);
	
	}
	
	 public void TabbedPane() {
	        
         //This will create the title you see in the upper left of the window    
        setName("Tabbed Pane");  
        setSize(300,300); //set size so the user can "see" it
        //Here we are creating the object
        JTabbedPane jtp = new JTabbedPane();

        //This creates the template on the windowed application that we will be using
       getRootPane().add(jtp);

       JPanel jp1 = new JPanel();//This will create the first tab

       JPanel jp2 = new JPanel();//This will create the second tab
        
         //This creates a non-editable label, sets what the label will read
        //and adds the label to the first tab
       JLabel label1 = new JLabel();
       label1.setText("This is Tab 1");
       jp1.add(label1);

       //This adds the first and second tab to our stabbed pane object and names it
       jtp.addTab("Tab1", jp1);
       jtp.addTab("Tab2", jp2);

        //This creates a new button called "Press" and adds it to the second tab
       JButton test = new JButton("Press");
       jp2.add(test);

        //This is an Action Listener which reacts to clicking on 
        //the test button called "Press"
        ButtonHandler phandler = new ButtonHandler();
        test.addActionListener(phandler);
        setVisible(true); //otherwise you won't "see" it 
    }
    
    //This is the internal class that defines what the above Action Listener
    //will do when the test button is pressed.
    class ButtonHandler implements ActionListener{
           public void actionPerformed(ActionEvent e){
                   JOptionPane.showMessageDialog(null, "I've been pressed", "What happened?", JOptionPane. INFORMATION_MESSAGE);
           }
    }

    //example usage
     public static void main (String []args){
        @SuppressWarnings("unused")
		TabbedPane tab = new TabbedPane();
    }
	
	
	
	

}
