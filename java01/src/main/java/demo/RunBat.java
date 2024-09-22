package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import system.Sys;

public class RunBat {
  public static void main(String[] args) {
    Sys.setenv("BBB", "bbb");

    RunBat pbs = new RunBat();

    ProcessBuilder pb = new ProcessBuilder("D:\\.repo\\base14\\lt\\java01\\test.bat");
    Map<String, String> env = pb.environment();
    env.put("AAA", "aaa");
    pb.redirectErrorStream(true);
    try {
      Process p = pb.start();
      pbs.print(p.getInputStream());
      p.waitFor();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void print(InputStream is) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(is)); ) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    }
  }
}
