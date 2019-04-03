import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class HistoryScreen extends Dialog {

	protected Object result;
	protected Shell shell;
	private Label Procedures, TotalCost, Requests;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public int ID;
	public Connection conn;
	public HistoryScreen(Shell parent, int style, Connection c, int i) {
		super(parent, style);
		setText("SWT Dialog");
		ID = i;
		conn = c;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		try {
			write();
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
	
	public void write() throws SQLException
	{
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql;
		sql = "SELECT * FROM HISTORY WHERE HISTORY.history_ID =" + ID;
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next())
		{
			String nrProc = rs.getString("no_of_procedures");
			String spent = rs.getFloat("total_payed") + "";
			Procedures.setText("No. Of Procedures: " + nrProc);
			TotalCost.setText("Total money spent: " + spent);
		}
		rs.close();
		
		sql = "SELECT * FROM REQUEST WHERE REQUEST.customer_ID =" + ID;
		rs = stmt.executeQuery(sql);
		String res = String.format("ReqID %-12s %-20s %-12s %-12s %4s\n","Status", "Type", "CreateDate", "ProcDate", "Cost");
		while(rs.next())
		{
			 int req_ID = rs.getInt("request_ID");
			    int cust_ID = rs.getInt("customer_ID");
			    String proc_type = rs.getString("procedure_type");
			    String req_status = rs.getString("request_status");
			    String req_date = rs.getString("request_date");
			    String proc_date = rs.getString("procedure_date");
			    float req_cost = rs.getFloat("request_cost");
			    res = res + String.format("%-5s %-12s %-20s %-12s %-12s %4s\n", req_ID, req_status, proc_type, req_date, proc_date, req_cost);
		}
		Requests.setText(res);
		rs.close();
		
		
		
	}
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(681, 300);
		shell.setText(getText());
		
		Procedures = new Label(shell, SWT.NONE);
		Procedures.setBounds(10, 10, 212, 15);
		
		TotalCost = new Label(shell, SWT.NONE);
		TotalCost.setBounds(269, 10, 320, 15);
		TotalCost.setText("");
		
		Requests = new Label(shell, SWT.NONE);
		Requests.setFont(SWTResourceManager.getFont("Source Code Pro", 9, SWT.NORMAL));
		Requests.setBounds(10, 41, 655, 220);
		
		Button btnPay = new Button(shell, SWT.NONE);
		btnPay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PayScreen p = new PayScreen(shell, SWT.NONE, ID, conn);
				p.open();
			}
		});
		btnPay.setBounds(590, 10, 75, 25);
		btnPay.setText("PAY");

	}
}
