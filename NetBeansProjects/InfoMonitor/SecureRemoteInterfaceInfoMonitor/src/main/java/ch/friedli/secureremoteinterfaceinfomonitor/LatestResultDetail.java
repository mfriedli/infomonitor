package ch.friedli.secureremoteinterfaceinfomonitor;

/**
 *
 * @author Michael Frieldi
 */
public class LatestResultDetail {
    private String league;
    private String dateString;
    private String timeString;
    private String homeTeam;
    private String guestTeam;
    private String result;
    private String resultFirstHalf;
    private String resultSecondHalf;
    private String scoreSuffix; // e.g. n.P. or n.V.

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
    
    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getScoreSuffix() {
        return scoreSuffix;
    }

    public void setScoreSuffix(String scoreSuffix) {
        this.scoreSuffix = scoreSuffix;
    }

    public String getResultFirstHalf() {
        return resultFirstHalf;
    }

    public void setResultFirstHalf(String resultFirstHalf) {
        this.resultFirstHalf = resultFirstHalf;
    }

    public String getResultSecondHalf() {
        return resultSecondHalf;
    }

    public void setResultSecondHalf(String resultSecondHalf) {
        this.resultSecondHalf = resultSecondHalf;
    }

   
}
