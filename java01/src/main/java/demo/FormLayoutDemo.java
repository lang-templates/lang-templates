package demo;

import javax.swing.*;

public class FormLayoutDemo extends system.JFrame {
  private JButton button1;
  private JPanel panel1;

  public FormLayoutDemo() {
    this.setMainPanel(panel1, 640, 480, "FormLayoutDemo");
  }

  public static void main(String[] args) {
    var frame = new FormLayoutDemo();
    frame.setVisible(true);
  }
}
