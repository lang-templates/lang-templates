// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.net.URL;
import java.util.Optional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    JTable table = new JTable();
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL url = cl.getResource("example/restore_to_background_color.gif");
    Icon icon = Optional.ofNullable(url)
        .<Icon>map(ImageIcon::new)
        .orElseGet(() -> UIManager.getIcon("html.missingImage"));
    Object[][] data = {
        {"Default ImageIcon", icon},
        {"ImageIcon#setImageObserver", makeImageIcon(url, table, 1, 1)}
    };
    String[] columnNames = {"String", "ImageIcon"};
    table.setModel(new DefaultTableModel(data, columnNames) {
      @Override public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
      }

      @Override public boolean isCellEditable(int row, int column) {
        return column == 0;
      }
    });
    table.setAutoCreateRowSorter(true);
    table.setRowHeight(20);

    add(new JScrollPane(table));
    setPreferredSize(new Dimension(320, 240));
  }

  public static Icon makeImageIcon(URL url, JTable table, int row, int col) {
    return Optional.ofNullable(url)
        .<Icon>map(u -> {
          ImageIcon icon = new ImageIcon(u);
          // Wastefulness: icon.setImageObserver((ImageObserver) table);
          icon.setImageObserver((img, infoflags, x, y, w, h) -> {
            // @see http://www2.gol.com/users/tame/swing/examples/SwingExamples.html
            if (!table.isShowing()) {
              return false; // @see javax.swing.JLabel#imageUpdate(...)
            }
            // @see java.awt.Component#imageUpdate(...)
            if ((infoflags & (FRAMEBITS | ALLBITS)) != 0) {
              int vr = table.convertRowIndexToView(row); // JDK 1.6.0
              int vc = table.convertColumnIndexToView(col);
              table.repaint(table.getCellRect(vr, vc, false));
            }
            return (infoflags & (ALLBITS | ABORT)) == 0;
          });
          return icon;
        })
        .orElseGet(() -> UIManager.getIcon("html.missingImage"));
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(MainPanel::createAndShowGui);
  }

  private static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException ignored) {
      Toolkit.getDefaultToolkit().beep();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
      ex.printStackTrace();
      return;
    }
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
