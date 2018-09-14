package hb.nflguru.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import hb.nflguru.statistics.model.Matchup;
import hb.nflguru.utils.ScheduleUtils;

public abstract class BaseImporter
{
	protected static final String NFL_SCORESTRIP_JSON_URL_TEMPLATE = "http://www.nfl.com/ajax/scorestrip?season=%s&seasonType=REG&week=%s";
	protected static final int WEEKS_IN_REGULAR_SEASON = 17;
	protected Gson gson;
	

	protected void initialize()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
		gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
		gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
		System.out.println("Initializing ...");
	}

	protected List<Matchup> getHistoricalMatchups()
	{
		List<Matchup> matchups = new ArrayList<>();

		int currentSeason = ScheduleUtils.getCurrentSeason();
		for (int season = 2008; season <= currentSeason; season++)
		{
			for (int week = 1; week <= WEEKS_IN_REGULAR_SEASON; week++)
			{
				
				String importUrl = getImportUrl(season, week);
				String gamesForWeekAsJson;
				try
				{
					gamesForWeekAsJson = getJson(importUrl);
					List<Matchup> matchupsFromJson = getMatchupsFromJson(gamesForWeekAsJson);
					matchups.addAll(matchupsFromJson);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
		return matchups;
	}
	
	protected List<Matchup> getMatchupsForCurrentWeek( )
	{
		List<Matchup> matchups = new ArrayList<>();
		int currentSeason = ScheduleUtils.getCurrentSeason();
		for (int season = currentSeason; season <= currentSeason; season++)
		{
			for (int week = ScheduleUtils.getCurrentWeek(); week <= ScheduleUtils.getCurrentWeek(); week++)
			{
				String importUrl = getImportUrl(season, week);
				String gamesForWeekAsJson;
				try
				{
					gamesForWeekAsJson = getJson(importUrl);
					List<Matchup> matchupsFromJson = getMatchupsFromJson(gamesForWeekAsJson);
					matchups.addAll(matchupsFromJson);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
		return matchups;
	}

	protected List<Matchup> getMatchupsFromJson(String gamesForWeekAsJson)
	{
		List<Matchup> matchups = new ArrayList<>();
		JSONObject json = XML.toJSONObject(gamesForWeekAsJson);
		JSONObject gms = json.getJSONObject("ss").getJSONObject("gms");

		int week = gms.getInt("w");
		int season = gms.getInt("y");

		JSONArray games = gms.getJSONArray("g");

		for (int i = 0; i < games.length(); i++)
		{
			JSONObject g = games.getJSONObject(i);

			if (g.getString("q").equals("F"))
			{
				System.out.println("Loading games for season=" + season + " week=" + week);
				
				
				g.put("game", i + 1);
				g.put("week", week);
				g.put("season", season);
				g.put("home", g.get("h"));
				g.put("away", g.get("v"));
				g.put("gameDay", g.get("eid"));
				g.put("gameTime", g.get("t"));
				if (g.getString("q").equals("F")) {
					g.put("homeScore", g.get("hs"));
					g.put("awayScore", g.get("vs"));
				} else {
					g.put("homeScore", -1);
					g.put("awayScore", -1);
				}
				
				if (!g.has("week"))
				{
					System.err.println("missing week:" + season + ":" + week);
				}
				
				String string = g.toString().replaceAll("\"JAX\"", "\"JAC\"").replaceAll("\"LA\"", "\"LAR\"");
				Matchup matchup = gson.fromJson(string, Matchup.class);
				matchups.add(matchup);
			}
		}
		return matchups;
	}

	protected String getJson(String importUrl) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		URL url = new URL(importUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
		{
			if (inputLine.length() > 10)
			{
				sb.append(inputLine);
			}
		}
		return sb.toString();
	}

	protected String getImportUrl(int season, int week)
	{
		return String.format(NFL_SCORESTRIP_JSON_URL_TEMPLATE, String.valueOf(season), String.valueOf(week));
	}

	
	
	
	
	protected int getLastCompleteSeason()
	{
		int season = ScheduleUtils.getCurrentSeason();
		LocalDate now = LocalDate.now();
		Month currentMonth = now.getMonth();
		System.out.println("Current month = " + currentMonth.name());

		if (currentMonth.compareTo(Month.FEBRUARY) > 0)
		{
			season = season - 1;
			System.out.println("Adjusting to last completed season = " + season);
		}

		return season;
	}

	class LocalDateDeserializer implements JsonDeserializer<LocalDate>
	{
		@Override
		public LocalDate deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			String date = element.getAsString();
			date = date.substring(0, 8);
			return LocalDate.parse(date, formatter);
		}
	}

	class LocalTimeDeserializer implements JsonDeserializer<LocalTime>
	{
		@Override
		public LocalTime deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
			String time = element.getAsString() + " PM";
			return LocalTime.parse(time, formatter);
		}
	}
}
