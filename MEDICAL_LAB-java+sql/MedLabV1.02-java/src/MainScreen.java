import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.swing.JRootPane;
import javax.swing.JPanel;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

public class MainScreen {

	protected Shell shell;

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/medical_lab";
	static final String USER = "arin";
	static final String PASS = "1234";
	static public Connection conn;
	private Text text;
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		try {
			MainScreen window = new MainScreen();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCustomers() throws ClassNotFoundException, SQLException
	{
		Statement stmt = null;
	      //STEP 2: Register JDBC driver
		Class.forName("com.mysql.cj.jdbc.Driver");  

		stmt = conn.createStatement();
		String sql;
		sql = "SELECT * FROM CUSTOMER";
		ResultSet rs = stmt.executeQuery(sql);

		String res = String.format("ID %-20s %-5s %-12s %-12s\n", "Name", "Sex", "DoB", "JoinDate");
		//STEP 5: Extract data from result set
		while(rs.next()){
			//Retrieve by column name

		    int customer_ID  = rs.getInt("customer_ID");
		    String customer_name = rs.getString("customer_name");
		    String customer_gender = rs.getString("customer_gender");
		    String customer_dayOfBirth = rs.getString("customer_dayOfBirth");
		    String customer_joinDate = rs.getString("customer_joinDate");
		    res = res + String.format(" %s %-20s %-5s %-12s %-12s\n", customer_ID, customer_name, customer_gender, customer_dayOfBirth, customer_joinDate);
		    
		}
		return res;
	}
	
	public String getRequests() throws ClassNotFoundException, SQLException
	{
		Statement stmt = null;
	      //STEP 2: Register JDBC driver
		Class.forName("com.mysql.cj.jdbc.Driver");  

		stmt = conn.createStatement();
		String sql;
		sql = "SELECT * FROM REQUEST";
		ResultSet rs = stmt.executeQuery(sql);

		String res = String.format("ReqID CustID %-12s %-20s %-12s %-12s %4s\n","Status", "Type", "CreateDate", "ProcDate", "Cost");
		//STEP 5: Extract data from result set
		while(rs.next()){
			//Retrieve by column name
		    int req_ID = rs.getInt("request_ID");
		    int cust_ID = rs.getInt("customer_ID");
		    String proc_type = rs.getString("procedure_type");
		    String req_status = rs.getString("request_status");
		    String req_date = rs.getString("request_date");
		    String proc_date = rs.getString("procedure_date");
		    float req_cost = rs.getFloat("request_cost");
		    res = res + String.format("%-5s  %-5s %-12s %-20s %-12s %-12s %4s\n", req_ID, cust_ID, req_status, proc_type, req_date, proc_date, req_cost);
		}
		return res;
	}
	/**
	 * Open the window. 
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(692, 459);
		shell.setText("SWT Application");
		
		Label lblScreen = new Label(shell, SWT.BORDER);
		lblScreen.setFont(SWTResourceManager.getFont("Source Code Pro", 9, SWT.NORMAL));
		lblScreen.setLocation(0, 31);
		lblScreen.setSize(567, 379);
		lblScreen.setText("Screen");
		
		text = new Text(shell, SWT.BORDER);
		text.setLocation(599, 71);
		text.setSize(42, 21);
		text.setVisible(false);
		
		Label lblId = new Label(shell, SWT.NONE);
		lblId.setLocation(580, 75);
		lblId.setSize(13, 15);
		lblId.setText("ID");
		lblId.setVisible(false);
		
		Label errLabel = new Label(shell, SWT.NONE);
		errLabel.setLocation(570, 130);
		errLabel.setSize(96, 15);
		errLabel.setVisible(false);
		
		
		Composite CustomerScreen = new Composite(shell, SWT.NONE);
		CustomerScreen.setBounds(0, 31, 666, 379);
		CustomerScreen.setVisible(false);
		
		Button btnViewcustomer = new Button(CustomerScreen, SWT.NONE);
		btnViewcustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				int ID = 0;
				try
				{
				    ID = Integer.parseInt(text.getText());

				}
				catch (NumberFormatException et)
				{
					
				}
				if(ID >= 1)
				{
					Statement s = null;
					try {
						s = conn.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String sql = "SELECT CUSTOMER.customer_ID FROM CUSTOMER WHERE CUSTOMER.customer_ID = " + ID + ";";
					ResultSet rs = null;
					try {
						rs = s.executeQuery(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						if(rs.next() == false)
							errLabel.setText("ID not found");
						else
						{
							ViewCustomerScreen v = new ViewCustomerScreen(shell, SWT.NONE, conn, ID);
							v.open();
						}
					} catch (SQLException e1) {
							// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				else
					errLabel.setText("ID not found");
					
				
			}
		});
		btnViewcustomer.setLocation(570, 69);
		btnViewcustomer.setSize(86, 25);
		btnViewcustomer.setText("ViewCustomer");
		
		Button btnAddcustomer = new Button(CustomerScreen, SWT.NONE);
		btnAddcustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				AddCustomer addCustomerScreen = new AddCustomer(shell, SWT.NONE, conn);
				addCustomerScreen.open();			}
		});
		btnAddcustomer.setLocation(570, 10);
		btnAddcustomer.setSize(86, 25);
		btnAddcustomer.setText("AddCustomer");
		
		
		
		Composite RequestScreen = new Composite(shell, SWT.NONE);
		RequestScreen.setLocation(0, 31);
		RequestScreen.setSize(666, 379);
		RequestScreen.setVisible(false);
		
		Button btnViewrequest = new Button(RequestScreen, SWT.NONE);
		btnViewrequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				int ID = 0;
				try
				{
				    ID = Integer.parseInt(text.getText());

				}
				catch (NumberFormatException et)
				{
					
				}
				if(ID >= 1)
				{
					Statement s = null;
					try {
						s = conn.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String sql = "SELECT REQUEST.request_ID FROM REQUEST WHERE REQUEST.request_ID = " + ID + ";";
					ResultSet rs = null;
					try {
						rs = s.executeQuery(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						if(rs.next() == false)
							errLabel.setText("ID not found");
						else
						{
							ViewRequest r = new ViewRequest(shell, SWT.NONE, conn, ID);
							r.open();
						}
					} catch (SQLException e1) {
							// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
			}
			}});
		btnViewrequest.setLocation(570, 69);
		btnViewrequest.setSize(86, 25);
		btnViewrequest.setText("EditRequest");
		
		Button btnAddrequest = new Button(RequestScreen, SWT.NONE);
		btnAddrequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				AddRequestScreen m = new AddRequestScreen(shell, SWT.NONE, conn);
				m.open();
			}
		});
		btnAddrequest.setLocation(570, 10);
		btnAddrequest.setSize(86, 25);
		btnAddrequest.setText("AddRequest");
				
				Button btnCustomers = new Button(shell, SWT.NONE);
				btnCustomers.setBounds(0, 2, 75, 25);
				btnCustomers.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseUp(MouseEvent e) {
						lblScreen.setText("");
						RequestScreen.setVisible(false);
						CustomerScreen.setVisible(true);
						text.setVisible(true);
						lblId.setVisible(true);
						text.setText("");
						errLabel.setVisible(true);
						try {
							lblScreen.setText(getCustomers());
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						lblScreen.redraw();
						
					}
				});
				btnCustomers.setText("Customers");
				
		Button btnRequests = new Button(shell, SWT.NONE);
		btnRequests.setBounds(78, 2, 75, 25);
		btnRequests.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				lblScreen.setText("");
				RequestScreen.setVisible(true);
				CustomerScreen.setVisible(false);
				text.setVisible(true);
				lblId.setVisible(true);
				text.setText("");
				errLabel.setVisible(true);
				try {
					lblScreen.setText(getRequests());
					} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						
			}
		});
		btnRequests.setText("Requests");

	
		
		
		

	}
}
