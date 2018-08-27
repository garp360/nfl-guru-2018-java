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
		DataAnalyzer dataAnalyzer = new DataAnalyzer(new DataLoader("C:\\Users\\win764\\git\\nfl-guru-2018-java\\nflguru2018\\data\\guru10YearData.json"));
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
			String homeTeam = matchup.getHomeName();
			String visitingTeam = matchup.getVisitorName();
			

			
			
			// --- Verses ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
			long gamesPlayedHeadToHead = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase()) && g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).count();
			Double avgPtsScoredHeadToHeadSituationalVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase()) && g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredHeadToHeadSituationalHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase()) && g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));

			// --- Overall ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
			long homeGamesOverallVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(visitingTeam.toLowerCase())).count();
			long awayGamesOverallVisitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).count();
			Double avgPtsScoredHomeOverallVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(visitingTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallVisitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(visitingTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallVisitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallVisitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
	
			Double avgPtsScoredHomeOverallLast10Visitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(visitingTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallLast10Visitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(visitingTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallLast10Visitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallLast10Visitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			
			Double avgPtsScoredHomeOverallLast3Visitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(visitingTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallLast3Visitor = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(visitingTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallLast3Visitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallLast3Visitor = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(visitingTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			
			long homeGamesOverallHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase())).count();
			long awayGamesOverallHome = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(homeTeam.toLowerCase())).count();
			Double avgPtsScoredHomeOverallHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallHome = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallHome = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(homeTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallHome = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(homeTeam.toLowerCase())).collect(Collectors.averagingInt(p -> p.getHomeScore()));
	
			Double avgPtsScoredHomeOverallLast10Home = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallLast10Home = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallLast10Home = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(homeTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallLast10Home = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(homeTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(10L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			
			
			Double avgPtsScoredHomeOverallLast3Home = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			Double avgPtsAllowedHomeOverallLast3Home = games.stream().filter(g -> g.getHomeName().toLowerCase().equals(homeTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsScoredAwayOverallLast3Home = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(homeTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getVisitorScore()));
			Double avgPtsAllowedAwayOverallLast3Home = games.stream().filter(g -> g.getVisitorName().toLowerCase().equals(homeTeam.toLowerCase())).sorted((s1, s2) -> {
		        return s2.getId().compareTo(s1.getId());
		    }).limit(3L).collect(Collectors.averagingInt(p -> p.getHomeScore()));
			
			Double visitorPts = Stream.of(avgPtsScoredHeadToHeadSituationalVisitor,avgPtsScoredAwayOverallVisitor,avgPtsScoredAwayOverallLast10Visitor ,avgPtsScoredAwayOverallLast3Visitor ).collect(Collectors.averagingDouble(p -> p.doubleValue()));
			Double homePts = Stream.of(avgPtsScoredHeadToHeadSituationalHome,avgPtsScoredAwayOverallHome,avgPtsScoredAwayOverallLast10Home ,avgPtsScoredAwayOverallLast3Home ).collect(Collectors.averagingDouble(p -> p.doubleValue()));
			
			
			System.out.println(matchup.getVisitorName().toLowerCase() + "(" + visitorPts.intValue() + ") @ "+ matchup.getHomeName().toUpperCase() + "(" + homePts.intValue() + ")");
			System.out.println(getHeader(visitingTeam, homeTeam, 35));
			System.out.println("Away (" + formatTwoDigitNumber(gamesPlayedHeadToHead) + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredHeadToHeadSituationalVisitor)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsScoredHeadToHeadSituationalHome)) + "]  |  Home (" + formatTwoDigitNumber(gamesPlayedHeadToHead) + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredHeadToHeadSituationalHome)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsScoredHeadToHeadSituationalVisitor)) + "]");
			
			System.out.println("--- Overall -----------------------------------------------------------");
			System.out.println("Home (" + formatTwoDigitNumber(homeGamesOverallVisitor) + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredHomeOverallVisitor)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedHomeOverallVisitor)) + "]  |  Home (" + formatTwoDigitNumber(homeGamesOverallHome) + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredHomeOverallHome)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedHomeOverallHome)) + "]");
			System.out.println("Away (" + formatTwoDigitNumber(awayGamesOverallVisitor) + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredAwayOverallVisitor)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedAwayOverallVisitor)) + "]  |  Away (" + formatTwoDigitNumber(awayGamesOverallHome) + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredAwayOverallHome)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedAwayOverallHome)) + "]");
			
			System.out.println("--- Last 10 -----------------------------------------------------------");
			System.out.println("Home (" + 10 + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredHomeOverallLast10Visitor)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedHomeOverallLast10Visitor)) + "]  |  Home (" + 10 + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredHomeOverallLast10Home)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedHomeOverallLast10Home)) + "]");
			System.out.println("Away (" + 10 + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredAwayOverallLast10Visitor)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedAwayOverallLast10Visitor)) + "]  |  Away (" + 10 + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredAwayOverallLast10Home)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedAwayOverallLast10Home)) + "]");
	
			System.out.println("--- Last 3 ------------------------------------------------------------");
			System.out.println("Home  (" + 3 + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredHomeOverallLast3Visitor)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedHomeOverallLast3Visitor)) + "]  |  Home  (" + 3 + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredHomeOverallLast3Home)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedHomeOverallLast3Home)) + "]");
			System.out.println("Away  (" + 3 + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredAwayOverallLast3Visitor)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedAwayOverallLast3Visitor)) + "]  |  Away  (" + 3 + ") [Scored :" + formatTwoDigitNumber(Math.round(avgPtsScoredAwayOverallLast3Home)) + " Allowed:" + formatTwoDigitNumber(Math.round(avgPtsAllowedAwayOverallLast3Home)) + "]");
			System.out.println("-----------------------------------------------------------------------\n");
			
		}
	}


	private String getHeader(String team, String opponent, int max) {
		long left = Math.round((max-team.length()) / 2);
		long right = Math.round((max-opponent.length()) / 2);
		long center = 68 - left - right - team.length() - opponent.length();
		StringBuilder sb = new StringBuilder();
		sb.append(mark(left));
		sb.append(" ");
		sb.append(team.toLowerCase());
		sb.append(" ");
		sb.append(mark(center-1));
		sb.append(" ");
		sb.append(opponent.toUpperCase());
		sb.append(" ");
		sb.append(mark(right));
		
		return sb.toString();
	}

	private String mark(long length) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<length; i++) {
			sb.append("-");
		}
		return sb.toString();
	}

	private String formatTwoDigitNumber(long pts) {
		return pts < 10 ? (" " + pts) : String.valueOf(pts);
	}

	
	
	
}
