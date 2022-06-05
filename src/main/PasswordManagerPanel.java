package main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PasswordManagerPanel extends JPanel implements ActionListener {
  
  private JButton[] buttons;
  
  private List<String[]> data;
  private LoginTableModel model;
  private JTable table;
  
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
    
    buttons = new JButton[] {
      new JButton("Add"),
      new JButton("Remove"),
      new JButton("Copy"),
      new JButton("Set Password"),
      new JButton("Open"),
      new JButton("Save As"),
      new JButton("Save"),
    };
    
    for (JButton b : buttons)
      b.addActionListener(this);
    
    left.add(buttons[0]);
    left.add(buttons[1]);
    center.add(buttons[2]);
    center.add(buttons[3]);
    right.add(buttons[4]);
    right.add(buttons[5]);
    
    buttonPanel.add(left,BorderLayout.LINE_START);
    buttonPanel.add(center,BorderLayout.CENTER);
    buttonPanel.add(right,BorderLayout.LINE_END);
    
    add(pane,BorderLayout.CENTER);
    add(buttonPanel,BorderLayout.PAGE_END);
  }
  
  @Override public void actionPerformed(ActionEvent e) {

  }
  
}
