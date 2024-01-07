package com.cricket.apis.CricketLiveScore.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "crick_matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matchId;

    private String teamHeading;
    private String matchNumberVenue;
    private String battingTeam;
    private String battingTeamScore;
    private String ballingTeam;
    private String ballingTeamScore;
    private String liveCommentry;
    private String matchLink;
    private String textComplete;
    @Enumerated
    private MatchStatus matchStatus;
    private Date date = new Date();


    // set match status
    public void setMatchStatus() {
        if (textComplete.isBlank()) {
            this.matchStatus = MatchStatus.LIVE;
        } else {
            this.matchStatus = MatchStatus.COMPLETED;
        }
    }
}
