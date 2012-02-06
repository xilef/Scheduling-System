import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;


@SuppressWarnings( "serial" )
public class coursesPanel extends JPanel {
	public static boolean assCrs = false, fromTree = false;
	private static ButtonGroup LL = new ButtonGroup();
	private static String unit[] = { "----", "1", "2", "3", "4", "5" };
	private static String cString[] =new String[100];
	private static String courses[][][] = {
		{{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""}},
		{{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""}},
		{{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""},{"","","","","","","","","","","","","","",""}},
		};
	private static JList courseList;
	private static JList rightList;
	
	private static DefaultListModel listAdd;
	private static DefaultListModel courseAdd;
	private static SortedListModel listAdda;
	private static DefaultComboBoxModel courseModel = new DefaultComboBoxModel();
	
	private static JLabel crsSelect = new JLabel( "Select a course to edit: " );
	private static JLabel crsAssign = new JLabel( "Select a major and level to edit its curriculum:" );
	private static JLabel crsCode = new JLabel( "Course Code:" );
	private static JLabel crsDesc = new JLabel( "Course Description:" );
	private static JLabel crsType = new JLabel( "Course Type:" );
	private static JLabel crsUnit = new JLabel( "Number of units:" );
	private static JLabel label3 = new JLabel();
	private static JLabel courseLabel = new JLabel("Courses:");
	
	private static JTextField crsCodeTF = new JTextField();
	private static JTextField crsDescTF = new JTextField();
	
	private static JComboBox crsUnitTF = new JComboBox( unit );
	private static JComboBox courseCBox;
	
	private static JLabel mj = new JLabel("Major:");
	private static JLabel lv = new JLabel("Level:");
	public static JComboBox majors, years;
	public static String coursemajors[] = {"---------","Computer Science","Information Technology","Information Systems"};
	public static String levels[] = {"---------", "First Year", "Second Year","Third Year","Fourth Year"};
	
	private static JButton addBtn = new JButton( "Save" );
	private static JButton clrBtn = new JButton( "Clear" );
	private static JButton clrListBtn = new JButton( "Clear" );
	private static JButton saveBtn = new JButton( "Save" );
	private static JButton leftBtn = new JButton( "<<" );
	private static JButton rightBtn = new JButton( ">>" );
	public static JButton delBtn = new JButton( "Delete" );
	
	private static JScrollPane listSPane;
	private static JScrollPane list2SPane;
	private static MyListSelectionListener courseListen = new MyListSelectionListener();
	private static MyListSelectionListener rightListen = new MyListSelectionListener();
	private static int semInt = 0;
	private static int coursehold = 0;
	private static int majorInt = 0;
	private static int year = 0;
	private static int courseCtr[][] = new int[3][4];
	private static int courseTask, previous = 0;
	private static ArrayList <String> course;

	private static String[] types = {"----","Lecture","Computer Lab","Physics Lab","IT Lab","Other Labs"};
	private static JComboBox roomTypes = new JComboBox(types);
	
	/*
	 * Constructor
	 */
	public coursesPanel() {
		majors = new JComboBox( coursemajors );
		years = new JComboBox( levels );
		course = new ArrayList <String> ();
		listAdd = new DefaultListModel();
		listAdd.addElement( "" );
		courseAdd = new DefaultListModel();
		courseAdd.addElement( "" );
		listAdda = new SortedListModel(listAdd);
		courseList = new JList( listAdda );
		listSPane = new JScrollPane( courseList );
		rightList = new JList( courseAdd );
		list2SPane = new JScrollPane( rightList );
		courseCBox = new JComboBox( cString );
		
		years.addActionListener( new yearListener() );
		majors.addActionListener( new majorListener() );
		
		courseCBox.addActionListener( new courseBoxListener() );
		clrBtn.addActionListener( new clrCourseListener() );
		addBtn.addActionListener( new addCourseListener() );
		delBtn.addActionListener( new delCourseListener() );
		clrListBtn.addActionListener( new clrlistCourseListener() );
		saveBtn.addActionListener( new saveCourseListener() );

		courseList.addListSelectionListener( courseListen );
		rightList.addListSelectionListener( rightListen );
		
		addComponent( this, mj, 20, 55, 100, 20 );
		addComponent( this, lv, 195, 55, 100, 20 );
		addComponent( this, majors, 20, 80, 160, 20 );
		addComponent( this, years, 195, 80, 100, 20);
		
		addComponent( this, courseCBox, 145, 35, 100, 20 );
		addComponent( this, crsSelect, 5, 35, 150, 20 );
		addComponent( this, crsCode, 3, 65, 100, 20 );
		addComponent( this, crsCodeTF, 130, 65, 150, 20 );
		addComponent( this, crsDesc, 3, 95, 120, 20 );
		addComponent( this, crsDescTF, 130, 95, 300, 20 ); 
		addComponent( this, roomTypes, 130, 130, 150, 20);
		addComponent( this, crsType, 4, 130, 100, 20 );
		addComponent( this, crsUnit, 4, 165, 100, 20 );
		addComponent( this, crsUnitTF, 130, 165, 50, 20 );
		addComponent( this, addBtn, 50, 210, 100, 20 );
		addComponent( this, clrBtn, 200, 210, 100, 20 );
		addComponent( this, delBtn, 145, 210, 100, 20 );

		addComponent( this, courseLabel, 20, 112, 100, 20);
    	addComponent( this, label3, 310, 112, 200, 20);
		addComponent( this, listSPane, 20, 137, 170, 200 );
		addComponent( this, list2SPane, 310, 137, 170, 200 ); 
		addComponent( this, rightBtn, 220, 227, 55, 20 );
		addComponent( this, leftBtn, 220, 327, 55, 20 );
		addComponent( this, clrListBtn, 260, 380, 75, 20 ); 
		addComponent( this, saveBtn, 110, 380, 75, 20 ); 
		
		addComponent( this, crsAssign, 5, 35, 445, 20 );
		
		rightBtn.addActionListener( new rightHandler() );
		leftBtn.addActionListener( new leftHandler() );
			
		setvisible( false );
		loadCourses(); 
	}
	
	private static void loadCourses() {
		listAdd.clear();
		course.clear();
		String temp[] = new String[0];
		try {
			temp = file_handle.getCourselist( "" );
			Arrays.sort(temp);
		} 
		catch ( IOException e2 ) {
			e2.printStackTrace();
			return;
		}
		courseCBox.setModel( courseModel );
		courseModel.removeAllElements();
		courseModel.addElement( "-------" );
		for( int y = 0; y < temp.length; y++ ) {
			course.add( temp[y] );
			listAdd.addElement( temp[y] );
			courseModel.addElement( temp[y] );
		}
	}
	public static void loadcourseAdd() {
		courseAdd.clear();
		for( int x = 0 ; x < courseCtr[majorInt-1][year]; x++ ) {
			courseAdd.insertElementAt( courses[majorInt-1][year][x], x );
			rightList.clearSelection();
			clrListBtn.setEnabled( true );
		}
	}
	public static void enablecourse( boolean flag ) {
		crsCode.setEnabled( true && flag );
		crsCodeTF.setEnabled( true && flag );
		crsDesc.setEnabled( true && flag );
		crsDescTF.setEnabled( true && flag );
		crsType.setEnabled( true && flag );
		crsUnit.setEnabled( true && flag );
		crsUnitTF.setEnabled( true && flag );
		addBtn.setEnabled( true && flag );
		clrBtn.setEnabled( true && flag );
		rightBtn.setEnabled( true && flag );
		leftBtn.setEnabled( true && flag );
		saveBtn.setEnabled( true && flag );
 		listSPane.setEnabled( true && flag );
		courseList.setEnabled( true && flag );
		clrListBtn.setEnabled( true && flag );
		rightList.setEnabled( true && flag );
		roomTypes.setEnabled( true && flag );
		if ( !flag ) 
			courseAdd.removeAllElements();		
	}
	public static void clearCourse() {
		roomTypes.setSelectedIndex( 0 );
		courseCBox.setSelectedIndex( 0 );
		crsCodeTF.setText( "" );
		crsDescTF.setText( "" );
		LL.clearSelection();
		crsUnitTF.setSelectedIndex( 0 );
	}
	private static void addComponent( Container container, Component c, int x, int y, int width, int height ){
		c.setBounds( x, y, width, height );
		container.add( c );
	}
	
	public static void labelsetVisible( boolean arg0 ) {
		label3.setVisible( arg0 );
		if ( !arg0 )
			courseAddremoveAllElements();
	}
	public void labelsetText( String arg0 ) {
		label3.setText( arg0 );
	}
	public void clrListBtnsetEnabled( boolean arg ) {
		clrListBtn.setEnabled( arg );
	}
	public void clrListBtnsetVisible( boolean arg ) {
		clrListBtn.setVisible( arg );
	}
	public void saveBtnsetEnabled( boolean arg ) {
		saveBtn.setEnabled( arg );
	}
	public void saveBtnsetVisible( boolean arg ) {
		saveBtn.setVisible( arg );
	}
	public void delBtnsetVisible( boolean arg ) {
		delBtn.setVisible( arg );
	}
	public void delBtnsetEnable( boolean arg ) {
		delBtn.setEnabled( arg );
	}
	public void rightBtnsetEnabled( boolean arg ) {
		rightBtn.setEnabled( arg );
	}
	public void leftBtnsetEnabled( boolean arg ) {
		leftBtn.setEnabled( arg );
	}
	public void setmajorInt( int in ) {
		majorInt = in;
		fromTree = true;
		majors.setSelectedIndex( in );
	}
	public void setyear( int in ) {
		year = in;
		fromTree = true;
		years.setSelectedIndex( in + 1 );
	}
	public void setsemInt( int in ) {
		semInt = in;
	}
	public void setcourseCtr( int[][] in ) {
		courseCtr = in;
	}
	public void setcoursehold( int in ) {
		coursehold = in;
	}
	public void setcourses( String[][][] in ) {
		courses = in;
	}
	public void setcourseTask ( int in ) {
		courseTask = in;
	}
	public void setvisible( boolean aFlag ) {
		crsCode.setVisible( aFlag );
		crsCodeTF.setVisible( aFlag );
		crsDesc.setVisible( aFlag );
		crsDescTF.setVisible( aFlag );
		crsUnit.setVisible( aFlag );
		crsType.setVisible( aFlag);
		crsUnitTF.setVisible( aFlag );
		addBtn.setVisible( aFlag );
		clrBtn.setVisible( aFlag );
		crsSelect.setVisible( aFlag );
		clrListBtn.setVisible( aFlag );
		saveBtn.setVisible( aFlag );
		crsAssign.setVisible( aFlag );
		label3.setVisible( aFlag );
		listSPane.setVisible( aFlag );
		list2SPane.setVisible( aFlag );
		rightBtn.setVisible( aFlag );
		leftBtn.setVisible( aFlag );
		courseCBox.setVisible( aFlag );
		crsSelect.setVisible( aFlag );
		roomTypes.setVisible( aFlag );
	}
	
	public void courseAddinsertElementAt( Object arg0, int arg1 ) {
		courseAdd.insertElementAt( arg0, arg1 );
	}
	public void rightListclearSelection() {
		rightList.clearSelection();
	}
	public void courseListclearSelection() {
		courseList.clearSelection();
	}
	public static void courseAddremoveAllElements() {
		courseAdd.removeAllElements();
	}
	
	/* first = false, second = true ->add course
	 * first = true, second = false -> assign course
	 * first = true, second = true -> edit course
	 * first = false, second = false ->assign course
	 */
	public static void course( boolean flag, boolean flag2, boolean f ) {
		crsCode.setVisible( true && flag2 );
		crsCodeTF.setVisible( true && flag2 );
		crsDesc.setVisible( true && flag2 );
		crsDescTF.setVisible( true && flag2 );
		roomTypes.setVisible( true && flag2 );
		crsUnit.setVisible( true && flag2 );
		crsType.setVisible( true && flag2 );
		crsUnitTF.setVisible( true && flag2 );
		addBtn.setVisible( true && flag2 && !f );
		clrBtn.setVisible( true && flag2 && !f );
		crsSelect.setVisible( true && flag2 );
		clrListBtn.setVisible( !( false || flag2 ) );
		saveBtn.setVisible( !( false || flag2 ) );
		
		crsAssign.setVisible( !( false || flag2 ) );
		years.setVisible( !( false || flag2 ) );
		majors.setVisible( !( false || flag2 ) );
		mj.setVisible( !( false || flag2 ) );
		lv.setVisible( !( false || flag2 ) );
		courseLabel.setVisible( !( false || flag2 ) );
		
		label3.setVisible( !( false || flag2 ) );
		listSPane.setVisible( !( false || flag2 ) );
		list2SPane.setVisible( !( false || flag2 ) );
		rightBtn.setVisible( !( false || flag2 ) );
		leftBtn.setVisible( !( false || flag2 ) );
		courseCBox.setVisible( true && flag && flag2 );
		crsSelect.setVisible( true && flag && flag2 );
		delBtn.setVisible( true && f );
	}
	public void combobox( boolean j ) {
		courseCBox.setVisible( j );
		crsSelect.setVisible( j );
		courseCBox.setEnabled( !j );
		crsSelect.setEnabled( !j );
	}
	
	public int getmajorInt() {
		return majorInt;
	}
	public int getyear() {
		return year;
	}
	public int getsemInt() {
		return semInt;
	}
	public int getcoursehold() {
		return coursehold;
	}
	public int[][] getcourseCtr() {
		return courseCtr;
	}
	public String[][][] getcourses() {
		return courses;
	}
	
	public static class majorListener implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			if ( !fromTree ){
				if (majors.getSelectedIndex() != 0){
					if (majors.getSelectedIndex() != previous)
						years.setSelectedIndex( 0 );
					years.setEnabled( true ); 
					}
				else{
					years.setSelectedIndex( 0 );
					years.setEnabled( false );
				}
			}
			else
				fromTree = false;
			
			previous = majors.getSelectedIndex();
		}
	}
	
	public static class yearListener implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			if ( years.getSelectedIndex() == 0 ){
				labelsetVisible( false );
			}
			else
				years.setEnabled( true );
			if ( !fromTree ){
				if ( years.getSelectedIndex() != 0 ){
					majorInt = majors.getSelectedIndex();
					year = years.getSelectedIndex()-1;
					SchedulerBeta.yearTree.setSelectionPath(new TreePath(SchedulerBeta.levels[majors.getSelectedIndex()-1][years.getSelectedIndex()-1].getPath()));
				}
				fromTree = false;
			}
				
		}
	}
	private static class courseBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if( courseCBox.getSelectedIndex()!=0 )
			{
				enablecourse( true );
				coursehold = courseCBox.getSelectedIndex();
				if ( courseCBox.getSelectedIndex() > 0 ) {
						String cDetails = courseCBox.getSelectedItem().toString();
					try {
						String details = file_handle.getCourseDetails( cDetails );
						StringTokenizer st = new StringTokenizer( details, "," );
						if ( st.hasMoreTokens() ) 
							crsCodeTF.setText( st.nextToken() );
						else 
							crsCodeTF.setText( "" );
							
						if ( st.hasMoreTokens() ) 
							crsDescTF.setText( st.nextToken() );
						else 
							crsDescTF.setText( "" );
							
						if ( st.hasMoreTokens() ) {
							/*String llString = st.nextToken();
							if( llString.equals( "1" ) ) {
								lab.setSelected( true );
								lec.setSelected( false );
							}
							else if( llString.equals( "2" ) ) {
								lec.setSelected( true );
								lab.setSelected( false );
							}*/
							int room = Integer.parseInt( st.nextToken() );
							if( room == 1 )
								roomTypes.setSelectedIndex( 2 );
							else if( room == 2 )
								roomTypes.setSelectedIndex( 1 );
							else
								roomTypes.setSelectedIndex( room );
						}
							
						if ( st.hasMoreTokens() ) 
							crsUnitTF.setSelectedIndex( Integer.parseInt( st.nextToken() ) );
						else 
							crsUnitTF.setSelectedIndex( 0 );
					} catch ( IOException e1 ) {
						e1.printStackTrace();
						return;
					}
				}
				else if ( courseCBox.getSelectedIndex()!=1 )
					enablecourse( false );
					
				if( courseTask == 4 ) { 
					enablecourse( false );
					delBtn.setEnabled( true );
				}
				else {
					enablecourse( true );
					delBtn.setEnabled( false );
					delBtn.setVisible( false );
				}
			}
			else if ( courseCBox.getSelectedIndex() == 0 ) {
				if ( courseTask != 1)
					enablecourse( false );
				if ( courseTask == 4 ) {
					course( true, true, true );
					delBtn.setEnabled( false );
				}
				else
					course( true, true, false );
				clearCourse();
				coursehold = 0;
			}
		}
	}
	private static class MyListSelectionListener implements ListSelectionListener {
		public void valueChanged( ListSelectionEvent e ) {
			if( courseList.getSelectedIndex() != -1 )
				rightBtn.setEnabled( true );
			
			if( rightList.getSelectedIndex() != -1 ){
				leftBtn.setEnabled( true );
				clrListBtn.setEnabled( true );
			}
		}	
	}
	
	/*
	 * Button handlers
	 */
	private static class addCourseListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			String courseDetails[] = new String[4];
			
			if( crsCodeTF.getText().equals( "" ) ) {
				JOptionPane.showMessageDialog( null, "Please enter a valid course code.", 
						"Error", JOptionPane.ERROR_MESSAGE  );
				return;
			}
			if ( course.contains(crsCodeTF.getText()) ) {
				int j = JOptionPane.showOptionDialog(null, "The course code already exists. Overwrite?", "", JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, new Object[] {"Yes", "No"}, null);
				if (j != 0)
					return;
			}
			if( crsDescTF.getText().equals( "" ) ) {
				JOptionPane.showMessageDialog( null, "Please enter a course description", 
						"Error", JOptionPane.ERROR_MESSAGE  );
				return;
			}
			if( roomTypes.getSelectedIndex() == 0 ) {
				JOptionPane.showMessageDialog( null, "Please select the course type.", 
						"Error", JOptionPane.ERROR_MESSAGE  );
				return;
			}
			if( crsUnitTF.getSelectedIndex()==0 ) {
				JOptionPane.showMessageDialog( null, "Please select the number of units.", 
						"Error", JOptionPane.ERROR_MESSAGE  );
				return;
			}

			else{
				courseDetails[0] = crsCodeTF.getText();
				courseDetails[1] = crsDescTF.getText();
				
				if ( coursehold==0 )
					courseModel.addElement( courseDetails[0] );
				else
					coursehold = 0;
				if ( roomTypes.getSelectedIndex() == 2 ){
					if( courseDetails[0].charAt( courseDetails[0].length() - 1 ) != 'L' )
						courseDetails[0] += "L";
						courseDetails[2] = "1";
				}
				else if ( roomTypes.getSelectedIndex() == 1 )
					courseDetails[2] = "2";
				else if ( roomTypes.getSelectedIndex() == 3 )
					courseDetails[2] = "3";
				else if ( roomTypes.getSelectedIndex() == 4 )
					courseDetails[2] = "4";
				else if ( roomTypes.getSelectedIndex() == 5 )
					courseDetails[2] = "5";
					
				courseDetails[3] = String.valueOf( crsUnitTF.getSelectedIndex() );
				
				try  {
					file_handle.setCourseDetails( courseDetails );
				} catch ( IOException e1 ) {
					e1.printStackTrace();
					return;
				}
				JOptionPane.showMessageDialog( null, "Course has been successfully added.");
				
				//courseCBox.setSelectedIndex( 1 );
				clearCourse();
				enablecourse( true );
				loadCourses();
			}
		}	
	}
	private static class delCourseListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			int o, a;
	 		a = courseCBox.getSelectedIndex(); 
	 		o = JOptionPane.showConfirmDialog( null, "Are you sure you want to remove " + courseCBox.getSelectedItem() + "?" ,"Delete Course",JOptionPane.YES_NO_OPTION );
	 		if( o == 0 ) {
	 			try {
	 				file_handle.removeCourse( courseCBox.getSelectedItem().toString() );
	 			} catch ( IOException e1 ) {
	 				e1.printStackTrace();
	 				return;
	 			}
	 			listAdd.removeElement( courseCBox.getSelectedItem() );
	 			courseModel.removeElementAt( a );
	 			clearCourse();
	 			JOptionPane.showMessageDialog( null, "Deleted." );
	 		}	
		}
	}
	private static class clrCourseListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			crsCodeTF.setText( "" );
			crsDescTF.setText( "" );
			LL.clearSelection();
			crsUnitTF.setSelectedIndex( 0 );
			roomTypes.setSelectedIndex( 0 );
		}
	}
	private static class clrlistCourseListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			int o = 0;
			if ( courseCtr[majorInt-1][year]>0 ) {
				o = JOptionPane.showConfirmDialog( null,"Are you sure you want to remove all courses for this level? " );
				if ( o == 0 ) {
					courseAdd.removeAllElements();
					for( int x = 0 ; x < courseCtr[majorInt-1][year]; x++ )
						courses[majorInt-1][year][x]="";
					courseCtr[majorInt-1][year]=0;

					rightList.clearSelection();
					leftBtn.setEnabled( false );
					clrListBtn.setEnabled( false );
					saveBtn.setEnabled( false );
					courseList.clearSelection();
					JOptionPane.showMessageDialog( null,"Courses are removed." );
				}
			}
		}
	}
	private static class saveCourseListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			try {
				if (majorInt == 1)
					file_handle.setCSCourses( courses[0][year], semInt, year + 1 );
				else if (majorInt == 2)
					file_handle.setITCourses( courses[1][year], semInt, year + 1 );
				else
					file_handle.setISCourses( courses[2][year], semInt, year + 1 );
			} catch ( IOException e1 ) {
				e1.printStackTrace();
				return;
			}
			JOptionPane.showMessageDialog( null,"Courses are saved." );
		}
	}
	private static class rightHandler implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			assCrs = true;
			if ( courseAdd.contains( courseList.getSelectedValue().toString() ) ) {
				JOptionPane.showMessageDialog( null, "Already in the list.", 
						"Error", JOptionPane.ERROR_MESSAGE  );
				return;
			}
			saveBtn.setEnabled( true );
			if ( courseCtr[majorInt-1][year] == 0 )
				clrListBtn.setEnabled( false );
			
			courses[majorInt-1][year][courseCtr[majorInt-1][year]] = ( String ) courseList.getSelectedValue();
			courseCtr[majorInt-1][year]++;
			courseList.clearSelection();
			loadcourseAdd();
			rightBtn.setEnabled( false );
			leftBtn.setEnabled( false );
		}
	}
	private static class leftHandler implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			assCrs = true;
			for( int x = 0 ; x < courseCtr[majorInt-1][year]; x++ ) {
				if( rightList.getSelectedIndex()==x ) {
					if ( x<courseCtr[majorInt-1][year]-1 )
						for ( int y = x + 1; y < courseCtr[majorInt-1][year]; y++ )
							courses[majorInt-1][year][y-1] = courses[majorInt-1][year][y];
					else
						courses[majorInt-1][year][x] = "";
					courseCtr[majorInt-1][year]--;
					break;
				}
			}
			if ( courseCtr[majorInt-1][year] == 0 ) {
				clrListBtn.setEnabled( false );
				saveBtn.setEnabled( false );
			}
			courseAdd.removeElementAt( rightList.getSelectedIndex() );
			rightList.clearSelection();
			rightBtn.setEnabled( false );
			leftBtn.setEnabled( false );
		}	
	}
	
	/*
	 * Tester
	 */
	public static void main( String args[] ) {
		coursesPanel a = new coursesPanel();
		JFrame window = new JFrame();
		window.setContentPane( a );
		window.setLayout( null );
		window.setBounds( 300, 200, 300, 400 );
		window.setVisible( true );
	}
}
