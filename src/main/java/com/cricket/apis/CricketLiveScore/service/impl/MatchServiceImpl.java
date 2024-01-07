package com.cricket.apis.CricketLiveScore.service.impl;

import com.cricket.apis.CricketLiveScore.entities.Match;
import com.cricket.apis.CricketLiveScore.repositories.MatchRepo;
import com.cricket.apis.CricketLiveScore.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepo matchRepo;
    public MatchServiceImpl(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }

    @Override
    public ResponseEntity<List<Match>> getAllMatches() {
        return new ResponseEntity<>(matchRepo.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Match>> getLiveMatches() {
        List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores";
            Document document = Jsoup.connect(url).get();
            Elements liveScoreElements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for (Element match : liveScoreElements) {
                HashMap<String, String> liveMatchInfo = new LinkedHashMap<>();
                String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = match.select("span").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String ballingTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String ballingTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String liveCommentry = match.select("div.cb-text-live").text();
                String textComplete = match.select("div.cb-text-complete").text();
                //getting match link
                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();

                Match match1 = new Match();
                match1.setTeamHeading(teamsHeading);
                match1.setMatchNumberVenue(matchNumberVenue);
                match1.setBattingTeam(battingTeam);
                match1.setBattingTeamScore(score);
                match1.setBallingTeam(ballingTeam);
                match1.setBallingTeamScore(ballingTeamScore);
                match1.setLiveCommentry(liveCommentry);
                match1.setMatchLink(matchLink);
                match1.setTextComplete(textComplete);
                match1.setMatchStatus();


                matches.add(match1);

               //  update the match in database

                updateMatchInDB(match1);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(matches,HttpStatus.OK);
    }

    private void updateMatchInDB(Match match1) {
        Match match = matchRepo.findByTeamHeading(match1.getTeamHeading()).orElse(null);

        if (match == null) {
            matchRepo.save(match1);
        } else {
            match1.setMatchId(match.getMatchId());
            matchRepo.save(match1);
        }
    }

    @Override
    public List<Map<String, String>> getPointTable() {
        return null;
    }
}
