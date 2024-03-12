package cycling;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Map;

/**
 * Cycling portal implementation
 * 
 * @author Finlay Fordham, Boris Cheung
 */

public class CyclingPortalImpl implements CyclingPortal {
	// global hashmaps
	private HashMap<Integer, Team> teamHash = new HashMap<Integer, Team>();
	private HashMap<Integer, Rider> riderHash = new HashMap<Integer, Rider>();
	private HashMap<Integer, Race> raceHash = new HashMap<Integer, Race>();

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// IllegalNameException
		for (Team val : teamHash.values()){
			if (name == val.getName()){
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
		if (teamHash.size() == 0){
			teamHash.put(0, newTeam);
		} else {
			teamHash.put(Collections.max(teamHash.keySet()) + 1, newTeam);
		}

		return Collections.max(teamHash.keySet());
	}

	@Override
	public void removeTeam(int teamID) throws IDNotRecognisedException{
		// IDNotRecognisedException
		if (!teamHash.containsKey(teamID)){
			throw new IDNotRecognisedException("Team ID does not exist");
		}

		// removing from hashmap
		teamHash.remove(teamID);
	}

	@Override
	public int[] getTeams(){
		Set<Integer> IDSet = teamHash.keySet();

		//converting set to Array
		int[] teamIDs = new int[IDSet.size()];
		int i = 0;
		for (Integer val : IDSet) {
			teamIDs[i++] = val;
		}

		return teamIDs;
	}

	public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException{
		// IDNotrecognisedException
		boolean IDExists = false;

		for (int key : riderHash.keySet()){
			if (key == teamID){
				IDExists = true;
			}
		}

		if (!IDExists){
			throw new IDNotRecognisedException("teamID doesn't exist");
		}

		// IllegalArgumentException
		if (name == null){
			throw new IllegalArgumentException("name is null");
		} else if (name.isEmpty()){
			throw new IllegalArgumentException("name is empty");
		} else if (yearOfBirth < 1900){
			throw new IllegalArgumentException("Year is before 1900");
		}

		// method logic
		Rider newRider = new Rider(teamID, name, yearOfBirth);

		// adding rider to rider hashmap
		if (riderHash.size() == 0){
			riderHash.put(0, newRider);
		} else {
			riderHash.put(Collections.max(riderHash.keySet()) + 1, newRider);
		}

		return Collections.max(riderHash.keySet());
	}

	public void removeRider(int riderID) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!riderHash.containsKey(riderID)){
			throw new IDNotRecognisedException("RiderID does not exist");
		}

		riderHash.remove(riderID);
	}

	public int[] getTeamRiders(int teamID) throws IDNotRecognisedException{
		// IDNotRecognisedException
		if (!teamHash.containsKey(teamID)){
			throw new IDNotRecognisedException("Team ID does not exist");
		}

		ArrayList<Integer> riderList = new ArrayList<Integer>();

		// loop through all riders finding Team IDS that match
		for (Map.Entry<Integer, Rider> entry : riderHash.entrySet()){
			if (entry.getValue().getTeamID() == teamID){
				riderList.add(entry.getKey());
			}			
		}

		// convert ArrayList to Array
		// int[] IDs = riderList.toArray();
		int[] IDs = riderList.stream().mapToInt(i->i).toArray();

		return IDs;
	}

	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
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

		// IllegalNameException
		// looping through all race checking if name exists
		// id is key, value is race *del*
		for (Race i : raceHash.values()) {
			if (name == i.getName()){
				throw new IllegalNameException("Race name already exists");
			}
		}

		// method logic
		Race newRace = new Race(name, description);
		// assigning ID to race, ******NEED EDITING****
		int raceId = raceHash.size();
		if (raceId == 0){
			raceHash.put(0, newRace);
		} else {
			raceHash.put(Collections.max(raceHash.keySet()) + 1, newRace);
		}

		return Collections.max(teamHash.keySet()); // can this not just be raceId + 1 ??? 

	}
}
