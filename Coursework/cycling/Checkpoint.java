package cycling;

public class Checkpoint{
	// attributes
	private int stageId;
	private Double location;
	private CheckpointType type;
	private Double averageGradient;
	private Double length;

	// getters and setters
	public int getStageId(){
		return stageId;
	}

	public Double getLocation(){
		return location;
	}

	public CheckpointType getType() {
        	return type;
    	}

	// constructors
	public Checkpoint(int stageId, Double location, CheckpointType type){
		this.stageId = stageId;
		this.location = location;
		this.type = type;
	}

	public Checkpoint(int stageId, Double location, CheckpointType type, Double averageGradient, Double length){
		this(stageId, location, type);
		this.averageGradient = averageGradient;
		this.length = length;
	}
}
