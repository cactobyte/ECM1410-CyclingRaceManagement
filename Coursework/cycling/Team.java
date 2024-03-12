package cycling;

public class Team {
	private String name;
	private String description;
	private int teamID;

	public String getName(){
		return name;
	}

	//contructor
	public Team(String name, String description){
		this.name = name;
		this.description = description;
	}
}