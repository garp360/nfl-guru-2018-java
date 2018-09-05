package hb.nflguru.utils;

public enum NFLTeam
{		
	ARI ("ARIZONA", "cardinals", "ARIZONA"),
	ATL ("ATLANTA", "falcons", "ATLANTA"),
	BAL ("BALTIMORE", "ravens", "BALTIMORE"),
	BUF ("BUFFALO", "bills", "BUFFALO"),
	CAR ("CAROLINA", "panthers", "CAROLINA"),
	CHI ("CHICAGO", "bears", "CHICAGO"),
	CIN ("CINCINNATI", "bengals", "CINCINNATI"),
	CLE ("CLEVELAND", "browns", "CLEVELAND"),
	DAL ("DALLAS", "cowboys", "DALLAS"),
	DEN ("DENVER", "broncos", "DENVER"),
	DET ("DETROIT", "lions", "DETROIT"),
	GB ("GREEN BAY", "packers", "GREEN BAY"),
	HOU ("HOUSTON", "texans", "HOUSTON"),
	IND ("INDIANAPOLIS", "colts", "INDIANAPOLIS"),
	JAC ("JACKSONVILLE", "jaguars", "JACKSONVILLE"),
	KC ("KANSAS CITY", "chiefs", "KANSAS CITY"),
	LAC  ("LA CHARGERS", "chargers", "LA CHARGERS"),
	LAR  ("LA RAMS", "rams", "LA RAMS"),
	MIA ("MIAMI", "dolphins", "MIAMI"),
	MIN ("MINNESOTA", "vikings", "MINNESOTA"),
	NE ("NEW ENGLAND", "patriots", "NEW ENGLAND"),
	NO ("NEW ORLEANS", "saints", "NEW ORLEANS"),
	NYG  ("NY GIANTS", "giants", "NY GIANTS"),
	NYJ  ("NY JETS", "jets", "NY JETS"),
	OAK ("OAKLAND", "raiders", "OAKLAND"),
	PHI ("PHILADELPHIA", "eagles", "PHILADELPHIA"),
	PIT ("PITTSBURGH", "steelers", "PITTSBURGH"),
	SD ("SAN DIEGO", "chargers", "SAN DIEGO"),
	SEA ("SEATTLE", "seahawks", "SEATTLE"),
	SF ("SAN FRANCISCO", "49ers", "SAN FRANCISCO"),
	STL ("ST LOUIS", "rams", "ST LOUIS"),
	TB ("TAMPA BAY", "buccaneers", "TAMPA BAY"),
	TEN ("TENNESSEE", "titans", "TENNESSEE"),
	WAS ("WASHINGTON", "redskins", "WASHINGTON");

	private final String masseyName;
	private final String mascotName;
	private final String officeFootballPoolName;

	private NFLTeam(String masseyName, String mascotName, String officeFootballPoolName)
	{
		this.masseyName = masseyName;
		this.mascotName = mascotName;
		this.officeFootballPoolName = officeFootballPoolName;
	}
	
	public static String convertMascotNameToAbbr( String  mascotName) {
		NFLTeam[] values = NFLTeam.values();
		String abbr = "";
		for (NFLTeam team : values)
		{
			if(team.getMascotName().equals(mascotName)) {
				abbr = team.name();
				break;
			}
		}
		return abbr;
	}

	public static String convertMasseyNameToAbbr( String  masseyName) {
		NFLTeam[] values = NFLTeam.values();
		String abbr = "";
		for (NFLTeam team : values)
		{
			if(team.getMasseyName().equals(masseyName)) {
				abbr = team.name();
				break;
			}
		}
		return abbr;
	}
	
	public static String convertOfficeFootballPoolNameToAbbr( String  officeFootballPoolName) {
		NFLTeam[] values = NFLTeam.values();
		String abbr = "";
		for (NFLTeam team : values)
		{
			if(team.getOfficeFootballPoolName().equals(officeFootballPoolName)) {
				abbr = team.name();
				break;
			}
		}
		return abbr;
	}

	public String getMasseyName()
	{
		return masseyName;
	}

	public String getMascotName()
	{
		return mascotName;
	}

	public String getOfficeFootballPoolName()
	{
		return officeFootballPoolName;
	}
	
	
}
