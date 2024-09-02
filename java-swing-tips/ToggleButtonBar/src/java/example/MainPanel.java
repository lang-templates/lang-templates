// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.Objects;
import java.util.stream.Stream;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super();
    Icon roundIcon = new ToggleButtonBarCellIcon();
    Icon rectIcon = new CellIcon();

    add(makeToggleButtonBar(0xFF_74_00, roundIcon));
    add(makeToggleButtonBar(0x55_55_55, rectIcon));
    add(makeToggleButtonBar(0x00_64_00, roundIcon));
    add(makeToggleButtonBar(0x8B_00_00, rectIcon));
    add(makeToggleButtonBar(0x00_1E_43, roundIcon));

    setPreferredSize(new Dimension(320, 240));
  }

  private static AbstractButton makeButton(String title) {
    AbstractButton b = new JRadioButton(title);
    // b.setVerticalAlignment(SwingConstants.CENTER);
    // b.setVerticalTextPosition(SwingConstants.CENTER);
    // b.setHorizontalAlignment(SwingConstants.CENTER);
    b.setHorizontalTextPosition(SwingConstants.CENTER);
    b.setBorder(BorderFactory.createEmptyBorder());
    b.setContentAreaFilled(false);
    b.setFocusPainted(false);
    // b.setBackground(new Color(cc));
    b.setForeground(Color.WHITE);
    return b;
  }

  private static Component makeToggleButtonBar(int cc, Icon icon) {
    ButtonGroup bg = new ButtonGroup();
    JPanel p = new JPanel(new GridLayout(1, 0, 0, 0));
    p.setBorder(BorderFactory.createTitledBorder(String.format("Color: #%06X", cc)));
    Color color = new Color(cc);
    Stream.of(makeButton("left"), makeButton("center"), makeButton("right")).forEach(b -> {
      b.setBackground(color);
      b.setIcon(icon);
      bg.add(b);
      p.add(b);
    });
    return p;
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

class CellIcon implements Icon {
  // http://weboook.blog22.fc2.com/blog-entry-342.html
  // Webpark 2012.11.15
  private static final Color TL = new Color(1f, 1f, 1f, .2f);
  private static final Color BR = new Color(0f, 0f, 0f, .2f);
  private static final Color ST = new Color(1f, 1f, 1f, .4f);
  private static final Color SB = new Color(1f, 1f, 1f, .1f);

  @Override public void paintIcon(Component c, Graphics g, int x, int y) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.translate(x, y);

    Color ssc = TL;
    Color bgc = BR;
    if (c instanceof AbstractButton) {
      ButtonModel m = ((AbstractButton) c).getModel();
      if (m.isSelected() || m.isRollover()) {
        ssc = ST;
        bgc = SB;
      }
    }

    int w = c.getWidth();
    int h = c.getHeight();
    g2.setPaint(c.getBackground());
    g2.fillRect(0, 0, w, h);

    g2.setPaint(new GradientPaint(0f, 0f, ssc, 0f, h, bgc, true));
    g2.fillRect(0, 0, w, h);

    g2.setPaint(TL);
    g2.fillRect(0, 0, 1, h);
    g2.setPaint(BR);
    g2.fillRect(w, 0, 1, h);

    g2.dispose();
  }

  @Override public int getIconWidth() {
    return 80;
  }

  @Override public int getIconHeight() {
    return 20;
  }
}

class ToggleButtonBarCellIcon implements Icon {
  private static final Color TL = new Color(1f, 1f, 1f, .2f);
  private static final Color BR = new Color(0f, 0f, 0f, .2f);
  private static final Color ST = new Color(1f, 1f, 1f, .4f);
  private static final Color SB = new Color(1f, 1f, 1f, .1f);

  @Override public void paintIcon(Component c, Graphics g, int x, int y) {
    Container parent = c.getParent();
    if (Objects.isNull(parent)) {
      return;
    }
    double r = 4d;
    double rr = r * 4d * (Math.sqrt(2d) - 1d) / 3d; // = r * .5522;
    double w = c.getWidth();
    double h = c.getHeight() - 1d;

    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    Path2D p = new Path2D.Double();
    if (Objects.equals(c, parent.getComponent(0))) {
      // :first-child
      p.moveTo(x, y + r);
      p.curveTo(x, y + r - rr, x + r - rr, y, x + r, y);
      p.lineTo(x + w, y);
      p.lineTo(x + w, y + h);
      p.lineTo(x + r, y + h);
      p.curveTo(x + r - rr, y + h, x, y + h - r + rr, x, y + h - r);
    } else if (Objects.equals(c, parent.getComponent(parent.getComponentCount() - 1))) {
      // :last-child
      w--;
      p.moveTo(x, y);
      p.lineTo(x + w - r, y);
      p.curveTo(x + w - r + rr, y, x + w, y + r - rr, x + w, y + r);
      p.lineTo(x + w, y + h - r);
      p.curveTo(x + w, y + h - r + rr, x + w - r + rr, y + h, x + w - r, y + h);
      p.lineTo(x, y + h);
    } else {
      p.moveTo(x, y);
      p.lineTo(x + w, y);
      p.lineTo(x + w, y + h);
      p.lineTo(x, y + h);
    }
    p.closePath();

    Color ssc = TL;
    Color bgc = BR;
    if (c instanceof AbstractButton) {
      ButtonModel m = ((AbstractButton) c).getModel();
      if (m.isSelected() || m.isRollover()) {
        ssc = ST;
        bgc = SB;
      }
    }

    Area area = new Area(p);
    g2.setPaint(c.getBackground());
    g2.fill(area);
    g2.setPaint(new GradientPaint(x, y, ssc, x, y + (float) h, bgc, true));
    g2.fill(area);
    g2.setPaint(BR);
    g2.draw(area);
    g2.dispose();
  }

  @Override public int getIconWidth() {
    return 80;
  }

  @Override public int getIconHeight() {
    return 20;
  }
}
