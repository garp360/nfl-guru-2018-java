package hb.nflguru.statistics.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import nflguru2018.NFLTeam;

public class Matchup
{
	private NFLTeam home;
	
	private NFLTeam away;
	
	private LocalDate gameDay;

	private LocalTime gameTime;

	private Integer homeScore;

	private Integer awayScore;
	
	private Integer game;
	
	private Integer week;
	
	private Integer season;
	
	private BigDecimal homeSpread;
	
	private BigDecimal awaySpread;
	
	public NFLTeam getHome()
	{
		return home;
	}
	
	public void setHome(NFLTeam home)
	{
		this.home = home;
	}
	
	public NFLTeam getAway()
	{
		return away;
	}
	
	public void setAway(NFLTeam away)
	{
		this.away = away;
	}

	public LocalDate getGameDay()
	{
		return gameDay;
	}

	public void setGameDay(LocalDate gameDay)
	{
		this.gameDay = gameDay;
	}

	public LocalTime getGameTime()
	{
		return gameTime;
	}

	public void setGameTime(LocalTime gameTime)
	{
		this.gameTime = gameTime;
	}

	public Integer getHomeScore()
	{
		return homeScore;
	}

	public void setHomeScore(Integer homeScore)
	{
		this.homeScore = homeScore;
	}

	public Integer getAwayScore()
	{
		return awayScore;
	}

	public void setAwayScore(Integer awayScore)
	{
		this.awayScore = awayScore;
	}
	
	public Integer getGame()
	{
		return game;
	}

	public void setGame(Integer game)
	{
		this.game = game;
	}

	public Integer getWeek()
	{
		return week;
	}

	public void setWeek(Integer week)
	{
		this.week = week;
	}

	public Integer getSeason()
	{
		return season;
	}

	public void setSeason(Integer season)
	{
		this.season = season;
	}

	public BigDecimal getHomeSpread()
	{
		return homeSpread;
	}

	public void setHomeSpread(BigDecimal homeSpread)
	{
		this.homeSpread = homeSpread;
	}

	public BigDecimal getAwaySpread()
	{
		return awaySpread;
	}

	public void setAwaySpread(BigDecimal awaySpread)
	{
		this.awaySpread = awaySpread;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Matchup [home=").append(home).append(", away=").append(away).append(", gameDay=").append(gameDay).append(", gameTime=").append(gameTime).append(", homeScore=").append(homeScore).append(", awayScore=").append(awayScore).append(", game=").append(game).append(", week=").append(week).append(", season=").append(season).append(", homeSpread=").append(homeSpread)
				.append(", awaySpread=").append(awaySpread).append("]");
		return builder.toString();
	}


	

}
