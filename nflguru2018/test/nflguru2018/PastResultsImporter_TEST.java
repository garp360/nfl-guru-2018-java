package nflguru2018;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import hb.nflguru.importer.CurrentSeasonResultsImporter;
import hb.nflguru.importer.CurrentSpreadsImporter;
import hb.nflguru.importer.DataLoader;
import hb.nflguru.importer.PastResultsImporter;
import hb.nflguru.statistics.model.Matchup;
import hb.nflguru.utils.ScheduleUtils;

public class PastResultsImporter_TEST
{

	@Test
	public void testPastResultsImporter()
	{
		PastResultsImporter importer = new PastResultsImporter();

		String outputFilePath = "/Users/garthpidcock/Downloads/nflGuruData-%s.json";
		LocalDate now = LocalDate.now();
		String marker = now.format(DateTimeFormatter.BASIC_ISO_DATE);
		String filepath = String.format(outputFilePath, marker);

		// List<Matchup> matchups = importer.importPastResults(filepath);

	}
	
	@Test
	public void testCurrentSeasonImporter()
	{
		CurrentSeasonResultsImporter importer = new CurrentSeasonResultsImporter();
		
		String outputFilePath = "/Users/garthpidcock/git/nflguru2018/nflguru2018/data/nflGuruDataCurrentSeasonWeek%s.json";
		
		String marker = ScheduleUtils.getCurrentSeason() + "" + StringUtils.leftPad(String.valueOf(ScheduleUtils.getCurrentWeek()), 2, "0");
		String filepath = String.format(outputFilePath, marker);
		
		List<Matchup> matchups = importer.importResults(filepath);
	}

	@Test
	public void loadArchivedData()
	{
		DataLoader dataLoader = new DataLoader("/Users/garthpidcock/git/nflguru2018/nflguru2018/data/nflGuruDataArchive.json");
		
		String home = "NO";
		String away = "TB";

		System.out.println("headToHeadGamesPlayed=" + dataLoader.getSituationalHeadToHeadGamesPlayed(home, away));
		
		System.out.println(home + " homeAvgPtsScoredHead2Head=" + dataLoader.getSituationalHeadToHeadPtsScoredHome(home, away));
		System.out.println(home + " homeAvgPtsScoredLast10=" + dataLoader.getSituationalPtsScoredHome(home, 10L));
		System.out.println(home + " homeAvgPtsScoredLast5=" + dataLoader.getSituationalPtsScoredHome(home, 5L));
		System.out.println(home + " homeAvgPtsScoredLast3=" + dataLoader.getSituationalPtsScoredHome(home, 3L));
		System.out.println(home + " overallAvgPtsScoredLast10=" + dataLoader.getOverallPtsScored(home, 10L));
		System.out.println(home + " overallAvgPtsScoredLast5=" + dataLoader.getOverallPtsScored(home, 5L));
		System.out.println(home + " overallAvgPtsScoredLast3=" + dataLoader.getOverallPtsScored(home, 3L));
		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.println(away + " awayAvgPtsScoredHead2Head=" + dataLoader.getSituationalHeadToHeadPtsScoredAway(home, away));
		System.out.println(away + " awayAvgPtsScoredLast10=" + dataLoader.getSituationalPtsScoredAway(away, 10L));
		System.out.println(away + " awayAvgPtsScoredLast5=" + dataLoader.getSituationalPtsScoredAway(away, 5L));
		System.out.println(away + " awayAvgPtsScoredLast3=" + dataLoader.getSituationalPtsScoredAway(away, 3L));
		System.out.println(away + " overallAvgPtsScoredLast10=" + dataLoader.getOverallPtsScored(away, 10L));
		System.out.println(away + " overallAvgPtsScoredLast5=" + dataLoader.getOverallPtsScored(away, 5L));
		System.out.println(away + " overallAvgPtsScoredLast3=" + dataLoader.getOverallPtsScored(away, 3L));


	}

	
	@Test
	public void getCurrentWeek()
	{
		System.out.println(ScheduleUtils.getCurrentSeason());
		System.out.println(ScheduleUtils.getCurrentWeek());
	}

	@Test
	public void getCurrentSpreads()
	{
		CurrentSpreadsImporter importer = new CurrentSpreadsImporter();
		Map<String, BigDecimal> spreads = importer.getSpreads();
		Set<String> keySet = spreads.keySet();
		for (String team : keySet)
		{
			BigDecimal spread = spreads.get(team);
			System.out.println(team + " " + spread);
		}
	}
}