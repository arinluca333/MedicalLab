import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.sql.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddRequestScreen extends Dialog {

	protected Object result;
	protected Shell shell;
	private Connection conn;
	private Text CustomerIDText;
	private Text ProcTypeText;
	private Text CostText;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddRequestScreen(Shell parent, int style, Connection c) {
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
		shell.setSize(450, 300);
		shell.setText(getText());
		
		
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(250, 217, 75, 25);
		btnCancel.setText("CANCEL");
		
		CustomerIDText = new Text(shell, SWT.BORDER);
		CustomerIDText.setBounds(169, 10, 76, 21);
		
		DateTime ProcDateText = new DateTime(shell, SWT.BORDER);
		ProcDateText.setBounds(169, 37, 91, 24);
		
		ProcTypeText = new Text(shell, SWT.BORDER);
		ProcTypeText.setBounds(169, 67, 76, 21);
		
		Label lblCost = new Label(shell, SWT.NONE);
		lblCost.setBounds(79, 94, 89, 15);
		lblCost.setText("Cost");
		
		Label lblCustomerid = new Label(shell, SWT.NONE);
		lblCustomerid.setBounds(79, 16, 89, 15);
		lblCustomerid.setText("CustomerID");
		
		Label lblProceduredate = new Label(shell, SWT.NONE);
		lblProceduredate.setBounds(79, 46, 89, 15);
		lblProceduredate.setText("ProcedureDate");
		
		Label lblProceduretype = new Label(shell, SWT.NONE);
		lblProceduretype.setBounds(79, 73, 84, 15);
		lblProceduretype.setText("ProcedureType");
		
		CostText = new Text(shell, SWT.BORDER);
		CostText.setBounds(169, 94, 76, 21);

		Button btnAdd = new Button(shell, SWT.NONE);
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					String pDay = ProcDateText.getYear() + "-" + ProcDateText.getMonth() + "-" + ProcDateText.getDay();
					add(CustomerIDText.getText(), pDay, ProcTypeText.getText(), CostText.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(93, 217, 75, 25);
		btnAdd.setText("ADD");
		
	}
	
	
	public void add(String id, String date, String type, String cost) throws SQLException
	{
		CallableStatement cStmt = null;
		cStmt = conn.prepareCall("{CALL AddRequest(?, ?, ?, ?)}");
		cStmt.setString(1, id);
		cStmt.setString(2, date);
		cStmt.setString(3, type);
		cStmt.setString(4, cost);

		cStmt.executeQuery();
		
		shell.close();
	}
}
