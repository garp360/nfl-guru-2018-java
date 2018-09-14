package nflguru2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import hb.nflguru.statistics.model.Matchup;

public class DataImporterTest
{

	@Test
	public void DataImporterTest_TEST()
	{
		
		// Files.write(Paths.get("/Users/garthpidcock/Development/Workspaces/guru17/nflguru2018/data/schedule2018.json"), jsonPrettyPrintString.getBytes());
		String NFL_SCORESTRIP_JSON_URL = "http://www.nfl.com/ajax/scorestrip?season=2017&seasonType=REG&week=3";

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
		gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		
		try
		{
			StringBuilder sb = new StringBuilder();
			URL url = new URL(NFL_SCORESTRIP_JSON_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				if (inputLine.length() > 10)
				{
					sb.append(inputLine);
				}
			}

			List<Matchup> matchups = new ArrayList<>();
			JSONObject json = XML.toJSONObject(sb.toString());
			JSONObject gms = json.getJSONObject("ss").getJSONObject("gms");
			
			int week = gms.getInt("w");
			int season = gms.getInt("y");
			
			JSONArray games = gms.getJSONArray("g");
			
			for (int i = 0; i < games.length(); i++)
			{
				JSONObject g = games.getJSONObject(i);
				g.put("game", i+1);
				g.put("week", week);
				g.put("season", season);
				g.put("home", g.get("h"));
				g.put("away", g.get("v"));
				g.put("gameDay", g.get("eid"));
				g.put("gameTime", g.get("t"));
				g.put("homeScore", g.get("hs"));
				g.put("awayScore", g.get("vs"));

				String string = g.toString().replaceAll("\"JAX\"", "\"JAC\"").replaceAll("\"LA\"", "\"LAR\"");
				Matchup matchup = gson.fromJson(string, Matchup.class);
				matchups.add(matchup);
			}

			String output = gson.toJson(matchups);
			System.out.println(output);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public class DateDeserializer implements JsonDeserializer<Date>
	{
		@Override
		public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String date = element.getAsString();
			if(date.length() > 6) {
				date = date.substring(0, 8);
			} else if ( date.length() < 5) {
				formatter = new SimpleDateFormat("h:mm");
			}

			try
			{
				return formatter.parse(date);
			}
			catch (ParseException e)
			{
				System.err.println("Failed to parse Date due to:");
				return null;
			}
		}
	}
	
	public class LocalDateDeserializer implements JsonDeserializer<LocalDate>
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
	
	public class LocalTimeDeserializer implements JsonDeserializer<LocalTime>
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
