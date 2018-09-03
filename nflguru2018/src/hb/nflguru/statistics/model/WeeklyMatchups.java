package hb.nflguru.statistics.model;

import java.util.List;

public class WeeklyMatchups
{
	private int season;
	private int week;
	private List<Matchup> matchups;
	
	public WeeklyMatchups(int season, int week, List<Matchup> matchups)
	{
		this.season = season;
		this.week = week;
		this.matchups = matchups;
	}

	
}
