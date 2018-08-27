package hb.nflguru.statistics.model;

import hb.nflguru.statistics.DataLoader;

public class StatBundle
{
	private String team;
	private String opponent;
	private Integer ptsForVersesOpponentSeasonHome;
	private Integer ptsForVersesOpponentSeasonAway;
	private Integer ptsForVersesOpponentSeasonTotal;
	private Integer ptsForVersesOpponentOverallHome;
	private Integer ptsForVersesOpponentOverallAway;
	private Integer ptsForVersesOpponentOverallTotal;
	
	
	public StatBundle(String team, String opponent)
	{
		this.team = team;
		this.opponent = opponent;
	}
	
	public void setPtsForVersesOpponentForCurrentSeason( Integer home, Integer away, Integer total ) {
		this.ptsForVersesOpponentSeasonHome = home == null ? -1 : home;
		this.ptsForVersesOpponentSeasonAway = away == null ? -1 : away;
		this.ptsForVersesOpponentSeasonTotal = total == null ? -1 : total;
	}
	
	public void setPtsForVersesOpponentForOverall( Integer home, Integer away, Integer total ) {
		this.ptsForVersesOpponentOverallHome = home == null ? -1 : home;
		this.ptsForVersesOpponentOverallAway = away == null ? -1 : away;
		this.ptsForVersesOpponentOverallTotal = total == null ? -1 : total;
	}

	public String getTeam()
	{
		return team;
	}

	public void setTeam(String team)
	{
		this.team = team;
	}

	public String getOpponent()
	{
		return opponent;
	}

	public void setOpponent(String opponent)
	{
		this.opponent = opponent;
	}

	public Integer getPtsForVersesOpponentSeasonHome()
	{
		return ptsForVersesOpponentSeasonHome;
	}

	public void setPtsForVersesOpponentSeasonHome(Integer ptsForVersesOpponentSeasonHome)
	{
		this.ptsForVersesOpponentSeasonHome = ptsForVersesOpponentSeasonHome;
	}

	public Integer getPtsForVersesOpponentSeasonAway()
	{
		return ptsForVersesOpponentSeasonAway;
	}

	public void setPtsForVersesOpponentSeasonAway(Integer ptsForVersesOpponentSeasonAway)
	{
		this.ptsForVersesOpponentSeasonAway = ptsForVersesOpponentSeasonAway;
	}

	public Integer getPtsForVersesOpponentSeasonTotal()
	{
		return ptsForVersesOpponentSeasonTotal;
	}

	public void setPtsForVersesOpponentSeasonTotal(Integer ptsForVersesOpponentSeasonTotal)
	{
		this.ptsForVersesOpponentSeasonTotal = ptsForVersesOpponentSeasonTotal;
	}

	public Integer getPtsForVersesOpponentOverallHome()
	{
		return ptsForVersesOpponentOverallHome;
	}

	public void setPtsForVersesOpponentOverallHome(Integer ptsForVersesOpponentOverallHome)
	{
		this.ptsForVersesOpponentOverallHome = ptsForVersesOpponentOverallHome;
	}

	public Integer getPtsForVersesOpponentOverallAway()
	{
		return ptsForVersesOpponentOverallAway;
	}

	public void setPtsForVersesOpponentOverallAway(Integer ptsForVersesOpponentOverallAway)
	{
		this.ptsForVersesOpponentOverallAway = ptsForVersesOpponentOverallAway;
	}

	public Integer getPtsForVersesOpponentOverallTotal()
	{
		return ptsForVersesOpponentOverallTotal;
	}

	public void setPtsForVersesOpponentOverallTotal(Integer ptsForVersesOpponentOverallTotal)
	{
		this.ptsForVersesOpponentOverallTotal = ptsForVersesOpponentOverallTotal;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder
			.append("-------------------------------------------------------------------\n")
			.append(team.toUpperCase()).append(" -vs- ").append(opponent.toUpperCase())
			.append("\n        : ")
				.append("HOME").append(" : ").append("AWAY").append(" : ").append("TOTAL");
		if(DataLoader.CURRENT_WEEK >= 6) {
			builder.append("\n SEASON : ")
				.append(spaces(ptsForVersesOpponentSeasonHome < 10 ? 2 : 1)).append(ptsForVersesOpponentSeasonHome).append("  : ").append(spaces(ptsForVersesOpponentSeasonAway < 10 ? 2 : 1)).append(ptsForVersesOpponentSeasonAway).append("  : ").append(spaces(ptsForVersesOpponentSeasonTotal < 10 ? 2 : 1)).append(ptsForVersesOpponentSeasonTotal);
		}
		builder.append("\nOVERALL : ")
				.append(spaces(ptsForVersesOpponentOverallHome < 10 ? 2 : 1)).append(ptsForVersesOpponentOverallHome).append("  : ").append(spaces(ptsForVersesOpponentOverallAway < 10 ? 2 : 1)).append(ptsForVersesOpponentOverallAway).append("  : ").append(spaces(ptsForVersesOpponentOverallTotal < 10 ? 2 : 1)).append(ptsForVersesOpponentOverallTotal);
		return builder.toString();
	}

	private String spaces(int i)
	{
		StringBuilder sb = new StringBuilder();
		for(int w = 0; w < i; w++) {
			sb.append(" ");
		}
		return sb.toString();
	}
	
	
}
