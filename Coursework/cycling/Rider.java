package cycling;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Rider{
	// attributes
	private int teamID;
	private String name;
	private int yearOfBirth;
	private Map<Integer, LocalTime[]> stageResults;

	// setters and getters
	public int getTeamID(){
		return teamID;
	}

	// constructor
	public Rider(int teamID, String name, int yearOfBirth){
		this.teamID = teamID;
		this.name = name;
		this.yearOfBirth = yearOfBirth;
		this.stageResults = new HashMap<>();
	}

	public void addStageResult(int stageId, LocalTime[] checkpointTimes) {
		stageResults.put(stageId, checkpointTimes);
	}

	public LocalTime[] getStageResult(int stageId){
		if (stageResults.containsKey(stageId)) {
        		return stageResults.get(stageId);
    		} else {
        		return null;
    		}
	}
	
}
