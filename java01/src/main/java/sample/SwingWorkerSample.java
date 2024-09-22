package sample;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.SwingWorker;

// https://nowokay.hatenablog.com/entry/20081207/1228644742
public class SwingWorkerSample {
  public static void main(String[] args) {
    // なんとなくNimbusを使う
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception ex) {
    }
    // ウィンドウ
    JFrame f = new JFrame("SwingWorkerサンプル");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // テキストエリア
    final JTextArea taOutput = new JTextArea(15, 30);
    JScrollPane sp = new JScrollPane(taOutput);
    f.add(sp);
    // プログレスバー
    final JProgressBar progressBar = new JProgressBar();
    f.add(BorderLayout.SOUTH, progressBar);
    // ボタン
    JButton b = new JButton("開始");
    f.add(BorderLayout.NORTH, b);

    // 実行結果Integer, 処理経過データint[]のSwingWorker
    final SwingWorker<Integer, int[]> sw =
        new SwingWorker<Integer, int[]>() {
          /** バックグラウンド処理 */
          @Override
          protected Integer doInBackground() throws Exception {
            int sum = 0;
            for (int i = 1; i <= 10; ++i) {
              sum += i;
              publish(new int[] {i, sum}); // 結果出力
              setProgress(i * 10); // 進捗
              Thread.sleep(1000);
            }
            return sum;
          }

          /** 途中経過の表示 */
          @Override
          protected void process(List<int[]> chunks) {
            StringBuilder sb = new StringBuilder();
            for (int[] values : chunks) {
              sb.append(String.format("%dを足して%d%n", values[0], values[1]));
            }
            taOutput.append(sb.toString());
          }

          /** 処理終了 */
          @Override
          protected void done() {
            try {
              int result = get();
              taOutput.append("終了。" + result + "でした\n");
            } catch (InterruptedException ex) {
            } catch (ExecutionException ex) {
            }
          }
        };
    // プログレスバーの処理
    sw.addPropertyChangeListener(
        new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent evt) {
            if ("progress".equals(evt.getPropertyName())) {
              progressBar.setValue((Integer) evt.getNewValue());
            }
          }
        });

    // ボタンが押されたときの処理
    b.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            sw.execute(); // SwingWorkderの実行
          }
        });

    // ウィンドウ表示
    f.pack();
    f.setVisible(true);
  }
}
