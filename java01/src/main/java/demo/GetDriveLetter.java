package demo;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GetDriveLetter {
  public static void main(String[] args) {
    String dllPathString = "C:\\Program Files\\Notepad++\\scilexer.dll";
    Path dllPath = Paths.get(dllPathString);
    Path driveLetter = dllPath.getRoot();
    System.out.println("The dll is in: " + driveLetter);
  }
}
