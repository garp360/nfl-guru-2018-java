package hb.nflguru.statistics;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import hb.nflguru.statistics.model.Game;

public class DataLoader
{
	public static final int CURRENT_WEEK = 0;
	private static final int TOTAL_WEEKS = 17;
	public static final int MAX_SEASON = 2018;
	private static final int MIN_SEASON = 2008;
	private JSONObject data;
	private Gson gson = new Gson();
	private List<Game> games = new ArrayList<>();

	public DataLoader(String filepath)
	{
		try
		{
			String json = new String(Files.readAllBytes(Paths.get(filepath)));
			data = new JSONObject(json);
			games = getAllGames();
		}
		catch ( Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public final List<Game> getAllGames() {
		List<Game> games = new ArrayList<>();
		
		for(int season=MIN_SEASON; season<MAX_SEASON; season++) {
			games.addAll(getGames(season));
		}
		
		return games;
	}
	
	public final List<Game> getGames(int season) {
		List<Game> games = new ArrayList<>();
		
		for(int week=1; week<=TOTAL_WEEKS; week++) {
			games.addAll(getGames(season, week));
		}
		
		return games;
	}
	
	public final List<Game> getGames(int season, int week) {
		List<Game> games = new ArrayList<>();
		JSONArray arr = data.getJSONArray(String.valueOf(season)).getJSONArray(week);
		
		for(int i=0; i<arr.length(); i++) {
			games.add(gson.fromJson(arr.getJSONObject(i).toString(), Game.class));
		}
		
		return games;
	}

	public List<Game> getGames()
	{
		return games;
	}
	
}
