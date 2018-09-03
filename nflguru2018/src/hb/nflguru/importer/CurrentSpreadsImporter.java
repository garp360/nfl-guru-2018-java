package hb.nflguru.importer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;

import nflguru2018.NFLTeam;

public class CurrentSpreadsImporter
{

	public CurrentSpreadsImporter()
	{
		super();
	}

	public Map<String, BigDecimal> getSpreads()
	{
		Map<String, BigDecimal> data = new HashMap<String, BigDecimal>();
		try
		{
			@SuppressWarnings("resource")
			WebClient webClient = new WebClient();
			HtmlPage page = webClient.getPage("https://www.officefootballpool.com/picks.cfm");
			HtmlForm form = (HtmlForm) page.getElementById("loginform_loginPopup");
			HtmlTextInput txtUsername = (HtmlTextInput) page.getElementById("username_lp");
			HtmlPasswordInput txtPassword = (HtmlPasswordInput) page.getElementById("password_lp");
			txtUsername.setText("OOBI");
			txtPassword.setText("WotstWTWW00$1$2");
			page = (HtmlPage) form.fireEvent(Event.TYPE_SUBMIT).getNewPage();

			List<HtmlElement> pickButtons = page.getByXPath("//div[contains(@class, 'team') and contains(@class, 'pickButton')]");
			for (HtmlElement htmlElement : pickButtons)
			{
				String title = htmlElement.getAttribute("title");
				String team = title.split("\\(")[0].trim();
				team = NFLTeam.convertOfficeFootballPoolNameToAbbr(team.toUpperCase());
				String operand = title.split("\\)")[1].trim().substring(0, 1);
				String spreadString = title.split("\\)")[1].trim().substring(1);
				
				BigDecimal spread = new BigDecimal(spreadString);
				
				if(operand.equalsIgnoreCase("-")) {
					spread = spread.negate();
				}
				
				data.put(team, spread);
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return data;
	}
}
