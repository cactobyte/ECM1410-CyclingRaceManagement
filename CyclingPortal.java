package cycling;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class CyclingPortal implements CyclingPortalInterface {
	// global hashmaps
	private HashMap<Integer, Team> teamHash = new HashMap<Integer, Team>();

	//global lists
	private ArrayList<Team> teamList = new ArrayList<Team>();

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// IllegalNameException
		// looping through all team checking if name exists
		for (int i = 0; i < teamList.size(); i++){
			if (name == teamList.get(i).getName()){
				throw new IllegalNameException("Team name already exists");
			}
		}

		// InvalidNameException
		if (name == null){
			throw new InvalidNameException("Name is null");
		} else if (name.isEmpty()){
			throw new InvalidNameException("Name is empty");
		} else if (name.length() > 30){
			throw new InvalidNameException("Name is too long");
		} else if (name.contains(" ")){
			throw new InvalidNameException("Name contains whitespace");
		}

		// main method logic
		Team newTeam = new Team(name, description);
		// assigning ID to team
		// if no teams
		if (teamList.size() == 0){
			teamHash.put(0, newTeam);
		} else {
			teamHash.put(Collections.max(teamHash.keySet()) + 1, newTeam);
		}

		teamList.add(newTeam);

		return Collections.max(teamHash.keySet());
		}

	@Override
	public void removeTeam(int teamID) throws IDNotRecognisedException{
		// IDNotRecognisedException
		if (!teamHash.contains(teamID)){
			throw new IDNotRecognisedException("Team ID does not exist")
		}

		// removing from list
		String name = teamHash.get(teamID);
		for (int i = 0; i < teamList.size(); i++){
			if (name.equals(teamList.get(i).getName())){
				teamList.remove(i);
			}
		}

		// removing from hashmap
		teamHash.remove(teamID);
	}

}
