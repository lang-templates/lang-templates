// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    JLabel label = new JLabel() {
      @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();
        g2.setPaint(new GradientPaint(0f, 0f, Color.ORANGE, w, h, Color.WHITE, true));
        g2.fillRect(0, 0, w, h);
        g2.dispose();
      }

      @Override public Dimension getPreferredSize() {
        return new Dimension(640, 640);
      }
    };
    label.setBorder(BorderFactory.createTitledBorder("Horizontal scroll: CTRL + Wheel"));
    label.addMouseWheelListener(e -> {
      Component c = e.getComponent();
      Container s = SwingUtilities.getAncestorOfClass(JScrollPane.class, c);
      if (s instanceof JScrollPane) {
        JScrollPane scrollPane = (JScrollPane) s;
        Component scrollBar = e.isControlDown()
            ? scrollPane.getHorizontalScrollBar()
            : scrollPane.getVerticalScrollBar();
        scrollBar.dispatchEvent(SwingUtilities.convertMouseEvent(c, e, scrollBar));
      }
    });

    JScrollPane scroll = new JScrollPane(label);
    scroll.getVerticalScrollBar().setUnitIncrement(10);

    JScrollBar hsb = scroll.getHorizontalScrollBar();
    hsb.setUnitIncrement(10);
    hsb.addMouseWheelListener(e -> {
      JScrollBar sb = (JScrollBar) e.getComponent();
      Container c = SwingUtilities.getAncestorOfClass(JScrollPane.class, sb);
      if (c instanceof JScrollPane) {
        JViewport vport = ((JScrollPane) c).getViewport();
        Point vp = vport.getViewPosition();
        int d = hsb.getUnitIncrement() * e.getWheelRotation();
        vp.translate(d, 0);
        JComponent v = (JComponent) SwingUtilities.getUnwrappedView(vport);
        v.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
      }
    });

    add(scroll);
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
