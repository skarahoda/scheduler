package io.scheduler.gui;

import java.awt.Component;
import java.awt.List;

import io.scheduler.data.DegreeReq;
import io.scheduler.data.Program;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class PanelGraduationTable extends JPanel implements CustomComponent {
	private Program program;
	private ImmutableTableModel degreeReqModel;
	
	public PanelGraduationTable(Program program) {
		this.program = program;
		Object[] columnNames = program.getRequirements();
		degreeReqModel = new ImmutableTableModel(null, columnNames);
		JTable reqTable = new JTable(degreeReqModel);
		JScrollPane scrollPane = new JScrollPane(reqTable);
		scrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		scrollPane.setColumnHeaderView(reqTable.getTableHeader());
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
	}
	
	
	@Override
	public Object getObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
