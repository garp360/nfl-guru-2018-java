package hb.nflguru.statistics.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import hb.nflguru.utils.NFLTeam;

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
	
	private Integer head2HeadGamesPlayed;
	
	private Integer homeHead2HeadPtsScored;
	private Integer awayHead2HeadPtsScored;
	
	private Integer homeSituationalPtsScoredLast10;
	private Integer homeSituationalPtsScoredLast5;
	private Integer homeSituationalPtsScoredLast3;

	private Integer awaySituationalPtsScoredLast10;
	private Integer awaySituationalPtsScoredLast5;
	private Integer awaySituationalPtsScoredLast3;

	private Integer homeOverallPtsScoredLast10;
	private Integer homeOverallPtsScoredLast5;
	private Integer homeOverallPtsScoredLast3;
	
	private Integer awayOverallPtsScoredLast10;
	private Integer awayOverallPtsScoredLast5;
	private Integer awayOverallPtsScoredLast3;
	
	
	private Integer homeSituationalPtsAllowedLast10;
	private Integer homeSituationalPtsAllowedLast5;
	private Integer homeSituationalPtsAllowedLast3;

	private Integer awaySituationalPtsAllowedLast10;
	private Integer awaySituationalPtsAllowedLast5;
	private Integer awaySituationalPtsAllowedLast3;

	private Integer homeOverallPtsAllowedLast10;
	private Integer homeOverallPtsAllowedLast5;
	private Integer homeOverallPtsAllowedLast3;
	
	private Integer awayOverallPtsAllowedLast10;
	private Integer awayOverallPtsAllowedLast5;
	private Integer awayOverallPtsAllowedLast3;
	
	
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

	public Integer getHead2HeadGamesPlayed()
	{
		return head2HeadGamesPlayed;
	}

	public void setHead2HeadGamesPlayed(Integer head2HeadGamesPlayed)
	{
		this.head2HeadGamesPlayed = head2HeadGamesPlayed;
	}

	public Integer getHomeHead2HeadPtsScored()
	{
		return homeHead2HeadPtsScored;
	}

	public void setHomeHead2HeadPtsScored(Integer homeHead2HeadPtsScored)
	{
		this.homeHead2HeadPtsScored = homeHead2HeadPtsScored;
	}

	public Integer getAwayHead2HeadPtsScored()
	{
		return awayHead2HeadPtsScored;
	}

	public void setAwayHead2HeadPtsScored(Integer awayHead2HeadPtsScored)
	{
		this.awayHead2HeadPtsScored = awayHead2HeadPtsScored;
	}

	public Integer getHomeSituationalPtsScoredLast10()
	{
		return homeSituationalPtsScoredLast10;
	}

	public void setHomeSituationalPtsScoredLast10(Integer homeSituationalPtsScoredLast10)
	{
		this.homeSituationalPtsScoredLast10 = homeSituationalPtsScoredLast10;
	}

	public Integer getHomeSituationalPtsScoredLast5()
	{
		return homeSituationalPtsScoredLast5;
	}

	public void setHomeSituationalPtsScoredLast5(Integer homeSituationalPtsScoredLast5)
	{
		this.homeSituationalPtsScoredLast5 = homeSituationalPtsScoredLast5;
	}

	public Integer getHomeSituationalPtsScoredLast3()
	{
		return homeSituationalPtsScoredLast3;
	}

	public void setHomeSituationalPtsScoredLast3(Integer homeSituationalPtsScoredLast3)
	{
		this.homeSituationalPtsScoredLast3 = homeSituationalPtsScoredLast3;
	}

	public Integer getAwaySituationalPtsScoredLast10()
	{
		return awaySituationalPtsScoredLast10;
	}

	public void setAwaySituationalPtsScoredLast10(Integer awaySituationalPtsScoredLast10)
	{
		this.awaySituationalPtsScoredLast10 = awaySituationalPtsScoredLast10;
	}

	public Integer getAwaySituationalPtsScoredLast5()
	{
		return awaySituationalPtsScoredLast5;
	}

	public void setAwaySituationalPtsScoredLast5(Integer awaySituationalPtsScoredLast5)
	{
		this.awaySituationalPtsScoredLast5 = awaySituationalPtsScoredLast5;
	}

	public Integer getAwaySituationalPtsScoredLast3()
	{
		return awaySituationalPtsScoredLast3;
	}

	public void setAwaySituationalPtsScoredLast3(Integer awaySituationalPtsScoredLast3)
	{
		this.awaySituationalPtsScoredLast3 = awaySituationalPtsScoredLast3;
	}

	public Integer getHomeOverallPtsScoredLast10()
	{
		return homeOverallPtsScoredLast10;
	}

	public void setHomeOverallPtsScoredLast10(Integer homeOverallPtsScoredLast10)
	{
		this.homeOverallPtsScoredLast10 = homeOverallPtsScoredLast10;
	}

	public Integer getHomeOverallPtsScoredLast5()
	{
		return homeOverallPtsScoredLast5;
	}

	public void setHomeOverallPtsScoredLast5(Integer homeOverallPtsScoredLast5)
	{
		this.homeOverallPtsScoredLast5 = homeOverallPtsScoredLast5;
	}

	public Integer getHomeOverallPtsScoredLast3()
	{
		return homeOverallPtsScoredLast3;
	}

	public void setHomeOverallPtsScoredLast3(Integer homeOverallPtsScoredLast3)
	{
		this.homeOverallPtsScoredLast3 = homeOverallPtsScoredLast3;
	}

	public Integer getAwayOverallPtsScoredLast10()
	{
		return awayOverallPtsScoredLast10;
	}

	public void setAwayOverallPtsScoredLast10(Integer awayOverallPtsScoredLast10)
	{
		this.awayOverallPtsScoredLast10 = awayOverallPtsScoredLast10;
	}

	public Integer getAwayOverallPtsScoredLast5()
	{
		return awayOverallPtsScoredLast5;
	}

	public void setAwayOverallPtsScoredLast5(Integer awayOverallPtsScoredLast5)
	{
		this.awayOverallPtsScoredLast5 = awayOverallPtsScoredLast5;
	}

	public Integer getAwayOverallPtsScoredLast3()
	{
		return awayOverallPtsScoredLast3;
	}

	public void setAwayOverallPtsScoredLast3(Integer awayOverallPtsScoredLast3)
	{
		this.awayOverallPtsScoredLast3 = awayOverallPtsScoredLast3;
	}

	public Integer getHomeSituationalPtsAllowedLast10()
	{
		return homeSituationalPtsAllowedLast10;
	}

	public void setHomeSituationalPtsAllowedLast10(Integer homeSituationalPtsAllowedLast10)
	{
		this.homeSituationalPtsAllowedLast10 = homeSituationalPtsAllowedLast10;
	}

	public Integer getHomeSituationalPtsAllowedLast5()
	{
		return homeSituationalPtsAllowedLast5;
	}

	public void setHomeSituationalPtsAllowedLast5(Integer homeSituationalPtsAllowedLast5)
	{
		this.homeSituationalPtsAllowedLast5 = homeSituationalPtsAllowedLast5;
	}

	public Integer getHomeSituationalPtsAllowedLast3()
	{
		return homeSituationalPtsAllowedLast3;
	}

	public void setHomeSituationalPtsAllowedLast3(Integer homeSituationalPtsAllowedLast3)
	{
		this.homeSituationalPtsAllowedLast3 = homeSituationalPtsAllowedLast3;
	}

	public Integer getAwaySituationalPtsAllowedLast10()
	{
		return awaySituationalPtsAllowedLast10;
	}

	public void setAwaySituationalPtsAllowedLast10(Integer awaySituationalPtsAllowedLast10)
	{
		this.awaySituationalPtsAllowedLast10 = awaySituationalPtsAllowedLast10;
	}

	public Integer getAwaySituationalPtsAllowedLast5()
	{
		return awaySituationalPtsAllowedLast5;
	}

	public void setAwaySituationalPtsAllowedLast5(Integer awaySituationalPtsAllowedLast5)
	{
		this.awaySituationalPtsAllowedLast5 = awaySituationalPtsAllowedLast5;
	}

	public Integer getAwaySituationalPtsAllowedLast3()
	{
		return awaySituationalPtsAllowedLast3;
	}

	public void setAwaySituationalPtsAllowedLast3(Integer awaySituationalPtsAllowedLast3)
	{
		this.awaySituationalPtsAllowedLast3 = awaySituationalPtsAllowedLast3;
	}

	public Integer getHomeOverallPtsAllowedLast10()
	{
		return homeOverallPtsAllowedLast10;
	}

	public void setHomeOverallPtsAllowedLast10(Integer homeOverallPtsAllowedLast10)
	{
		this.homeOverallPtsAllowedLast10 = homeOverallPtsAllowedLast10;
	}

	public Integer getHomeOverallPtsAllowedLast5()
	{
		return homeOverallPtsAllowedLast5;
	}

	public void setHomeOverallPtsAllowedLast5(Integer homeOverallPtsAllowedLast5)
	{
		this.homeOverallPtsAllowedLast5 = homeOverallPtsAllowedLast5;
	}

	public Integer getHomeOverallPtsAllowedLast3()
	{
		return homeOverallPtsAllowedLast3;
	}

	public void setHomeOverallPtsAllowedLast3(Integer homeOverallPtsAllowedLast3)
	{
		this.homeOverallPtsAllowedLast3 = homeOverallPtsAllowedLast3;
	}

	public Integer getAwayOverallPtsAllowedLast10()
	{
		return awayOverallPtsAllowedLast10;
	}

	public void setAwayOverallPtsAllowedLast10(Integer awayOverallPtsAllowedLast10)
	{
		this.awayOverallPtsAllowedLast10 = awayOverallPtsAllowedLast10;
	}

	public Integer getAwayOverallPtsAllowedLast5()
	{
		return awayOverallPtsAllowedLast5;
	}

	public void setAwayOverallPtsAllowedLast5(Integer awayOverallPtsAllowedLast5)
	{
		this.awayOverallPtsAllowedLast5 = awayOverallPtsAllowedLast5;
	}

	public Integer getAwayOverallPtsAllowedLast3()
	{
		return awayOverallPtsAllowedLast3;
	}

	public void setAwayOverallPtsAllowedLast3(Integer awayOverallPtsAllowedLast3)
	{
		this.awayOverallPtsAllowedLast3 = awayOverallPtsAllowedLast3;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Matchup [home=").append(home).append(", away=").append(away).append(", gameDay=").append(gameDay).append(", gameTime=").append(gameTime).append(", homeScore=").append(homeScore).append(", awayScore=").append(awayScore).append(", game=").append(game).append(", week=").append(week).append(", season=").append(season).append(", homeSpread=").append(homeSpread)
				.append(", awaySpread=").append(awaySpread).append(", head2HeadGamesPlayed=").append(head2HeadGamesPlayed).append(", homeHead2HeadPtsScored=").append(homeHead2HeadPtsScored).append(", awayHead2HeadPtsScored=").append(awayHead2HeadPtsScored).append(", homeSituationalPtsScoredLast10=").append(homeSituationalPtsScoredLast10).append(", homeSituationalPtsScoredLast5=")
				.append(homeSituationalPtsScoredLast5).append(", homeSituationalPtsScoredLast3=").append(homeSituationalPtsScoredLast3).append(", awaySituationalPtsScoredLast10=").append(awaySituationalPtsScoredLast10).append(", awaySituationalPtsScoredLast5=").append(awaySituationalPtsScoredLast5).append(", awaySituationalPtsScoredLast3=").append(awaySituationalPtsScoredLast3)
				.append(", homeOverallPtsScoredLast10=").append(homeOverallPtsScoredLast10).append(", homeOverallPtsScoredLast5=").append(homeOverallPtsScoredLast5).append(", homeOverallPtsScoredLast3=").append(homeOverallPtsScoredLast3).append(", awayOverallPtsScoredLast10=").append(awayOverallPtsScoredLast10).append(", awayOverallPtsScoredLast5=").append(awayOverallPtsScoredLast5)
				.append(", awayOverallPtsScoredLast3=").append(awayOverallPtsScoredLast3).append(", homeSituationalPtsAllowedLast10=").append(homeSituationalPtsAllowedLast10).append(", homeSituationalPtsAllowedLast5=").append(homeSituationalPtsAllowedLast5).append(", homeSituationalPtsAllowedLast3=").append(homeSituationalPtsAllowedLast3).append(", awaySituationalPtsAllowedLast10=")
				.append(awaySituationalPtsAllowedLast10).append(", awaySituationalPtsAllowedLast5=").append(awaySituationalPtsAllowedLast5).append(", awaySituationalPtsAllowedLast3=").append(awaySituationalPtsAllowedLast3).append(", homeOverallPtsAllowedLast10=").append(homeOverallPtsAllowedLast10).append(", homeOverallPtsAllowedLast5=").append(homeOverallPtsAllowedLast5)
				.append(", homeOverallPtsAllowedLast3=").append(homeOverallPtsAllowedLast3).append(", awayOverallPtsAllowedLast10=").append(awayOverallPtsAllowedLast10).append(", awayOverallPtsAllowedLast5=").append(awayOverallPtsAllowedLast5).append(", awayOverallPtsAllowedLast3=").append(awayOverallPtsAllowedLast3).append("]");
		return builder.toString();
	}
}
