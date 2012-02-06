import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RepaintManager;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class profReport extends JPanel{
	public static JFrame frame = new JFrame();
	public JTable table = new JTable();
	private List<Row> theRows;
	private JLabel ust = new JLabel("University of Santo Tomas", SwingConstants.CENTER);
	private JLabel ics = new JLabel("Information and Computer Studies Department", SwingConstants.CENTER);
	private JLabel sem = new JLabel("", SwingConstants.CENTER);
	private LocalTableModel theTableModel;
	private JButton print = new JButton("Print");
	private JButton export = new JButton("Export to CSV File..");
	private JPanel panel = new JPanel();
	private AbstractAction theRemoveRowAction;
	public static ArrayList <String>data = new ArrayList<String>();
	public static JFileChooser fc = new JFileChooser();
	public static CSVFileFilter filter = new CSVFileFilter();
	public profReport(){
		position();
		data.add("University of Santo Tomas");
		data.add("Information and Computer Studies Department");
		data.add("General List of Faculty Members");
		
		theRows = new LinkedList<Row>();
		theTableModel = new LocalTableModel();
		table = new JTable(theTableModel);
		table.setVisible( true );
		this.setLayout( null );
		panel.setLayout(null);
		frame.setContentPane( this );
		theRemoveRowAction = new AbstractAction("Remove row") {
			public void actionPerformed(ActionEvent e) {
				removeRow();
			}
		};
		export.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				String fName = "";
				
				fc.setFileFilter( filter );
				int returnVal = fc.showSaveDialog(new JPanel());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            try {
		            	System.out.println(file.getAbsolutePath() + " " + file.getCanonicalPath());
		            	fName = file.getAbsolutePath();
		            } catch (Exception ex) {
		            	return;
		            }
		            if (fName.endsWith(".csv"))
		            	System.out.println("Saving: " + fName);
		            else{
		            	System.out.println("Saving: " + fName+".csv");
		            	fName = fName + ".csv";
		            }
		            
					try {
						saveReport(fName);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        } else {
		        	System.out.println("Save command cancelled by user.");
		        }
			}
		});
		print.addActionListener(new ActionListener(){
			@SuppressWarnings("static-access")
			public void actionPerformed( ActionEvent e ){
				    PrinterJob printerJob = PrinterJob.getPrinterJob();
				    PageFormat PF = new PageFormat();
				    PF.setOrientation(PF.PORTRAIT);
				    Book book = new Book();
				    book.append(new PrintPage(), PF);
				    printerJob.setPageable(book);
				    boolean doPrint = printerJob.printDialog();
				    if (doPrint) {
				      try {
				        printerJob.print();
				      }
				      catch (PrinterException exception) {
				        System.err.println("Printing error: " + exception);
				      }
				    }
			}
		});
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(132);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		sem.setText(SchedulerBeta.s+"   SY: "+SchedulerBeta.sy);
		data.add(sem.getText());
		data.add("Name, No. of Prepn's, Lec Units, Lab Units, Total Units,Overload Units");
		addComponent( this, print, 20,15,100,20);
		addComponent( this, export, 320, 15, 150, 20);
		
		
		addComponent( panel, ust, 0,7,450,20);
		addComponent( panel, ics, 0,32,450,20);
		addComponent( panel, sem, 0,57,450,20);
		
		addComponent( panel, new JScrollPane(table), 0,90,450,400);
		
		addComponent( this, panel, 20,50,500,600);
		print.setVisible( true );
		export.setVisible( true ); 
		panel.setVisible( true );
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				validateSelection();
			}
		});
		validateSelection();
		reload();
	}
	
	private class Row {
		private String name;
		private int preps,lecs, labs, total, overload;
 
		public Row(String one, int two, int three, int four, int five, int six) {
			name = one;
			preps = two;
			lecs = three;
			labs = four;
			total = five;
			overload = six;
		}
 
		public String getFirstField() {
			return name;
		}
 
		public int getSecondField() {
			return preps;
		}
		
		public int getThirdField() {
			return lecs;
		}
		public int getFourthField() {
			return labs;
		}
		public int getFifthField() {
			return total;
		}
		public int getSixthField() {
			return overload;
		}
	}
	
	@SuppressWarnings("unused")
	private class LocalTableModel extends AbstractTableModel {
		public String getColumnName(int column) {
			if (column == 0)
				return "Name";
			else if (column == 1)
				return "Prep'ns";
			else if (column == 2)
				return "Lec Units";
			else if (column == 3)
				return "Lab Units";
			else if (column == 4)
				return "Total Units";
			else
				return "Overload";
		}
 
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int columnIndex) {
			if (columnIndex == 0)
				return String.class;
			else if (columnIndex == 1)
				return Integer.class;
			else if (columnIndex == 2)
				return Integer.class;
			else if (columnIndex == 3)
				return Integer.class;
			else if (columnIndex == 4)
				return Integer.class;
			else
				return Integer.class;
		}
 
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
 
		public int getRowCount() {
			return theRows.size();
		}
 
		public int getColumnCount() {
			return 6;
		}
 
		public Object getValueAt(int rowIndex, int columnIndex) {
			Row row = theRows.get(rowIndex);
			if (columnIndex == 0)
				return row.getFirstField();
			else if (columnIndex == 1)
				return row.getSecondField();
			else if (columnIndex == 2)
				return row.getThirdField();
			else if (columnIndex == 3)
				return row.getFourthField();
			else if (columnIndex == 4)
				return row.getFifthField();
			else
				return row.getSixthField();
		}
	}
	private void validateSelection() {
		theRemoveRowAction.setEnabled(table.getSelectedRow() != -1);
	}

	private void removeRow() {
		int index = table.getSelectedRow();
		if (index == -1) return;
		theRows.remove(index);
		theTableModel.fireTableRowsDeleted(index, index);
		index = Math.min(index, theRows.size() - 1);
		if (index >= 0) {
			table.getSelectionModel().setSelectionInterval(index, index);
		}
	}
 
	@SuppressWarnings("static-access")
	private void reload() {
		int size = theRows.size();
		if (size > 0) {
			theRows.clear();
			theTableModel.fireTableRowsDeleted(0, size - 1);
		}
		for (int i = 0; i < SchedulerBeta.sc.p.length; i++) {
			int overload = SchedulerBeta.sc.unitLecCt[i]+SchedulerBeta.sc.unitLabCt[i];
			if ( overload > 24 )
				overload = overload - 24;
			else
				overload = 0;
			theRows.add(new Row(SchedulerBeta.sc.p[i].name,SchedulerBeta.sc.crsCt[i],SchedulerBeta.sc.unitLecCt[i],SchedulerBeta.sc.unitLabCt[i],SchedulerBeta.sc.unitLecCt[i]+SchedulerBeta.sc.unitLabCt[i],overload));
			
			
			data.add(SchedulerBeta.sc.p[i].name+","+SchedulerBeta.sc.crsCt[i]+","+SchedulerBeta.sc.unitLecCt[i]+","+SchedulerBeta.sc.unitLabCt[i]+","+(SchedulerBeta.sc.unitLecCt[i]+SchedulerBeta.sc.unitLabCt[i])+","+overload);
		}
		theTableModel.fireTableRowsInserted(theRows.size(), theRows.size());
	}
	@SuppressWarnings("unused")
	private void addRow() {
		int size = theRows.size();
		theTableModel.fireTableRowsInserted(size, size);
	}
	public void position(){
		int x, y;
		Point topLeft = SchedulerBeta.window.getLocationOnScreen();
		Dimension parentSize = SchedulerBeta.window.getSize();

		  if (parentSize.width > 500) 
		    x = ((parentSize.width - 500)/2) + topLeft.x;
		  else 
		    x = topLeft.x;
		   
		  if (parentSize.height > 520) 
		    y = ((parentSize.height - 520)/2) + topLeft.y;
		  else 
		    y = topLeft.y;
		   
		frame.setLocation (x, y);
		frame.setSize( new Dimension( 500, 600 ) );
		frame.setTitle("General List of Faculty Members");
		frame.setLayout( null );
		frame.setVisible(true);
		frame.requestFocus();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	private static void addComponent( Container container, Component c, int x, int y, int width, int height ) {
		c.setBounds( x, y, width, height );
		container.add( c );
	}
	
	
	/*
	 * Print and save stuff
	 */
	double XSC = 1;
	double YSC = 1;
	public static void saveReport(String fname) throws IOException{
			BufferedWriter pw = new BufferedWriter ( new FileWriter ( fname, false ) );
			for (int x = 0; x < data.size(); x++){
				pw.write(data.get(x));
				pw.newLine();
			}
			pw.close();
	}
	public static class CSVFileFilter extends javax.swing.filechooser.FileFilter {
	    public boolean accept(File f) {
	        return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
	    }
	    public String getDescription() {
	        return ".csv files";
	    }
	}
	class PrintPage implements Printable{
		 
	    public int print(Graphics g, PageFormat format, int pageIndex) {
	      Graphics2D g2D = (Graphics2D) g;
	      g2D.translate(format.getImageableX (), format.getImageableY ());
	      System.out.println("get i x " + format.getImageableX ());
	      System.out.println("get i x " + format.getImageableY ());
	      System.out.println("getx: " + format.getImageableWidth() );
	      System.out.println("getx: " + format.getImageableHeight() );
	      // scale to fill the page
	      double dw = format.getImageableWidth();
	      double dh = format.getImageableHeight();
	      @SuppressWarnings("unused")
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	      double xScale = dw / (1024 / XSC);
	      double yScale = dh / (768 / YSC);
	      double scale = Math.min(xScale, yScale);
	      System.out.println("" + scale);
	      g2D.scale( xScale, yScale);
	      ((JPanel)panel).paint(g);
	      return Printable.PAGE_EXISTS;
	    }
	 
	    public void disableDoubleBuffering(Component c) {
	      RepaintManager currentManager = RepaintManager.currentManager(c);
	      currentManager.setDoubleBufferingEnabled(false);
	    }
	 
	    public void enableDoubleBuffering(Component c) {
	      RepaintManager currentManager = RepaintManager.currentManager(c);
	      currentManager.setDoubleBufferingEnabled(true);
	    }
	  }
}
