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
	protected MasseyPredictionsImporter masseyPredictionsImporter = new MasseyPredictionsImporter();
	protected String historicalDataFilePath = "/Users/garthpidcock/git/nflguru2018/nflguru2018/data/nflGuruDataArchive%s.json";
	public CurrentSeasonResultsImporter()
	{
		initialize();
	}

	public List<Matchup> importResults(String outputFilePath)
	{
		Map<String, Integer> masseyPredictions = masseyPredictionsImporter.importResults();
		CurrentSpreadsImporter currentSpreadsImporter = new CurrentSpreadsImporter();
		String filePath = String.format(historicalDataFilePath, ScheduleUtils.getSuffix());
		DataLoader dataLoader = new DataLoader(filePath);

		Map<String, BigDecimal> spreads = currentSpreadsImporter.getSpreads();
		List<Matchup> matchups = getMatchupsForCurrentWeek( );

		for (Matchup matchup : matchups)
		{
			String home = matchup.getHome().name();
			String away = matchup.getAway().name();

			matchup.setHomeSpread(spreads.get(home));
			matchup.setAwaySpread(spreads.get(away));
			
			matchup.setHead2HeadGamesPlayed(dataLoader.getSituationalHeadToHeadGamesPlayed(home, away));
			
			matchup.setHomeHead2HeadPtsScored(dataLoader.getSituationalHeadToHeadPtsScoredHome(home, away));
			matchup.setHomeSituationalPtsScoredLast10(dataLoader.getSituationalPtsScoredHome(home, 10L));
			matchup.setHomeSituationalPtsScoredLast5(dataLoader.getSituationalPtsScoredHome(home, 5L));
			matchup.setHomeSituationalPtsScoredLast3(dataLoader.getSituationalPtsScoredHome(home, 3L));
			matchup.setHomeOverallPtsScoredLast10(dataLoader.getOverallPtsScored(home, 10L));
			matchup.setHomeOverallPtsScoredLast5(dataLoader.getOverallPtsScored(home, 5L));
			matchup.setHomeOverallPtsScoredLast3(dataLoader.getOverallPtsScored(home, 3L));
			
			matchup.setHomeSituationalPtsAllowedLast10(dataLoader.getSituationalPtsAllowedHome(home, 10L));
			matchup.setHomeSituationalPtsAllowedLast5(dataLoader.getSituationalPtsAllowedHome(home, 5L));
			matchup.setHomeSituationalPtsAllowedLast3(dataLoader.getSituationalPtsAllowedHome(home, 3L));
			matchup.setHomeOverallPtsAllowedLast10(dataLoader.getOverallPtsAllowed(home, 10L));
			matchup.setHomeOverallPtsAllowedLast5(dataLoader.getOverallPtsAllowed(home, 5L));
			matchup.setHomeOverallPtsAllowedLast3(dataLoader.getOverallPtsAllowed(home, 3L));

			matchup.setAwayHead2HeadPtsScored(dataLoader.getSituationalHeadToHeadPtsScoredAway(home, away));
			matchup.setAwaySituationalPtsScoredLast10(dataLoader.getSituationalPtsScoredAway(away, 10L));
			matchup.setAwaySituationalPtsScoredLast5(dataLoader.getSituationalPtsScoredAway(away, 5L));
			matchup.setAwaySituationalPtsScoredLast3(dataLoader.getSituationalPtsScoredAway(away, 3L));
			matchup.setAwayOverallPtsScoredLast10(dataLoader.getOverallPtsScored(away, 10L));
			matchup.setAwayOverallPtsScoredLast5(dataLoader.getOverallPtsScored(away, 5L));
			matchup.setAwayOverallPtsScoredLast3(dataLoader.getOverallPtsScored(away, 3L));
			
			matchup.setAwaySituationalPtsAllowedLast10(dataLoader.getSituationalPtsAllowedAway(away, 10L));
			matchup.setAwaySituationalPtsAllowedLast5(dataLoader.getSituationalPtsAllowedAway(away, 5L));
			matchup.setAwaySituationalPtsAllowedLast3(dataLoader.getSituationalPtsAllowedAway(away, 3L));
			matchup.setAwayOverallPtsAllowedLast10(dataLoader.getOverallPtsAllowed(away, 10L));
			matchup.setAwayOverallPtsAllowedLast5(dataLoader.getOverallPtsAllowed(away, 5L));
			matchup.setAwayOverallPtsAllowedLast3(dataLoader.getOverallPtsAllowed(away, 3L));
			
			matchup.setMasseyPredictionHomePtsScored(masseyPredictions.get(matchup.getHome().name()));
			matchup.setMasseyPredictionAwayPtsScored(masseyPredictions.get(matchup.getAway().name()));
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
