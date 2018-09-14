package hb.nflguru.statistics.model;

public class AdvancedPrediction extends SimplePrediction
{

	public AdvancedPrediction(Matchup matchup)
	{
		super(matchup);
		calculate();
	}

	protected void calculate()
	{
		super.calculate();
	}

	protected Integer getHomeScore()
	{
		Integer h2hScored = matchup.getHomeHead2HeadPtsScored();
		Integer sPtsScored3 = matchup.getHomeSituationalPtsScoredLast3();
		Integer oPtsScored5 = matchup.getHomeOverallPtsScoredLast5();
		Integer masseyScore = matchup.getMasseyPredictionHomePtsScored();

		Integer avgPtsScored = ((h2hScored + sPtsScored3 + oPtsScored5) / 4);

		Integer oppPtsAllowed3 = matchup.getAwaySituationalPtsAllowedLast3();
		Integer oppPtsAllowed5 = matchup.getAwayOverallPtsAllowedLast5();

		Integer avgPtsAllowed = ((oppPtsAllowed3 + oppPtsAllowed5) / 2);

		return Math.abs(((((avgPtsScored + masseyScore)/2) + avgPtsAllowed) / 2));
	}

	protected Integer getAwayScore()
	{
		Integer h2hScored = matchup.getAwayHead2HeadPtsScored();
		Integer sPtsScored3 = matchup.getAwaySituationalPtsScoredLast3();
		Integer oPtsScored5 = matchup.getAwayOverallPtsScoredLast5();
		Integer masseyScore = matchup.getMasseyPredictionAwayPtsScored();

		Integer avgPtsScored = ((h2hScored + sPtsScored3 + oPtsScored5 + masseyScore) / 4);

		Integer oppPtsAllowed3 = matchup.getHomeSituationalPtsAllowedLast3();
		Integer oppPtsAllowed5 = matchup.getHomeOverallPtsAllowedLast5();

		Integer avgPtsAllowed = ((oppPtsAllowed3 + oppPtsAllowed5) / 2);

		return Math.abs(((avgPtsScored + avgPtsAllowed) / 2));
	}
	
	
}
