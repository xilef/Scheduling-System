import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.tree.TreePath;

@SuppressWarnings( "serial" )
public class sectionPanel extends JPanel {
	private static int x, previous = 0;
	public static JButton addB = new JButton( "Add" );
	private static JButton modB = new JButton( "Change" );
	private static JPanel group = new JPanel();
	private static JButton saveB = new JButton( "Save" );
	public static String output[][] = {{"","","",""},{"","","",""},{"","","",""}};
	//private static JLabel secAssign = new JLabel( "Select a major and level to edit:" );
	private static JLabel secNumber = new JLabel( "No. of blocks (1-10):" );
	private static JLabel mj = new JLabel("Major:");
	private static JLabel lv = new JLabel("Level:");
	private static JLabel label2 = new JLabel();
	private static JLabel alNum = new JLabel("Sectioning:");
	private static JRadioButton alpha = new JRadioButton("Alphabetic");
	private static JRadioButton numeric = new JRadioButton("Numeric");
	private static ButtonGroup sectioning = new ButtonGroup();
	private static int majorInt = 0;
	private static int year = 0;
	private static int blocks = 0;
	private static int levelOk[][] = new int[3][4];
	private static String add = "", major = "";
	public static JTextArea outArea = new JTextArea();
	public static JTextField secNum = new JTextField();
	private static JScrollPane secSPane = new JScrollPane( outArea );
	public static boolean complete = true, isChange = false, fromTree = false;
	public static JComboBox majors, years;
	public static String coursemajors[] = {"---------","Computer Science","Information Technology","Information Systems"};
	public static String levels[] = {"---------", "First Year", "Second Year","Third Year","Fourth Year"};
	
	/*
	 * Constructor
	 */
	public sectionPanel() {
		secSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sectioning.add( alpha );
		sectioning.add( numeric );
		majors = new JComboBox( coursemajors );
		years = new JComboBox( levels );
		group.setBorder(new TitledBorder("Select a major and level to edit:"));
		group.setLayout( null );
		group.setVisible( true );
		//addComponent( this, secAssign, 5, 5, 450, 20 );
		
		addComponent( group, mj, 20, 30, 100, 20 );
		addComponent( group, lv, 210, 30, 100, 20 );
		
		addComponent( group, majors, 20, 55, 160, 20 );
		addComponent( group, years, 210, 55, 100, 20);
		
		addComponent( group, secNumber, 20, 100, 120, 20 );
		addComponent( group, secNum, 150, 100, 100, 20 );
		
		addComponent( group, alNum, 20, 130, 120, 20 );
		addComponent( group, alpha, 145,130, 90, 20 );
		addComponent( group, numeric, 240, 130, 80, 20 );
		
		addComponent( group, addB, 105, 170, 85, 20 );
		addComponent( group, modB, 105, 170, 85, 20 );
		addComponent( this, group, 5,5, 330,205);
		
		addComponent( this, label2, 20, 220, 300, 20 );
		addComponent( this, secSPane, 75, 260, 150, 100);

		addComponent( this, saveB, 105, 380, 85, 20 );
		
		
		years.addActionListener( new yearListener() );
		majors.addActionListener( new majorListener() );
		addB.addActionListener( new addSectionListener() );
		modB.addActionListener( new addSectionListener() );
		saveB.addActionListener( new saveSectionListener() );
		addB.setVisible( true );
		secSPane.setVisible( true );
		majors.setVisible( true );
		outArea.setEditable( false );
		outArea.setOpaque( false );
		years.setVisible( true );
		setvisible( false );
		addB.setVisible( true );
	}
	
	/*
	 * Gets the values
	 */
	public String getmajor() {
		return major;
	}
	public String getadd() {
		return add;
	}
	public int[][] getlevelOK() {
		return levelOk;
	}
	public int getblocks() {
		return blocks;
	}
	public int getyear() {
		return year;
	}
	public String[][] getoutput() {
		return output;
	}
	public int getmajorInt() {
		return majorInt;
	}
	
	/*
	 * Sets the values.
	 */
	public void outAreasetText( String text ) {
		outArea.setText( text );
	}
	public void labelsettext( String text ) {
		label2.setText( text );
	}
	public void setmajor( String in ) {
		major = in;
	}
	public void setadd( String in ) {
		add = in;
	}
	public void setlevelOk( int[][] in ) {
		levelOk = in;
	}
	public void setblocks( int in ) {
		blocks = in;
	}
	public void setyear( int in ) {
		year = in;
		fromTree = true;
		years.setSelectedIndex( in + 1 );
	}
	public void setoutput( String[][] in ) {
		output = in;
	}
	public void setmajorInt( int in ) {
		majorInt = in;
		fromTree = true;
		majors.setSelectedIndex( in );
	}

	public static void setComplete(boolean flag){
		complete = flag;
	}
	public static boolean isComplete(){
		if ( complete ){
			return true;
		}
		else{
		for ( int i = 0; i < 3; i++ ) {
			for( int m = 0; m < 4; m++ ) {
				if ( output[i][m].equals( "" ) ) {
					return false;
				}
			}
		}
		
		return true;
		}
	}
	public static void save(boolean saveManual){
		String line[] = { "", "", "" };
		int op = 1;
		
		if ( !isComplete() && !saveManual ){
			JOptionPane.showMessageDialog( null,"Sections are not complete. Cannot continue with action.","Input Incomplete",JOptionPane.ERROR_MESSAGE );
			return;
		}
		
		if ( saveManual ){
			op = JOptionPane.showConfirmDialog( null, "Save sections for all levels?","Confirm",JOptionPane.OK_CANCEL_OPTION );
		
			if ( op == 0 ) {
				for ( int i = 0; i < 3; i++ ) {
					for( int m = 0; m < 4; m++ ) 
						line[i] += output[i][m]+" "; 
				}
				
				try {
					file_handle.setSections(line);
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
				JOptionPane.showMessageDialog( null, "Sections are saved." );
			}
		}
		else{
			if (!SchedulerBeta.open){
				for ( int i = 0; i < 3; i++ ) {
					for( int m = 0; m < 4; m++ ) 
						line[i] += output[i][m]+" "; 
				}
				
				try {
					file_handle.setSections(line);
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
			}
		}
	}
	public static void clear(){
		sectioning.clearSelection();
		secNum.setText("");
	}
	/*
	 * Displays certain parts of the panel depending on the selection
	 */
	public static void setvisible( boolean aFlag ) {
		secNum.setEnabled( aFlag );
		secNumber.setEnabled( aFlag );
		secSPane.setEnabled( aFlag );
		outArea.setVisible( aFlag );
		//outArea.setEnabled( aFlag );
		addB.setEnabled( aFlag );
		modB.setEnabled( aFlag );
		addB.setVisible( aFlag );
		modB.setVisible( aFlag );
		saveB.setEnabled( aFlag );
		label2.setEnabled( aFlag );
		alpha.setEnabled( aFlag );
		numeric.setEnabled( aFlag );
		alNum.setEnabled( aFlag );
	}
	
	/*
	 * Combo box listeners
	 */
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
				setvisible( false );
				addB.setVisible( true );
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
			clear();
		}
	}
	
	/*
	 * Button listeners
	 */
	private static class addSectionListener implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			String j = "";
			
			if ( secNum.getText().equals("") || secNum.getText() == null){
				JOptionPane.showMessageDialog( null,"Please enter a number from 1 to 10.","Input Error",JOptionPane.ERROR_MESSAGE );
				clear();
				return;
			}
			
			try {
				blocks = Integer.parseInt( secNum.getText() );} 
			catch ( NumberFormatException nfe ) {
				j = nfe.toString();
				JOptionPane.showMessageDialog( null,"Please enter a number from 1 to 10.","Input Error",JOptionPane.ERROR_MESSAGE );
				clear();
				return;
			}
			
			if ( !j.equals("")){
				JOptionPane.showMessageDialog( null,"Please enter a number from 1 to 10.","Input Error",JOptionPane.ERROR_MESSAGE );
				clear();
				return;
			}
			
			if ( blocks < 1 || blocks > 10 ){
				JOptionPane.showMessageDialog( null,"Please enter a number from 1 to 10.","Input Error",JOptionPane.ERROR_MESSAGE );
				clear();
				return;
			}
			
			if ( !alpha.isSelected() && !numeric.isSelected() && blocks != 1){
				JOptionPane.showMessageDialog( null,"Please choose a sectioning style.","Input Error",JOptionPane.ERROR_MESSAGE );
				clear();
				return;
			}
			x = majorInt - 1;
			if ( blocks == 1 ){
				output[x][year] = "";
				output[x][year] = Integer.toString( year+1 )+major+" ";
				outArea.setText( output[x][year] );
				clear();
			}
			
			else if ( alpha.isSelected() ){
				String []alphaArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
				output[x][year] = "";
				for( int ctr=0; ctr < blocks; ctr++ ) {
					add = ""+alphaArray[ctr];
					output[x][year] += Integer.toString( year+1 )+major+add+" ";
				}
				outArea.setText("");
				StringTokenizer st = new StringTokenizer(output[x][year]," ");
				while(st.hasMoreTokens()){
					outArea.append(st.nextToken()+"\n");
				}
				clear();
			}
			else if ( numeric.isSelected() ){
				output[x][year] = "";
				for( int ctr=0; ctr < blocks; ctr++ ) {
					add = String.valueOf( ctr+1 );
					output[x][year] += Integer.toString( year+1 )+major+add+" ";
				}
				outArea.setText("");
				StringTokenizer st = new StringTokenizer(output[x][year]," ");
				while(st.hasMoreTokens()){
					outArea.append(st.nextToken()+"\n");
				}
				clear();
			}
		}
	}
	
	private static class saveSectionListener implements ActionListener{
		public void actionPerformed( ActionEvent e ){
			save( true );
		}
	}

	private static void addComponent( Container container, Component c, int x, int y, int width, int height ){
		c.setBounds( x,y,width,height );
		container.add( c );
	}
	
	/*
	 * Tester
	 */
	public static void main( String args[] ) {
		sectionPanel a = new sectionPanel();
		JFrame window = new JFrame();
		window.setContentPane( a );
		window.setBounds( 300, 200, 300, 400 );
		window.setVisible( true );
	}
}
