package hb.nflguru.importer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import hb.nflguru.statistics.model.Matchup;

public class PastResultsImporter extends BaseImporter
{
	
	//http://www.nfl.com/ajax/scorestrip?season=2011&seasonType=REG&week=2
	

	public PastResultsImporter()
	{
		initialize();
	}
	
	public void importPastResults()
	{
		List<Matchup> matchups = getMatchups();

		String output = gson.toJson(matchups);
		System.out.println(output);
	}
	
	public List<Matchup> importPastResults( String outputFilePath )
	{
		List<Matchup> matchups = getMatchups();
		String output = gson.toJson(matchups);
		
		try
		{
			Files.write(Paths.get(outputFilePath), output.getBytes());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return matchups;
	}

	

	
}
