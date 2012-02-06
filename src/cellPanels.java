import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class cellPanels extends JPanel {
	public static JFrame frame = new JFrame();
	JLabel courseName = new JLabel();
	
	public cellPanels(String course) {
		courseName.setText(course);
		this.setVisible( true );
		this.add(courseName);
		this.setOpaque( false );
		courseName.setBounds( 0,0,10,10 );
		courseName.setVisible( true );
	}
	public void settext(String text) {
		courseName.setText(text);
	}
}
