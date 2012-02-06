import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class inputSem extends JPanel{
	public static String semS[] = {"","1st Sem","2nd Sem"}, s = "", syear = ""; 
	public static JComboBox sem = new JComboBox(semS);
	public static JComboBox schoolyears;
	public static JFrame frame = new JFrame();
	public static JButton ok = new JButton("Continue");
	public static JLabel semester = new JLabel("Semester:");
	public static JLabel sy = new JLabel("School Year:");
	public static JButton cancel = new JButton("Cancel");
	public static boolean fake = false;
	Calendar c = Calendar.getInstance();
	public inputSem(){
		String test[] = new String[10];
		int next = 0;
		String choices[] = new String[10];
	
		test[0] = c.getTime().toString().substring(c.getTime().toString().length()-4);
		for( int x = 1; x<10; x++){
			next = Integer.parseInt(test[x-1]);
			next += 1;
			test[x] = String.valueOf(next);
		}
		choices[0] = "";
		for( int x = 0; x<9; x++){
			choices[x+1] = test[x]+" - "+test[x+1];
		}
		
		position();
		this.setLayout(null);
		schoolyears = new JComboBox(choices);
		addComponent(this, semester, 75,10,100,20);
		addComponent(this, sem, 95,40,120,20);
		addComponent(this, sy, 75,80,100,20);
		addComponent(this, schoolyears,95,110,120,20);
		
		
		addComponent(this, ok, 32,160,100,20);
		addComponent(this, cancel, 158,160,100,20);
		sem.setSelectedIndex(0);
		schoolyears.setSelectedIndex(0);
		ok.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e){
				if ( sem.getSelectedIndex() == 0 ){
					JOptionPane.showMessageDialog( null,"Please select a semester.","Input Incomplete",JOptionPane.ERROR_MESSAGE );
					return;
				}
				if ( schoolyears.getSelectedIndex() == 0 ){
					JOptionPane.showMessageDialog( null,"Please select a school year.","Input Incomplete",JOptionPane.ERROR_MESSAGE );
					return;
				}
				
				s = sem.getSelectedItem().toString();
				syear = schoolyears.getSelectedItem().toString();
				SchedulerBeta.sy = syear;
				fake = false;
				Object source = e.getSource();
				if (source instanceof Component) {
					Window w = getWindow((Component) e.getSource());
					if (w != null) {
						Toolkit t = Toolkit.getDefaultToolkit();
						EventQueue eq = t.getSystemEventQueue();
						eq.postEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));
					}
				}
				return;
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e){
				Object source = e.getSource();
				if (source instanceof Component) {
					Window w = getWindow((Component) e.getSource());
					if (w != null) {
						Toolkit t = Toolkit.getDefaultToolkit();
						EventQueue eq = t.getSystemEventQueue();
						eq.postEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));
					}
				}
				fake = true;
				return;
			}
		});
		cancel.setVisible( true );
		ok.setVisible( true );
		sem.setVisible( true );
		frame.setContentPane(this);
	}
	public void position(){
		int x, y;
		Point topLeft = SchedulerBeta.window.getLocationOnScreen();
		Dimension parentSize = SchedulerBeta.window.getSize();

		  if (parentSize.width > 300) 
		    x = ((parentSize.width - 300)/2) + topLeft.x;
		  else 
		    x = topLeft.x;
		   
		  if (parentSize.height > 320) 
		    y = ((parentSize.height - 320)/2) + topLeft.y;
		  else 
		    y = topLeft.y;
		   
		frame.setLocation (x, y);
		frame.setTitle("Semester and School Year");
		frame.setLayout( null );
		frame.setSize( new Dimension( 300, 240 ) );
		frame.setResizable( false );
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener( new WindowAdapter() {
			public void windowClosing ( WindowEvent e ) {
				Window w = (Window) e.getSource();
				w.dispose();
			}
		});
	}
	private static void addComponent( Container container, Component c, int x, int y, int width, int height ) {
		c.setBounds( x, y, width, height );
		container.add( c );
	}
	private static Window getWindow(Component comp) {
		Component parent = null;
		while (comp != null) {
			parent = comp;
			if (comp instanceof JPopupMenu)
				comp = ((JPopupMenu) comp).getInvoker();
			else
				comp = comp.getParent();
		}
		if (parent instanceof Window)
			return (Window) parent;
		else
			return null;
	}
}
