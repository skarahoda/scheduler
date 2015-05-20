package io.scheduler.gui;

import io.scheduler.data.Course;
import io.scheduler.data.SUClass;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CourseFrame{
	
	private JFrame courseFrame = new JFrame();
	private List<Course> courseList;
	
	public CourseFrame(final Collection<SUClass> classList) {
		super();
		this.courseList = new ArrayList<Course>();
		this.courseFrame.setSize(500, 500);
		courseFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		ScrollPane scroll = new ScrollPane();
		DefaultListModel<String> listmodel = new DefaultListModel<String>();
		
	
		
		for(SUClass suClass : classList){
			//String listElement = suClass.getCode() suClass.get + "-" + suClass.getName();
			Course crs = suClass.getCourse();
			
			  if(!this.courseList.contains(crs) && crs !=null) {
				  this.courseList.add(crs);
			  }
		}
				
		for(Course course : this.courseList){
			 String listElement = course.getCode()  + " - " + course.getName();
			
			  listmodel.addElement(listElement);
		}
		
				
		final JList<String> list1 = new JList<String>(listmodel);
		final DefaultListModel<String> listmodel2 = new DefaultListModel<String>();
		list1.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				
				if (!e.getValueIsAdjusting()) {
	                  //System.out.println(list1.getSelectedValue());
					  listmodel2.removeAllElements();
					  for(SUClass suClass : classList){
						 
						  Course crs = suClass.getCourse();
						  String[] arr = list1.getSelectedValue().split("-");
						  
						  System.out.println(arr[0]);
						  if(crs != null && crs.getCode().trim().equals(arr[0].trim())){
							  System.out.println(list1.getSelectedValue().substring(0, 8).trim());
							  
							  
							  listmodel2.addElement(crs.getCode()+ " - " + suClass.getSection() + " - " + suClass.getInstructorName() );
						  }
					  }
	                  
	                }				
			}
		});
		
		
		
		
		 ListDataListener listDataListener = new ListDataListener() {
			 
		      @Override
			public void contentsChanged(ListDataEvent listDataEvent) {
		        appendEvent(listDataEvent);
		      }

			@Override
			public void intervalAdded(ListDataEvent listDataEvent) {
				// TODO Auto-generated method stub
				appendEvent(listDataEvent);
			}

			@Override
			public void intervalRemoved(ListDataEvent listDataEvent) {
				// TODO Auto-generated method stub
				 appendEvent(listDataEvent);
			}
		 };
		 
		 
		
		 
		 
		listmodel2.addListDataListener(listDataListener);
		final JList<String> list2 = new JList<String>(listmodel2);
		
		
		scroll.add(list1);
		courseFrame.getContentPane().add(scroll, BorderLayout.LINE_START);
		courseFrame.getContentPane().add(list2, BorderLayout.LINE_END);
		this.courseFrame.setVisible(true);
		
		
	}
	
	
	 private void appendEvent(ListDataEvent listDataEvent) {
	        switch (listDataEvent.getType()) {
	        case ListDataEvent.CONTENTS_CHANGED:
	          System.out.println("Type: Contents Changed");
	          break;
	        case ListDataEvent.INTERVAL_ADDED:
	          System.out.println("Type: Interval Added");
	          break;
	        case ListDataEvent.INTERVAL_REMOVED:
	          System.out.println("Type: Interval Removed");
	          break;
	        }
	        System.out.println(", Index0: " + listDataEvent.getIndex0());
	        System.out.println(", Index1: " + listDataEvent.getIndex1());
	        DefaultListModel<?> theModel = (DefaultListModel<?>) listDataEvent.getSource();
	        System.out.println(theModel);
	      }
	    
	
	
}
