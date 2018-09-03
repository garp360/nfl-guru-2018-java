
package hb.nflguru.importer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import hb.nflguru.statistics.model.Matchup;

public class DataLoader
{
	private List<Matchup> games = new ArrayList<>();
	private JSONArray gameData;
	private Gson gson = new Gson();

	public DataLoader(String filepath)
	{
		try
		{
			String data = new String(Files.readAllBytes(Paths.get(filepath)));
			gameData = new JSONObject(data).getJSONArray("data");
			
			for (int i = 0; i < gameData.length(); i++)
			{
				Matchup matchup = gson.fromJson(gameData.getJSONObject(i).toString(), Matchup.class);
				games.add(matchup);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println(games.size());
	}

	public Integer getSituationalHeadToHeadGamesPlayed(String home, String away)
	{
		Long gamesPlayedHeadToHead = games.stream().filter(g -> g.getHome().name().toLowerCase().equals(home.toLowerCase()) && g.getAway().name().toLowerCase().equals(away.toLowerCase())).count();
		return gamesPlayedHeadToHead.intValue();
	}

	public Integer getSituationalHeadToHeadPtsScoredHome(String home, String away)
	{
		Double avgPtsScoredHeadToHeadSituationalHome = games.stream().filter(g -> g.getHome().name().toLowerCase().equals(home.toLowerCase()) && g.getAway().name().toLowerCase().equals(away.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
		return avgPtsScoredHeadToHeadSituationalHome.intValue();
	}
	
	public Integer getSituationalHeadToHeadPtsScoredAway(String home, String away)
	{
		Double avgPtsScoredHeadToHeadSituationalVisitor = games.stream().filter(g -> g.getHome().name().toLowerCase().equals(home.toLowerCase()) && g.getAway().name().toLowerCase().equals(away.toLowerCase())).collect(Collectors.averagingInt(p -> p.getAwayScore()));
		return avgPtsScoredHeadToHeadSituationalVisitor.intValue();
	}

	public Integer getSituationalPtsScoredHome(String team, long limit)
	{
		Double ptsScored = games.stream().filter(g -> g.getHome().name().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
			return s2.getGameDay().compareTo(s1.getGameDay());
		}).limit(limit).collect(Collectors.averagingInt(p -> p.getHomeScore()));
		return ptsScored.intValue();
	}
	
	public Integer getSituationalPtsScoredAway(String team, long limit)
	{
		Double ptsScored = games.stream().filter(g -> g.getAway().name().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
	        return s2.getGameDay().compareTo(s1.getGameDay());
	    }).limit(limit).collect(Collectors.averagingInt(p -> p.getAwayScore()));
		return ptsScored.intValue();
	}
	
	public Integer getOverallPtsScored(String team, long limit)
	{
		Double ptsScored = games.stream().filter(g -> g.getHome().name().toLowerCase().equals(team.toLowerCase()) || g.getAway().name().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
			return s2.getGameDay().compareTo(s1.getGameDay());
		}).limit(limit).collect(Collectors.averagingInt(p -> p.getHomeScore()));
		return ptsScored.intValue();
	}
	
}
