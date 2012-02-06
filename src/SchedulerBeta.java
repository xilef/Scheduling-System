import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.*;


@SuppressWarnings("static-access")
public class SchedulerBeta {
	///objects
	public static JFrame window = new JFrame( "Scheduler v0.99.9");
	
	private static final String dataList[] = { "First Year", "Second Year", "Third Year", "Fourth Year" };
	private static String secName;
	public static String s, fName, d, sem, sy="";
	private static String allBlocks[];
	private static int blockNo[][] = new int[3][4];
	private static int totalBlock = 0;
	private static int days = 0, day;
	
	private static JSeparator jo = new JSeparator();
	private static JToolBar toolBar = new JToolBar("Tools");
	private static JButton newTool = new JButton();
	private static JButton saveTool = new JButton();
	private static JButton openTool = new JButton();
	private static JButton startTool = new JButton();
	public static JButton printTool = new JButton();
	public static JButton reportTool = new JButton();
	
	private static JPanel secTab = new JPanel();
	private static JPanel pTab = new JPanel();
	private static JPanel rTab = new JPanel();
	private static JPanel menuPane = new JPanel();
	private static JPanel leftPane = new JPanel();
	private static JPanel coursePane = new JPanel();
	private static JPanel profPane = new JPanel();
	private static JPanel roomPane = new JPanel();
	private static JPanel pbottomPane = new JPanel();
	private static JPanel rbottomPane = new JPanel();
	
	private static sectionPanel sPanel;
	private static coursesPanel cPanel;
	private static professorsPanel pPanel;
	private static roomsPanel rPanel;
	public static schedPanel scPanel;
	public static bottomPanel bPanel;
	public static pschedPanel pscPanel;
	public static rschedPanel rscPanel;
	
	public static JTextArea profRemarks = new JTextArea();
	public static JTextArea roomRemarks = new JTextArea();
	public static JTextPane todoList = new JTextPane();
	private static ArrayList <String> section = new ArrayList <String> ();
	private static JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private static JTabbedPane rightTPane = new JTabbedPane();
	private static JScrollPane treeSPane = new JScrollPane();	
	private static JScrollPane schedSPane;
	private static JScrollPane pschedSPane;
	private static JScrollPane rschedSPane;
	private static JSplitPane yearSplitPane = new JSplitPane();
	private static JScrollPane bottomPane = new JScrollPane();
	private static JScrollPane remarksPane ;
	private static JScrollPane rremarksPane ;
	private static JScrollPane todoPane ;
	
	private static JMenuBar menuBar = new JMenuBar();
	private static JMenu courseMenu = new JMenu( "Courses" );
	private static JMenu profMenu = new JMenu( "Professors" );
	private static JMenu resourceMenu = new JMenu( "Resources" );
	private static JMenu roomMenu = new JMenu( "Rooms" );
	private static JMenu saveMenu = new JMenu( "Save" );
	private static JMenu toolsMenu = new JMenu( "Tools" );
	private static JMenu helpMenu = new JMenu( "Help" );
	private static JMenuItem sections;
	private static JMenuItem play;
	private static JMenuItem saveDoc;
	private static JMenuItem printDoc;
	private static JMenuItem startSched;
	private static JMenuItem newDoc;
	
	private static JMenuItem about;
	private static JMenuItem report;
	private static JMenuItem help;
	
	public static DefaultMutableTreeNode root = new DefaultMutableTreeNode( "ICS Department" );
	private static DefaultMutableTreeNode csProg = new DefaultMutableTreeNode( "CS" );
	private static DefaultMutableTreeNode itProg = new DefaultMutableTreeNode( "IT" );
	private static DefaultMutableTreeNode isProg = new DefaultMutableTreeNode( "IS" );
	public static DefaultMutableTreeNode levels[][];
	public static DefaultMutableTreeNode rootBlocks = new DefaultMutableTreeNode( "Students" );
	public static DefaultMutableTreeNode rootProf = new DefaultMutableTreeNode( "Professors" );
	public static DefaultMutableTreeNode rootRoom = new DefaultMutableTreeNode( "Rooms" );
	public static DefaultMutableTreeNode pfs[];
	public static DefaultMutableTreeNode rms[];
	public static DefaultMutableTreeNode summer3[] = new DefaultMutableTreeNode[3];
	public static JTree yearTree = new JTree ( root );
	
	private static final JLabel label = new JLabel();
	private static boolean newSched = false;
	private static boolean start = false;

	public static JComboBox roomTask;
	public static JComboBox courseTask;
	public static JComboBox profTasks;
	
	public static JFileChooser fc = new JFileChooser();
	public static CSVFileFilter filter = new CSVFileFilter();
	public static String testing = "";
	public static String roomlist[] = new String[0];
	public static String proflist[] = new String[0];
	private static String sched[][][];
	public static boolean open = false, isTab = false, isRTab = false, isProf = false, isTree = false, isHelp = false, isAbout = false;
	public static automateSched sc;
	public static JPanel jp;
	public static Font font = new Font("Verdana",Font.CENTER_BASELINE,12);
	///end objects
	
	private static void genSplash(){
		JFrame splash = new JFrame();
		JLabel page = new JLabel( new ImageIcon("images\\Splash.png") );
		splash.setSize(new Dimension(600,400));
		splash.add(page);
		page.setVisible(true);
		splash.setVisible(true);
		splash.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = dim.width/3-100;
		int y = dim.height/3-100;
		splash.setLocation(x, y);
		
		try{
			Thread.sleep(5000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		splash.dispose();
	}
	private static void setmajorInt( int in ) {
		sPanel.setmajorInt( in );
		cPanel.setmajorInt( in );
	}
	private static void setyear( int in ) {
		sPanel.setyear( in );
		cPanel.setyear( in );
	}
	private static void sectionListener() {
		rightTPane.setSelectedIndex( 0 );
		Object path = yearTree.getModel().getRoot();
		yearTree.setSelectionPath( new TreePath( path ) );
		yearTree.setEnabled( true );
	}
	private static void tabStateChanged( ChangeEvent e ) {
		sPanel.majors.setSelectedIndex(0);
		sPanel.years.setSelectedIndex(0);
		cPanel.majors.setSelectedIndex(0);
		cPanel.years.setSelectedIndex(0);
		
		int i = rightTPane.getSelectedIndex();
		if ( i == 0)
			sectionListener();
		else if ( i == 1 )
			courseTask.setSelectedIndex( 0 );	

		else if ( i == 2 ) {
			profTasks.setSelectedIndex( 0 );
			yearTree.setEnabled( true );
		}
		else if ( i == 3 ) {
			roomTask.setSelectedIndex( 0 );
			yearTree.setEnabled( false );
		}
		else if (i == 4 ){
			yearTree.setEnabled( true );
			scPanel.setBorder( new TitledBorder( " Select a section from the tree." ) );
			bPanel.clrCourse();
			bPanel.seeTable( false );
			scPanel.seecells( false );
			scPanel.seedays( false );
			scPanel.seetime( false );
			scPanel.seejo( false );
			scPanel.seecells( false );
			
			for ( int j = 0; j<allBlocks.length; j++){
				if (testing.equals(allBlocks[j])){
					int yr, mj;
					yr = sPanel.getyear();
					mj = sPanel.getmajorInt();
					bPanel.seeTable( true );
					bPanel.setdetails(mj, yr, cPanel.getsemInt());	
					
					scPanel.setBorder( new TitledBorder( " Section: " + testing + " " ) );
					printTool.setEnabled( true );
					printDoc.setEnabled( true );
					scPanel.setVisible( true );
					scPanel.setSection( testing );
					scPanel.seedays( true );
					scPanel.seetime( true );
					scPanel.seejo( true );
					scPanel.seecells( true );
				}
			}
		}
		else if ( i == 5 ){
			yearTree.setEnabled( true );
			isTab = true;
			rightTPane.setSelectedComponent( pTab );
			pscPanel.setBorder( new TitledBorder( " Select a professor from the tree." ) );
			pscPanel.seecells( false );
			pscPanel.seedays( false );
			pscPanel.seetime( false );
			pscPanel.seejo( false );
			pscPanel.seecells( false );	
			for ( int j = 0; j<proflist.length; j++){
				if (testing.equals(proflist[j])){
					pscPanel.setBorder( new TitledBorder( " Name: " + testing + " " ) );
					printTool.setEnabled( true );
					printDoc.setEnabled( true );
					pscPanel.setVisible( true );
					pscPanel.setProf( testing );
					pscPanel.seedays( true );
					pscPanel.seetime( true );
					pscPanel.seejo( true );
					pscPanel.seecells( true );
					yearTree.setEnabled( true );
				}
			}
		}
		else if ( i == 6 ){
			yearTree.setEnabled( true );
			isRTab = true;
			rightTPane.setSelectedComponent( rTab );
			rscPanel.setBorder( new TitledBorder( " Select a room from the tree." ) );
			rscPanel.seecells( false );
			rscPanel.seedays( false );
			rscPanel.seetime( false );
			rscPanel.seejo( false );
			rscPanel.seecells( false );	
			roomRemarks.setText("");

			for ( int j = 0; j<roomlist.length; j++){
				if (testing.equals(roomlist[j])){
					rscPanel.setBorder( new TitledBorder( " Room: " + testing + " " ) );
					rscPanel.setVisible( true );
					rscPanel.setRoom( testing );
					rscPanel.seedays( true );
					rscPanel.seetime( true );
					rscPanel.seejo( true );
					rscPanel.seecells( true );
					yearTree.setEnabled( true );
				}
			}
			
		}
	}	
	private static void addComponent( Container container, Component c, int x, int y, int width, int height ) {
		c.setBounds( x, y, width, height );
		container.add( c );
	}
	public static void getID( String major ) {
		String yearLabel = new String( "" );
		if( major.equals( "CS" ) )
			setmajorInt( 1 );
		else if( major.equals( "IT" ) )
			setmajorInt( 2 );
		else if( major.equals( "IS" ) )
			setmajorInt( 3);

		switch( sPanel.getyear() ) {
		case 0: yearLabel = "First Year ";
			break;
		case 1: yearLabel = "Second Year ";
			break;
		case 2: yearLabel = "Third Year ";
			break;
		case 3: yearLabel = "Fourth Year ";
			break;
		}
		sPanel.labelsettext( yearLabel + major + " Major Sections:" );
		cPanel.labelsetText( yearLabel + major + " Courses:" );
		cPanel.labelsetVisible( false );
	}
	public static void getCourses( String sem ) {
		if ( sem.equals( "Summer" ) )
			return;
		else
			cPanel.setsemInt( Integer.valueOf( sem.substring( 0, 1 ) ) );
		int count = 0, m = 0;
		String temp[] = new String[4];
		StringTokenizer st;
	
		while( m<3){
			try {
				if ( m == 0 )
					temp = file_handle.getCSCourselist( "",cPanel.getsemInt() );
				else if ( m == 1 )
					temp = file_handle.getITCourselist( "",cPanel.getsemInt() );
				else if ( m == 2 )
					temp = file_handle.getISCourselist( "",cPanel.getsemInt() );
				} 
			catch ( IOException e2 ) {
				e2.printStackTrace();
				return;
			}
			int [][] temp1 = cPanel.getcourseCtr();
			String[][][] tmp = cPanel.getcourses();
			for( int y = 0; y < temp.length; y++ ) {
				count = 0;
				if ( temp[y] != null ) {
					st = new StringTokenizer( temp[y], "," );
					while ( st.hasMoreTokens() ) {
						tmp[m][y][count] = st.nextToken();
						count++;
						temp1[m][y]++;
					}
				}
			}
			cPanel.setcourseCtr( temp1 );
			m++;
		}
	}
	public static void createBlockArray(){
		String output[][] = sPanel.getoutput();
		String sec = "";
		int z = 0;
		
		for( int j = 0; j < 3;j++ ) {
			for( int i = 0; i < 4; i++ ) {
				StringTokenizer st = new StringTokenizer( output[j][i], " " );
				while ( st.hasMoreTokens() ){
					sec = st.nextToken();
					allBlocks[z] = sec;
					z++;
				}
			}
		}
		if (!section.isEmpty()){
			for( int j = 0; j<section.size(); j++){
				allBlocks[j] =  section.get(j);
			}
			output = new String[3][4];
			String save[] = {"","",""};
			int j = 0, i = -1;
			String major = section.get(0).substring(1,3);
			while( j<section.size() ){
				i++;
				while (j < section.size() && section.get(j).contains(major)) {
					save[i] += section.get(j) + " ";
					j++;
				}
				if (j < section.size() && !section.get(j).contains(major))
					major = section.get(j).substring(1,3);
			}
			
			try {
				file_handle.setSections(save);
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			
		}
		
	}
	private static class profTaskListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			pPanel.setvisible( false );
			pPanel.setprofTasks( profTasks.getSelectedIndex() );
			profTasks.setVisible( true );
		
			if ( profTasks.getSelectedIndex() == 1 )
				pPanel.aProfListener();
			
			if ( profTasks.getSelectedIndex() == 2 )
				pPanel.eProfListener();
			
			if ( profTasks.getSelectedIndex() == 3 )
				pPanel.eProfListener();
		}
	}
	private static class comboTaskListener implements ActionListener {
		public void actionPerformed ( ActionEvent e ) {
			int c = courseTask.getSelectedIndex();
			cPanel.setcourseTask( c );
			cPanel.clearCourse();
			Object path = yearTree.getModel().getRoot();
			yearTree.setSelectionPath( new TreePath( path ) );
			yearTree.setEnabled( false );
			cPanel.combobox( false );
			switch( c ){
				case 0:
					yearTree.setEnabled( false );
					cPanel.setvisible( false );
					break;
				case 1:
					cPanel.course( false, true, false );
					cPanel.enablecourse( true );
					cPanel.combobox( true );				
					break;
				case 2:
					cPanel.course( true, true, false );
					cPanel.enablecourse( false );
					break;
				case 3:
					cPanel.course( false, false, false );
					cPanel.labelsetVisible( false );
					path = yearTree.getModel().getRoot();
					yearTree.setSelectionPath( new TreePath( path ) );
					yearTree.setEnabled( true );
					break;
				case 4:
					cPanel.course( true, true, true );
					cPanel.delBtnsetEnable( false );
					path = yearTree.getModel().getRoot();
					yearTree.setSelectionPath( new TreePath( path ) );
					break;
			}
		}
	}
	private static class roomTaskListener implements ActionListener {
		public void actionPerformed( ActionEvent e ){
			rPanel.setvisible( false );
			rPanel.setenable( false );
			if ( roomTask.getSelectedIndex() == 0 )
				rPanel.setvisible( false );
			else {
				rPanel.setvisible( true );
				rPanel.setenable( true );
				rPanel.setselectedtask( roomTask.getSelectedIndex() );
				rPanel.clearRoom();
			}
		}
	}
	
	/*
	 * Menu Listeners
	 */
		/*
		 * Professor Menu
		 */
	private static class addProfListener implements ActionListener {
		public void actionPerformed( ActionEvent e ){
			rightTPane.setSelectedIndex( 2 );
			profTasks.setSelectedIndex( 1 );
			pPanel.removeBtnsetEnabled( false );
		}
	}
	private static class editProfListener implements ActionListener {
		public void actionPerformed( ActionEvent e ){
			rightTPane.setSelectedIndex( 2 );
			profTasks.setSelectedIndex( 2 );
			pPanel.removeBtnsetEnabled( false );
		}
	}
	private static class removeProfListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			rightTPane.setSelectedIndex( 2 );
			profTasks.setSelectedIndex( 3 );
		}
	}
		
		/*
		 * Course Menu
		 */
	private static class addCourseHandler implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			rightTPane.setSelectedIndex( 1 );
			courseTask.setSelectedIndex( 1 );
		}
	}
	private static class editCourseListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			rightTPane.setSelectedIndex( 1 );
			courseTask.setSelectedIndex( 2 );
		}
	}
	private static class assignCourseListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			rightTPane.setSelectedIndex( 1 );
			courseTask.setSelectedIndex( 3 );
		}
	}
	private static class deleteCourseListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			rightTPane.setSelectedIndex( 1 );
			courseTask.setSelectedIndex( 4 );
		}
	}
	
		/*
		 * Room Menu
		 */
	private static class addRoomListener implements ActionListener {
		public void actionPerformed ( ActionEvent e ){
			rightTPane.setSelectedIndex( 3 );
			roomTask.setSelectedIndex( 1 );
		}
	}
	private static class editRoomListener implements ActionListener {
		public void actionPerformed ( ActionEvent e ){
			rightTPane.setSelectedIndex( 3 );
			roomTask.setSelectedIndex( 2 );
		}
	}
	private static class delRoomListener implements ActionListener {
		public void actionPerformed ( ActionEvent e ){
			rightTPane.setSelectedIndex( 3 );
			roomTask.setSelectedIndex( 3 );
		}
	}
	
		/*
		 * File Menu
		 */
	private static class newdocHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			open = false;
			newDoc();
		}
	}
	private static class startSched implements ActionListener {
		public void actionPerformed ( ActionEvent e ){
			printDoc.setEnabled( false );
			if ( !start ){
				String day[] = {"5","6"};
				d = ( String )JOptionPane.showInputDialog( window,"Number of school days: ","Days",JOptionPane.PLAIN_MESSAGE,null,day,"----" );
				if ( d == null || d.equals( "----" ) || d.equals( "" )){
					d = "";
					return;
				}
				else{
					startAll();
					runSched();
				}
			}
			else{
				runSched();
			}
		}
	}
	private static class saveSched implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			savePrompt();
		}
		
	}
	private static class exitHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			System.exit( 0 );
		}
	}
	private static class openSched implements ActionListener{
		public void actionPerformed( ActionEvent e){
			open = true;
			sPanel.setComplete( true );
			fc.setFileFilter( filter );
			int returnVal = fc.showOpenDialog(new JPanel());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            try {
					fName = file.getCanonicalPath();
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
	            getData(fName);

	            if ( !newSched ){
	            	
	            	newAll();
	            	startOpen();
	            }
	            else{
	            	if (!start){
		            	startOpen();
		            }
	            	else{
	            		int j = JOptionPane.showOptionDialog(null, "Do you want to save your current document?", "Save File", JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, new Object[] {"Save","Continue Without Saving","Cancel"}, null);
		            	if ( j == 0 ){
		            		savePrompt();
		            		startOpen();
		            	}
		            	else if( j == 1){
		            		startOpen();
		            	}
	            	}
	            }
	            
	        } else {
	        	System.out.println("Open command cancelled by user.");
	        }
		}
	}
	private static class printSched implements ActionListener{
		public void actionPerformed( ActionEvent e){
			jp = new JPanel();
			getprintPanels();
			JFrame pFrame = new JFrame();
			jp.setOpaque( false );
			jp.setBounds( 5, 5, jp.getWidth(), jp.getHeight() );
			PrintPreviewS pp = new PrintPreviewS(jp);
			Container con2 = pFrame.getContentPane();
		    con2.add(pp, BorderLayout.CENTER);
		    pFrame.setTitle("Print Preview");
		    pFrame.pack();
		    pFrame.setVisible( true );
		    pFrame.toFront();
		}
	}
	private static class helpAction implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			String temp = e.getActionCommand();
			helpPanel helpFrame = null;
			helpFrame = new helpPanel(temp);
			ModalFrameUtil.showAsModal(helpFrame.frame, window);
			helpFrame = null;
		}
	}
	public static class viewReport implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			profReport view = new profReport();
			ModalFrameUtil.showAsModal(view.frame, window);
		}
	}	
		
		/*
		 * Tree Roots
		 */
	private static void studentTree(TreePath object){
		
		if ( start )
			rightTPane.setSelectedIndex( 4 );
		sPanel.setmajor( object.getPathComponent(2).toString() );
		if ( object.getPathComponent(3).toString().equals( "First Year" ) )
			setyear( 0 );
		else if ( object.getPathComponent(3).toString().equals( "Second Year" ) )
			setyear( 1 );
		else if ( object.getPathComponent(3).toString().equals( "Third Year" ) )
			setyear( 2 );
		else if ( object.getPathComponent(3).toString().equals( "Fourth Year" ) )
			setyear( 3 );
		getID( sPanel.getmajor() );
		
		String[][] temp = sPanel.getoutput();
		
		sPanel.outArea.setText("");
		
		StringTokenizer st = new StringTokenizer(temp[sPanel.getmajorInt()-1][sPanel.getyear()]," ");
		while(st.hasMoreTokens()){
			sPanel.outArea.append(st.nextToken()+"\n");
		}
		
		if ( rightTPane.getSelectedIndex() == 0 ){
			sPanel.setvisible( true );
			if (!sPanel.outArea.getText().toString().equals("")){
				sPanel.addB.setVisible( false );
			}
		}
				
		if ( rightTPane.getSelectedIndex() == 1 && courseTask.getSelectedIndex() == 3 ) {
			cPanel.enablecourse( true );
			cPanel.courseAddremoveAllElements();
			cPanel.clrListBtnsetVisible( true );
			cPanel.saveBtnsetVisible( true );
			cPanel.rightBtnsetEnabled( false );
			cPanel.leftBtnsetEnabled( false );
			cPanel.saveBtnsetEnabled( true );
			cPanel.labelsetVisible( true );
			cPanel.courseListclearSelection();
			
			int [][] temp2 = cPanel.getcourseCtr();
			if ( temp2[sPanel.getmajorInt()-1][sPanel.getyear()] == 0 ) {
				cPanel.clrListBtnsetEnabled( false );
				if (cPanel.assCrs)
					cPanel.saveBtnsetEnabled( true );
				else
					cPanel.saveBtnsetEnabled( false );
				}
			else {
				cPanel.clrListBtnsetEnabled( true );
				if (cPanel.assCrs)
					cPanel.saveBtnsetEnabled( true );
				else
					cPanel.saveBtnsetEnabled( false );
				}
			cPanel.loadcourseAdd();
			}

	}
	private static void profTree(TreePath object){
		String pf = "";
		if ( start ){
			if (object.getPathCount()==2){
				rightTPane.setSelectedComponent( pTab );
				pscPanel.setBorder( new TitledBorder( " Select a professor from the tree." ) );
				pscPanel.seecells( false );
				pscPanel.seedays( false );
				pscPanel.seetime( false );
				pscPanel.seejo( false );
				pscPanel.seecells( false );	
				printTool.setEnabled( false );
				printDoc.setEnabled( false );
			}
			else if (object.getPathCount()==3){
				rightTPane.setSelectedComponent( pTab );
				pscPanel.hourCtr = 0;
				pf = object.getPathComponent(2).toString();
				pscPanel.setBorder( new TitledBorder( " Name: " + pf + " " ) );
				profRemarks.setText("");
				pscPanel.setVisible( true );
				pscPanel.setProf( pf );
				pscPanel.seedays( true );
				pscPanel.seetime( true );
				pscPanel.seejo( true );
				pscPanel.seecells( true );
				yearTree.setEnabled( true );
				printTool.setEnabled( true );
				printDoc.setEnabled( true );
			}
		}
	}
	private static void roomTree(TreePath object){
		String rm = "";
		if ( start ){
			if (object.getPathCount()==2){
				rightTPane.setSelectedComponent( rTab );
				rscPanel.setBorder( new TitledBorder( " Select a room from the tree." ) );
				rscPanel.seecells( false );
				rscPanel.seedays( false );
				rscPanel.seetime( false );
				rscPanel.seejo( false );
				rscPanel.seecells( false );
				printTool.setEnabled( false );
				printDoc.setEnabled( false );
			}
			else if ( object.getPathCount() == 3 ){
				rightTPane.setSelectedComponent( rTab );
				rm = object.getPathComponent(2).toString();
				rscPanel.setBorder( new TitledBorder( " Room: " + rm + " " ) );
				rscPanel.setVisible( true );
				rscPanel.setRoom( rm );
				rscPanel.seedays( true );
				rscPanel.seetime( true );
				rscPanel.seejo( true );
				rscPanel.seecells( true );
				yearTree.setEnabled( true );
				printTool.setEnabled( true );
				printDoc.setEnabled( true );
			}			
		}
	}
	private static void startTree(TreePath object){
		scPanel.seecells( false );
		
		if ( object.getPathCount() > 4 && object!=null) {
			int yr, mj;
			yr = sPanel.getyear();
			mj = sPanel.getmajorInt();
			bPanel.seeTable( true );
			bPanel.setdetails(mj, yr, cPanel.getsemInt());	
			secName = object.getPathComponent(4).toString();
			scPanel.setBorder( new TitledBorder( " Section: " + secName + " " ) );
			printTool.setEnabled( true );
			printDoc.setEnabled( true );
			scPanel.setVisible( true );
			scPanel.setSection( secName );
			scPanel.seedays( true );
			scPanel.seetime( true );
			scPanel.seejo( true );
			scPanel.seecells( true );
			rightTPane.setSelectedComponent( secTab );
			yearTree.setEnabled( true );
		}
		else{
			scPanel.setBorder( new TitledBorder( " Select a section from the tree." ) );
			if (!isProf){
				printTool.setEnabled( false );
				printDoc.setEnabled( false );
			}
			bPanel.clrCourse();
			bPanel.seeTable( false );
			scPanel.seecells( false );
			scPanel.seedays( false );
			scPanel.seetime( false );
			scPanel.seejo( false );
			scPanel.seecells( false );
		}	
	}
		
		/*
		 * OP Listeners
		 */
	private static class treeListener implements TreeSelectionListener{
		public void valueChanged( TreeSelectionEvent treeSelectionEvent ) {
			
			JTree tree = ( JTree ) treeSelectionEvent.getSource();
			TreePath object = tree.getSelectionPath();
			cPanel.labelsetVisible( false );
			testing = object.getLastPathComponent().toString();
			if ( object!=null && object.getPathCount() >= 2 ){
				if ( object.getPathComponent(1).toString().equals("Professors")){
					profTree( object );
					isProf = true;
				}
				else if ( object.getPathComponent(1).toString().equals("Rooms")){
					roomRemarks.setText("");
					roomTree( object );
					isProf = false;
				}
				else if ( object.getPathComponent(1).toString().equals("Students") && !start ){
					if ( rightTPane.getSelectedIndex() != 1 )
						rightTPane.setSelectedIndex( 0 );
					isProf = true;
				}
					
			}
			
			if ( object.getPathCount() > 3 && object!=null && object.getPathComponent(1).toString().equals("Students")) {
				sPanel.setmajor( "" );
			 	setyear( 0 );	
				studentTree( object );	
			}
			
			if ( start )
				{
				if ( object.getPathCount() >= 2 && object!=null && object.getPathComponent(1).toString().equals("Rooms"))
					{}
				else
					startTree( object );	
				}
			
			else if ( object.getPathCount() < 3 && ( rightTPane.getSelectedIndex()==1 && courseTask.getSelectedIndex() == 3 ) )
				cPanel.enablecourse( false );
				
			else if ( object.getPathCount() <= 3 && rightTPane.getSelectedIndex() == 0 ) {
				sPanel.majors.setSelectedIndex(0);
				sPanel.years.setSelectedIndex(0);
				sPanel.setvisible( false );
				sPanel.addB.setVisible( true );
				cPanel.saveBtnsetEnabled( false );
			}
		}

	}
	public static class CSVFileFilter extends javax.swing.filechooser.FileFilter {
	    public boolean accept(File f) {
	        return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
	    }
	    public String getDescription() {
	        return ".csv files";
	    }
	}

	
		/*
		 * Functions
		 */
	public static void newDoc(){
		inputSem a = new inputSem();
		ModalFrameUtil.showAsModal(a.frame, window);
		if( !a.fake ){
			s = a.s;
			sy = a.syear;
			newAll();
			getCourses ( s );
			fName = s;
			window.setTitle( "Scheduler v0.99.9 " + s + "  SY: " + sy);
			label.setText( "Schedule for " + s  + "  SY: " + sy);
			label.setVisible( true );
			for( int m = 0; m<3; m++ ){
				for ( int j = 0; j<4; j++ ){
					sPanel.output[m][j] = "";		
				}
			}
			newTool.setEnabled( false );
			newDoc.setEnabled( false );
			Object path = yearTree.getModel().getRoot();
			yearTree.setSelectionPath( new TreePath( path ) );
		}
		else{
			return;
		}
		
		
	}
	public static void newAll(){

		int [][] temp = new int[3][4];
		for ( int m = 0; m < 3; m++ )
			for ( int j = 0; j < 4; j++ )
				temp[m][j] = 0;
		
		leftPane.setLayout( null );
		coursePane.setLayout( null );
		profPane.setLayout( null );
		roomPane.setLayout( null );
		toolsMenu.setEnabled( true );
		startTool.setEnabled( true );
		play.setEnabled( true );
		saveTool.setEnabled( false );
		saveDoc.setEnabled( false );
		
		createSecPane();
		createCoursePane();
		createProfPane();
		createRoomPane();
		
		sPanel.setLayout( null );
		cPanel.setLayout( null );
		pPanel.setLayout( null );
		rPanel.setLayout( null );
		
		treeListener treeHandler = new treeListener();
		s = s.replaceAll(" ", "");

		if (s.equals("1") || s.equals("1stSem")) {
			s = "1st Sem";
			cPanel.setsemInt(1);
		}
		else {
			s = "2nd Sem";
			cPanel.setsemInt(2);
		}
		
		sem = "";
	
		cPanel.setcourseCtr( temp );
		newSched = true;
		fName = s;
		window.setTitle( "Scheduler v0.99.9 " + s + "  SY: " + sy);
		label.setText( "Schedule for " + s  + "  SY: " + sy);
		label.setVisible( true );
		resourceMenu.setEnabled( true );
		todoPane = new JScrollPane( todoList );
		
		yearSplitPane.setLeftComponent( leftPane );
		yearSplitPane.setRightComponent( rightPane );
		rightPane.add(rightTPane, JSplitPane.TOP);
		rightPane.add(todoPane, JSplitPane.BOTTOM);
		rightPane.setDividerLocation(450);
		yearSplitPane.setVisible( false );
		yearSplitPane.setDividerLocation( 170 );
						
		addComponent( menuPane, yearSplitPane, 15, 55, 977, 640 );
		yearSplitPane.setVisible( true );	
		(((BasicSplitPaneUI)yearSplitPane.getUI()).getDivider()).addComponentListener(new ComponentAdapter() {
	      @SuppressWarnings("deprecation")
		public void componentMoved(ComponentEvent ce) { 
	    	  treeSPane.setBounds(0,0,yearSplitPane.getDividerLocation(),638);
	    	  window.show();
	    	  }
	    });
			
		addComponent( leftPane, treeSPane, 0, 0, yearSplitPane.getDividerLocation(), 638 );
		treeSPane.getViewport().add( yearTree );
		if ( !isTree ){
			root.add( rootBlocks );
			root.add( rootProf );
			root.add( rootRoom );
			
			rootBlocks.add( csProg );
			rootBlocks.add( itProg );
			rootBlocks.add( isProg );
			
			rightTPane.addTab( "Sections", sPanel );
			rightTPane.addTab( "Courses", coursePane );
			rightTPane.addTab( "Professors", profPane );
			rightTPane.addTab( "Rooms", roomPane );
			String roomlist[] = new String[0];
			String proflist[] = new String[0];
			
			try {
				proflist = file_handle.getProflist( "" );
				roomlist = file_handle.getRoomlist( "" );
			} catch ( IOException e2 ) {
				e2.printStackTrace();
				return;
			}
			pfs = new DefaultMutableTreeNode[proflist.length];
			rms = new DefaultMutableTreeNode[roomlist.length];
	
			for (int p = 0; p<proflist.length; p++){
				pfs[p] = new DefaultMutableTreeNode( proflist[p] );
				rootProf.add( pfs[p] );
			}
			
			for (int r = 0; r<roomlist.length; r++){
				rms[r] = new DefaultMutableTreeNode( roomlist[r] );
				rootRoom.add( rms[r] );
			}
			
			rightTPane.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ){
					tabStateChanged( e );
				}
			} );
			yearTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				
			levels = new DefaultMutableTreeNode[4][4];
			for( int i = 0; i < dataList.length; i++ ) {
				levels[0][i] = new DefaultMutableTreeNode( dataList[i] );
				levels[1][i] = new DefaultMutableTreeNode( dataList[i] );
				levels[2][i] = new DefaultMutableTreeNode( dataList[i] );
			}
			for( int i = 0; i < dataList.length; i++ ) {
					csProg.add( levels[0][i] );
					itProg.add( levels[1][i] );
					isProg.add( levels[2][i] );
			}
			
			TreeModel treemodel = yearTree.getModel( );
			Object TraverseObject = treemodel.getRoot( );
			if ( ( TraverseObject != null ) && ( TraverseObject instanceof DefaultMutableTreeNode ) ) 
				yearTree.addTreeSelectionListener( treeHandler );
		}
		isTree = true;
	}
	public static void startAll(){
		rightPane.remove(todoPane);
		reportTool.setEnabled( true );
		report.setEnabled( true );
		sPanel.save( false );
		start = true;
		play.setEnabled( true );
		//goTool.setEnabled( true );
		startSched.setEnabled( false );
		String[][] output = sPanel.getoutput();
		String sec = "";
		
		for( int j = 0; j < 3;j++ ) {
			for( int i = 0; i < 4; i++ ) {
				StringTokenizer st = new StringTokenizer( output[j][i], " " );
				while ( st.hasMoreTokens() ){
					sec = st.nextToken();
					levels[j][i].add( new DefaultMutableTreeNode( sec ) );
					blockNo[j][i]++;
				}
				totalBlock += blockNo[j][i];
			}
		}
		
		if (section.isEmpty())
			allBlocks = new String[totalBlock];
		else{
			allBlocks = new String[section.size()];
			int x = 0;
			int major = 0;
			while(x<section.size()){
				int year = 0;
				year = Integer.parseInt(String.valueOf(section.get(x).charAt(0))) - 1;
				levels[major][year].add(new DefaultMutableTreeNode( section.get(x)));
				if (section.get(x).charAt(0)=='4' && ((x+1) < section.size() && section.get(x+1).charAt(0)=='1' ))
					major++;
				x++;
			}
		}
		
		createBlockArray();
		
		
		
		scPanel = new schedPanel();
		scPanel.startSched(d, allBlocks);
		days = Integer.parseInt(d);
		
		bPanel = new bottomPanel();
		
		int row = 0;
	    while (row < yearTree.getRowCount()) {
	      yearTree.expandRow(row);
	      row++;
	    }
		try {
			proflist = file_handle.getProflist( "" );
			roomlist = file_handle.getRoomlist( "" );
		} catch ( IOException e2 ) {
			e2.printStackTrace();
			return;
		}
		
		schedSPane = new JScrollPane();
		rightTPane.addTab( "Schedule for Students", secTab );
		secTab.add( schedSPane );
		schedSPane.getViewport().add( scPanel );
		schedSPane.setBounds( new Rectangle (0,0,792,400) );
		schedSPane.setVisible( true );
		scPanel.setVisible( true );

		try {
			sc = new automateSched(days, cPanel.getsemInt());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//prof panel
		pscPanel = new pschedPanel();
		pschedSPane = new JScrollPane();
		rightTPane.addTab( "Schedule for Professors", pTab );
		pTab.add( pschedSPane );
		pschedSPane.getViewport().add( pscPanel );
		pschedSPane.setBounds( new Rectangle (0,0,792,400) );
		pschedSPane.setVisible( true );
		pscPanel.setVisible( true );
		pscPanel.startSched(d, proflist);
		
		//room panel
		rscPanel = new rschedPanel();
		rschedSPane = new JScrollPane();
		rightTPane.addTab( "Schedule for Rooms", rTab );
		rTab.add( rschedSPane );
		rschedSPane.getViewport().add( rscPanel );
		rschedSPane.setBounds( new Rectangle (0,0,792,400) );
		rschedSPane.setVisible( true );
		rscPanel.setVisible( true );
		rscPanel.startSched(d, roomlist);
		
		//bottom panel
		bottomPane.setBorder(BorderFactory.createLoweredBevelBorder());
		remarksPane = new JScrollPane( profRemarks );
		profRemarks.setOpaque( false );
		profRemarks.setEditable( false );
		pbottomPane.setBorder(new TitledBorder("Remarks: "));
		addComponent( secTab, bottomPane, 0,405,792,208 );
		addComponent( pTab, pbottomPane, 0,405,792,200 );
		addComponent( pbottomPane, remarksPane, 5,15,780,180 );
		bottomPane.setVisible( true );
		bottomPane.setLayout( null );
		pbottomPane.setVisible( true );
		pbottomPane.setLayout( null );
		profRemarks.setText("Dito po nasusulat kung ang isang propesor ay nagsosobrang bagahe o hindi. XD");
		profRemarks.setVisible( true );
		
		rremarksPane = new JScrollPane( roomRemarks );
		roomRemarks.setOpaque( false );
		roomRemarks.setEditable( false );
		rbottomPane.setBorder(new TitledBorder("Remarks: "));
		addComponent( rTab, rbottomPane, 0,405,792,200 );
		addComponent( rbottomPane, rremarksPane, 5,15,780,180 );
		bottomPane.setVisible( true );
		bottomPane.setLayout( null );
		rbottomPane.setVisible( true );
		rbottomPane.setLayout( null );
		roomRemarks.setText("Rooms?");
		roomRemarks.setVisible( true );
		
		addComponent( bottomPane, bPanel, 0,0,792,204 );

		sPanel.setEnabled( false );
		cPanel.setEnabled( false );
		rightTPane.setEnabledAt( 0, false );
		rightTPane.setEnabledAt( 1, false );
		rightTPane.setSelectedIndex( 4 );		
		Object path = yearTree.getModel().getRoot();
		yearTree.setSelectionPath( new TreePath( path ) );
	}
	public static void getData(String fName){
		Scanner line;
        try {
        	line = new Scanner (new FileReader (fName.substring(0, fName.indexOf('-')) + "-sec.csv"));
        } catch (Exception ex) {
        	return;
        }
        ArrayList <String> temp = new ArrayList <String> ();
        day = -1;
        while (line.hasNextLine())
        	temp.add(line.nextLine());
        
        sem = temp.get(0).substring(temp.get(0).indexOf(':') + 1, temp.get(0).length());
        sy = sem.substring(sem.indexOf(':')+1, sem.length());
        sy = sy.replaceAll(",", "");
        System.out.println("SY = "+sy);
        StringTokenizer c = new StringTokenizer (sem, ",");
        if (c.hasMoreTokens())
        	s = c.nextToken();
        else
        	s = sem;
        
        for (int x = 0; x < temp.size(); x++) {
        	StringTokenizer a = new StringTokenizer(temp.get(x), ":");
        	while (a.hasMoreTokens()) {
        		String tmp = a.nextToken();
        		if (tmp.equals("Section")) {
        			tmp = a.nextToken();
        			StringTokenizer b = new StringTokenizer (tmp, ",");
        			section.add(b.nextToken().substring(1));
        		}
        	}
        }
        StringTokenizer a = new StringTokenizer (temp.get(1), ",");
        while (a.hasMoreTokens()) {
        	day++;
        	a.nextToken();
        }
        
        sched = new String[section.size()][day][13];
        for (int x = 0; x < section.size(); x++) {
        	for (int y = 0; y < day; y++) {
        		for (int z = 0; z < 13; z++)
        			sched[x][y][z] = "";
        	}
        }
        int num = -1;
        for (int x = 0; x < temp.size(); x++) {
        	if (temp.get(x).indexOf(':') != -1 && temp.get(x).substring(0, temp.get(x).indexOf(':')).equals("Section")) {
        		int y;
        		num++;
        		for (y = 0; y < 13; y++) {
        			a = new StringTokenizer (temp.get(y + x + 1), ",");
        			int z = -1;
        			while (a.hasMoreTokens()) {
        				String tmp;
        				if (z == -1)
        					tmp = a.nextToken();
        				z++;
        				if (a.hasMoreTokens())
        					tmp = a.nextToken();
        				else
        					tmp = "";
        				
        				sched[num][z][y] = tmp;
        			}
        		}
        		x = x + y;
        	}
        }
	}
	public static void startOpen(){
		saveTool.setEnabled( true );
		printTool.setEnabled( true );
		saveDoc.setEnabled( true );
		d = String.valueOf( day );
		
    	startAll();
    	for (int sec = 0; sec < allBlocks.length; sec++){
		int x, y;
			for (x = 0; x < days; x++){
				for (y = 0; y < 13; y++){
					String cCode = "";
					String prof = "";
					String rm = "";
					
					StringTokenizer t = new StringTokenizer(sched[sec][x][y], ":");
					if (t.hasMoreTokens() && sched[sec][x][y].length()>1){
						rm = t.nextToken();
						cCode = t.nextToken();
						prof = t.nextToken();
					}
					
					scPanel.setCellData(y, x, rm, cCode, prof, sec);
					rscPanel.setCellData(y, x, rm, cCode, allBlocks[sec]);
					pscPanel.setCellData(y, x, rm, cCode, allBlocks[sec], prof);
				}
			}
		}
	}
	public static void savePrompt(){
		String fName = "";
		
		fc.setFileFilter( filter );
		int returnVal = fc.showSaveDialog(new JPanel());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
            	fName = file.getAbsolutePath();
            } catch (Exception ex) {
            	return;
            }
            if (fName.endsWith(".csv"))
            	System.out.println("Saving: " + fName);
            else{
            	fName = fName + ".csv";
            }
            
			try {
				fName = fName.substring(0,fName.indexOf('.')) + "-sec.csv";
				scPanel.saveSched(fName, cPanel.getsemInt());
				fName = fName.substring(0,fName.indexOf('-')) + "-prof.csv";
				pscPanel.saveSched(fName);
				fName = fName.substring(0,fName.indexOf('-')) + "-room.csv";
				rscPanel.saveSched(fName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        } else {
        	System.out.println("Save command cancelled by user.");
        }
	}
	public static void getprintPanels(){
		if ( rightTPane.getSelectedIndex() == 4 ){
			jp = scPanel;
		}
		else if ( rightTPane.getSelectedIndex() == 5 ){
			jp = pscPanel;
		}
		else if ( rightTPane.getSelectedIndex() == 6 ){
			jp = rscPanel;
		}
	}
	public static void runSched(){
/*		scPanel = null;
		scPanel = new schedPanel();
		scPanel.startSched(d, allBlocks);
		schedSPane.getViewport().add( scPanel );
		scPanel.setVisible( true );*/
		
		sc.NaghahatiNgMgaAralin();
		JOptionPane.showMessageDialog(null, "Scheduling complete.");
		saveTool.setEnabled( true );
		saveDoc.setEnabled( true );
		rightTPane.setSelectedIndex( 4 );
		for (int sec = 0; sec < allBlocks.length; sec++){
			int x, y;
			for (x = 0; x < days; x++){
				for (y = 0; y < 13; y++){
					scPanel.setCellData(y, x, "", "", "", sec);
				}
			}
		}
		for (int sec = 0; sec < pscPanel.allProf.length; sec++){
			int x, y;
			for (x = 0; x < days; x++){
				for (y = 0; y < 13; y++){
					pscPanel.setCellData(y, x, "", "", "", pscPanel.allProf[sec]);
				}
			}
		}
		for (int sec = 0; sec < rscPanel.allRooms.length; sec++){
			int x, y;
			for (x = 0; x < days; x++){
				for (y = 0; y < 13; y++){
					rscPanel.rSched[sec][y][x] = "";
				}
			}
		}
		Object path = yearTree.getModel().getRoot();
		yearTree.setSelectionPath( new TreePath( path ) );
		for (int sec = 0; sec < allBlocks.length; sec++){
			int x, y;
			for (x = 0; x < days; x++){
				for (y = 0; y < 13; y++){
					String stemp = "";
					String ctemp = "";
					String cCode = "";
					String prof = "";
					String rm = "";
					int z = 0;
					
					StringTokenizer t = new StringTokenizer(sc.s[sec].sched[x][y], ":");
					if (t.hasMoreTokens()){
						rm = t.nextToken();
						cCode = t.nextToken();
					}
					
					while(z<sc.profStat.length){
						stemp = "";
						ctemp = "";
						prof = "";
						
						StringTokenizer j = new StringTokenizer(sc.profStat[z][x][y], ":");
						if (j.hasMoreTokens()){
							stemp = j.nextToken();
							ctemp = j.nextToken();
						}
						if (stemp.equals(sc.s[sec].name) && ctemp.equals(cCode) ){
							prof = sc.p[z].name;
							break;
						}
						z++;
					}
					if (prof.equals("") || prof == null){
						prof = "...";
					}
					pscPanel.setCellData(y, x, rm, cCode, allBlocks[sec], prof);
					scPanel.setCellData(y, x, rm, cCode, prof, sec);
				}
			}
		}
		String secR = "", codeR = "";
		for(int z = 0; z < rscPanel.allRooms.length; z++){
			for( int x = 0; x < days; x++){
				for( int y = 0; y < 13; y++){
					StringTokenizer s = new StringTokenizer (sc.r[z].sched[x][y], ":");
					secR = "";
					codeR = "";
					if (s.hasMoreTokens()){
						secR = s.nextToken();
						codeR = s.nextToken();
					}
					rscPanel.setCellData(y, x, sc.r[z].name, codeR, secR);
				}
			}
		}
	}
	
	/*
	 * Tabs pane creator
	 */
	private static void createSecPane() {
		sPanel = new sectionPanel();
	}
	private static void createRoomPane() {
		JLabel rmTask = new JLabel( "Select a task: " );
		String rTasks[] = {"-------","Add Room","Edit Room", "Delete Room"};
		roomTask = new JComboBox( rTasks );
		addComponent( roomPane, rmTask, 3, 5, 100, 20 );
		addComponent( roomPane, roomTask, 105, 5, 130, 20 );
		roomTask.addActionListener( new roomTaskListener() );
		rPanel = new roomsPanel();
		addComponent( roomPane, rPanel, 0, 0, 500, 500 );
		roomTask.setSelectedIndex( 0 );
	}
	private static void createProfPane() {
		JLabel cLabel = new JLabel( "Select a task: " );
		String ptasks[] = {"----","Add Professor","Edit Prof Info", "Remove Prof"};
		profTasks = new JComboBox( ptasks );
		addComponent( profPane, cLabel, 3, 5, 100, 20 );
		addComponent( profPane, profTasks, 105, 5, 130, 20 );
		profTasks.addActionListener( new profTaskListener() );
		pPanel = new professorsPanel();
		addComponent( profPane, pPanel, 0, 0, 500, 500 );
	}
	private static void createCoursePane() {
		JLabel crsTask = new JLabel( "Select a task: " );
		String tasks[] = {"----","Add New Course", "Edit Course", "Assign Course", "Delete Course"};
		courseTask = new JComboBox( tasks );
		addComponent( coursePane, crsTask, 3, 5, 100, 20 );
		addComponent( coursePane, courseTask, 105, 5, 130, 20 );
		courseTask.addActionListener( new comboTaskListener() );
		cPanel = new coursesPanel();
		addComponent( coursePane, cPanel, 0, 0, 500, 500 );
	}

	/*
	 * Menu creators
	 */
	private static JMenu makeFileMenu() {
		JMenu fileMenu = new JMenu( "File" );
		fileMenu.setMnemonic( 'F' );
		JMenuItem openDoc;
		JMenuItem exitCommand;
		
		
		exitCommand = new JMenuItem( "Exit", KeyEvent.VK_X );
		exitCommand.addActionListener( new exitHandle() );
		exitCommand.setToolTipText( "Exit the program." );
		window.setContentPane( menuPane );
		menuPane.setLayout( null );
		
		newDoc = new JMenuItem( "New Schedule", KeyEvent.VK_N );
		newDoc.setToolTipText( "Create a new schedule." );
		addComponent( menuPane,label, 3, 35, 300, 10 );
		
		label.setVisible( false );
		newDoc.addActionListener( new newdocHandle() );
		
		saveDoc = new JMenuItem( "Save", KeyEvent.VK_S );
		saveDoc.addActionListener( new saveSched() );
		
		openDoc = new JMenuItem( "Open", KeyEvent.VK_O );
		openDoc.addActionListener( new openSched() );
		
		printDoc = new JMenuItem( "Print", KeyEvent.VK_P );
		printDoc.addActionListener( new printSched() );
		
		saveDoc.setToolTipText( "Save the schedule." );
		fileMenu.setLayout( null );
		fileMenu.add( newDoc );
		fileMenu.add( openDoc );
		fileMenu.add( saveDoc );
		fileMenu.add( printDoc );
		fileMenu.add( jo );
		fileMenu.add( exitCommand );
		
		saveDoc.setEnabled( false );
		printDoc.setEnabled( false );
		
		addComponent( menuPane, fileMenu, 0, 0, 300, 20 );
		return fileMenu;
	}
	private static JMenu makeResourcesMenu() {
		resourceMenu.setMnemonic( 'R' );
		sections = new JMenuItem( "Sections", KeyEvent.VK_S );
		resourceMenu.add( sections );
		sections.addActionListener( new ActionListener() {
			public void actionPerformed ( ActionEvent e ){
				sectionListener();
			}
		} );

		resourceMenu.add( makeCoursesMenu() );
		resourceMenu.add( makeProfMenu() );
		resourceMenu.add( makeRoomMenu() );

		return resourceMenu;
	}
	public static JMenu makeRoomMenu() {
		JMenuItem addRoom = new JMenuItem( "Add Room", KeyEvent.VK_A );
		JMenuItem editRoom = new JMenuItem( "Edit Room", KeyEvent.VK_E );
		JMenuItem delRoom = new JMenuItem( "Delete Room", KeyEvent.VK_D );
		
		addRoom.addActionListener( new addRoomListener() );
		editRoom.addActionListener( new editRoomListener() );
		delRoom.addActionListener( new delRoomListener() );
		
		roomMenu.add( addRoom );
		roomMenu.add( editRoom );
		roomMenu.add( delRoom );
		roomMenu.setMnemonic( 'R' );
		return roomMenu;
	}
	public static JMenu makeCoursesMenu() {
		JMenuItem addCourses = new JMenuItem( "Add Courses", KeyEvent.VK_A );
		JMenuItem editCourses = new JMenuItem( "Edit Courses", KeyEvent.VK_E );
		JMenuItem assignCourses = new JMenuItem( "Assign Courses", KeyEvent.VK_S );
		JMenuItem deleteCourses = new JMenuItem( "Delete Course", KeyEvent.VK_D );
		
		addCourses.addActionListener( new addCourseHandler() );
		editCourses.addActionListener( new editCourseListener() );
		assignCourses.addActionListener( new assignCourseListener() );
		deleteCourses.addActionListener( new deleteCourseListener() );
		
		courseMenu.add( addCourses );
		courseMenu.add( editCourses );
		courseMenu.add( assignCourses );
		courseMenu.add( deleteCourses );
		courseMenu.setMnemonic( 'C' );
		return courseMenu;
	 }
	public static JMenu makeProfMenu() {
		JMenuItem addProf = new JMenuItem( "Add Professors", KeyEvent.VK_A );
		JMenuItem editProf = new JMenuItem( "Edit Professor Info", KeyEvent.VK_E );
		JMenuItem deleteProf = new JMenuItem( "Remove Professor", KeyEvent.VK_R );
		
		addProf.addActionListener( new addProfListener() );
		editProf.addActionListener( new editProfListener() );
		deleteProf.addActionListener( new removeProfListener() );
		
		profMenu.setLayout( null );
		profMenu.add( addProf );
		profMenu.add( editProf );
		profMenu.add( deleteProf );
		profMenu.setMnemonic( 'P' );
		return profMenu;
	 }
	public static JMenu makeSaveMenu(){
		JMenuItem saveSec = new JMenuItem( "Save Schedule for Sections", KeyEvent.VK_S );
		JMenuItem savePf = new JMenuItem( "Save Schedule for Professors", KeyEvent.VK_P );
		JMenuItem saveRm = new JMenuItem( "Save Schedule for Rooms", KeyEvent.VK_M );
		
		saveSec.addActionListener( new saveSched() );
		savePf.addActionListener( new saveSched() );
		saveRm.addActionListener( new saveSched() );
		
		saveMenu.setLayout( null );
		saveMenu.add( saveSec );
		saveMenu.add( savePf );
		saveMenu.add( saveRm );
		saveMenu.setMnemonic( 'S' );
		return saveMenu;
		
	}
	public static JMenu makeToolsMenu(){
		toolsMenu.setMnemonic( 'T' );
		startSched = new JMenuItem( "Start Scheduling", KeyEvent.VK_S );
		startSched.addActionListener( new startSched() );
		secTab.setLayout( null );
		pTab.setLayout( null );
		rTab.setLayout( null );
		
		play = new JMenuItem( "Schedule", KeyEvent.VK_S );
		play.addActionListener( new startSched() );
		
		play.setEnabled( false );
		
		
		report = new JMenuItem( "View Report", KeyEvent.VK_R );
		report.addActionListener( new viewReport() );
		
		report.setEnabled( false );
		
		toolsMenu.add( play );
		toolsMenu.add( report );
		toolsMenu.setEnabled( false );
		
		return toolsMenu;
	}
	public static JMenu makeHelpMenu(){
		helpMenu.setMnemonic( 'H' );
		help = new JMenuItem( "Help Index", KeyEvent.VK_H );
		help.addActionListener( new helpAction() );
		
		about = new JMenuItem( "About", KeyEvent.VK_A );
		about.addActionListener( new helpAction() );
		
		helpMenu.add( help );
		helpMenu.add( about );
		
		return helpMenu;
	}
	
	public static void main ( String args[] ) {
		genSplash();
		todoList.setFont(font);
		todoList.setText("Before Scheduling:\n\t>> Enter the number of blocks per year level and per major.\n\nTo Schedule:\n\t>>Go to Tools > Schedule.\n\t\tor\n\t>>Click the \"Schedule\" button from the toolbar.\n\nOptional tasks:\n\t>> Add new courses. \n\t>> Add new instructors.\n\t>> Add new rooms.\n\t>> Assign courses to corresponding level and major. (For curricullum changes.)\n\nNote that once the schedule action is executed, the \"Sections\" and \"Courses\" tabs will be disabled.");
		String newPath = "";
		newPath = "images\\";
		
		newTool.setIcon(new ImageIcon(newPath + "new.png"));
		newTool.setVisible( true );
		newTool.setToolTipText("New Schedule");
		toolBar.add( newTool );
		
		
		openTool.setIcon(new ImageIcon(newPath + "open.png"));
		openTool.setVisible( true );
		openTool.setToolTipText("Open");
		toolBar.add( openTool );
		
		saveTool.setIcon(new ImageIcon(newPath + "save.png"));
		saveTool.setVisible( true );
		saveTool.setToolTipText("Save");
		toolBar.add( saveTool );
		
		printTool.setIcon(new ImageIcon(newPath + "print.png"));
		printTool.setVisible( true );
		printTool.setToolTipText("Print");
		toolBar.add( printTool );
		
		startTool.setIcon(new ImageIcon(newPath + "play.png"));
		startTool.setVisible( true );
		startTool.setToolTipText("Schedule");
		toolBar.add( startTool );

		reportTool.setIcon(new ImageIcon(newPath + "report.png"));
		reportTool.setVisible( true );
		reportTool.setToolTipText("View Report");
		toolBar.add( reportTool );
		
		saveTool.setEnabled( false );
		printTool.setEnabled( false );
		startTool.setEnabled( false );
		reportTool.setEnabled( false );
		
		newTool.addActionListener( new newdocHandle() );
		saveTool.addActionListener( new saveSched() );
		printTool.addActionListener( new printSched() );
		openTool.addActionListener( new openSched() );
		startTool.addActionListener( new startSched() );
		reportTool.addActionListener( new viewReport() );
		
		menuBar.add( makeFileMenu() );
		menuBar.add( makeResourcesMenu() );
		menuBar.add( makeToolsMenu() );
		menuBar.add( makeHelpMenu() );
		
		resourceMenu.setEnabled( false );
		addComponent( window, toolBar, 0,0,1020,28 );
		toolBar.setVisible( true );
		
		window.setResizable( false );
		window.setLayout( null );
		window.setVisible( true );
		window.setJMenuBar( menuBar );
		window.pack();
		window.setLocation( new Point( 0, 0 ) );
		window.setSize( new Dimension( 1024, 768 ) );
		window.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
	 }
}
