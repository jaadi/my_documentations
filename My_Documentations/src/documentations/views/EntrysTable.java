package documentations.views;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class EntrysTable extends JTable {
	
	private static final long serialVersionUID = 1L;	
	
	public EntrysTable(DefaultTableModel defaultTableModel) {
		super(defaultTableModel);
		setPreferredScrollableViewportSize(new Dimension(500, 70));
		setFillsViewportHeight(true);		
		setRowHeight(20);
	}

	public void adjustColumnWidth(){		
				
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
		    column = getColumnModel().getColumn(i);
		    if (i == 0) {		    	
		        column.setMaxWidth(40); 		        
		    } else if (i == 1){
		    	column.setMaxWidth(60);		        
		    }else if (i == 3){
		    	column.setMaxWidth(80);		        
		    }
		}		
	}
}
