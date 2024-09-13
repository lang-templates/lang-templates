package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Dialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	public String result = null;
	private JTextField txtAbc;
	private JLabel lblNewLabel;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Dialog dialog = new Dialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Dialog() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtAbc = new JTextField();
			txtAbc.setBounds(12, 10, 96, 22);
			contentPanel.add(txtAbc);
			txtAbc.setColumns(10);
		}
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(12, 39, 50, 13);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Dialog.this.result = "this is result!" + txtAbc.getText();
						Dialog.this.setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Dialog.this.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		init();
	}

	private void init() {
		txtAbc.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				Dialog.this.lblNewLabel.setText(txtAbc.getText());
				if (Dialog.this.lblNewLabel.getText().length()==0)
					okButton.setEnabled(false);
				else
					okButton.setEnabled(true);
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				Dialog.this.lblNewLabel.setText(txtAbc.getText());
				if (Dialog.this.lblNewLabel.getText().length()==0)
					okButton.setEnabled(false);
				else
					okButton.setEnabled(true);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				Dialog.this.lblNewLabel.setText(txtAbc.getText());
				if (Dialog.this.lblNewLabel.getText().length()==0)
					okButton.setEnabled(false);
				else
					okButton.setEnabled(true);
			}
		});
		okButton.setEnabled(false);
	}
}
