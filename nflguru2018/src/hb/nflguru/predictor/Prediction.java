package hb.nflguru.predictor;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import hb.nflguru.statistics.model.Matchup;
import hb.nflguru.utils.NFLTeam;

public class Prediction
{
	private final Matchup matchup;

	private Integer predictedHomeScore;

	private Integer predictedAwayScore;

	private NFLTeam atsWinner;

	private BigDecimal atsMargin;

	public Prediction(Matchup matchup)
	{
		this.matchup = matchup;
		calculate();
	}

	public Matchup getMatchup()
	{
		return matchup;
	}

	public Integer getPredictedHomeScore()
	{
		return predictedHomeScore;
	}

	public Integer getPredictedAwayScore()
	{
		return predictedAwayScore;
	}

	public NFLTeam getAtsWinner()
	{
		return atsWinner;
	}

	public BigDecimal getAtsMargin()
	{
		return atsMargin;
	}

	private void calculate()
	{
		this.predictedHomeScore = getHomeScore();
		this.predictedAwayScore = getAwayScore();

		if (matchup.getHomeSpread().compareTo(matchup.getAwaySpread()) <= 0)
		{
			// Home is FAV or PK
			BigDecimal score = new BigDecimal(this.predictedHomeScore);
			score = score.subtract(matchup.getHomeSpread().abs());
			atsWinner = score.compareTo(new BigDecimal(predictedAwayScore)) >= 0 ? matchup.getHome() : matchup.getAway();
		}
		else
		{
			// Away is FAV
			BigDecimal score = new BigDecimal(this.predictedAwayScore);
			score = score.subtract(matchup.getAwaySpread().abs());
			atsWinner = score.compareTo(new BigDecimal(predictedHomeScore)) >= 0 ? matchup.getAway() : matchup.getHome();
		}
		
		BigDecimal diff = new BigDecimal(predictedAwayScore - predictedHomeScore);
		if (predictedHomeScore >= predictedAwayScore)
		{
			diff = diff.negate();
			diff = diff.add(matchup.getHomeSpread()).abs();
		}
		else
		{
			diff = diff.subtract(matchup.getHomeSpread()).abs();
		}
		
		atsMargin = diff;
	}

	private Integer getHomeScore()
	{
		Integer h2hScored = matchup.getHomeHead2HeadPtsScored();
		Integer sPtsScored3 = matchup.getHomeSituationalPtsScoredLast3();
		Integer oPtsScored5 = matchup.getHomeOverallPtsScoredLast5();

		Integer avgPtsScored = ((h2hScored + sPtsScored3 + oPtsScored5) / 3);

		Integer oppPtsAllowed3 = matchup.getAwaySituationalPtsAllowedLast3();
		Integer oppPtsAllowed5 = matchup.getAwayOverallPtsAllowedLast5();

		Integer avgPtsAllowed = ((oppPtsAllowed3 + oppPtsAllowed5) / 2);

		return Math.abs(((avgPtsScored + avgPtsAllowed) / 2));
	}

	private Integer getAwayScore()
	{
		Integer h2hScored = matchup.getAwayHead2HeadPtsScored();
		Integer sPtsScored3 = matchup.getAwaySituationalPtsScoredLast3();
		Integer oPtsScored5 = matchup.getAwayOverallPtsScoredLast5();

		Integer avgPtsScored = ((h2hScored + sPtsScored3 + oPtsScored5) / 3);

		Integer oppPtsAllowed3 = matchup.getHomeSituationalPtsAllowedLast3();
		Integer oppPtsAllowed5 = matchup.getHomeOverallPtsAllowedLast5();

		Integer avgPtsAllowed = ((oppPtsAllowed3 + oppPtsAllowed5) / 2);

		return Math.abs(((avgPtsScored + avgPtsAllowed) / 2));
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		String away = StringUtils.rightPad(StringUtils.rightPad(matchup.getAway().name(), 3, " ") + "(" + predictedAwayScore + ")", 7, " ");
		String home = StringUtils.rightPad(StringUtils.rightPad(matchup.getHome().name(), 3, " ") + "(" + predictedHomeScore + ")", 7, " ");
		String homeSpread = StringUtils.leftPad(matchup.getHomeSpread().toString(), 5, " ");

		
		

		String winner = StringUtils.leftPad("Winner ATS: " + StringUtils.rightPad(atsWinner.name(), 3, " "), 17, " ");

		String diffAbs = StringUtils.leftPad(atsMargin.toString(), 6, " ");
		
		builder.append(away + " @ " + home + " " + homeSpread + winner + diffAbs);
		return builder.toString();
	}

}
