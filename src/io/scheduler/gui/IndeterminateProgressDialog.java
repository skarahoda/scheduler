package io.scheduler.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class IndeterminateProgressDialog {

	private SwingWorker<Void, Void> task;
	private SwingWorker<Void, Void> option;

	public IndeterminateProgressDialog(final String message,
			final Callable<Void> mainExecution, final Runnable cancelExecution) {
		this.option = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				JProgressBar progressBar = new JProgressBar();
				progressBar.setIndeterminate(true);
				Object[] messages = { message, progressBar };
				Object[] options = { "Cancel" };
				JOptionPane.showOptionDialog(null, messages, "Progress",
						JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,
						null, options, options[0]);
				return null;
			}

			@Override
			protected void done() {
				if (!this.isCancelled()) {
					task.cancel(true);
					if (cancelExecution != null)
						cancelExecution.run();
				}
				super.done();
			}

		};
		this.task = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {
					mainExecution.call();
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, "Invalid options.",
							"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane
							.showMessageDialog(
									null,
									"We cannot get information from the website please try again.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					JOptionPane
							.showMessageDialog(
									null,
									"Database is already in use, please close the database connection.",
									"Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Unkown error is occurred.", "Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void done() {
				option.cancel(true);
				super.done();
			}
		};
		this.task.execute();
		this.option.run();
	}
}
