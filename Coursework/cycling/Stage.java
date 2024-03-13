package cycling;

import java.time.LocalDateTime;

public class Stage {
	private int raceId;
	private String stageName;
	private String description;
	private double length;
	private LocalDateTime startTime;
	private StageType type;

	public String getName(){
		return stageName;
	}

	//contructor
	public Stage(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type){
		this.raceId = raceId;
		this.stageName = stageName;
		this.description = description;
		this.length = length;
		this.startTime = startTime;
		this.type = type;
	}

}
