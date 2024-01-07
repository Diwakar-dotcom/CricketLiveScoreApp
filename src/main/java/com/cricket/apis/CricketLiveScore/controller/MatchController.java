package com.cricket.apis.CricketLiveScore.controller;

import com.cricket.apis.CricketLiveScore.entities.Match;
import com.cricket.apis.CricketLiveScore.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("matches")
public class MatchController {

    @Autowired
    MatchService matchService;

    @GetMapping("all")
    public ResponseEntity<List<Match>> getAllMatches() {
        return matchService.getAllMatches();
    }
    @GetMapping("live")
    public ResponseEntity<List<Match>> getLiveMatches() {
        return matchService.getLiveMatches();
    }


}
