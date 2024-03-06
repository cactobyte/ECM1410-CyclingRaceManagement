import java.util.ArrayList;

public class database{
	private ArrayList<Team> Teams = new ArrayList<>();

	public addTeam(Team name){
		Teams.add(name);
	}

	public int getNumTeams(){
		return Teams.size();
	}

	public ArrayList getTeams(){
		return Teams;
	}

	public database(){}
}