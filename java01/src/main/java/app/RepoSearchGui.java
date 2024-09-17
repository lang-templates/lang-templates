package app;

import common.DirModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RepoSearchGui extends system.JFrame {
    private JTextField textField1;
    private JPanel tablePanel;
    private JPanel mainPanel;

    private JTable table;
    private DirModel dirModel = new DirModel("D:\\.repo.\\base14");

    public RepoSearchGui() {
        this.setMainPanel(mainPanel, "RepoSearchGui");
        this.textField1.grabFocus();
        // セル編集不可
        this.table = new JTable(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.addColumn("Path");
        // テーブルダブルクリック
        this.table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // your valueChanged overridden method
                    var path = (String) model.getValueAt(row, 0);
                    JOptionPane.showMessageDialog(RepoSearchGui.this, path);
                }
            }
        });
        this.tablePanel.add(new JScrollPane(this.table));

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
