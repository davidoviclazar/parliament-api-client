package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Deputy;

public class ParliamentAPICommunication {

	private static final String membersURL = "http://147.91.128.71:9090/parlament/api/members";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

	public static List<Deputy> returnDeputies() {

		List<Deputy> deputies = new LinkedList<>();

		try {
			String result = sendGet(membersURL);

			JsonArray jsonArray = new GsonBuilder().create().fromJson(result, JsonArray.class);

			Deputy d = null;
			JsonObject jsonObject = null;

			for (int i = 0; i < jsonArray.size(); i++) {
				jsonObject = (JsonObject) jsonArray.get(i);

				d = new Deputy();
				d.setId(jsonObject.get("id").getAsInt());
				d.setFirstName(jsonObject.get("name").getAsString());
				d.setLastName(jsonObject.get("lastName").getAsString());

				String birthDate = jsonObject.get("birthDate").getAsString();
				if (birthDate != null) {
					d.setBirthDate(sdf.parse(birthDate));
				}

				deputies.add(d);
			}

			return deputies;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return deputies;
	}

	public static JsonArray returnInJSON() throws Exception {
		return new GsonBuilder().setPrettyPrinting().create().fromJson(sendGet(membersURL), JsonArray.class);
	}

	private static String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String row = null;
		String text = "";

		while ((row = in.readLine()) != null) {
			text += row;
		}

		return text;

	}

	public static JsonArray transferToJSONArray(List<Deputy> deputies) {
		JsonArray jsonArray = new JsonArray();

		JsonObject jsonObject = null;

		for (int i = 0; i < deputies.size(); i++) {
			Deputy d = deputies.get(i);

			jsonObject = new JsonObject();
			jsonObject.addProperty("id", d.getId());
			jsonObject.addProperty("name", d.getFirstName());
			jsonObject.addProperty("lastName", d.getLastName());
			try {
				jsonObject.addProperty("birthDate", sdf.format(d.getBirthDate()));
			} catch (Exception e) {

				jsonObject.addProperty("birthDate", "Unknown");
			}

			jsonArray.add(jsonObject);

		}

		return jsonArray;
	}
}