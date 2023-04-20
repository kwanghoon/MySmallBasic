package com.coducation.smallbasic.gui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.coducation.smallbasic.Value;


public class MonitoringTable extends JPanel
{
	private JTable table;
	DefaultTableModel tableModel;
	
	public static final float TABLE_FONT_SIZE = 16.0f;
	
	public MonitoringTable()
	{
		String[] columnName = {"변수명", "값"};
		Object rowData[][] = {};
		tableModel = new DefaultTableModel(rowData, columnName)
		{
			//value column만 edit 가능
		     public boolean isCellEditable(int row, int col) 
		     {
		        if (col== 1) 
		        {
		            return true;
		        } else 
		        {
		            return false;
		        }
		    }
		};
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		//테이블 설정 변경
		table.setFont(table.getFont().deriveFont(TABLE_FONT_SIZE));
		table.setRowHeight(22);
		
		
		add(scrollPane, BorderLayout.CENTER);
	}

	public void renewValueInfo(HashMap<String, String> variableMap)
	{
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		
		for(String key : variableMap.keySet())
		{
			String value = variableMap.get(key);
			
			Object[] addValue = {key, value.toString()};
			tableModel.addRow(addValue);
			
		}
		
		//key 칼럼 가운데 정렬
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setCellRenderer(cellRenderer);
	}
	
	public void clear()
	{
		table.removeAll();
	}
}
