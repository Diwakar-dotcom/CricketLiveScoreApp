package com.cricket.apis.CricketLiveScore.service;

import com.cricket.apis.CricketLiveScore.entities.Match;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface MatchService {
    ResponseEntity<List<Match>> getAllMatches();
    ResponseEntity<List<Match>> getLiveMatches();
    List<Map<String, String>> getPointTable();
}
