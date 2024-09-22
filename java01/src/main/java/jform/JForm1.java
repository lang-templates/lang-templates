/*
 * Created by JFormDesigner on Sat Sep 14 04:34:12 JST 2024
 */

package jform;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author user
 */
public class JForm1 extends JFrame {
  public static void main(String[] args) {
    var frame = new JForm1();
    frame.setVisible(true);
  }

  public JForm1() {
    initComponents();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // @formatter:off
    // Generated using JFormDesigner Evaluation license - Akio Tokaji
    dialogPane = new JPanel();
    contentPanel = new JPanel();
    buttonBar = new JPanel();
    okButton = new JButton();
    button1 = new JButton();

    // ======== this ========
    var contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // ======== dialogPane ========
    {
      dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
      dialogPane.setBorder(
          new javax.swing.border.CompoundBorder(
              new javax.swing.border.TitledBorder(
                  new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                  "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e",
                  javax.swing.border.TitledBorder.CENTER,
                  javax.swing.border.TitledBorder.BOTTOM,
                  new java.awt.Font("D\u0069al\u006fg", java.awt.Font.BOLD, 12),
                  java.awt.Color.red),
              dialogPane.getBorder()));
      dialogPane.addPropertyChangeListener(
          new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent e) {
              if ("\u0062or\u0064er".equals(e.getPropertyName())) throw new RuntimeException();
            }
          });
      dialogPane.setLayout(new BorderLayout());

      // ======== contentPanel ========
      {
        contentPanel.setLayout(null);

        {
          // compute preferred size
          Dimension preferredSize = new Dimension();
          for (int i = 0; i < contentPanel.getComponentCount(); i++) {
            Rectangle bounds = contentPanel.getComponent(i).getBounds();
            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
          }
          Insets insets = contentPanel.getInsets();
          preferredSize.width += insets.right;
          preferredSize.height += insets.bottom;
          contentPanel.setMinimumSize(preferredSize);
          contentPanel.setPreferredSize(preferredSize);
        }
      }
      dialogPane.add(contentPanel, BorderLayout.CENTER);

      // ======== buttonBar ========
      {
        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] {0, 80};
        ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

        // ---- okButton ----
        okButton.setText("OK");
        buttonBar.add(
            okButton,
            new GridBagConstraints(
                1,
                0,
                1,
                1,
                0.0,
                0.0,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0,
                0));
      }
      dialogPane.add(buttonBar, BorderLayout.SOUTH);

      // ---- button1 ----
      button1.setText("text");
      dialogPane.add(button1, BorderLayout.NORTH);
    }
    contentPane.add(dialogPane, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  // Generated using JFormDesigner Evaluation license - Akio Tokaji
  private JPanel dialogPane;
  private JPanel contentPanel;
  private JPanel buttonBar;
  private JButton okButton;
  private JButton button1;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
