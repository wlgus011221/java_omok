package gui;

import java.awt.Component;
import java.util.Collections;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class PasswordCellRenderer extends DefaultTableCellRenderer {
    private boolean isPasswordVisible = false;

    public void setPasswordVisible(boolean isPasswordVisible) {
        this.isPasswordVisible = isPasswordVisible;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isPasswordVisible) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        } else {
            String str = String.valueOf(value);
            String masked = String.join("", Collections.nCopies(str.length(), "*"));
            return super.getTableCellRendererComponent(table, masked, isSelected, hasFocus, row, column);
        }
    }
}
