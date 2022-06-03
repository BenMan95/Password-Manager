package main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PasswordManagerPanel extends JPanel {
  
  public PasswordManagerPanel() {
    setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
    
//    List<String[]> data = new ArrayList<String[]>();
//    data.add(new String[] {"1","2","3"});
//    data.add(new String[] {"4","5","6"});
//    data.add(new String[] {"7","8","9"});
//    data.add(new String[] {null,null,null});
    
    LoginTableModel model = new LoginTableModel();
//    model.setData(data);
    JTable table = new JTable(model);
    JScrollPane pane = new JScrollPane(table);
    
    JButton button = new JButton("test");
    
    Border border = BorderFactory.createEmptyBorder(10,10,10,10);
    pane.setBorder(border);
    button.setBorder(border);
    
    add(pane);
    add(button);
  }
  
}
