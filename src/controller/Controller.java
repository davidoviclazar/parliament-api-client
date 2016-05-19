package controller;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Deputy;
import gui.MainForm;
import gui.model.TableModel;
import util.ParliamentAPICommunication;

public class Controller {

	private static MainForm mainForm;
	private static final String location = "data/serviceMembers.json";

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					mainForm = new MainForm();
					mainForm.setLocationRelativeTo(null);
					mainForm.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void returnDeputiesInJson() {
		try {
			JsonArray jsonArray = ParliamentAPICommunication.returnInJSON();

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(location)));

			String text = new GsonBuilder().setPrettyPrinting().create().toJson(jsonArray);
			out.println(text);

			out.close();

			addInStatus("List of deputies has been successfully loaded!");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainForm, "An error has occurred!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void fillTable() {

		try {

			List<Deputy> deputies = deserialize();

			TableModel dtm = (TableModel) mainForm.getTable().getModel();
			dtm.setDeputies(deputies);

			addInStatus("Table has been successfully updated!");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainForm, "An error has occurred!", "Error!", JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void addInStatus(String text) {
		mainForm.getTextAreaStatus().append(text + System.lineSeparator());
	}

	private static List<Deputy> deserialize() throws Exception {
		List<Deputy> deputies = new LinkedList<>();

		FileReader in = new FileReader(location);

		JsonArray jsonAray = new GsonBuilder().create().fromJson(in, JsonArray.class);

		in.close();

		JsonObject jsonObject = null;
		Deputy d = null;

		for (int i = 0; i < jsonAray.size(); i++) {
			jsonObject = (JsonObject) jsonAray.get(i);

			d = new Deputy();
			d.setId(jsonObject.get("id").getAsInt());
			d.setFirstName(jsonObject.get("name").getAsString());
			d.setLastName(jsonObject.get("lastName").getAsString());
			if (jsonObject.get("birthDate") != null) {
				try {
					d.setBirthDate((Date) new SimpleDateFormat("dd.MM.yyyy.")
							.parse(jsonObject.get("birthDate").getAsString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			deputies.add(d);
		}

		return deputies;
	}

}