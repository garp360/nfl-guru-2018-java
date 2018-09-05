package hb.nflguru.statistics.model;

public class Game
{
	private String day;
	private String gameType;
	private Integer gsis;
	private String homeAbbr;
	private String homeName;
	private Integer homeScore;
	private Long id;
	private Integer season;
	private Float spread;
	private String time;
	private String visitorAbbr;
	private String visitorName;
	private Integer visitorScore;
	private Integer week;
	
	
	public String getDay()
	{
		return day;
	}
	public void setDay(String day)
	{
		this.day = day;
	}
	public String getGameType()
	{
		return gameType;
	}
	public void setGameType(String gameType)
	{
		this.gameType = gameType;
	}
	public Integer getGsis()
	{
		return gsis;
	}
	public void setGsis(Integer gsis)
	{
		this.gsis = gsis;
	}
	public String getHomeAbbr()
	{
		return homeAbbr;
	}
	public void setHomeAbbr(String homeAbbr)
	{
		this.homeAbbr = homeAbbr;
	}
	public String getHomeName()
	{
		return homeName;
	}
	public void setHomeName(String homeName)
	{
		this.homeName = homeName;
	}
	public Integer getHomeScore()
	{
		return homeScore;
	}
	public void setHomeScore(Integer homeScore)
	{
		this.homeScore = homeScore;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Integer getSeason()
	{
		return season;
	}
	public void setSeason(Integer season)
	{
		this.season = season;
	}
	public Float getSpread()
	{
		return spread;
	}
	public void setSpread(Float spread)
	{
		this.spread = spread;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public String getVisitorAbbr()
	{
		return visitorAbbr;
	}
	public void setVisitorAbbr(String visitorAbbr)
	{
		this.visitorAbbr = visitorAbbr;
	}
	public String getVisitorName()
	{
		return visitorName;
	}
	public void setVisitorName(String visitorName)
	{
		this.visitorName = visitorName;
	}
	public Integer getVisitorScore()
	{
		return visitorScore;
	}
	public void setVisitorScore(Integer visitorScore)
	{
		this.visitorScore = visitorScore;
	}
	public Integer getWeek()
	{
		return week;
	}
	public void setWeek(Integer week)
	{
		this.week = week;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Game [day=").append(day).append(", gameType=").append(gameType).append(", gsis=").append(gsis).append(", homeAbbr=").append(homeAbbr).append(", homeName=").append(homeName).append(", homeScore=").append(homeScore).append(", id=").append(id).append(", season=").append(season).append(", spread=").append(spread).append(", time=").append(time).append(", visitorAbbr=")
				.append(visitorAbbr).append(", visitorName=").append(visitorName).append(", visitorScore=").append(visitorScore).append(", week=").append(week).append("]");
		return builder.toString();
	}
	
	
}
