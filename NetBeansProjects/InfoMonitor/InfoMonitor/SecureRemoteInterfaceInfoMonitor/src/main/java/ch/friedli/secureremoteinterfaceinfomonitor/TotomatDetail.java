package ch.friedli.secureremoteinterfaceinfomonitor;

/**
 *
 * @author mfrie_000
 */
public class TotomatDetail {

    private String league;
    private String homeTeam;
    private String guestTeam;
    private String result;
    private String dateString;
    private String timeString;
    private String resultFirstHalf;
    private String resultSecondHalf;
    private String scoreSuffix; // e.g. n.P. or n.V.
    private boolean isRunning;

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
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

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
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

    public String getScoreSuffix() {
        return scoreSuffix;
    }

    public void setScoreSuffix(String scoreSuffix) {
        this.scoreSuffix = scoreSuffix;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
