package hb.nflguru.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hb.nflguru.statistics.model.Game;
import hb.nflguru.statistics.model.StatBundle;

public class DataAnalyzer
{
	private static final String OVERALL = "OVERALL";
	private static final String SEASON = "SEASON";
	private static final String TOTAL = "TOTAL";
	private static final String AWAY = "AWAY";
	private static final String HOME = "HOME";
	private DataLoader dataLoader;
	private static final int CURRENT_SEASON = 2018;
	
	
	public DataAnalyzer(DataLoader dataLoader)
	{
		this.dataLoader = dataLoader;
	}

	public static void main(String[] args)
	{
		DataAnalyzer dataAnalyzer = new DataAnalyzer(new DataLoader("/Users/garthpidcock/Development/Workspaces/guru17/nflguru2018/data/guru10YearData.json"));
		Map<String, StatBundle> stats = dataAnalyzer.statsAgainstOpponent(1);
		//dataAnalyzer.export(stats);
		dataAnalyzer.verify(DataLoader.MAX_SEASON, 1);
	}
	
	private void export(Map<String, StatBundle> stats)
	{

		for (String team : stats.keySet())
		{
			StatBundle statBundle = stats.get(team);
			System.out.println(statBundle.toString());
		}
	}

	private Map<String, StatBundle> statsAgainstOpponent(int week)
	{
		Map<String, StatBundle> statBundleMap = new HashMap<>();
		Map<String, Map<String, Map<String, Integer>>> data = new HashMap<>();

		List<Game> matchups = dataLoader.getGames(CURRENT_SEASON, week);
		Map<String, String> homeTeams = matchups.stream().collect(Collectors.toMap(Game::getHomeName, Game::getVisitorName));

		Map<String, String> awayTeams = matchups.stream().collect(Collectors.toMap(Game::getVisitorName, Game::getHomeName));

		Map<String, String> games = new HashMap<>(homeTeams);
		games.putAll(awayTeams);

		for (String team : games.keySet())
		{
			String opponent = games.get(team);
			Map<String, Integer> avg1 = getVersesOpponentCurrentSeason(team, opponent);
			Map<String, Integer> avg2 = getVersesOpponentAllTime(team, opponent);
			Map<String, Map<String, Integer>> verses = new HashMap<>();
			verses.put(SEASON, avg1);
			verses.put(OVERALL, avg2);
			data.put(team, verses);
		}
		
		
		for (String team : data.keySet())
		{
			StatBundle stats = new StatBundle(team, games.get(team));
			Map<String, Map<String, Integer>> pointsScored = data.get(team);
			
			Map<String, Integer> seasonStats = pointsScored.get(SEASON);
			stats.setPtsForVersesOpponentForCurrentSeason(seasonStats.get(HOME), seasonStats.get(AWAY), seasonStats.get(TOTAL));
			
			Map<String, Integer> overallStats = pointsScored.get(OVERALL);
			stats.setPtsForVersesOpponentForOverall(overallStats.get(HOME), overallStats.get(AWAY), overallStats.get(TOTAL));
			
			statBundleMap.put(team, stats);
		}
		
		return statBundleMap;
	}

	private Map<String, Integer> getVersesOpponentCurrentSeason(String team, String opponent)
	{
		List<Game> games = dataLoader.getGames(CURRENT_SEASON);
		return getVersesOpponent(games, team, opponent);
	}

	private Map<String, Integer> getVersesOpponentAllTime(String team, String opponent)
	{
		List<Game> games = dataLoader.getGames();
		return getVersesOpponent(games, team, opponent);
	}

	private Map<String, Integer> getVersesOpponent(List<Game> games, String team, String opponent)
	{
		Map<String, Integer> results = new HashMap<>();

		Double homeAverage = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase()) && g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
		int hpts = (int) Math.round(homeAverage);
		results.put(HOME, hpts);

		Double awayAverage = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase()) && g.getVisitorName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
		int vpts = (int) Math.round(awayAverage);
		results.put(AWAY, vpts);

		int totpts = (int) Math.round((hpts+vpts)/2);
		results.put(TOTAL, totpts);
		
		return results;
	}
	
	private void verify(int season, int week) {
		List<Game> games = dataLoader.getGames();
		List<Game> matchups = dataLoader.getGames(season, week);
		
		for (Game matchup : matchups)
		{
			String opponent = matchup.getHomeName();
			String team = matchup.getVisitorName();
			
			System.out.println(matchup.getVisitorName().toLowerCase() + " @ " + matchup.getHomeName().toUpperCase());

			// --- Verses ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
			long homeGamesVersesVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase()) && g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).count();
			long awayGamesVersesVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase()) && g.getVisitorName().toLowerCase().equals(team.toLowerCase())).count();
			Double avgPtsScoredHomeVersesVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase()) && g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeVersesVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase()) && g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayVersesVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase()) && g.getVisitorName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayVersesVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase()) && g.getVisitorName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));

			long homeGamesVersesHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase()) && g.getVisitorName().toLowerCase().equals(team.toLowerCase())).count();
			long awayGamesVersesHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase()) && g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).count();
			Double avgPtsScoredHomeVersesHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase()) && g.getVisitorName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeVersesHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase()) && g.getVisitorName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayVersesHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase()) && g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayVersesHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase()) && g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
	
			// --- Overall ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
			long homeGamesOverallVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase())).count();
			long awayGamesOverallVisitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(team.toLowerCase())).count();
			Double avgPtsScoredHomeOverallVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallVisitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallVisitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(team.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
	
			Double avgPtsScoredHomeOverallLast10Visitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallLast10Visitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallLast10Visitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallLast10Visitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			
			
			Double avgPtsScoredHomeOverallLast3Visitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallLast3Visitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallLast3Visitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallLast3Visitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(team.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			
			
			
			long homeGamesOverallHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase())).count();
			long awayGamesOverallHome = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).count();
			Double avgPtsScoredHomeOverallHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallHome = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallHome = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
	
			Double avgPtsScoredHomeOverallLast10Home = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallLast10Home = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallLast10Home = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallLast10Home = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			
			
			Double avgPtsScoredHomeOverallLast3Home = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallLast3Home = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(opponent.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallLast3Home = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallLast3Home = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(opponent.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			
			
			System.out.println("--- Verses ------------------------------------------------------------");
			System.out.println("Home (" + homeGamesVersesVisitor + ") [Scored :" + Math.round(avgPtsScoredHomeVersesVisitor) + " Allowed:" + Math.round(avgPtsAllowedHomeVersesVisitor) + "]  |  Home (" + homeGamesVersesHome + ") [Scored :" + Math.round(avgPtsScoredHomeVersesHome) + " Allowed:" + Math.round(avgPtsAllowedHomeVersesHome) + "]");
			System.out.println("Away (" + awayGamesVersesVisitor + ") [Scored :" + Math.round(avgPtsScoredAwayVersesVisitor) + " Allowed:" + Math.round(avgPtsAllowedAwayVersesVisitor) + "]  |  Away (" + awayGamesVersesHome + ") [Scored :" + Math.round(avgPtsScoredAwayVersesHome) + " Allowed:" + Math.round(avgPtsAllowedAwayVersesHome) + "]");
			
			System.out.println("--- Overall -----------------------------------------------------------");
			System.out.println("Home (" + homeGamesOverallVisitor + ") [Scored :" + Math.round(avgPtsScoredHomeOverallVisitor) + " Allowed:" + Math.round(avgPtsAllowedHomeOverallVisitor) + "]  |  Home (" + homeGamesOverallHome + ") [Scored :" + Math.round(avgPtsScoredHomeOverallHome) + " Allowed:" + Math.round(avgPtsAllowedHomeOverallHome) + "]");
			System.out.println("Away (" + awayGamesOverallVisitor + ") [Scored :" + Math.round(avgPtsScoredAwayOverallVisitor) + " Allowed:" + Math.round(avgPtsAllowedAwayOverallVisitor) + "]  |  Away (" + awayGamesOverallHome + ") [Scored :" + Math.round(avgPtsScoredAwayOverallHome) + " Allowed:" + Math.round(avgPtsAllowedAwayOverallHome) + "]");
			
			System.out.println("--- Last 10 -----------------------------------------------------------");
			System.out.println("Home (" + 10 + ") [Scored :" + Math.round(avgPtsScoredHomeOverallLast10Visitor) + " Allowed:" + Math.round(avgPtsAllowedHomeOverallLast10Visitor) + "]  |  Home (" + 10 + ") [Scored :" + Math.round(avgPtsScoredHomeOverallLast10Home) + " Allowed:" + Math.round(avgPtsAllowedHomeOverallLast10Home) + "]");
			System.out.println("Away (" + 10 + ") [Scored :" + Math.round(avgPtsScoredAwayOverallLast10Visitor) + " Allowed:" + Math.round(avgPtsAllowedAwayOverallLast10Visitor) + "]  |  Away (" + 10 + ") [Scored :" + Math.round(avgPtsScoredAwayOverallLast10Home) + " Allowed:" + Math.round(avgPtsAllowedAwayOverallLast10Home) + "]");
	
			System.out.println("--- Last 3 ------------------------------------------------------------");
			System.out.println("Home  (" + 3 + ") [Scored :" + Math.round(avgPtsScoredHomeOverallLast3Visitor) + " Allowed:" + Math.round(avgPtsAllowedHomeOverallLast3Visitor) + "]  |  Home (" + 3 + ") [Scored :" + Math.round(avgPtsScoredHomeOverallLast3Home) + " Allowed:" + Math.round(avgPtsAllowedHomeOverallLast3Home) + "]");
			System.out.println("Away  (" + 3 + ") [Scored :" + Math.round(avgPtsScoredAwayOverallLast3Visitor) + " Allowed:" + Math.round(avgPtsAllowedAwayOverallLast3Visitor) + "]  |  Away (" + 3 + ") [Scored :" + Math.round(avgPtsScoredAwayOverallLast3Home) + " Allowed:" + Math.round(avgPtsAllowedAwayOverallLast3Home) + "]");
			System.out.println("-----------------------------------------------------------------------\n");
			
		}
	}
}
