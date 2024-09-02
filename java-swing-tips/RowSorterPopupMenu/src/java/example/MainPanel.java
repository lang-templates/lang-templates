// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    String[] columnNames = {"String", "Integer", "Boolean"};
    Object[][] data = {
        {"aaa", 12, true}, {"bbb", 5, false}, {"CCC", 92, true}, {"DDD", 0, false}
    };
    TableModel model = new DefaultTableModel(data, columnNames) {
      @Override public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
      }
    };
    JTable table = new JTable(model);
    table.setRowSorter(new TableRowSorter<TableModel>(model) {
      @Override public void toggleSortOrder(int column) {
        /* Disable header click sorting */
      }
    });
    RowSorter.SortKey key = new RowSorter.SortKey(1, SortOrder.DESCENDING);
    table.getRowSorter().setSortKeys(Collections.singletonList(key));

    TableColumn col = table.getColumnModel().getColumn(0);
    col.setMinWidth(80);
    col.setMaxWidth(80);
    col.setResizable(false);

    table.getTableHeader().setComponentPopupMenu(new TableHeaderPopupMenu());

    // JPopupMenu pop = new TableHeaderPopupMenu();
    // JTableHeader header = table.getTableHeader();
    // header.setComponentPopupMenu(pop);
    // pop.addPopupMenuListener(new PopupMenuListener() {
    //   @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    //     // System.out.println("popupMenuWillBecomeInvisible");
    //     header.setDraggedColumn(null);
    //     // header.setResizingColumn(null);
    //     // header.setDraggedDistance(0);
    //     header.repaint();
    //     table.repaint();
    //   }
    //
    //   @Override public void popupMenuCanceled(PopupMenuEvent e) {
    //     /* not needed */
    //   }
    //
    //   @Override public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    //     /* not needed */
    //   }
    // });

    add(new JScrollPane(table));
    setPreferredSize(new Dimension(320, 240));
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

final class TableHeaderPopupMenu extends JPopupMenu {
  private final List<SortAction> actions = Arrays.asList(
      new SortAction(SortOrder.ASCENDING),
      new SortAction(SortOrder.DESCENDING)); // new SortAction(SortOrder.UNSORTED));

  /* default */ TableHeaderPopupMenu() {
    super();
    actions.forEach(this::add);
  }

  @Override public void show(Component c, int x, int y) {
    if (c instanceof JTableHeader) {
      JTableHeader header = (JTableHeader) c;
      JTable table = header.getTable();
      header.setDraggedColumn(null);
      header.repaint();
      table.repaint();
      int i = table.convertColumnIndexToModel(header.columnAtPoint(new Point(x, y)));
      if (i >= 0) {
        actions.forEach(a -> a.setIndex(i));
        super.show(c, x, y);
      }
    }
  }

  private class SortAction extends AbstractAction {
    private final SortOrder dir;
    private int index = -1;

    protected SortAction(SortOrder dir) {
      super(dir.toString());
      this.dir = dir;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    @Override public void actionPerformed(ActionEvent e) {
      JTableHeader h = (JTableHeader) getInvoker();
      RowSorter.SortKey sortKey = new RowSorter.SortKey(index, dir);
      h.getTable().getRowSorter().setSortKeys(Collections.singletonList(sortKey));
    }
  }
}
