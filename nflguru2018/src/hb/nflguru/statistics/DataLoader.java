
package hb.nflguru.statistics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import hb.nflguru.statistics.model.Game;
import nflguru2018.NFLTeam;

public class DataLoader
{
	public static final int CURRENT_WEEK = 0;
	private static final int TOTAL_WEEKS = 17;
	public static final int MAX_SEASON = 2018;
	private static final int MIN_SEASON = 2008;
	private JSONObject data;
	private Gson gson = new Gson();
	private List<Game> games = new ArrayList<>();

	public DataLoader(String filepath)
	{
		try
		{
			String json = new String(Files.readAllBytes(Paths.get(filepath)));
			data = new JSONObject(json);
			games = getAllGames();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public final List<Game> getAllGames()
	{
		List<Game> games = new ArrayList<>();

		for (int season = MIN_SEASON; season < MAX_SEASON; season++)
		{
			games.addAll(getGames(season));
		}

		return games;
	}

	public final List<Game> getGames(int season)
	{
		List<Game> games = new ArrayList<>();

		for (int week = 1; week <= TOTAL_WEEKS; week++)
		{
			games.addAll(getGames(season, week));
		}

		return games;
	}

	public final List<Game> getGames(int season, int week)
	{
		List<Game> games = new ArrayList<>();
		JSONArray arr = data.getJSONArray(String.valueOf(season)).getJSONArray(week);

		for (int i = 0; i < arr.length(); i++)
		{
			games.add(gson.fromJson(arr.getJSONObject(i).toString(), Game.class));
		}

		return games;
	}

	public List<Game> getGames()
	{
		return games;
	}

	public void getMasseyPredictions()
	{
		StringBuilder builder = new StringBuilder();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dt = df.format(new Date());

		URL oracle;
		try
		{
			oracle = new URL("https://www.masseyratings.com/predjson.php?s=nfl&dt=" + dt);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				inputLine = inputLine.replaceAll("\t", "");
				inputLine = inputLine.replaceAll("\n", "").replaceAll("\r", "");
				// inputLine = inputLine.replaceAll("\\s", "");
				builder.append(inputLine);
			}
			in.close();

			String str = builder.toString();

			JSONObject json = new JSONObject(str);
			JSONArray gamesArray = json.getJSONArray("DI");
			for (int i = 0; i < gamesArray.length(); i++)
			{
				try
				{
					JSONArray game = (JSONArray) gamesArray.get(i);
					JSONArray homeGroup = (JSONArray) game.get(3);
					JSONArray awayGroup = (JSONArray) game.get(2);
					JSONArray homeRankGroup = (JSONArray) game.get(5);
					JSONArray awayRankGroup = (JSONArray) game.get(4);
					JSONArray homeScoreGroup = (JSONArray) game.get(9);
					JSONArray awayScoreGroup = (JSONArray) game.get(8);
					String away = NFLTeam.convertMasseyNameToAbbr(((String)awayGroup.get(0)).trim().toUpperCase());
					String home = NFLTeam.convertMasseyNameToAbbr(((String)homeGroup.get(0)).trim().substring(2).trim().toUpperCase());
					
					String awayRank = awayRankGroup.getString(0).replaceAll("[^0-9]", "");
					String homeRank = homeRankGroup.getString(0).replaceAll("[^0-9]", "");
					
					Integer homeScore = homeScoreGroup.getInt(0);
					Integer awayScore = awayScoreGroup.getInt(0);
					//System.out.println(awayRankGroup);
					System.out.println(away + "(" + awayRank + " | " + awayScore + ") @ " + home + "(" + homeRank + " | " + homeScore + ")");
					
				}
				catch (Exception e)
				{

				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//System.out.println(away.substring(0,3) + " (\"" + away + "\"),");
	//System.out.println(home.substring(0,3) + " (\"" + home + "\"),");
}
