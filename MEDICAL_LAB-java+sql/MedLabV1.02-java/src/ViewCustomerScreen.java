import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ViewCustomerScreen extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text IDText;
	private Text NameText;
	private Text StreetText;
	private Text StreetNoText;
	private Text ApartmentText;
	private Text CityText;
	private Text CountyText;
	private Text OtherText;
	private Text Phone1Text;
	private Text Phone2Text;
	private Combo GenderCombo;
	DateTime DoBText;
	private Connection conn;
	private int ID;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ViewCustomerScreen(Shell parent, int style, Connection c, int ID) {
		super(parent, style);
		setText("SWT Dialog");
		conn = c;
		this.ID = ID;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		try {
			writeText();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void writeText() throws SQLException
	{
		IDText.setText("" + ID);
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql;
		sql = "SELECT CUSTOMER.customer_name, CUSTOMER.customer_gender, CUSTOMER.customer_dayOfBirth FROM CUSTOMER WHERE CUSTOMER.customer_ID =" + ID;
		ResultSet rs = stmt.executeQuery(sql);

		while(rs.next()){
			//Retrieve by column name
		    String customer_name = rs.getString("customer_name");
		    NameText.setText(customer_name +"");
		    String customer_gender = rs.getString("customer_gender");
		    GenderCombo.setText(customer_gender + "");
		    String customer_dayOfBirth = rs.getString("customer_dayOfBirth");
		    String year = customer_dayOfBirth.substring(0, 4);
		    String month = customer_dayOfBirth.substring(5, 7);
		    String day = customer_dayOfBirth.substring(8, 10);
		    int y = Integer.parseInt(year);
		    int m = Integer.parseInt(month);
		    int d = Integer.parseInt(day);
		    DoBText.setDate(y, m, d);
		    
		}
		rs.close();
		sql = "SELECT * FROM ADDRESS WHERE ADDRESS.address_ID =" + ID;
		rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			//Retrieve by column name
		    String street = rs.getString("street");
		    StreetText.setText(street + "");
		    int street_no = rs.getInt("st_no");
		    StreetNoText.setText(street_no + "");
		    int apartment = rs.getInt("apartment");
		    ApartmentText.setText(apartment + "");
		    String city = rs.getString("city");
		    CityText.setText(city + "");
		    String state = rs.getString("state_county");
		    CountyText.setText(state + "");
		    String other = rs.getString("other");
		    OtherText.setText(other + "");
		}
		
		rs.close();
		sql = "SELECT * FROM PHONE_NO WHERE PHONE_NO.phone_ID =" + ID;
		rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			String ph1 = rs.getString("phone_no1");
			String ph2 = rs.getString("phone_no2");
			Phone1Text.setText(ph1 + "");
			Phone2Text.setText(ph2 + "");
			
		}
	}
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(723, 322);
		shell.setText(getText());
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setBounds(10, 31, 55, 15);
		lblName.setText("Name");
		
		Label lblGender = new Label(shell, SWT.NONE);
		lblGender.setBounds(10, 52, 55, 15);
		lblGender.setText("Gender");
		
		Label lblDayofbirth = new Label(shell, SWT.NONE);
		lblDayofbirth.setBounds(10, 79, 59, 15);
		lblDayofbirth.setText("DayOfBirth");
		
		Label lblStreet = new Label(shell, SWT.NONE);
		lblStreet.setBounds(254, 10, 55, 15);
		lblStreet.setText("Street");
		
		Label lblStreetno = new Label(shell, SWT.NONE);
		lblStreetno.setBounds(254, 31, 55, 15);
		lblStreetno.setText("StreetNo");
		
		Label lblApartment = new Label(shell, SWT.NONE);
		lblApartment.setBounds(254, 52, 66, 15);
		lblApartment.setText("Apartment");
		
		Label lblCity = new Label(shell, SWT.NONE);
		lblCity.setBounds(254, 73, 55, 15);
		lblCity.setText("City");
		
		Label lblCounty = new Label(shell, SWT.NONE);
		lblCounty.setBounds(254, 94, 55, 15);
		lblCounty.setText("County");
		
		Label lblOther = new Label(shell, SWT.NONE);
		lblOther.setBounds(254, 115, 55, 15);
		lblOther.setText("Other");
		
		Label lblPhone = new Label(shell, SWT.NONE);
		lblPhone.setBounds(501, 10, 40, 15);
		lblPhone.setText("Phone1");
		
		Label lblPhone_1 = new Label(shell, SWT.NONE);
		lblPhone_1.setBounds(501, 31, 40, 15);
		lblPhone_1.setText("Phone2");
		
		Label lblId = new Label(shell, SWT.NONE);
		lblId.setBounds(10, 10, 55, 15);
		lblId.setText("ID");
		
		IDText = new Text(shell, SWT.BORDER);
		IDText.setEditable(false);
		IDText.setBounds(75, 7, 172, 21);
		
		NameText = new Text(shell, SWT.BORDER);
		NameText.setBounds(75, 28, 172, 21);
		
		GenderCombo = new Combo(shell, SWT.READ_ONLY);
		GenderCombo.setItems(new String[] {"M", "F"});
		GenderCombo.setBounds(75, 49, 172, 23);
		
		DoBText = new DateTime(shell, SWT.BORDER);
		DoBText.setBounds(75, 73, 172, 24);
		
		StreetText = new Text(shell, SWT.BORDER);
		StreetText.setBounds(325, 7, 172, 21);
		
		StreetNoText = new Text(shell, SWT.BORDER);
		StreetNoText.setBounds(325, 28, 172, 21);
		
		ApartmentText = new Text(shell, SWT.BORDER);
		ApartmentText.setBounds(325, 49, 172, 21);
		
		CityText = new Text(shell, SWT.BORDER);
		CityText.setBounds(325, 70, 172, 21);
		
		CountyText = new Text(shell, SWT.BORDER);
		CountyText.setBounds(325, 91, 172, 21);
		
		OtherText = new Text(shell, SWT.BORDER);
		OtherText.setBounds(325, 112, 172, 21);
		
		Phone1Text = new Text(shell, SWT.BORDER);
		Phone1Text.setBounds(545, 7, 172, 21);
		
		Phone2Text = new Text(shell, SWT.BORDER);
		Phone2Text.setBounds(545, 28, 172, 21);
		
		Button btnEdit = new Button(shell, SWT.NONE);
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					edit();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEdit.setBounds(149, 226, 75, 25);
		btnEdit.setText("EDIT");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(492, 226, 75, 25);
		btnCancel.setText("CANCEL");
		
		Button btnDelete = new Button(shell, SWT.NONE);
		btnDelete.setBounds(642, 268, 75, 25);
		btnDelete.setText("DELETE");
		
		Button btnViewhistory = new Button(shell, SWT.NONE);
		btnViewhistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				HistoryScreen h = new HistoryScreen(shell, SWT.NONE, conn, ID);
				h.open();
			}
		});
		btnViewhistory.setBounds(561, 268, 75, 25);
		btnViewhistory.setText("ViewHistory");

	}
	public void edit() throws SQLException
	{
		CallableStatement cStmt = null;
		cStmt = conn.prepareCall("{CALL EditCustomer(?, ?, ?, ?)}");
		cStmt.setString(1, ID + "");
		cStmt.setString(2, NameText.getText());
		cStmt.setString(3, GenderCombo.getText());
		cStmt.setString(4, DoBText.getYear() + "-" + DoBText.getMonth() + "-" + DoBText.getDay());
		cStmt.executeQuery();
		
		cStmt.close();
		cStmt = conn.prepareCall("{CALL EditCustomerAddress(?, ?, ?, ?, ?, ?, ?)}");
		cStmt.setString(1, ID + "");
		cStmt.setString(2, StreetText.getText());
		cStmt.setString(3, StreetNoText.getText());
		cStmt.setString(4, ApartmentText.getText());
		cStmt.setString(5, CityText.getText());
		cStmt.setString(6, CountyText.getText());
		cStmt.setString(7, OtherText.getText());
		cStmt.executeQuery();
		
		cStmt.close();
		cStmt = conn.prepareCall("{CALL EditCustomerPhone(?, ?, ?)}");
		cStmt.setString(1, ID + "");
		cStmt.setString(2, Phone1Text.getText());
		cStmt.setString(3, Phone2Text.getText());
		
		shell.close();
	}
}
