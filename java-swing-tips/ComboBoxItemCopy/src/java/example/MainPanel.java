// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

public final class MainPanel extends JPanel {
  public static final String COPY_KEY = "copy";

  private MainPanel() {
    super(new BorderLayout());
    JComboBox<String> combo1 = new JComboBox<>(makeModel(5));
    Action copy = new AbstractAction() {
      @Override public void actionPerformed(ActionEvent e) {
        JComboBox<?> combo = (JComboBox<?>) e.getSource();
        Optional.ofNullable(combo.getSelectedItem()).ifPresent(text -> {
          Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
          Transferable contents = new StringSelection(Objects.toString(text));
          clipboard.setContents(contents, null);
        });
      }
    };
    ActionMap am = combo1.getActionMap();
    am.put(COPY_KEY, copy);
    int modifiers = InputEvent.CTRL_DOWN_MASK;
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, modifiers);
    InputMap im = combo1.getInputMap(WHEN_FOCUSED);
    im.put(keyStroke, COPY_KEY);
    JPopupMenu popup = new JPopupMenu();
    popup.add(COPY_KEY).addActionListener(e -> {
      Class<?> clz = JComboBox.class;
      Component o = popup.getInvoker();
      Component c = clz.isInstance(o) ? o : SwingUtilities.getAncestorOfClass(clz, o);
      if (c instanceof JComboBox) {
        JComboBox<?> combo = (JComboBox<?>) c;
        Action act = combo.getActionMap().get(COPY_KEY);
        act.actionPerformed(new ActionEvent(combo, e.getID(), e.getActionCommand()));
        // SwingUtilities.notifyAction(
        //     act, keyStroke, new KeyEvent(combo, 0, 0, 0, 0, 'C'), combo, modifiers);
      }
    });
    combo1.setComponentPopupMenu(popup);

    JComboBox<String> combo2 = new JComboBox<>(makeModel(10));
    combo2.setEditable(true);
    JTextField field = (JTextField) combo2.getEditor().getEditorComponent();
    field.setComponentPopupMenu(new TextFieldPopupMenu());

    Box box = Box.createVerticalBox();
    box.add(makeTitledPanel("Default:", new JComboBox<>(makeModel(0))));
    box.add(Box.createVerticalStrut(5));
    box.add(makeTitledPanel("Editable: false, JPopupMenu, Ctrl+C", combo1));
    box.add(Box.createVerticalStrut(5));
    box.add(makeTitledPanel("Editable: true, JPopupMenu, Ctrl+C", combo2));
    box.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(box, BorderLayout.NORTH);

    JTextArea textArea = new JTextArea();
    textArea.setComponentPopupMenu(new TextFieldPopupMenu());
    add(new JScrollPane(textArea));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setPreferredSize(new Dimension(320, 240));
  }

  private static Component makeTitledPanel(String title, Component c) {
    JPanel p = new JPanel(new BorderLayout());
    p.setBorder(BorderFactory.createTitledBorder(title));
    p.add(c);
    return p;
  }

  private static ComboBoxModel<String> makeModel(int start) {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    IntStream.range(start, start + 5).forEach(i -> model.addElement("item: " + i));
    return model;
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

final class TextFieldPopupMenu extends JPopupMenu {
  private final Action cutAction = new DefaultEditorKit.CutAction();
  private final Action copyAction = new DefaultEditorKit.CopyAction();
  // private final Action pasteAction = new DefaultEditorKit.PasteAction();
  private final Action deleteAction = new AbstractAction("delete") {
    @Override public void actionPerformed(ActionEvent e) {
      Component c = getInvoker();
      if (c instanceof JTextComponent) {
        ((JTextComponent) c).replaceSelection(null);
      }
    }
  };

  /* default */ TextFieldPopupMenu() {
    super();
    add(cutAction);
    add(copyAction);
    add(new DefaultEditorKit.PasteAction());
    add(deleteAction);
  }

  @Override public void show(Component c, int x, int y) {
    if (c instanceof JTextComponent) {
      JTextComponent tc = (JTextComponent) c;
      boolean hasSelectedText = Objects.nonNull(tc.getSelectedText());
      cutAction.setEnabled(hasSelectedText);
      copyAction.setEnabled(hasSelectedText);
      deleteAction.setEnabled(hasSelectedText);
      super.show(c, x, y);
    }
  }
}
