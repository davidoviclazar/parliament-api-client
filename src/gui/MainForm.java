package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import controller.Controller;
import gui.model.TableModel;

public class MainForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel eastPanel;
	private JPanel southPanel;
	private JScrollPane scrollPane;
	private JTable table;
	private JScrollPane scrollPaneStatus;
	private JTextArea textAreaStatus;
	private JButton btnUpdateMembers;
	private JButton btnFillTable;
	private JButton btnGetMembers;

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setTitle("Parlament Members");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getEastPanel(), BorderLayout.EAST);
		contentPane.add(getSouthPanel(), BorderLayout.SOUTH);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
	}

	private JPanel getEastPanel() {
		if (eastPanel == null) {
			eastPanel = new JPanel();
			eastPanel.setPreferredSize(new Dimension(200, 10));
			eastPanel.add(getBtnGetMembers());
			eastPanel.add(getBtnFillTable());
			eastPanel.add(getBtnUpdateMembers());
		}
		return eastPanel;
	}

	private JPanel getSouthPanel() {
		if (southPanel == null) {
			southPanel = new JPanel();
			southPanel.setBorder(new TitledBorder(null, "STATUS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			southPanel.setPreferredSize(new Dimension(10, 100));
			southPanel.setLayout(new BorderLayout(0, 0));
			southPanel.add(getScrollPaneStatus(), BorderLayout.CENTER);
		}
		return southPanel;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable(new TableModel());
			table.setFillsViewportHeight(true);
		}
		return table;
	}

	private JScrollPane getScrollPaneStatus() {
		if (scrollPaneStatus == null) {
			scrollPaneStatus = new JScrollPane();
			scrollPaneStatus.setViewportView(getTextAreaStatus());
		}
		return scrollPaneStatus;
	}

	public JTextArea getTextAreaStatus() {
		if (textAreaStatus == null) {
			textAreaStatus = new JTextArea();
			textAreaStatus.setEditable(false);
			textAreaStatus.setLineWrap(true);
			textAreaStatus.setWrapStyleWord(true);
		}
		return textAreaStatus;
	}

	private JButton getBtnUpdateMembers() {
		if (btnUpdateMembers == null) {
			btnUpdateMembers = new JButton("Update members");
			btnUpdateMembers.setPreferredSize(new Dimension(160, 30));
		}
		return btnUpdateMembers;
	}

	private JButton getBtnFillTable() {
		if (btnFillTable == null) {
			btnFillTable = new JButton("Fill table");
			btnFillTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					Controller.fillTable();
				}
			});
			btnFillTable.setPreferredSize(new Dimension(160, 30));
		}
		return btnFillTable;
	}

	private JButton getBtnGetMembers() {
		if (btnGetMembers == null) {
			btnGetMembers = new JButton("GET members");
			btnGetMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					Controller.returnDeputiesInJson();
				}
			});
			btnGetMembers.setPreferredSize(new Dimension(160, 30));
		}
		return btnGetMembers;
	}
}