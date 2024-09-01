package app;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainSWT {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("main() start");
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(2, false));
		new Label(shell, SWT.NONE);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.beep();
				FileDialog dialog = new FileDialog(shell);
				dialog.setText("オープンします");
				String open = dialog.open();
				System.out.println(open);
				DirectoryDialog dirDlg = new DirectoryDialog(shell);
				String open2 = dirDlg.open();
				System.out.println(open2);
				var dlg = new DialogSWT(shell, 0);
				Object open3 = dlg.open();
				System.out.println(open3);
			}
		});
		btnNewButton.setText("New Button");

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		System.out.println("main() end");
	}

}
