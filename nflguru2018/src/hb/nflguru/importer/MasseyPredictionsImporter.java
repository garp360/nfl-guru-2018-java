package hb.nflguru.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import hb.nflguru.utils.NFLTeam;

public class MasseyPredictionsImporter
{
	public Map<String, Integer> importResults()
	{
		String url = getCurrentMasseyUrl();
		Map<String, Integer> predictions = new HashMap<>();

		try
		{
			predictions = getMasseyPredictions(url);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return predictions;
	}

	/*
	 * 
	 * ["Sun 09.09", "", "game.php?gid=885277911", "737311-3-1300-467"],
	 * ["1:00pm ET", ""],
	 * ["Tennessee", "", "team.php?s=300936&t=16578"],
	 * ["@ Miami", "", "team.php?s=300936&t=15859"],
	 * ["# 21 (-)", "", "", 33],
	 * ["# 27 (-)"],
	 * [0, "white", "", 0],
	 * [0, "white"],
	 * [20, "white", "", 23],
	 * [23, "cWin"],
	 * [45.3244, "white", "", 53.919200000000004],
	 * [53.919200000000004, "cWin"],
	 * [-1, "ltred", "", 2.5],
	 * [-2.5, "ltgreen", "", 2.5],
	 * [42.5, "ltgreen"],
	 * [45, "ltred"]
	 */

	private Map<String, Integer> getMasseyPredictions(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		Map<String, Integer> predictions = new HashMap<>();

		@SuppressWarnings("resource")
		WebClient webClient = new WebClient();
		StringBuilder masseyPacket = new StringBuilder();
		masseyPacket.append("{");
		masseyPacket.append(webClient.getPage(url).toString());
		masseyPacket.append("}");

		JSONObject json = new JSONObject(getJsonData(url));
		JSONArray games = json.getJSONArray("DI");

		for (int i = 0; i < games.length(); i++)
		{
			JSONArray game = games.getJSONArray(i);

			
			extractHomeTeamData(predictions, game);
			extractAwayTeamData(predictions, game);

		}

		return predictions;
	}

	private String getJsonData(String masseyUrl)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			URL url = new URL(masseyUrl);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				sb.append(inputLine);
			}
			

			in.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	private void extractHomeTeamData(Map<String, Integer> predictions, JSONArray game)
	{
		
		String rawTeam = game.getJSONArray(2).getString(0).trim();
		String team = NFLTeam.convertMasseyNameToAbbr(rawTeam.toUpperCase());

		Integer predictedScore = game.getJSONArray(8).getInt(0);
		predictions.put(team, predictedScore);
	}

	private void extractAwayTeamData(Map<String, Integer> predictions, JSONArray game)
	{
		String rawTeam = game.getJSONArray(3).getString(0);
		if (rawTeam.contains("@"))
		{
			rawTeam = rawTeam.split("@")[1].trim();
		}
		String team = NFLTeam.convertMasseyNameToAbbr(rawTeam.toUpperCase());

		Integer predictedScore = game.getJSONArray(9).getInt(0);
		predictions.put(team, predictedScore);
	}

	public JsonArray parse(String json)
	{
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray d1 = jobject.getAsJsonArray("DI");

		return d1;
	}

	private String getCurrentMasseyUrl()
	{
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dt = df.format(new Date());
		return "https://www.masseyratings.com/predjson.php?s=nfl&dt=" + dt;
	}
}