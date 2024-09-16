package demo;

import common.FileSystemTree;
import system.Sys;
import system.Waiter;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;

public class WaiterGui extends system.JFrame {
    private JPanel contentPane;
    private JButton スタートButton;
    private JTextField textField1;

    public WaiterGui() {
        //setContentPane(contentPane);
        setMainPanel(contentPane, 640, 150, "WaiterGui");

        // X をクリックしたとき、 onCancel() を呼ぶ
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // ESCAPE で onCancel() を呼ぶ
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        スタートButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BackgroundTask(5000) {
                    @Override
                    protected void process(List<String> msgs) {
                        msgs.stream()
                                .forEach((msg)->{
                                    if (!msg.isEmpty()) WaiterGui.this.textField1.setText(msg);
                                });
                    }
                }.execute();
            }
        });
    }

    private void onOK() {
        // ここにコードを追加
        dispose();
    }

    private void onCancel() {
        // 必要に応じてここにコードを追加
        dispose();
    }

    public static void main(String[] args) {
        Sys.println("start");
        WaiterGui dialog = new WaiterGui();
        //dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }

    class BackgroundTask extends SwingWorker<String, String> {
        int ms;

        protected BackgroundTask(int ms) {
            super();
            this.ms = ms;
        }

        @Override
        public String doInBackground() {
            Waiter.wait(ms, new Waiter.Callback() {
                @Override
                public void progress(String s) {
                    publish(s);
                }
            });
            return "done";
        }
    }
}
