package nflguru2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

public class StatsBuilder
{
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;

	public static void main(String[] args)
	{
		StatsBuilder builder = new StatsBuilder();
		Map<Integer,Map<Integer,List<JsonImportObject>>> seasons = builder.getData(10);
		Gson gson = new Gson();
		String json = gson.toJson(seasons);
		System.out.println(json);
	}

	private Map<Integer, Map<Integer, List<JsonImportObject>>> getData(int yearsBack)
	{
		Map<Integer,Map<Integer,List<JsonImportObject>>> seasons = new HashMap<>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		for(int season = year-yearsBack; season<=year; season++) 
		{
			Map<Integer,List<JsonImportObject>> weeks = new HashMap<>();
			for (int week = 1; week <= 17; week++) 
			{
				List<JsonImportObject> matchups = getMatchups( season, week );
				weeks.put(week, matchups);
			}
			seasons.put(season, weeks);
		}
		return seasons;
	}

	private List<JsonImportObject> getMatchups(int season, int week)
	{
		String url = "http://www.nfl.com/ajax/scorestrip?season=" + season + "&seasonType=REG&week=" + week;
		List<JsonImportObject> matchups = new ArrayList<>();
		try
		{
			URL matchupUrl = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(matchupUrl.openStream()));
			List<String> result = in.lines().collect(Collectors.toList());
			String[] data = result.get(1).split(">");

			for (String string : data)
			{
				if (string.trim().startsWith("<g "))
				{
					String modifiedString = modify(string, season, week);
					JSONObject jsonObj = XML.toJSONObject(modifiedString);

					String json = jsonObj.toString().replace("{\"matchup\":{", "{");
					json = json.replace("}}", "}");

					Gson gson = new Gson();
					JsonImportObject m = gson.fromJson(json.toString(), JsonImportObject.class);
					matchups.add(m);
				}
			}
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchups;
	}


	private String modify(String string, int season, int week)
	{
		String s = string + ">";
		s = s.replace(" hs=\"\" ", " hs=0 ");
		s = s.replace(" vs=\"\" ", " vs=0 ");
		s = s.replace("<g ", "<matchup ");
		s = s.replace(" eid=", " id=");

		s = s.replace(" d=", " season=" + season + " week=" + week + " d=");
		s = s.replace(" d=", " day=");
		s = s.replace(" t=", " time=");
		s = s.replace(" k=\"\"", "");
		s = s.replace(" p=\"\"", "");
		s = s.replace(" rz=\"\"", "");
		s = s.replace(" ga=\"\"", "");
		s = s.replace(" h=", " spread=0 homeAbbr=");
		s = s.replace(" hnn=", " homeName=");
		s = s.replace(" hs=", " homeScore=");
		s = s.replace(" v=", " visitorAbbr=");
		s = s.replace(" vnn=", " visitorName=");
		s = s.replace(" vs=", " visitorScore=");
		s = s.replace(" q=\"FO\"", " overtime=true");
		s = s.replace(" q=\"F\"", " overtime=false");
		s = s.replace(" gt=", " gameType=");
		return s;
	}
}
