package nflguru2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class ScheduleBuilder
{

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;

	public static void main(String[] args)
	{
		ScheduleBuilder scheduleBuilder = new ScheduleBuilder();
		try
		{
			JSONObject xmlJSONObj = XML.toJSONObject(scheduleBuilder.getData( 2018 ));
			
			String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			System.out.println(jsonPrettyPrintString);
		
			Files.write(Paths.get("/Users/garthpidcock/Development/Workspaces/guru17/nflguru2018/data/schedule2018.json"), jsonPrettyPrintString.getBytes());
		}
		catch (JSONException je)
		{
			System.out.println(je.toString());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getData(int season)
	{
		StringBuilder sb = new StringBuilder();
		URL url;
		String text = "<season></season>";
		try
		{
			for (int i = 1; i <= 17; i++)
			{
				url = new URL("http://www.nfl.com/ajax/scorestrip?season=2014&seasonType=REG&week=" + i);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null)
				{
					if(inputLine.length() > 10) {
						sb.append(inputLine);
					}
				}
			}

			text = "<season>" + sb.toString() + "</season>";
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
		
		return text;

	}

}
