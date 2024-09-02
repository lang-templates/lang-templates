// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    JTextArea textArea = new JTextArea();

    JLabel label = new JLabel();
    label.addHierarchyListener(new AutomaticallyCloseListener());

    JButton button = new JButton("show");
    button.addActionListener(e -> {
      Container p = getRootPane();
      String title = "Automatically close dialog";
      int r = JOptionPane.showConfirmDialog(
          p, label, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
      switch (r) {
        case JOptionPane.OK_OPTION:
          textArea.append("OK\n");
          break;
        case JOptionPane.CANCEL_OPTION:
          textArea.append("Cancel\n");
          break;
        case JOptionPane.CLOSED_OPTION:
          textArea.append("Closed(automatically)\n");
          break;
        default:
          textArea.append("----\n");
          break;
      }
      textArea.append("\n");
    });

    JPanel p = new JPanel(new BorderLayout());
    p.setBorder(BorderFactory.createTitledBorder("HierarchyListener"));
    p.add(button);

    add(p, BorderLayout.NORTH);
    add(new JScrollPane(textArea));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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

class AutomaticallyCloseListener implements HierarchyListener {
  // private static final Logger LOGGER = Logger.getLogger(MainPanel.LOGGER_NAME);
  private static final int SECONDS = 5;
  private final AtomicInteger atomicDown = new AtomicInteger(SECONDS);
  private final Timer timer = new Timer(1000, null);
  private ActionListener listener;

  @Override public void hierarchyChanged(HierarchyEvent e) {
    if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
      Component c = e.getComponent();
      if (c.isShowing() && c instanceof JLabel) {
        // LOGGER.info(() -> "isShowing=true");
        JLabel l = (JLabel) c;
        atomicDown.set(SECONDS);
        l.setText(String.format("Closing in %d seconds", SECONDS));
        timer.removeActionListener(listener);
        listener = event -> {
          int i = atomicDown.decrementAndGet();
          l.setText(String.format("Closing in %d seconds", i));
          if (i <= 0 && timer.isRunning()) {
            // LOGGER.info(() -> "Timer: timer.stop()");
            timer.stop();
            Optional.ofNullable(l.getTopLevelAncestor())
                .filter(Window.class::isInstance).map(Window.class::cast)
                .ifPresent(Window::dispose);
            // Container c = l.getTopLevelAncestor();
            // if (c instanceof Window) {
            //   // LOGGER.info(() -> "window.dispose()");
            //   ((Window) c).dispose();
            // }
          }
        };
        timer.addActionListener(listener);
        timer.start();
      } else {
        // LOGGER.info(() -> "isShowing=false");
        if (timer.isRunning()) {
          // LOGGER.info(() -> "timer.stop()");
          timer.stop();
        }
      }
    }
  }
}
