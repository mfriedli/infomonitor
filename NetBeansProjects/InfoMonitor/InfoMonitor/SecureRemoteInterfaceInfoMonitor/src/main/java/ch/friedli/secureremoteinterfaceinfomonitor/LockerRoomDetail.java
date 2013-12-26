package ch.friedli.secureremoteinterfaceinfomonitor;

/**
 *
 * @author Michael Frieldi
 */
public class LockerRoomDetail {
    private int roomNumber;
    private String timeString;
    private String dateString;
    private String teamName;
    private String league;

    public String getLeague() {
        return league;
    }
    
    public void setLeague(String league) {
        this.league = league;
    }    
    
    /**
     * @return the roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return the timeString
     */
    public String getTimeString() {
        return timeString;
    }

    /**
     * @param timeString the timeString to set
     */
    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    /**
     * @return the dateString
     */
    public String getDateString() {
        return dateString;
    }

    /**
     * @param dateString the dateString to set
     */
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    /**
     * @return the teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
