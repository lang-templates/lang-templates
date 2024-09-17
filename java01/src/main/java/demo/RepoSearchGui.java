package demo;

import common.DirModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RepoSearchGui extends system.JFrame {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTable table1;

    private DirModel dirModel = new DirModel("D:\\.repo.\\base14");

    public RepoSearchGui() {
        this.setMainPanel(mainPanel, "RepoSearchGui");
        this.textField1.grabFocus();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        // Create a couple of columns
        model.addColumn("Path");
        // Append a row
        //model.addRow(new Object[]{"v1", "v2"});
        //model.addRow(new Object[]{"v1", "v2"});
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (model.getRowCount() > 0) {
                    model.removeRow(0);
                }
                var list = dirModel.filterByRegex(textField1.getText());
                list.stream()
                        .forEach(x -> {
                            model.addRow(new Object[]{x});
                        });
            }
        });
    }

    public static void main(String[] args) {
        var frame = new RepoSearchGui();
        frame.setVisible(true);
    }
}
