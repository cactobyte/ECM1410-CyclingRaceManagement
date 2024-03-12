package cycling;

public class Rider{
	// attributes
	private int teamID;
	private String name;
	private int yearOfBirth;

	// setters and getters
	public int getTeamID(){
		return teamID;
	}

	// constructor
	public Rider(int teamID, String name, int yearOfBirth){
		this.teamID = teamID;
		this.name = name;
		this.yearOfBirth = yearOfBirth;
	}
}