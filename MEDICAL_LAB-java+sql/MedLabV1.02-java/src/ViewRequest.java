import java.sql.*;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Combo;

public class ViewRequest extends Dialog {

	protected Object result;
	protected Shell shell;
	private Connection conn;
	private Text RequestIDText;
	private Text CustomerIDText;
	private Combo RStatusText;
	private Text RTypeText;
	private Text CostText;
	private Combo SStatusText;
	private Text STypeText;
	private Text LocationText;
	private Text ResultText;
	private Combo ResultStatusText;
	private Text PStatusText;
	private Text BalanceText;
	private DateTime CreateDateText;
	private DateTime ProcDateText;
	private int ID;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ViewRequest(Shell parent, int style, Connection c, int i) {
		super(parent, style);
		setText("SWT Dialog");
		conn = c;
		ID = i;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		try {
			writeText(ID);
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

	
	public void writeText(int ID) throws SQLException
	{
		RequestIDText.setText(ID + "");
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql;
		sql = "SELECT * FROM REQUEST WHERE REQUEST.request_ID =" + ID;
		ResultSet rs = stmt.executeQuery(sql);

		while(rs.next()){
			//Retrieve by column name
		    String customer_ID = rs.getString("customer_ID");
		    CustomerIDText.setText(customer_ID +"");
		    String requestStatus = rs.getString("request_status");
		    RStatusText.setText(requestStatus + "");
		    String createDate = rs.getString("request_date");
		    String year = createDate.substring(0, 4);
		    String month = createDate.substring(5, 7);
		    String day = createDate.substring(8, 10);
		    int y = Integer.parseInt(year);
		    int m = Integer.parseInt(month);
		    int d = Integer.parseInt(day);
		    CreateDateText.setDate(y, m, d);
		    String joinDate = rs.getString("procedure_date");
		    year = joinDate.substring(0, 4);
		    month = joinDate.substring(5, 7);
		    day = joinDate.substring(8, 10);
		    y = Integer.parseInt(year);
		    m = Integer.parseInt(month);
		    d = Integer.parseInt(day);
		    ProcDateText.setDate(y, m, d);
		    String reqType = rs.getString("procedure_type");
		    RTypeText.setText(reqType + "");
		    String reqStatus = rs.getString("request_status");
		    RStatusText.setText(reqStatus + "");
		    float cost = rs.getFloat("request_cost");
		    CostText.setText(cost + "");
		    
		}
		rs.close();
		sql = "SELECT * FROM SAMPLE WHERE SAMPLE.sample_ID =" + ID;
		rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			String status = rs.getString("sample_status");
			SStatusText.setText(status + "");
			String location = rs.getString("sample_location");
			LocationText.setText(location + "");
			String type = rs.getString("sample_type");
			STypeText.setText(type + "");
		}
		rs.close();
		sql = "SELECT * FROM RESULT WHERE RESULT.result_ID =" + ID;
		rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			String status = rs.getString("result_status");
			ResultStatusText.setText(status + "");
			String result = rs.getString("result_description");
			ResultText.setText(result + "");
		}
		rs.close();
		sql = "SELECT * FROM PAYMENT WHERE PAYMENT.payment_ID =" + ID;
		rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			String status = rs.getString("payment_status");
			PStatusText.setText(status + "");
			String balance = rs.getString("payment_balance");
			BalanceText.setText(balance + "");
		}
		
	}
	
	public void edit(int ID) throws SQLException
	{
		CallableStatement cStmt = null;
		cStmt = conn.prepareCall("{CALL ModifySample(?, ?, ?, ?)}");
		cStmt.setString(1, ID + "");
		cStmt.setString(2, SStatusText.getText() + "");
		cStmt.setString(3, LocationText.getText() + "");
		cStmt.setString(4, STypeText.getText() + "");
		cStmt.executeQuery();
		
		cStmt.close();
		cStmt = conn.prepareCall("{CALL ModifyResult(?, ?, ?)}");
		cStmt.setString(1, ID + "");
		cStmt.setString(2, ResultStatusText.getText());
		cStmt.setString(3, ResultText.getText());
		cStmt.executeQuery();
		
		cStmt.close();
		cStmt = conn.prepareCall("{CALL ModifyRequestStatus(?, ?)}");
		cStmt.setString(1, ID + "");
		cStmt.setString(2, RStatusText.getText());
		cStmt.executeQuery();
		
		shell.close();
	}
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(775, 322);
		shell.setText(getText());
		
		Label lblRequestid = new Label(shell, SWT.NONE);
		lblRequestid.setBounds(10, 10, 55, 15);
		lblRequestid.setText("RequestID");
		
		Label lblCustomerid = new Label(shell, SWT.NONE);
		lblCustomerid.setBounds(10, 31, 73, 15);
		lblCustomerid.setText("CustomerID");
		
		Label lblStatus = new Label(shell, SWT.NONE);
		lblStatus.setBounds(10, 52, 55, 15);
		lblStatus.setText("Status");
		
		Label lblType = new Label(shell, SWT.NONE);
		lblType.setBounds(10, 73, 55, 15);
		lblType.setText("Type");
		
		Label lblCost = new Label(shell, SWT.NONE);
		lblCost.setBounds(10, 94, 55, 15);
		lblCost.setText("Cost");
		
		Label lblCreatedate = new Label(shell, SWT.NONE);
		lblCreatedate.setBounds(10, 124, 73, 15);
		lblCreatedate.setText("CreateDate");
		
		Label lblProcdate = new Label(shell, SWT.NONE);
		lblProcdate.setBounds(10, 154, 55, 15);
		lblProcdate.setText("ProcDate");
		
		Label lblSamplestatus = new Label(shell, SWT.NONE);
		lblSamplestatus.setBounds(205, 10, 75, 15);
		lblSamplestatus.setText("SampleStatus");
		
		Label lblSampletype = new Label(shell, SWT.NONE);
		lblSampletype.setBounds(205, 31, 75, 15);
		lblSampletype.setText("SampleType");
		
		Label lblLocation = new Label(shell, SWT.NONE);
		lblLocation.setBounds(205, 52, 55, 15);
		lblLocation.setText("Location");
		
		Label lblResult = new Label(shell, SWT.NONE);
		lblResult.setBounds(426, 10, 32, 15);
		lblResult.setText("Result");
		
		Label lblResultstatus = new Label(shell, SWT.NONE);
		lblResultstatus.setBounds(426, 118, 64, 15);
		lblResultstatus.setText("ResultStatus");
		
		Label lblPaymentstatus = new Label(shell, SWT.NONE);
		lblPaymentstatus.setBounds(597, 10, 80, 15);
		lblPaymentstatus.setText("PaymentStatus");
		
		Label lblBalance = new Label(shell, SWT.NONE);
		lblBalance.setBounds(597, 52, 55, 15);
		lblBalance.setText("Balance");
		
		RequestIDText = new Text(shell, SWT.BORDER);
		RequestIDText.setEditable(false);
		RequestIDText.setBounds(89, 4, 110, 21);
		
		CustomerIDText = new Text(shell, SWT.BORDER);
		CustomerIDText.setEditable(false);
		CustomerIDText.setBounds(89, 25, 110, 21);
		
		RStatusText = new Combo(shell, SWT.READ_ONLY);
		RStatusText.setBounds(89, 46, 110, 21);
		RStatusText.add("in progress");
		RStatusText.add("complete");
		
		RTypeText = new Text(shell, SWT.BORDER);
		RTypeText.setEditable(false);
		RTypeText.setBounds(89, 67, 110, 21);
		
		CostText = new Text(shell, SWT.BORDER);
		CostText.setEditable(false);
		CostText.setBounds(89, 88, 110, 21);
		
		CreateDateText = new DateTime(shell, SWT.BORDER);
		CreateDateText.setBounds(89, 115, 91, 24);
		
		ProcDateText = new DateTime(shell, SWT.BORDER);
		ProcDateText.setBounds(89, 145, 91, 24);
		
		SStatusText = new Combo(shell, SWT.READ_ONLY);
		SStatusText.setItems(new String[] {"extracted", "not extracted"});
		SStatusText.setBounds(286, 4, 118, 21);
		
		STypeText = new Text(shell, SWT.BORDER);
		STypeText.setBounds(286, 25, 118, 21);
		
		LocationText = new Text(shell, SWT.BORDER);
		LocationText.setBounds(286, 46, 118, 21);
		
		ResultText = new Text(shell, SWT.BORDER);
		ResultText.setBounds(463, 7, 128, 105);
		
		ResultStatusText = new Combo(shell, SWT.READ_ONLY);
		ResultStatusText.setItems(new String[] {"in progress", "complete"});
		ResultStatusText.setBounds(496, 118, 95, 21);
		
		PStatusText = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		PStatusText.setBounds(683, 10, 86, 21);
		
		BalanceText = new Text(shell, SWT.BORDER);
		BalanceText.setEditable(false);
		BalanceText.setBounds(683, 49, 86, 21);
		
		Button btnEdit = new Button(shell, SWT.NONE);
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					edit(ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEdit.setBounds(246, 238, 75, 25);
		btnEdit.setText("EDIT");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(442, 238, 75, 25);
		btnCancel.setText("CANCEL");

	}

}
