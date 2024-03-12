package cycling;

public class Race {
	private String name;
	private String description;
	private int raceID;

	public String getName(){
		return name;
	}

	//contructor
	public Race(String name, String description){
		this.name = name;
		this.description = description;
	}
}
