package cycling;

public class Checkpoint{
	// attributes
	private int stageId;
	private Double location;
	private CheckpointType type;
	private Double averageGradient;
	private Double length;

	// constructor
	public Checkpoint(int stageId, Double location, CheckpointType type, Double averageGradient, Double length){
		this.stageId = stageId;
		this.location = location;
		this.type = type;
		this.averageGradient = averageGradient;
		this.length = length;
	}
}