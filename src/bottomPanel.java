import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import com.sun.rowset.internal.Row;

@SuppressWarnings("serial")
public class bottomPanel extends JPanel{
	@SuppressWarnings("unused")
	private static List<Row> theRows;
	static int allCourse = 0;
	static String list[] = {""};
	static Course c[] = null;
	static JTextArea courses = new JTextArea();
	static LocalTableModel tableModel;
	static JTable theTable;
	@SuppressWarnings("unchecked")
	static Vector rows = new Vector();
	@SuppressWarnings("unchecked")
	static Vector columns;
	public static JFrame frame = new JFrame();
	static String[] columnNames = {"Code","Description","Unassigned","Instructor"};

	
	public static void seeTable( boolean j ){
		theTable.setVisible( j );
	}
	
	public static void clrCourse(){
		rows.removeAllElements();
		theTable.clearSelection();
	}
	
	public Vector <String>addRow(String code, String desc, String prof, String room){
		Vector <String>t = new Vector<String>();
		t.addElement(code);
		t.addElement(desc);
		t.addElement(prof);
		t.addElement(room);
		return t;
	}

	@SuppressWarnings("unchecked")
	public void addColumns(String[] colName){
	columns = new Vector<String>();
	 for(int i=0;i<colName.length;i++)
		 columns.addElement(colName[i]);
	}
	
	private class LocalTableModel extends DefaultTableModel {
		public String getColumnName(int column) {
			String temp = "";
			if (column == 0)
				temp = columnNames[0];
			else if (column == 1)
				temp = columnNames[1];
			else if (column == 2)
				temp = columnNames[2];
			else if (column == 3)
				temp = columnNames[3];
			return temp;
		}
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
 
		public int getRowCount() {
			return rows.size();
		}
 
		public int getColumnCount() {
			return columns.size();
		}
 
		@SuppressWarnings("unchecked")
		public Object getValueAt(int rowIndex, int columnIndex) {
            return ((Vector)rows.elementAt(rowIndex)).elementAt(columnIndex);
		}
		
		@SuppressWarnings("unchecked")
		public void setValueAt(Object value, int row, int column) {
            ((Vector)rows.elementAt(row)).setElementAt(value, column);
            fireTableCellUpdated(row, column);
        }
	}
  
	public static void setTableData(String room, String prof, String code){
		int z = 0;
		for (z = 0; z < tableModel.getRowCount(); z++){
			if (theTable.getValueAt(z, 0).toString().equals(code)){
				break;
				}
		}
		if ( z >= tableModel.getRowCount() )
			return;
		theTable.setValueAt(room, z, 2);
		theTable.setValueAt(prof, z, 3);
	}

	public bottomPanel(){
		
		addColumns(columnNames);
		tableModel = new LocalTableModel();
		theTable = new JTable( tableModel );
		tableModel.setDataVector(rows,columns);
		theTable.getTableHeader().setReorderingAllowed(false); 
		
		setVisible( true ); 
		setLayout( null );
		theTable.setVisible( true );
		theTable.setPreferredScrollableViewportSize(new Dimension(792, 200));
		theTable.setFillsViewportHeight(true);
		theTable.setOpaque( false );
		theTable.setAutoscrolls( true );
		theTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		theTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        theTable.getColumnModel().getColumn(1).setPreferredWidth(320);
        theTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        theTable.getColumnModel().getColumn(3).setPreferredWidth(230);
		
        JScrollPane a = new JScrollPane( theTable );
        add( a );
        a.setBounds( 0,0,792,205 );
        a.setVisible( true );
	}
	
	public static String getDesc( String cCode ){
		int num;
		for ( num = 0; num < allCourse; num++ ){
			if ( cCode.equals(c[num].id)){
				break;
			}
		}
		return c[num].description;
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public void setdetails( int major, int year, int sem ) {
		clrCourse();
		try {
			c = new Course[file_handle.getCourselistCount()];
			list = file_handle.getCourselist("");
			allCourse = list.length;
			for ( int num = 0; num < allCourse; num++ ) {
				c[num] = new Course( list[num] );
			}
			
			if (major == 1) 
				list = file_handle.getCSCourselist("", sem);
			else if (major == 2)
				list = file_handle.getITCourselist("", sem);
			else
				list = file_handle.getISCourselist("", sem);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		StringTokenizer s = new StringTokenizer(list[year],",");
		if (s!=null){
			while (s.hasMoreTokens()){
				String temp = s.nextToken();
				Vector r = new Vector();
				r = addRow( temp, getDesc( temp ), "", "");
				rows.addElement(r);
			}
			repaint();
		}
	}
}
