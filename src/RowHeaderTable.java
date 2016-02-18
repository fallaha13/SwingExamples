import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class RowHeaderTable extends JFrame {

	public static void main(String[] args) {
//		RowHeaderTable rht = new RowHeaderTable();
//		rht.setVisible(true);
		String strDate = "021816";
		System.out.println(new Long(strDate));
	
		
	    SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
//	    Date date = new Date();
	    Date str = null;
		try {
			str = (Date) sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    System.out.println("formatted date in mm/dd/yy : " + df.format(str));

//		String out = date.fo
	}
	ErrorTable et = new ErrorTable();
	public RowHeaderTable() {
		super("Error Dialog");
		Dimension prefSize = new Dimension(700, 400);
		setSize(prefSize);
		setPreferredSize(prefSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		JButton closeButt = new JButton("Close");
		closeButt.setSize(new Dimension(80, 40));
		closeButt.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		        System.exit(0);
		    }
		});
		
		DefaultTableColumnModel cm = new DefaultTableColumnModel() {
			boolean first = true;
			public void addColumn(TableColumn tc) {
				if(first) {
					first = false;
					return;
				}
				tc.setMinWidth(150);
				super.addColumn(tc);
			}
		};
		
		//Create a column model that will serve as our row header table
		TableColumnModel rowHeaderModel = new DefaultTableColumnModel() {
			boolean first = true;
			public void addColumn(TableColumn tc) {
				if (first) {
					tc.setMaxWidth(tc.getPreferredWidth());
					super.addColumn(tc);
					first = false;
				}
				//drop the rest of the columns, this is the header only
			}
		};
		
		JTable jt = new JTable(et,cm){
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
		
		//set up header column and hook it to everything
		JTable headerColumn = new JTable (et,rowHeaderModel);
		jt.createDefaultColumnsFromModel();
		headerColumn.createDefaultColumnsFromModel();
		
		jt.setSelectionModel(headerColumn.getSelectionModel());
		
		 jt.setCellSelectionEnabled(true);
		 ListSelectionModel cellSelectionModel = jt.getSelectionModel();
		 cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		     
		        
		        int[] selectedRow = jt.getSelectedRows();
		        int[] selectedColumns = jt.getSelectedColumns();

		        for (int i = 0; i < selectedRow.length; i++) {
		        	String title = (String) jt.getValueAt(selectedRow[i],0);
		          for (int j = 0; j < selectedColumns.length; j++) {
					//Store the selected data into a variable
		            String selectedData = (String) jt.getValueAt(selectedRow[i], selectedColumns[j]);
		            //Show info on click
		            System.out.println("Selected: " + selectedData);
		            JOptionPane.showMessageDialog(rootPane, selectedData, title, NORMAL);
		            
		          }
		        }
		      }
	

		    });
		
		//make header look pretty
		headerColumn.setBackground(Color.lightGray);
		headerColumn.setColumnSelectionAllowed(false);
		headerColumn.setCellSelectionEnabled(false);
		
		//put in viewport that we can control
		JViewport jv = new JViewport();
		jv.setView(headerColumn);
		jv.setPreferredSize(headerColumn.getMaximumSize());
		
		//without shutting off autoResizemode our tables wont scroll correctly
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setResizable(false);
		
		
		//we have too manually attach the row header but the scroll pane will keep them in sync
		JScrollPane jsp = new JScrollPane(jt);
		jsp.setRowHeader(jv);
		jsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, headerColumn.getTableHeader());
		
		rootPane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 270;      //make this component tall
		c.ipadx = 670;
		c.weightx = 0.0;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 1;
		rootPane.add(jsp, c);
			
		GridBagConstraints d = new GridBagConstraints();
		d.insets = new Insets(10,0,0,0);
		d.gridx = 3;
		d.gridy = 2;
		rootPane.add(closeButt, d);
		
		JButton addRowButt = new JButton("add row");
		addRowButt.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	et.makeRowList("635435323423","Some new error description", "The date","The time");
		    }
		});
		GridBagConstraints e = new GridBagConstraints();
		d.gridx = 4;
		d.gridy = 3;
		rootPane.add(addRowButt, e);
	}

}
