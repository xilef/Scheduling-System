import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;



import java.util.Arrays;

@SuppressWarnings( "serial" )
public class roomsPanel extends JPanel{
	private static JLabel roomCode = new JLabel( "Room Name:" );
	private static JLabel roomDtls = new JLabel( "Room Type:" );
	private static JLabel roomLabel = new JLabel( "Rooms:" );
	private static JTextField roomCodeTF = new JTextField();
	private static JButton addRmBtn = new JButton( "Add" );
	private static JButton editRmBtn = new JButton( "Update" );
	private static JButton clrRmBtn = new JButton( "Clear" );
	private static JButton delRmBtn = new JButton( "Delete" );
	private static DefaultListModel roomAdd = new DefaultListModel();
	private static String roomList[] = {"", "", "", "", ""};
	private static JComboBox roomCBList;
	private static DefaultComboBoxModel roomModel = new DefaultComboBoxModel();
	private static DefaultComboBoxModel subjModel = new DefaultComboBoxModel();
	private static int roomTaskSelected = 0;
	private static String[] types = {"","Lecture","Computer Lab","Physics Lab","IT Lab","Other Labs"};
	private static JComboBox roomTypes = new JComboBox(types);
	private static DefaultListModel subjList;
	private static JList subjectList;
	private static JScrollPane subjectlistPane;
	private static JComboBox subjectlistCBox = new JComboBox();
	private static String subjlist[];
	private static JLabel sLabel = new JLabel( "Subject/s handled:" );
	private static JLabel s2Label = new JLabel( "Subjects:" );
	private static JButton addsubjBtn = new JButton( "Add Subject" );
	private static JButton removeBtn = new JButton( "Remove Subject" );
	private static boolean update = false;
	private static JComboBox tsCBox;
	private static JComboBox teCBox;
	private static JLabel tsLabel = new JLabel( "Time Start:" );
	private static JLabel teLabel = new JLabel( "Time End:" );
	String timeList[] = { "-------", "7:00 AM","8:00 AM","9:00 AM","10:00 AM","11:00 AM","12:00 PM",
			"1:00 PM","2:00 PM","3:00 PM","4:00 PM","5:00 PM","6:00 PM",
			"7:00 PM","8:00 PM" };
	private static Boolean time = false;
	/*
	 * Constructor
	 */
	public roomsPanel() {
		roomAdd = new DefaultListModel();
		tsCBox = new JComboBox( timeList );
		teCBox = new JComboBox( timeList );
		tsCBox.setSelectedIndex( 0 );
		teCBox.setSelectedIndex( 0 );
		roomAdd.addElement( "" );
		String temp[] = new String[0];
		try {
			temp = file_handle.getRoomlist( "-------" );
			subjlist = file_handle.getCourselist( " " );
			Arrays.sort(temp);
			Arrays.sort(subjlist);
		} 
		catch ( IOException e2 ) {
			e2.printStackTrace();
			return;
		}
		
		for( int y = 0; y < temp.length; y++ )
			roomModel.addElement( temp[y] );
		
		roomCBList = new JComboBox();
		roomCBList.setModel( roomModel );
		roomCBList.addActionListener( new rmCBList() );
		
		

		subjList = new DefaultListModel();
		subjectList = new JList( subjList );
		subjectlistPane = new JScrollPane( subjectList );
		subjectList.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ){
				if ( subjectList.getSelectedIndex() > -1)
					removeBtn.setEnabled( true );
				else
					removeBtn.setEnabled( false );
			}
		} );
		
		subjectlistCBox.setModel( subjModel );
		subjModel.addElement( " ");
		for( int y = 0; y < subjlist.length; y++ ){
			String details = "";
			try {
				details = file_handle.getCourseDetails( subjlist[y] );
			} catch (IOException e) {
				e.printStackTrace();
			}
			StringTokenizer st = new StringTokenizer( details, "," );
			String content = "";
			if ( st.hasMoreTokens() ){
				content = st.nextToken();
				content = st.nextToken();
				content = st.nextToken();
			}
			if( content.equals( "1" ) ) {
				subjModel.addElement( subjlist[y] );
			}
		}
		
		this.setLayout( null );
		roomCBList.setSelectedIndex( 0 );
		roomTypes.addActionListener( new roomTypeListener());
		subjectlistCBox.addActionListener( new roomTypeListener() );
		
		addComponent( this, tsLabel, 3, 100, 100, 20 );
		addComponent( this, tsCBox, 125, 100, 100, 20 );
		addComponent( this, teLabel, 3, 130, 100, 20 );
		addComponent( this, teCBox, 125, 130, 100, 20 );
		
		addComponent( this, roomLabel, 3, 40, 100, 20 );
		addComponent( this, roomCBList, 125, 40, 150, 20 );
		addComponent( this, roomCode, 3, 70, 100, 20 );
		addComponent( this, roomCodeTF, 125, 70, 80, 20 );
		
		
		addComponent( this, roomDtls, 3, 165, 300, 20 );
		
		addComponent( this, roomTypes, 125, 165, 150, 20 );
		
		addComponent( this, sLabel, 3, 200, 120, 20 );
		addComponent( this, subjectlistPane, 125, 200, 150,100 );
		addComponent( this, s2Label, 3, 320, 100, 20 );
		addComponent( this, subjectlistCBox, 125, 320, 150, 20 );
		
		addComponent( this, addRmBtn, 100, 370, 80, 20 );
		addComponent( this, editRmBtn, 100, 370, 80, 20 );
		addComponent( this, delRmBtn, 150, 370, 80, 20 );
		addComponent( this, clrRmBtn, 215, 370, 80, 20 );
		addComponent( this, addsubjBtn,325,320,128,20 );
		addComponent( this, removeBtn,325,200,128,20 );
		
		removeBtn.setEnabled( false );
		
		addsubjBtn.addActionListener( new addSubjectHandle() );
		removeBtn.addActionListener( new removeHandle() );
		addRmBtn.addActionListener( new addRmListener() );
		clrRmBtn.addActionListener( new clrRmListener() );
		delRmBtn.addActionListener( new deleteRmListener() );
		editRmBtn.addActionListener( new updateRmListener() );
		tsCBox.addActionListener( new timeHandle() );
		teCBox.addActionListener( new timeHandle() );
	}
	private static class timeHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if ( time == false && tsCBox.getSelectedIndex() == 0 && teCBox.getSelectedIndex() != 0 )
				JOptionPane.showMessageDialog( null, "Please select a start time.",
						"Error", JOptionPane.ERROR_MESSAGE );
			else if ( time == false && ( tsCBox.getSelectedIndex() >= teCBox.getSelectedIndex() && tsCBox.getSelectedIndex() != 0 && teCBox.getSelectedIndex() != 0)) {
				JOptionPane.showMessageDialog( null, "Start time must be earlier than the end time!",
						"Error", JOptionPane.ERROR_MESSAGE );
				tsCBox.setSelectedIndex( 0 );
			}
		}
	}
	private static class removeHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if ( subjectList.getSelectedIndex() >= 0 ) {
				subjList.removeElementAt( subjectList.getSelectedIndex() );
				if ( subjList.getSize() == 0 ) {
					removeBtn.setEnabled( false );
				}
			}
		}
	}
	
	private static class addSubjectHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if ( subjectlistCBox.getSelectedIndex() == 0 ) {
				JOptionPane.showMessageDialog( null, "There are no selected subjects!", 
						"Error", JOptionPane.ERROR_MESSAGE );
			}
			else if ( subjList.contains( subjectlistCBox.getSelectedItem().toString() ) ) {
				JOptionPane.showMessageDialog( null, "Selected subject already in the list!", 
						"Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			else {
				subjList.addElement( subjectlistCBox.getSelectedItem().toString() );
				subjectlistCBox.setSelectedIndex( 0 );
			}
		}
	}
	private static class addRmListener implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			time = true;
	 		if( roomCodeTF.getText().equals( "" ) )
	 			JOptionPane.showMessageDialog( null, "Please enter a valid room name.", 
						"Error", JOptionPane.ERROR_MESSAGE );
	 		
	 		if( tsCBox.getSelectedIndex() == 0 && teCBox.getSelectedIndex() != 0 ) {
				JOptionPane.showMessageDialog( null, "Please choose a start time.", "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
	 		
	 		else {
	 			for ( int j = 0; j < roomModel.getSize(); j++ ) {
					if ( roomModel.getElementAt( j ).toString().equals( roomCodeTF.getText() ) ) {
						int k = JOptionPane.showOptionDialog(null, "The room code already exists. Overwrite?", "", JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, new Object[] {"Yes", "No"}, null);
						if (k != 0){
							roomCodeTF.setText( "" );
							return;
						}
					}
				}
	 			
				roomList[0] = roomCodeTF.getText();
				if( roomTypes.getSelectedIndex() == 0 /*|| roomTypes.getSelectedIndex() > 5 */ )
					JOptionPane.showMessageDialog( null, "Please select a room type.", 
							"Error", JOptionPane.ERROR_MESSAGE );
				else
				{
					if( roomTypes.getSelectedIndex() == 2){
						roomList[1] = "1";
						if ( subjList.getSize() != 0 ){
							roomList[4] = subjList.getElementAt(0).toString();
							for (int x = 1; x < subjList.getSize(); x++){
								roomList[4] = roomList[4] + ":" + subjList.getElementAt(x);
							}
						}
					}
					else if( roomTypes.getSelectedIndex() == 1)
						roomList[1] = "2";
					else if( roomTypes.getSelectedIndex() == 3)
						roomList[1] = "3";
					else if( roomTypes.getSelectedIndex() == 4)
						roomList[1] = "4";
					else if( roomTypes.getSelectedIndex() == 5)
						roomList[1] = "5";
					
					roomList[2] = String.valueOf( tsCBox.getSelectedIndex() );
					roomList[3] = String.valueOf( teCBox.getSelectedIndex() );
					
					try {
						file_handle.setRoomDetails( roomList );
					} catch ( IOException e1 ) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog( null, "Room has been successfully added." );
					roomModel.addElement( roomCodeTF.getText() );
					DefaultTreeModel tm = (DefaultTreeModel)SchedulerBeta.yearTree.getModel();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) SchedulerBeta.rootRoom ;
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode( roomCodeTF.getText() );
					tm.insertNodeInto(newNode, node, node.getChildCount());
					clearRoom();
					roomCBList.setSelectedIndex( 0 );
				}
	 		}
		}
	}
	private static class updateRmListener implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			String t = roomCBList.getSelectedItem().toString();
			update = true;

			if( t.equals( "" ) )
				JOptionPane.showMessageDialog( null, "Please enter a room name.", 
						"Error", JOptionPane.ERROR_MESSAGE  );
			else {
				 roomList[0] = t;
					if( roomTypes.getSelectedIndex() == 2){
						roomList[1] = "1";
						roomList[2] = "";
						if ( subjList.getSize() != 0 ){
							roomList[2] = subjList.getElementAt(0).toString();
							for (int x = 1; x < subjList.getSize(); x++){
								roomList[2] = roomList[2] + ":" + subjList.getElementAt(x);
							}
						}
					}
					else if( roomTypes.getSelectedIndex() == 1)
						roomList[1] = "2";
					else if( roomTypes.getSelectedIndex() == 3)
						roomList[1] = "3";
					else if( roomTypes.getSelectedIndex() == 4)
						roomList[1] = "4";
					else if( roomTypes.getSelectedIndex() == 5)
						roomList[1] = "5";
					roomList[2] = String.valueOf( tsCBox.getSelectedIndex() - 1 );
					roomList[3] = String.valueOf( teCBox.getSelectedIndex() - 1 );
					try {
						file_handle.setRoomDetails( roomList );
					} catch ( IOException e1 ) {
						e1.printStackTrace();
						return;
					}
					roomModel.removeElement( roomCBList.getSelectedItem() );
					roomModel.addElement( t );
					clearRoom();
					
					roomCBList.setSelectedIndex( 0 );
					JOptionPane.showMessageDialog( null, "Updated." );
				}
			}
	}
	private static class clrRmListener implements ActionListener{
	 	public void actionPerformed( ActionEvent e ){
	 		clearRoom();
		}
	}
	private static class deleteRmListener implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			int o, a;
	 		a = roomCBList.getSelectedIndex();
	 		if( a==0 )
	 			JOptionPane.showMessageDialog( null, "Please select a room to delete.", 
						"Error", JOptionPane.ERROR_MESSAGE  );
	 		else {
	 			o = JOptionPane.showConfirmDialog( null, "Are you sure you want to remove " + roomCBList.getSelectedItem() + "?" ,"Delete Room",JOptionPane.YES_NO_OPTION );
	 			if( o == 0 ) {
	 				try {
	 					file_handle.removeRoom( roomCBList.getSelectedItem().toString() );
	 				} catch ( IOException e1 ) {
	 					e1.printStackTrace();
	 					return;
	 				}
	 				roomModel.removeElementAt( a );
	 				clearRoom();
	 				roomCBList.setSelectedIndex( 0 );
	 				JOptionPane.showMessageDialog( null, "Deleted." );
	 			}
	 		}
	 	}
	}
	private static class roomTypeListener implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			if ( roomTypes.getSelectedIndex() == 2 ){
				subjectlistCBox.setEnabled( true );
				subjectlistPane.setEnabled( true );
			}
			else{
				subjectlistCBox.setSelectedIndex(0);
				subjectlistCBox.setEnabled( false );
				subjectlistPane.setEnabled( false );
			}
			
			if ( subjectlistCBox.getSelectedIndex()!=0 ){
				addsubjBtn.setEnabled( true );
			}
			else
				addsubjBtn.setEnabled( false );
		}
	}
	private static class rmCBList implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			if ( roomCBList.getSelectedIndex() != 0 && !update) {
				editRmBtn.setEnabled( true );
				delRmBtn.setEnabled( true );
				clrRmBtn.setEnabled( true );
				subjList.removeAllElements();
				String temp;
				if ( roomTaskSelected == 2 )
					setenable( true );
				else if ( roomTaskSelected != 3 )
					setenable( false );
				try {
					temp = file_handle.getRoomDetails( roomCBList.getSelectedItem().toString() );
				} 
				catch ( IOException e2 ) {
					e2.printStackTrace();
					return;
				}		
				StringTokenizer st = new StringTokenizer( temp, "," );
				if ( st.hasMoreTokens() ) 
					roomCodeTF.setText( st.nextToken() );
				else 
					roomCodeTF.setText( "" );
				
				if ( st.hasMoreTokens() ) {
					int room = Integer.parseInt( st.nextToken() );
					if ( st.hasMoreTokens() ) 
						tsCBox.setSelectedIndex( Integer.parseInt( st.nextToken() ) + 1 );
					else 
						tsCBox.setSelectedIndex( 0 );
					
					if ( st.hasMoreTokens() ) 
						teCBox.setSelectedIndex( Integer.parseInt( st.nextToken() ) + 1 );
					else 
						teCBox.setSelectedIndex( 0 );
					if( room == 1 ) {
						roomTypes.setSelectedIndex( 2 );
						if (st.hasMoreTokens()){
							StringTokenizer s = new StringTokenizer( st.nextToken(), ":" );
							while ( s.hasMoreTokens() ) 
								subjList.addElement( s.nextToken() );
							
							if( subjectList.getSelectedIndex() != -1 )
								removeBtn.setEnabled( true );
						}
						else {
							subjList.removeAllElements();
						}
					}
					else if( room == 2 ){
						roomTypes.setSelectedIndex( 1 );
					}
					else{
						roomTypes.setSelectedIndex( room );
					}
				}				
			}
			else {
				update = false;
				editRmBtn.setEnabled( false );
				delRmBtn.setEnabled( false );
				clrRmBtn.setEnabled( false );
				clearRoom();
			}
		}
	}
	
	public static void setvisible( boolean j ){
		roomCode.setVisible( j );
		roomCodeTF.setVisible( j );
		roomDtls.setVisible( j );
		roomTypes.setVisible( j );
		roomLabel.setVisible( j );
		roomCBList.setVisible( j );
		addRmBtn.setVisible( j );
		editRmBtn.setVisible( j );
		clrRmBtn.setVisible( j );
		delRmBtn.setVisible( j );
		subjectlistPane.setVisible( j );
		subjectlistCBox.setVisible( j );
		s2Label.setVisible( j );
		sLabel.setVisible( j );
		addsubjBtn.setVisible( j );
		removeBtn.setVisible( j );
		tsLabel.setVisible( j );
		tsCBox.setVisible( j );
		teLabel.setVisible( j );
		teCBox.setVisible( j );
	}
	public static void setenable( boolean j ){
		roomCode.setEnabled( j );
		roomCodeTF.setEnabled( j );
		roomDtls.setEnabled( j );
		roomLabel.setEnabled( j );
		roomCBList.setEnabled( j );
		addRmBtn.setEnabled( j );
		editRmBtn.setEnabled( j );
		clrRmBtn.setEnabled( j );
		delRmBtn.setEnabled( j );
		roomTypes.setEnabled( j );
		subjectlistPane.setEnabled( j );
		subjectlistCBox.setEnabled( j );
		s2Label.setEnabled( j );
		sLabel.setEnabled( j );
		tsLabel.setEnabled( j );
		tsCBox.setEnabled( j );
		teLabel.setEnabled( j );
		teCBox.setEnabled( j );
		
	}
	public static void clearRoom(){
		roomCBList.setSelectedIndex(0);
		roomCodeTF.setText( "" );
		roomTypes.setSelectedIndex( 0 );
		time = true;
		tsCBox.setSelectedIndex( 0 );				
		teCBox.setSelectedIndex( 0 );
		//if ( subjList.getSize() > 0)
		subjList.removeAllElements();
	}
	public static void setselectedtask( int x ){
		roomCBList.setSelectedIndex( 0 );
		roomTaskSelected = x;
		if ( x == 1 ) {
			roomLabel.setEnabled( false ); 
			roomCBList.setEnabled( false );
			editRmBtn.setVisible( false );
			delRmBtn.setVisible( false );
			clrRmBtn.setEnabled( true );
		}
		else if ( x == 2 ) {
			setenable( false );
			addRmBtn.setVisible( false );
			delRmBtn.setVisible( false );
			roomLabel.setEnabled( true );
			roomCBList.setEnabled( true );
		}
		else if ( x == 3 ) {
			setenable( false );
			roomLabel.setEnabled( true );
			roomCBList.setEnabled( true );
			addRmBtn.setVisible( false );
			editRmBtn.setVisible( false );
			clrRmBtn.setVisible( false );
		}
	}
	private static void addComponent( Container container, Component c, int x, int y, int width, int height ) {
		c.setBounds( x, y, width, height );
		container.add( c );
	}
}
