package hb.nflguru.utils;

import java.time.LocalDate;

public class ScheduleUtils
{
	public static int getCurrentSeason()
	{
		LocalDate now = LocalDate.now();
		int season = now.getYear();
		return season;
	}

	public static int getCurrentWeek()
	{
		int currentWeek = 1;

		for (int i = 0; i < 17; i++)
		{
			if (LocalDate.now().isAfter(LocalDate.parse("2018-09-05").plusDays(i * 7)))
			{
				currentWeek = i + 1;
			}
			else
			{
				break;
			}
		}
		return currentWeek;
	}
}
