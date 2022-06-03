package main;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class LoginTableModel extends AbstractTableModel {

  private static final String[] COLUMNS = {"Website","Username","Password"};
  
  private List<String[]> data;
  
  public void setData(List<String[]> data) { this.data = data; }
  
  @Override public int getRowCount() {
    return data == null ? 0 : data.size();
  }
  
  @Override public int getColumnCount() {
    return 3;
  }
  
  @Override public String getColumnName(int num) {
    return COLUMNS[num];
  }
  
  @Override public Object getValueAt(int row,int col) {
    return data.get(row)[col];
  }
  
  @Override public boolean isCellEditable(int row,int col) {
    return true;
  }
  
  @Override public void setValueAt(Object val,int row,int col) {
    data.get(row)[col] = val == null ? null : val.toString();
  }
  
}
