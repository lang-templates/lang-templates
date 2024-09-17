package demo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RepoSearchGui extends system.JFrame {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTable table1;

    public RepoSearchGui() {
        this.setMainPanel(mainPanel, "RepoSearchGui");
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        // Create a couple of columns
        model.addColumn("Col1");
        model.addColumn("Col2");
        // Append a row
        model.addRow(new Object[]{"v1", "v2"});
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (model.getRowCount() > 0) {
                    model.removeRow(0);
                }
                model.addRow(new Object[]{"v1b", "v2b"});
            }
        });
    }

    public static void main(String[] args) {
        var frame = new RepoSearchGui();
        frame.setVisible(true);
    }
}
