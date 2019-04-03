import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import java.sql.*;

public class AddCustomer extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text NameText;
	private Text StreetText;
	private Text StreetNoText;
	private Text ApartmentText;
	private Text CityText;
	private Text StateText;
	private Text OtherText;
	private Text PhoneText;
	private Connection conn;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddCustomer(Shell parent, int style, Connection c) {
		super(parent, style);
		setText("SWT Dialog");
		conn = c;
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		shell.setSize(450, 300);
		shell.setText(getText());
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setBounds(0, 10, 65, 15);
		lblName.setText("Name");
		
		Label lblGender = new Label(shell, SWT.NONE);
		lblGender.setBounds(0, 40, 65, 15);
		lblGender.setText("Gender");
		
		Label lblDayofbirth = new Label(shell, SWT.NONE);
		lblDayofbirth.setBounds(0, 70, 65, 15);
		lblDayofbirth.setText("DayOfBirth");
		
		Label lblStreet = new Label(shell, SWT.NONE);
		lblStreet.setBounds(205, 12, 71, 15);
		lblStreet.setText("Street");
		
		Label lblStreetno = new Label(shell, SWT.NONE);
		lblStreetno.setBounds(205, 38, 71, 15);
		lblStreetno.setText("StreetNo");
		
		Label lblApartment = new Label(shell, SWT.NONE);
		lblApartment.setBounds(205, 65, 71, 15);
		lblApartment.setText("Apartment");
		
		Label lblCity = new Label(shell, SWT.NONE);
		lblCity.setBounds(205, 91, 71, 15);
		lblCity.setText("City");
		
		Label lblStatecounty = new Label(shell, SWT.NONE);
		lblStatecounty.setBounds(205, 118, 71, 15);
		lblStatecounty.setText("State/County");
		
		Label lblOther = new Label(shell, SWT.NONE);
		lblOther.setBounds(205, 145, 71, 15);
		lblOther.setText("Other");
		
		Label lblJoindate = new Label(shell, SWT.NONE);
		lblJoindate.setBounds(0, 100, 65, 15);
		lblJoindate.setText("JoinDate");
		
		NameText = new Text(shell, SWT.BORDER);
		NameText.setBounds(71, 7, 128, 21);
		
		DateTime DayOfBirthText = new DateTime(shell, SWT.BORDER);
		DayOfBirthText.setBounds(71, 61, 128, 24);
		
		DateTime JoinDateText = new DateTime(shell, SWT.BORDER);
		JoinDateText.setBounds(71, 91, 128, 24);
		
		Combo GenderText = new Combo(shell, SWT.READ_ONLY);
		GenderText.setItems(new String[] {"M", "F"});
		GenderText.setBounds(71, 32, 128, 23);
		
		StreetText = new Text(shell, SWT.BORDER);
		StreetText.setBounds(282, 7, 152, 21);
		
		StreetNoText = new Text(shell, SWT.BORDER);
		StreetNoText.setBounds(282, 34, 152, 21);
		
		ApartmentText = new Text(shell, SWT.BORDER);
		ApartmentText.setBounds(282, 61, 152, 21);
		
		CityText = new Text(shell, SWT.BORDER);
		CityText.setBounds(282, 88, 152, 21);
		
		StateText = new Text(shell, SWT.BORDER);
		StateText.setBounds(282, 115, 152, 21);
		
		OtherText = new Text(shell, SWT.BORDER);
		OtherText.setBounds(282, 142, 152, 21);
		
		Label lblPhone = new Label(shell, SWT.NONE);
		lblPhone.setBounds(205, 172, 71, 15);
		lblPhone.setText("Phone");
		
		PhoneText = new Text(shell, SWT.BORDER);
		PhoneText.setBounds(282, 169, 152, 21);
		
		Button btnAdd = new Button(shell, SWT.NONE);
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					String bDay = DayOfBirthText.getYear() + "-" + DayOfBirthText.getMonth() + "-" + DayOfBirthText.getDay();
					String jDay = JoinDateText.getYear() + "-" + JoinDateText.getMonth() + "-" + JoinDateText.getDay();
					add(NameText.getText(), GenderText.getText(), bDay, jDay, StreetText.getText(), StreetNoText.getText(),
						ApartmentText.getText(), CityText.getText(), StateText.getText(), OtherText.getText(),PhoneText.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnAdd.setBounds(107, 236, 75, 25);
		btnAdd.setText("ADD");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(282, 236, 75, 25);
		btnCancel.setText("CANCEL");

	}
	
	public void add(String name, String gender, String dayOfBirth, String joinDate, String street, String streetNo,
					String apartment, String city, String state, String other, String phone) throws SQLException
	{
		CallableStatement cStmt = null;
		cStmt = conn.prepareCall("{CALL AddCustomer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		cStmt.setString(1, name);
		cStmt.setString(2, gender);
		cStmt.setString(3, dayOfBirth);
		cStmt.setString(4, phone);
		cStmt.setString(5, street);
		cStmt.setString(6, streetNo);
		cStmt.setString(7, apartment);
		cStmt.setString(8, city);
		cStmt.setString(9, state);
		cStmt.setString(10, other);		
		cStmt.setString(11, joinDate);
		cStmt.executeQuery();
		
		shell.close();
	}
}
