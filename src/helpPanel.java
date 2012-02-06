import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


@SuppressWarnings("serial")
public class helpPanel extends JPanel {
	public static JFrame frame = new JFrame();
	
	/*
	 * Help Objects
	 */
	public static JPanel contentPane = new JPanel();
	public static JSplitPane splitPane = new JSplitPane();
	public static JPanel leftPane = new JPanel();
	public static JPanel rightPane = new JPanel();
	public static JLabel test = new JLabel("Help Topics");
	private static DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Help Topics" );
	private static DefaultMutableTreeNode how = new DefaultMutableTreeNode( "How to Use" );
	private static DefaultMutableTreeNode oView = new DefaultMutableTreeNode( "Overview" );
	public static JTree hTree = new JTree( root );
	public static JScrollPane treeSpane = new JScrollPane( hTree );
	public static JTextPane helpOut = new JTextPane();
	public static JScrollPane helpSpane = new JScrollPane( helpOut );
	public static String[] howTos = {"Creating a New Schedule","Open an Existing Schedule","Save Schedule","Print Schedule","Add a Course","Edit a Course","Assign a Course (Modify Curriculum)","Delete a Course","Adding a Professor","Editing Professor Information","Deleting a Professor Entry","Adding a Room","Edit Room Information","Delete Room Entry","View Summary for Professors","Print Summary for Professors","Export Summary of Professors to CSV File"};
	public static DefaultMutableTreeNode contents[];
	/*
	 * About Objects
	 */
	public static JPanel pane = new JPanel();
	public static JTextPane aboutInfo = new JTextPane();
	public helpPanel(String source) {
			position();
			contentPane.setLayout( null );
			if (source.equals("Help Index")){
				help();
			}
			else if (source.equals("About")){
				about();
			}
	}
	
	private static class treeListener implements TreeSelectionListener{
		public void valueChanged( TreeSelectionEvent treeSelectionEvent ) {
			String topic = "";
			JTree tree = ( JTree ) treeSelectionEvent.getSource();
			TreePath object = tree.getSelectionPath();
			topic = object.getLastPathComponent().toString();
			test.setText( topic );
			StyledDocument doc = helpOut.getStyledDocument();
			MutableAttributeSet standard = new SimpleAttributeSet();
			StyleConstants.setAlignment(standard, StyleConstants.ALIGN_LEFT);
			doc.setParagraphAttributes(0,0,standard,false);
			helpOut.setMargin(new Insets (10,12,0,10));
			Font font = new Font("Verdana",Font.HANGING_BASELINE,12);
			helpOut.setFont(font);
			if ( topic.equals("Overview")){
				test.setText( "Scheduler v0.1 "+topic );
				helpOut.setText("\nThe AUTOMATED SUBJECT AND INSTRUCTOR SCHEDULING SYSTEM, or simply Scheduler v0.1, is a program that generates a possible draft of schedules for the ICS Department of the Faculty of Engineering of the University of Santo Tomas.");
			}
			else if ( topic.equals("How to Use")){
				helpOut.setText("");
			}
			else if ( topic.equals("Creating a New Schedule")){
				helpOut.setText("-- Click File>New or the Paper button on the toolbar.\n-- Choose a semester and a school year.\n-- Input number of blocks per year level of each course.\n\t>> Browse through each level of each course using the drop-down boxes or the tree located at the left side of the screen.\n\t>>  Once you see the corresponding year levels for each course, you can now enter the number of blocks for each year level through clicking the add button on the right panel. When you click the add button, a prompt will be displayed, asking how many blocks are you going to input. If your input is greater than one, the system will ask you on how the sectioning will be, alphabetic or numeric sectioning. You should choose between the two.\n\t>> After doing these, you can now start scheduling.\n-- Click Tools>Start Scheduling or the Gear button on the toolbar.\n-- Click Tools>Schedule or the Play button on the toolbar.\n");
			}
			else if ( topic.equals("Open an Existing Schedule")){
				helpOut.setText("-- Click File>Open or the Folder button on the toolbar\n-- Choose the path where the saved document is located. Double click the file name.");
			}
			else if ( topic.equals("Save Schedule")){
				helpOut.setText("-- Click File>Save or the Diskette button on the toolbar.\n-- Enter filename [Filename must be of type .csv].");
			}
			else if ( topic.equals("Print Schedule")){
				helpOut.setText("-- Select a section to be printed from the tree.\n-- Click the Printer button on the toolbar or click File>Print.\n--Click the Print button.");
			}
			else if ( topic.equals("Add a Course")){
				helpOut.setText("-- Click Course Tab.\n-- Choose Add Course from the drop-down box.\n-- Enter the necessary information.\n-- Click Save.");				
			}
			else if ( topic.equals("Edit a Course")){
				helpOut.setText("-- Click Course Tab.\n-- Choose Edit Course from the drop-down box.\n-- Choose the course you need to edit from the courses' drop-down box.\n-- Edit the previously entered information [Editing the course code is not advisable].\n-- Click Save. ");
			}
			else if ( topic.equals("Assign a Course (Modify Curriculum)")){
				helpOut.setText("-- Click Course Tab.\n-- Choose Assign Course from the drop-down box.\n-- From the list of courses available, you can choose what are the courses offered for every year level of each course through clicking the '>>' button. You can also remove previously assigned courses through the '<<' button.\n-- Once you're done, click Save.");
			}
			else if ( topic.equals("Delete a Course")){
				helpOut.setText("-- Click Course Tab.\n-- Choose Delete Course from the drop-down box.\n-- Choose the course you want to delete from the courses' drop-down box.\n-- Click Delete.");
			}
			else if ( topic.equals("Adding a Professor")){
				helpOut.setText("-- Click Professor Tab.\n-- Choose Add Professor from the drop-down box.\n-- Enter the necessary information.\n-- Click Save.");
			}
			else if ( topic.equals("Editing Professor Information")){
				helpOut.setText("-- Click Professor Tab.\n-- Choose Edit Professor from the drop-down box.\n-- Choose the professor you need to edit from the professors' drop-down box.\n-- Edit the previously entered information.\n-- Click Save.");
			}
			else if ( topic.equals("Deleting a Professor Entry")){
				helpOut.setText("-- Click Professor Tab.\n-- Choose Delete Professor from the drop-down box.\n-- Choose the  professor you want to delete from the professors' drop-down box.\n-- Click Delete.");
			}
			else if ( topic.equals("Adding a Room")){
				helpOut.setText("-- Click Rooms Tab.\n-- Choose Add Room from the drop-down box.\n-- Enter the necessary information.\n-- Click Save.");
			}
			else if ( topic.equals("Edit Room Information")){
				helpOut.setText("-- Click Rooms Tab.\n-- Choose Edit Room from the drop-down box.\n-- Choose the room you need to edit from the rooms' drop-down box.\n-- Edit the previously entered information.\n-- Click Save.");
			}
			else if ( topic.equals("Delete Room Entry")){
				helpOut.setText("-- Click Rooms Tab.\n-- Choose Delete Room from the drop-down box.\n-- Choose the room you want to delete from the rooms' drop-down box.\n-- Click Delete.");
			}
			else if ( topic.equals("View Summary for Professors")){
				helpOut.setText("-- Click the Paper and Pen button (\"View Report\") on the toolbar or click Tools>View Report.");
			}
			else if ( topic.equals("Print Summary for Professors")){
				helpOut.setText("-- Click the Paper and Pen button (\"View Report\") on the toolbar or click Tools>View Report.\n-- Click the Print button.");
			}
			else if ( topic.equals("Export Summary of Professors to CSV File")){
				helpOut.setText("-- Click the Paper and Pen button (\"View Report\") on the toolbar or click Tools>View Report.\n-- Click the Export to CSV File.. button.");
			}
			else
				helpOut.setText("");
			helpOut.setCaretPosition(0);
		}
	}
	
	public void help(){
		splitPane.setLeftComponent( leftPane );
		splitPane.setRightComponent( rightPane );
		splitPane.setVisible( false );
		splitPane.setDividerLocation( 170 );
		splitPane.setLastDividerLocation( 170 );
			
		addComponent( rightPane, test, 5,5,200,20);
		addComponent( contentPane, splitPane, 5, 5, 480, 475 );
		addComponent( leftPane, treeSpane, 5, 5, 160, 467 );
		addComponent( rightPane, helpSpane, 10,25,280,447 );
		(((BasicSplitPaneUI)splitPane.getUI()).getDivider()).addComponentListener(new ComponentAdapter() {
			@SuppressWarnings("deprecation")
			public void componentMoved(ComponentEvent ce) { 
		    	  treeSpane.setBounds(0,0,splitPane.getDividerLocation(),467);
		    	  frame.show();
		    	  }
		    });
		helpOut.setEditable( false );
		
		contentPane.setLayout( null );
		
		TreeModel treemodel = hTree.getModel( );
		Object TraverseObject = treemodel.getRoot( );
		if ( ( TraverseObject != null ) && ( TraverseObject instanceof DefaultMutableTreeNode ) ) 
			hTree.addTreeSelectionListener( new treeListener() );

		root.add( oView );
		root.add( how );

		contents = new DefaultMutableTreeNode[howTos.length];
		
		
		for ( int x = 0; x < howTos.length; x++ ){
			contents[x] = new DefaultMutableTreeNode(howTos[x]);
			how.add(contents[x]);
		}
		
		
		splitPane.setVisible( true );
		
		test.setVisible( true );
		leftPane.setLayout( null );
		rightPane.setLayout( null );
		leftPane.setVisible( true );
		rightPane.setVisible( true );
		treeSpane.setVisible( true );
		leftPane.repaint();
		contentPane.setLayout( null );
		contentPane.setVisible( true );
		pane.setVisible( false );
		splitPane.setVisible( true );
		frame.setContentPane( contentPane );
	}
	
	
	public void about(){
		Font font = new Font("Verdana",Font.BOLD,14);
		addComponent( pane, aboutInfo, 0,0, 500,520);
		aboutInfo.setFont(font);
		aboutInfo.setOpaque( false );
		
		StyledDocument doc = aboutInfo.getStyledDocument();
		MutableAttributeSet standard = new SimpleAttributeSet();
		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0,0,standard,false);
		
		aboutInfo.setText("\n\nAbout Us\n\n\nScheduler v0.1\n\n\nGalfo, Mary Rose\nPalomares, Felix Isaac\nRodriguez, Maria Johanna\n\nUniversity of Santo Tomas\nFaculty of Engineering\nInformation and Computer Studies Department\n\n\n\nCopyright (c) 4CSA_JRF, 2008. All rights reserved.");
		pane.setLayout( null );
		contentPane.setVisible( false );
		pane.setVisible( true );
		frame.setContentPane( pane );
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
		frame.setResizable( false );
		frame.setLocation (x, y);
		frame.setSize( new Dimension( 500, 520 ) );
		frame.setTitle("Automated Scheduler Help Contents");
		frame.setLayout( null );
		frame.setVisible(true);
		frame.requestFocus();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main (String args[]) {
		//helpPanel a = new helpPanel();
		/*frame.setTitle("Automated Scheduler Help Contents");
		frame.setLayout( null );
		frame.setVisible( true );
		frame.pack();
		frame.setSize( new Dimension( 500, 520 ) );
		//frame.add(a);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);*/
	}
	
	private static void addComponent( Container container, Component c, int x, int y, int width, int height ) {
		c.setBounds( x, y, width, height );
		container.add( c );
	}
	
}
