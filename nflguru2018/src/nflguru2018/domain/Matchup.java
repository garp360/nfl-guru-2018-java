package nflguru2018.domain;

public class Matchup
{
	private String id;
	private String gsis;
	private Integer season;
	private Integer week;
	private String day;
	private String time;
	private Boolean overtime;
	private String homeAbbr;
	private String homeName;
	private Integer homeScore;
	private String visitorAbbr;
	private String visitorName;
	private Integer visitorScore;
	private String gameType;
	private Float spread;
	private String predictedWinner;	
	private Integer predictedHomeScore;
	private Integer predictedVisitorScore;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getGsis()
	{
		return gsis;
	}
	public void setGsis(String gsis)
	{
		this.gsis = gsis;
	}
	public Integer getSeason()
	{
		return season;
	}
	public void setSeason(Integer season)
	{
		this.season = season;
	}
	public Integer getWeek()
	{
		return week;
	}
	public void setWeek(Integer week)
	{
		this.week = week;
	}
	public String getDay()
	{
		return day;
	}
	public void setDay(String day)
	{
		this.day = day;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public Boolean getOvertime()
	{
		return overtime;
	}
	public void setOvertime(Boolean overtime)
	{
		this.overtime = overtime;
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
	public String getGameType()
	{
		return gameType;
	}
	public void setGameType(String gameType)
	{
		this.gameType = gameType;
	}
	public Float getSpread()
	{
		return spread;
	}
	public void setSpread(Float spread)
	{
		this.spread = spread;
	}
	public String getPredictedWinner()
	{
		return predictedWinner;
	}
	public void setPredictedWinner(String predictedWinner)
	{
		this.predictedWinner = predictedWinner;
	}
	public Integer getPredictedHomeScore()
	{
		return predictedHomeScore;
	}
	public void setPredictedHomeScore(Integer predictedHomeScore)
	{
		this.predictedHomeScore = predictedHomeScore;
	}
	public Integer getPredictedVisitorScore()
	{
		return predictedVisitorScore;
	}
	public void setPredictedVisitorScore(Integer predictedVisitorScore)
	{
		this.predictedVisitorScore = predictedVisitorScore;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Matchup [id=").append(id).append(", gsis=").append(gsis).append(", season=").append(season).append(", week=").append(week).append(", day=").append(day).append(", time=").append(time).append(", overtime=").append(overtime).append(", homeAbbr=").append(homeAbbr).append(", homeName=").append(homeName).append(", homeScore=").append(homeScore).append(", visitorAbbr=")
				.append(visitorAbbr).append(", visitorName=").append(visitorName).append(", visitorScore=").append(visitorScore).append(", gameType=").append(gameType).append(", spread=").append(spread).append(", predictedWinner=").append(predictedWinner).append(", predictedHomeScore=").append(predictedHomeScore).append(", predictedVisitorScore=").append(predictedVisitorScore).append("]");
		return builder.toString();
	}
	
	
	
}
