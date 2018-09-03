CALL apoc.load.json("file:///Users/garthpidcock/Downloads/rawData2010-2017.json") YIELD value AS matchup
CREATE (m:Matchup {home: matchup.home, visitor:matchup.away, homeScore: matchup.homeScore, visitorScore: matchup.awayScore, season:matchup.season, week:matchup.week, year:matchup.gameDay.year, month:matchup.gameDay.month, day:matchup.gameDay.day,hour:matchup.gameTime.hour,minute:matchup.gameTime.minute})

CALL apoc.load.json("file:///Users/garthpidcock/Downloads/rawData2010-2017.json") YIELD value AS matchup
MERGE (h:Team {name: matchup.home})
MERGE (v:Team {name: matchup.away})


MATCH (m:Matchup),(t:Team)
WHERE t.name = m.home
MERGE (t)-[p:PLAYED]->(m) ON CREATE SET p.score = m.homeScore

MATCH (m:Matchup),(t:Team)
WHERE t.name = m.visitor
MERGE (t)-[p:PLAYED]->(m) ON CREATE SET p.score = m.visitorScore


MATCH (t:Team),(m:Matchup) WHERE m.season = 2017 and m.week = 7 return t,m
