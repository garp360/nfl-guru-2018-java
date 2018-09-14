package hb.nflguru.app.prediction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hb.nflguru.importer.CurrentSeasonResultsImporter;
import hb.nflguru.statistics.model.AdvancedPrediction;
import hb.nflguru.statistics.model.Matchup;
import hb.nflguru.statistics.model.SimplePrediction;
import hb.nflguru.utils.ScheduleUtils;

public class AdvancedPredictor extends SimplePredictor
{
	private static final CurrentSeasonResultsImporter importer = new CurrentSeasonResultsImporter();

	public AdvancedPredictor() {  }

	public void predict()
	{
		List<SimplePrediction> predictions = new ArrayList<SimplePrediction>();
		String predictionPath = getCurrentFilePath();

		List<Matchup> matchups = getMatchups();
		
		for (Matchup matchup : matchups)
		{
			AdvancedPrediction prediction = new AdvancedPrediction(matchup);
			predictions.add(prediction);
		}

		List<SimplePrediction> sortedPredictions = predictions.stream().sorted((s1, s2) -> {
			return s2.getAtsMargin().compareTo(s1.getAtsMargin());
		}).collect(Collectors.toList());

		try
		{
			FileWriter writer = new FileWriter(predictionPath);
			for (SimplePrediction prediction : sortedPredictions)
			{
				writer.write(prediction.toString() + "\n");
			}
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private List<Matchup> getMatchups()
	{
		String outputFilePath = "./data/nflGuruDataCurrentSeasonWeek%s-ADVANCED.json";
		String filepath = String.format(outputFilePath, ScheduleUtils.getSuffix());
		return importer.importResults(filepath);
	}
	
	public String getCurrentFilePath()
	{
		String predictionsFilePath = "./data/nflGuruPredictions%s-ADVANCED.txt";
		String predictionPath = String.format(predictionsFilePath, ScheduleUtils.getSuffix());
		return predictionPath;
	}
}
