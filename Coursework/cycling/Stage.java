package cycling;

import java.time.LocalDateTime;

public class Stage {
	private int raceID;
	private String stageName;
	private String description;
	private double length;
	private java.time.LocalDateTime startTime,
	private StageType type;

	public String getName(){
		return stageName;
	}

	//contructor
	public Stage(int raceID, String stageName, String description, double length, java.time.LocalDateTime startTime, StageType type){
		this.raceId = raceID;
		this.stageName = stageName;
		this.description = description;
		this.length = length;
		this.startTime = startTime;
		this.type = type;
	}

}
