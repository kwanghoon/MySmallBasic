package com.coducation.smallbasic.gui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MonitoringTable extends JPanel
{
	private JTable table;
	DefaultTableModel tableModel;
	
	public MonitoringTable()
	{
		String[] columnName = {"변수명", "값"};
		Object rowData[][] = {};
		tableModel = new DefaultTableModel(rowData, columnName);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		add(scrollPane, BorderLayout.CENTER);
	}

	public void renewValueInfo(HashMap<String, String> variableMap)
	{
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		
		for(String key : variableMap.keySet())
		{
			Object[] addValue = {key, variableMap.get(key)};
			tableModel.addRow(addValue);
		}
	}
	
	public void clear()
	{
		table.removeAll();
	}
}
