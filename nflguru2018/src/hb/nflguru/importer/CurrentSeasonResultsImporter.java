package hb.nflguru.importer;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import hb.nflguru.statistics.model.Matchup;
import hb.nflguru.utils.ScheduleUtils;

public class CurrentSeasonResultsImporter extends BaseImporter
{

	// http://www.nfl.com/ajax/scorestrip?season=2011&seasonType=REG&week=2

	public CurrentSeasonResultsImporter()
	{
		initialize();

	}

	public List<Matchup> importResults()
	{
		List<Matchup> matchups = getMatchups(ScheduleUtils.getCurrentSeason());

		String output = gson.toJson(matchups);
		System.out.println(output);
		return matchups;
	}

	public List<Matchup> importResults(String outputFilePath)
	{
		CurrentSpreadsImporter currentSpreadsImporter = new CurrentSpreadsImporter();
		DataLoader dataLoader = new DataLoader("/Users/garthpidcock/git/nflguru2018/nflguru2018/data/nflGuruDataArchive.json");

		Map<String, BigDecimal> spreads = currentSpreadsImporter.getSpreads();
		List<Matchup> matchups = getMatchups(ScheduleUtils.getCurrentSeason());

		for (Matchup matchup : matchups)
		{
			String home = matchup.getHome().name();
			String away = matchup.getAway().name();

			matchup.setHomeSpread(spreads.get(home));
			matchup.setAwaySpread(spreads.get(away));
			
			Integer headToHeadGamesPlayed = dataLoader.getSituationalHeadToHeadGamesPlayed(home, away);
			Integer headToHeadAwayPointsScored = dataLoader.getSituationalHeadToHeadPtsScoredAway(home, away);
			Integer headToHeadHomePointsScored = dataLoader.getSituationalHeadToHeadPtsScoredHome(home, away);
		}

		String output = gson.toJson(matchups);

		try
		{
			Files.write(Paths.get(outputFilePath), output.getBytes());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return matchups;
	}

	protected List<Matchup> getMatchupsFromJson(String gamesForWeekAsJson)
	{
		List<Matchup> matchups = new ArrayList<>();
		JSONObject json = XML.toJSONObject(gamesForWeekAsJson);
		JSONObject gms = json.getJSONObject("ss").getJSONObject("gms");

		int week = gms.getInt("w");
		int season = gms.getInt("y");

		JSONArray games = gms.getJSONArray("g");

		for (int i = 0; i < games.length(); i++)
		{
			JSONObject g = games.getJSONObject(i);

			g.put("game", i + 1);
			g.put("week", week);
			g.put("season", season);
			g.put("home", g.get("h"));
			g.put("away", g.get("v"));
			g.put("gameDay", g.get("eid"));
			g.put("gameTime", g.get("t"));
			if (g.getString("q").equals("F"))
			{
				g.put("homeScore", g.get("hs"));
				g.put("awayScore", g.get("vs"));
			}
			else
			{
				g.put("homeScore", -1);
				g.put("awayScore", -1);
			}

			if (!g.has("week"))
			{
				System.err.println("missing week:" + season + ":" + week);
			}

			String string = g.toString().replaceAll("\"JAX\"", "\"JAC\"").replaceAll("\"LA\"", "\"LAR\"");
			Matchup matchup = gson.fromJson(string, Matchup.class);
			matchups.add(matchup);

		}
		return matchups;
	}

}
