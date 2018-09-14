package hb.nflguru.importer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import hb.nflguru.statistics.model.Matchup;

public class HistoricalDataImporter extends BaseImporter
{
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	

	public HistoricalDataImporter()
	{
		initialize();
	}

	public boolean loadData(String filepath)
	{
		boolean complete = false;
		try
		{
			List<Matchup> historicalMatchups = getHistoricalMatchups();
			String json = gson.toJson(historicalMatchups);
			json = "{ \"data\": " + json + " }";
			System.out.println(json);

			
			write(filepath, json);
			complete = true;
		}
		catch (Exception e)
		{
			complete = false;
		}
		return complete;
	}

	

	private void write(String historicalDataFilePath, String data)
	{
		try
		{
			FileWriter writer = new FileWriter(historicalDataFilePath);
			writer.write(data);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
