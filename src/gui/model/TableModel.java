package gui.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import domain.Deputy;

public class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] columnNames = { "ID", "First name", "Last name", "Birth date" };
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	private List<Deputy> deputies;

	public TableModel(List<Deputy> deputies) {

		super();

		if (deputies != null) {
			this.deputies = deputies;
		} else {
			this.deputies = new LinkedList<>();
		}
	}

	public TableModel() {
		this.deputies = new LinkedList<>();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		if (deputies == null) {
			return 0;
		}

		return deputies.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Deputy p = deputies.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return p.getId();
		case 1:
			return p.getFirstName();
		case 2:
			return p.getLastName();
		case 3:
			return (p.getBirthDate() != null) ? sdf.format(p.getBirthDate()) : "Unknown";

		default:
			return "NN";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}

		return true;
	}

	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		Deputy d = deputies.get(rowIndex);

		switch (columnIndex) {
		case 1:
			String firstName = obj.toString();
			if (firstName != null && !firstName.trim().isEmpty()) {
				d.setFirstName(firstName);
			} else {
				JOptionPane.showMessageDialog(null, "First name may not be empty string!", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}
			break;

		case 2:
			String lastName = obj.toString();
			if (lastName != null && !lastName.trim().isEmpty()) {
				d.setLastName(lastName);
			} else {
				JOptionPane.showMessageDialog(null, "Last name must not be empty string!", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}
			break;

		case 3:
			String dateString = obj.toString();
			try {
				Date date = sdf.parse(dateString);
				d.setBirthDate(date);
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, "The date must be in the format: dd.MM.yyyy!", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}

			break;

		default:
			return;
		}

		fireTableCellUpdated(rowIndex, columnIndex);

	}

	public void setDeputies(List<Deputy> deputies) {
		if (deputies != null) {
			this.deputies = deputies;
			fireTableDataChanged();
		}
	}

	public List<Deputy> getDeputies() {
		return deputies;
	}

	public void emptyList() {
		deputies = new LinkedList<>();
		fireTableDataChanged();
	}

}