package hb.nflguru.app;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import hb.nflguru.app.prediction.AdvancedPredictor;
import hb.nflguru.app.prediction.SimplePredictor;
import hb.nflguru.importer.HistoricalDataImporter;
import hb.nflguru.utils.ScheduleUtils;

public class NflGuru extends Frame
{
	private static final long serialVersionUID = 1L;

	private Frame mainFrame;
	private Label headerLabel;
	private Label statusLabel;
	private Panel controlPanel;
	private SimplePredictor predictor = new SimplePredictor();
	private AdvancedPredictor predictor2 = new AdvancedPredictor();
	private HistoricalDataImporter historicalDataImporter = new HistoricalDataImporter();
	String historicalDataFilePath = "./data/nflGuruDataArchive%s.json";

	public NflGuru()
	{
		prepareGUI();
	}

	public static void main(String[] args)
	{
		NflGuru awtControlDemo = new NflGuru();
		awtControlDemo.showEventDemo();
	}

	private void prepareGUI()
	{
		mainFrame = new Frame("Java AWT Examples");
		mainFrame.setSize(400, 240);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		headerLabel = new Label();
		headerLabel.setAlignment(Label.CENTER);
		statusLabel = new Label();
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(350, 100);

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}

	private void showEventDemo()
	{
		if (loadHistoricalData())
		{

			headerLabel.setText("NFLGuru predictions");

			Button simpleButton = new Button("Simple " + ScheduleUtils.getSuffix());
			Button advancedButton = new Button("Advanced " + ScheduleUtils.getSuffix());

			simpleButton.setActionCommand("Submit");
			simpleButton.addActionListener(new SimpleClickListener());
			controlPanel.add(simpleButton);

			advancedButton.setActionCommand("Submit");
			advancedButton.addActionListener(new AdvancedClickListener());
			controlPanel.add(advancedButton);

			mainFrame.setVisible(true);

		}
	}

	private class SimpleClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			if (command.equals("OK"))
			{
				statusLabel.setText("Ok Button clicked.");
			}
			else
				if (command.equals("Submit"))
				{
					try
					{
						String currentFilePath = predictor.getCurrentFilePath();
						File f = new File(currentFilePath);
						if (!f.exists())
						{
							predictor.predict();
						}

						Desktop.getDesktop().open(new File(currentFilePath));
					}
					catch (IOException e1)
					{
						statusLabel.setText("Failed to open " + predictor.getCurrentFilePath());
					}
				}
				else
				{
					statusLabel.setText("Cancel Button clicked.");
				}
		}
	}

	private class AdvancedClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			if (command.equals("OK"))
			{
				statusLabel.setText("Ok Button clicked.");
			}
			else
				if (command.equals("Submit"))
				{
					try
					{
						String currentFilePath = predictor2.getCurrentFilePath();
						File f = new File(currentFilePath);
						if (!f.exists())
						{
							predictor2.predict();
						}

						Desktop.getDesktop().open(new File(currentFilePath));
					}
					catch (IOException e1)
					{
						statusLabel.setText("Failed to open " + predictor.getCurrentFilePath());
					}
				}
				else
				{
					statusLabel.setText("Cancel Button clicked.");
				}
		}
	}

	private boolean loadHistoricalData()
	{
		boolean historicalDataLoaded = false;
		String currentFilePath = String.format(historicalDataFilePath, ScheduleUtils.getSuffix());
		try
		{
			File f = new File(currentFilePath);
			if (f.exists())
			{
				historicalDataLoaded = true;
			}
			else
			{
				historicalDataLoaded = historicalDataImporter.loadData(currentFilePath);

			}
		}
		catch (Exception e1)
		{
			statusLabel.setText("Failed to open " + currentFilePath);
		}
		return historicalDataLoaded;
	}
}
