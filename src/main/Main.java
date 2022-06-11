package main;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
  
  public static void main(String[] args) throws Exception {
//    JFrame.setDefaultLookAndFeelDecorated(true);
    JFrame frame = new JFrame("Password Manager");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new PasswordManagerPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  
}
