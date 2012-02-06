import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.StringTokenizer;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Arrays;

@SuppressWarnings( "serial" )
public class professorsPanel extends JPanel {
	private static JLabel nameLabel = new JLabel( "Name:" );
	private static JLabel tsLabel = new JLabel( "Time Start:" );
	private static JLabel teLabel = new JLabel( "Time End:" );
	private static JLabel sLabel = new JLabel( "Subject/s Taught:" );
	private static JLabel s2Label = new JLabel( "Subjects:" );
	private static JLabel cnameLabel = new JLabel( "Select a name: " );
	
	private static String profDetails[] = new String[4];
	private static String proflist[] ={ };
	private static String subjlist[] = {};
	
	private static JTextField nameTField = new JTextField();
	
	private static JComboBox tsCBox;
	private static JComboBox teCBox;
	private static JComboBox proflistCBox;
	private static JComboBox subjectlistCBox;
	
	private static JList subjectList;
	private static DefaultListModel listModel;
	
	private static JButton addProfBtn = new JButton( "Add Subject" );
	private static JButton removeBtn = new JButton( "Remove Subject" );
	private static JButton saveProfBtn = new JButton( "Save" );
	private static JButton updateProfBtn = new JButton( "Update" );
	private static JButton deleteProfBtn = new JButton( "Remove" );
	private static JButton clearProf = new JButton( "Clear" );
	
	private static JScrollPane subjectlistPane;
	private static MyListSelectionListener psubjectlistListen = new MyListSelectionListener();
	private static Boolean time = false;
	private static int profTasks;
	
	public professorsPanel() {
		String timeList[] = { "-------", "7:00 AM","8:00 AM","9:00 AM","10:00 AM","11:00 AM","12:00 PM",
				"1:00 PM","2:00 PM","3:00 PM","4:00 PM","5:00 PM","6:00 PM",
				"7:00 PM","8:00 PM" };
		
		try {
			proflist = file_handle.getProflist( "--------" );
			subjlist = file_handle.getCourselist( " " );
			Arrays.sort(proflist);
			Arrays.sort(subjlist);
		} catch ( IOException e ) {
			e.printStackTrace();
			return;
		}
		proflistCBox = new JComboBox( proflist );
		subjectlistCBox = new JComboBox( subjlist );
		tsCBox = new JComboBox( timeList );
		teCBox = new JComboBox( timeList );
		listModel = new DefaultListModel();
		subjectList = new JList( listModel );
		subjectlistPane = new JScrollPane( subjectList );
		
		tsCBox.setSelectedIndex( 0 );
		teCBox.setSelectedIndex( 0 );
		
		listModel.addElement( "" );
		subjectList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		subjectList.setLayoutOrientation( JList.VERTICAL );
		
		addComponent( this, cnameLabel, 3,40,100,20 );
		addComponent( this, proflistCBox, 105,40,190,20 );
		addComponent( this, nameLabel, 3,70,100,20 );
		addComponent( this, nameTField, 105,70,190,20 );
		addComponent( this, tsLabel, 3, 100, 100, 20 );
		addComponent( this, tsCBox, 105, 100, 100, 20 );
		addComponent( this, teLabel, 3, 130, 100, 20 );
		addComponent( this, teCBox, 105, 130, 100, 20 );
		addComponent( this, sLabel, 3, 160, 100, 20 );
		addComponent( this, subjectlistPane, 105,160,190,70 );
		addComponent( this, s2Label, 3, 240, 100, 20 );
		addComponent( this, subjectlistCBox, 105, 240, 190, 20 );
		addComponent( this, removeBtn,325,170,128,20 );
		addComponent( this, addProfBtn,325,240,128,20 );
		addComponent( this, saveProfBtn, 105,290,100,20 );
		addComponent( this, updateProfBtn, 105,290,100,20 );
		addComponent( this, deleteProfBtn, 150,290,100,20 );
		addComponent( this, clearProf, 210,290,100,20 );
		
		subjectList.addListSelectionListener( psubjectlistListen );

		proflistCBox.addActionListener( new proflistHandle() );
		removeBtn.addActionListener( new removeHandle() );
		subjectlistCBox.addActionListener( new profSubjectHandle() );
		addProfBtn.addActionListener( new addSubjectHandle() );
		saveProfBtn.addActionListener( new saveProfHandle() );
		updateProfBtn.addActionListener( new saveProfHandle() );
		deleteProfBtn.addActionListener( new saveProfHandle() );
		clearProf.addActionListener( new clearHandle() );
		tsCBox.addActionListener( new timeHandle() );
		teCBox.addActionListener( new timeHandle() );
		
		setvisible( false );
	}
	
	public void removeBtnsetEnabled ( boolean arg ) {
		removeBtn.setEnabled( arg );
	}
	public void setprofTasks( int in ) {
		profTasks = in;
	}
	public void settime( boolean in ) {
		time = in;
	}
	public void setvisible( boolean aFlag ) {
		cnameLabel.setVisible( aFlag );
		proflistCBox.setVisible( aFlag );
		nameLabel.setVisible( aFlag );
		nameTField.setVisible( aFlag );
		tsLabel.setVisible( aFlag );
		tsCBox.setVisible( aFlag );
		teLabel.setVisible( aFlag );
		teCBox.setVisible( aFlag );
		sLabel.setVisible( aFlag );
		subjectlistPane.setVisible( aFlag );
		s2Label.setVisible( aFlag );
		subjectlistCBox.setVisible( aFlag );
		addProfBtn.setVisible( aFlag );
		removeBtn.setVisible( aFlag );
		saveProfBtn.setVisible( aFlag );
		updateProfBtn.setVisible( aFlag );
		deleteProfBtn.setVisible( aFlag );
		clearProf.setVisible( aFlag );
		proflistCBox.setSelectedIndex( 0 );
	}
	public static void setenable( boolean arg ) {
		nameTField.setEnabled( arg );
		tsCBox.setEnabled( arg );
		teCBox.setEnabled( arg );
		subjectlistPane.setEnabled( arg );
		subjectlistCBox.setEnabled( arg );
		subjectList.setEnabled( arg );
		addProfBtn.setEnabled( arg );
		nameTField.setText( "" );
		tsCBox.setSelectedIndex( 0 );
		teCBox.setSelectedIndex( 0 );
		listModel.removeAllElements();
	}
	
	public int getprofTasks() {
		return profTasks;
	}
	public boolean gettime() {
		return time;
	}
	
	public void aProfListener() {
		time = false;
		setvisible( true );
		updateProfBtn.setVisible( false );
		deleteProfBtn.setVisible( false );
		tsCBox.setSelectedIndex( 0 );
		teCBox.setSelectedIndex( 0 );
		listModel.removeAllElements();
		cnameLabel.setEnabled( false );
		proflistCBox.setEnabled( false );
		clearProf.setEnabled( true );
		saveProfBtn.setEnabled( true );
		subjectlistCBox.setEnabled( true );
		addProfBtn.setEnabled( true );
		setenable( true );
	}
	public void eProfListener() {
		time = true;
		setvisible( true );
		setenable( false );
		proflistCBox.setEnabled( true );
		cnameLabel.setEnabled( true );
		if ( profTasks == 3 ) {
			saveProfBtn.setVisible( false );
			updateProfBtn.setVisible( false );
			deleteProfBtn.setVisible( true );
			clearProf.setVisible( false );
		}
		else {
			saveProfBtn.setVisible( false );
			updateProfBtn.setVisible( true );
			deleteProfBtn.setVisible( false );
			clearProf.setVisible( true );
		}
	}
	
	private static class MyListSelectionListener implements ListSelectionListener {
		public void valueChanged( ListSelectionEvent e ) {
			if( subjectList.getSelectedIndex() != -1 )
				removeBtn.setEnabled( true );
		}	
	}
	public static class proflistHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			removeBtn.setEnabled( false );
			updateProfBtn.setEnabled( false );
			deleteProfBtn.setEnabled( false );
			clearProf.setEnabled( false );
			if ( proflistCBox.getSelectedIndex() > 0 ) {
				time = true;
				updateProfBtn.setEnabled( true );
				deleteProfBtn.setEnabled( true );
				clearProf.setEnabled( true );
				if ( profTasks != 3 )
					setenable( true );
				String profName = proflistCBox.getSelectedItem().toString();
				listModel.removeAllElements();
				try {
					String details = file_handle.getProfDetails( profName );
						StringTokenizer st = new StringTokenizer( details, "," );
						if ( st.hasMoreTokens() ) 
							nameTField.setText( st.nextToken() );
						else 
							nameTField.setText( "" );
						
						if ( st.hasMoreTokens() ) 
							tsCBox.setSelectedIndex( Integer.parseInt( st.nextToken() ) );
						else 
							tsCBox.setSelectedIndex( 0 );
						
						if ( st.hasMoreTokens() ) 
							teCBox.setSelectedIndex( Integer.parseInt( st.nextToken() ) );
						else 
							teCBox.setSelectedIndex( 1 );
						
						if ( st.hasMoreTokens() ) {
							StringTokenizer s = new StringTokenizer( st.nextToken(), ":" );
							while ( s.hasMoreTokens() ) 
								listModel.addElement( s.nextToken() );
							
							if( subjectList.getSelectedIndex() != -1 )
								removeBtn.setEnabled( true );
						}
						else {
							listModel.removeAllElements();
							removeBtn.setEnabled( false );
						}
				} catch ( IOException e1 ) {
					e1.printStackTrace();
					return;
				}
			}
			else {
				time = true;
				setenable( false );
			}
			time = false;
		}
	}
	private static class profSubjectHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ){
			if ( subjectlistCBox.getSelectedIndex() == 0 )
				addProfBtn.setEnabled( false );
			else
				addProfBtn.setEnabled( true );
		}
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
	private static class addSubjectHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if ( subjectlistCBox.getSelectedIndex() == 0 ) {
				JOptionPane.showMessageDialog( null, "There are no selected subjects!", 
						"Error", JOptionPane.ERROR_MESSAGE );
			}
			else if ( listModel.contains( subjectlistCBox.getSelectedItem().toString() ) ) {
				JOptionPane.showMessageDialog( null, "Selected subject already in the list!", 
						"Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			else {
				listModel.addElement( subjectlistCBox.getSelectedItem().toString() );
				subjectlistCBox.setSelectedIndex( 0 );
			}
		}
	}
	private static class saveProfHandle implements ActionListener {
		public String localName;
		public void actionPerformed( ActionEvent e ) {
			if ( profTasks == 3 ) {
				int answer = JOptionPane.showConfirmDialog( null,"Are you sure you want to remove this professor?","Confirm Delete",JOptionPane.YES_NO_OPTION );
				if ( answer == 0 )
					deleteProf();
			}
			else if ( profTasks == 2 ) {
				int answer = JOptionPane.showConfirmDialog( null,"Are you sure you want to save your modifications for this professor?","Confirm Delete",JOptionPane.YES_NO_OPTION );
				if ( answer == 0 ) {
					deleteProf();
					addProf();
				}
			}
			
			else if ( profTasks == 1 )
				addProf();
		}
		
		private void addProf(){
			if ( nameTField.getText().equals( "" ) ) {
				JOptionPane.showMessageDialog( null, "Please fill in the required fields.", "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			if( tsCBox.getSelectedIndex() == 0 && teCBox.getSelectedIndex() != 0 ) {
				JOptionPane.showMessageDialog( null, "Please choose a start time.", "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			int num;
			if ( listModel.getSize() == 0 )
				num = 3;
			else
				num = 4;
		
			localName = nameTField.getText();
			profDetails = new String[num];
			profDetails[0] = nameTField.getText();
			profDetails[1] = String.valueOf( tsCBox.getSelectedIndex() );
			profDetails[2] = String.valueOf( teCBox.getSelectedIndex() );
			if ( num == 4 )
				profDetails[3] = listModel.getElementAt( 0 ).toString();
			for ( num = 1; num < listModel.getSize(); num++ )
				profDetails[3] = profDetails[3] + ":" + listModel.getElementAt( num );
			
			@SuppressWarnings("unused")
			String proflist1[] = new String[0];
			
			try {
				file_handle.setProfDetails( profDetails );
				proflist1 = file_handle.getProflist( "" );
			}catch ( IOException e1 ) {
				e1.printStackTrace();
				return;
			}
	
			
			if ( profTasks == 1 ) {
				for (int x = 0; x < proflistCBox.getItemCount(); x++) {
					if (proflistCBox.getItemAt(x).equals(nameTField.getText())) {
						proflistCBox.removeItemAt(x);
						break;
					}
				}
				proflistCBox.addItem( nameTField.getText() );
				nameTField.setText( "" );
				time = true;
				tsCBox.setSelectedIndex( 0 );				
				teCBox.setSelectedIndex( 0 );
				listModel.removeAllElements();
				JOptionPane.showMessageDialog( null, "Professor details successfully added.","Success!", JOptionPane.INFORMATION_MESSAGE );
				
				DefaultTreeModel tm = (DefaultTreeModel)SchedulerBeta.yearTree.getModel();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) SchedulerBeta.rootProf ;
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(profDetails[0]);
				tm.insertNodeInto(newNode, node, node.getChildCount());
			}
			else if ( profTasks == 2 ) {
				proflistCBox.removeItemAt( proflistCBox.getSelectedIndex() );
				proflistCBox.setSelectedIndex( 0 );
				proflistCBox.addItem( localName );
				nameTField.setText( "" );
				time = true;
				tsCBox.setSelectedIndex( 0 );				
				teCBox.setSelectedIndex( 0 );
				listModel.removeAllElements();
				JOptionPane.showMessageDialog( null, "Professor details successfully updated.", "Success!", JOptionPane.INFORMATION_MESSAGE );
			}
			nameTField.setText( "" );
			time = true;
			tsCBox.setSelectedIndex( 0 );				
			teCBox.setSelectedIndex( 0 );
			listModel.removeAllElements();
		}
		
		private void deleteProf() {
			try {
				String testing = proflistCBox.getSelectedItem().toString();
				System.out.println(testing);
				file_handle.removeProf( testing );
			} catch ( IOException e2 ) {
				e2.printStackTrace();
				return;
			}
			if( profTasks != 2 ) {
				proflistCBox.removeItemAt( proflistCBox.getSelectedIndex() );
				proflistCBox.setSelectedIndex( 0 );
			}
		}
	}
	private static class removeHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if ( subjectList.getSelectedIndex() >= 0 ) {
				listModel.removeElementAt( subjectList.getSelectedIndex() );
				if ( listModel.getSize() == 0 ) {
					removeBtn.setEnabled( false );
				}
			}
		}
	}
	private static class clearHandle implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			nameTField.setText( "" );
			time = true;
			tsCBox.setSelectedIndex( 0 );				
			teCBox.setSelectedIndex( 0 );
			listModel.removeAllElements();
		}
	}
	
	private static void addComponent( Container container, Component c, int x, int y, int width, int height ) {
		c.setBounds( x, y, width, height );
		container.add( c );
	}
	
	/*
	 * Tester
	 */
	public static void main( String args[] ) {
		professorsPanel a = new professorsPanel();
		JFrame window = new JFrame();
		
		a.setvisible( true );
		a.setLayout( null );
		window.setContentPane( a );
		window.setBounds( 300, 200, 300, 400 );
		window.setVisible( true );
	}
}
