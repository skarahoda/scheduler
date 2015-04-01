package io.scheduler.gui;


import io.scheduler.data.BannerParser;

import java.awt.EventQueue;
import java.io.IOException;


public class ApplicationWindow {



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					BannerParser.getCourses("201402");
					new MainFrame();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
