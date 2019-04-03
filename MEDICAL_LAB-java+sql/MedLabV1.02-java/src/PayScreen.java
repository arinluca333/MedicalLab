import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class PayScreen extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text IDText;
	private Text AmountText;
	int ID;
	Connection conn;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PayScreen(Shell parent, int style, int i, Connection c) {
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
		shell.setSize(267, 246);
		shell.setText(getText());
		
		Label lblPaymentid = new Label(shell, SWT.NONE);
		lblPaymentid.setBounds(53, 59, 66, 15);
		lblPaymentid.setText("PaymentID");
		
		IDText = new Text(shell, SWT.BORDER);
		IDText.setBounds(125, 53, 76, 21);
		
		Label lblAmount = new Label(shell, SWT.NONE);
		lblAmount.setBounds(53, 92, 55, 15);
		lblAmount.setText("Amount");
		
		AmountText = new Text(shell, SWT.BORDER);
		AmountText.setBounds(125, 86, 76, 21);
		
		Button btnPay = new Button(shell, SWT.NONE);
		btnPay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					pay();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPay.setBounds(53, 144, 75, 25);
		btnPay.setText("PAY");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(134, 144, 75, 25);
		btnCancel.setText("CANCEL");

	}
	
	public void pay() throws SQLException
	{
		CallableStatement cStmt = null;
		cStmt = conn.prepareCall("{CALL Pay(?, ?)}");
		cStmt.setString(1, IDText.getText());
		cStmt.setString(2, AmountText.getText());

		cStmt.executeQuery();
		
		shell.close();
	}
}
