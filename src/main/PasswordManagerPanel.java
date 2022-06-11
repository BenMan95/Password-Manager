package main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PasswordManagerPanel extends JPanel {
  
  private List<String[]> data;
  private LoginTableModel model;
  private JTable table;
  
  EncrypterDecrypter io = new EncrypterDecrypter();
  File file;
  
  public PasswordManagerPanel() {
    super(new BorderLayout());
    
    data = new ArrayList<String[]>();
    model = new LoginTableModel();
    model.setData(data);
    table = new JTable(model);
    
    JScrollPane pane = new JScrollPane(table);
    pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    JPanel buttonPanel = new JPanel(new BorderLayout());
    JPanel left = new JPanel();
    JPanel center = new JPanel();
    JPanel right = new JPanel();
    
    JButton[] buttons = new JButton[] {
      new JButton("Add"),
      new JButton("Remove"),
      new JButton("Copy"),
      new JButton("Set Password"),
      new JButton("Open"),
      new JButton("Save As"),
      new JButton("Save"),
    };
    
    buttons[0].addActionListener(e -> {
      data.add(new String[] {"","",""});
      model.fireTableDataChanged();
    });
    buttons[1].addActionListener(e -> {
      int[] rows = table.getSelectedRows();
      for (int i=0;i<rows.length;i++)
        data.remove(rows[i]-i);
      model.fireTableDataChanged();
    });
    buttons[2].addActionListener(e -> {
      int row = table.getSelectedRow();
      int col = table.getSelectedColumn();
      if (row >= 0 && col >= 0) {
        String str = data.get(row)[col];
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
      }
    });
    buttons[3].addActionListener(e -> {
      String password = JOptionPane.showInputDialog("Enter the password to use");
      try {
        io.setPassword(password);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                                      "Password could not be set",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
    });
    buttons[4].addActionListener(e -> {
      JFileChooser fc = new JFileChooser();
      int result = fc.showOpenDialog(PasswordManagerPanel.this);
      if (result == JFileChooser.APPROVE_OPTION) {
        file = fc.getSelectedFile();
        try (FileInputStream in = new FileInputStream(file)){
          String str = io.decrypt(in);
          String[] lines = str.split("\n");
          data.clear();
          for (String line : lines)
            data.add(line.split(" "));
          model.fireTableDataChanged();
        } catch (Exception ex) {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(this,
                                        "There an error opening the file",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    buttons[5].addActionListener(e -> {
      JFileChooser fc = new JFileChooser();
      int result = fc.showSaveDialog(PasswordManagerPanel.this);
      if (result == JFileChooser.APPROVE_OPTION) {
        file = fc.getSelectedFile();
        buttons[6].getActionListeners()[0].actionPerformed(null);
      }
    });
    buttons[6].addActionListener(e -> {
      try (FileOutputStream out = new FileOutputStream(file)) {
        StringBuilder str = new StringBuilder();
        Iterator<String[]> iter = data.iterator();
        while (true) {
          str.append(String.join(" ",iter.next()));
          if (!iter.hasNext())
            break;
          str.append('\n');
        }
        io.encrypt(str.toString(),out);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                                      "There was an error saving",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
    });
    
    left.add(buttons[0]);
    left.add(buttons[1]);
    center.add(buttons[2]);
    center.add(buttons[3]);
    right.add(buttons[4]);
    right.add(buttons[5]);
    right.add(buttons[6]);
    
    buttonPanel.add(left,BorderLayout.LINE_START);
    buttonPanel.add(center,BorderLayout.CENTER);
    buttonPanel.add(right,BorderLayout.LINE_END);
    
    add(pane,BorderLayout.CENTER);
    add(buttonPanel,BorderLayout.PAGE_END);
  }
  
  private void save() {
  }
  
}
