package cycling;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Map;
import java.time.LocalDateTime;

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
	private HashMap<Integer, Stage> stageHash = new HashMap<Integer, Stage>();
	private HashMap<Integer, Checkpoint> checkpointHash = new HashMap<Integer, Checkpoint>();

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
	public void removeTeam(int teamId) throws IDNotRecognisedException{
		// IDNotRecognisedException
		if (!teamHash.containsKey(teamId)){
			throw new IDNotRecognisedException("Team ID does not exist");
		}

		// removign all the riders associated with Team
		for (Map.Entry<Integer, Rider> entry : riderHash.entrySet()){
			if (entry.getValue().getTeamID() == teamId){
				riderHash.remove(entry.getKey());
			}			
		}

		// removing from hashmap
		teamHash.remove(teamId);
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

	@Override
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

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!riderHash.containsKey(riderId)){
			throw new IDNotRecognisedException("riderId does not exist");
		}

		riderHash.remove(riderId);
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException{
		// IDNotRecognisedException
		if (!teamHash.containsKey(teamId)){
			throw new IDNotRecognisedException("Team ID does not exist");
		}

		ArrayList<Integer> riderList = new ArrayList<Integer>();

		// loop through all riders finding Team IDS that match
		for (Map.Entry<Integer, Rider> entry : riderHash.entrySet()){
			if (entry.getValue().getTeamID() == teamId){
				riderList.add(entry.getKey());
			}			
		}

		// convert ArrayList to Array
		// int[] IDs = riderList.toArray();
		int[] IDs = riderList.stream().mapToInt(i->i).toArray();

		return IDs;
	}

	@Override
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

	@Override
	public int[] getRaceIds() {
		int[] raceId = new int[raceHash.size()];
		int index = 0;

		for (Integer i : raceHash.keySet()) {
			raceId[index++] = i;
		}

		return raceId;
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("Race ID not in system");
		}

		// method logic
		raceHash.remove(raceId);
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description,
		double length, LocalDateTime startTime, StageType type)
		throws IDNotRecognisedException, IllegalNameException,
		InvalidNameException, InvalidLengthException{

		// IDNotRecognised
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("ID does not match to any race in the system");
		}

		// IllegalName
		for (Stage stage : stageHash.values()) {
			if (stage.getName() == stageName){
				throw new IllegalNameException("Stage name already exists");
			}
		}

		// InvalidName
		if (stageName == null){
			throw new InvalidNameException("Stage name is null");
		} else if (stageName.isEmpty()){
			throw new InvalidNameException("Stage name is empty");
		} else if (stageName.length() > 30){
			throw new InvalidNameException("Stage name is too long");
		} else if (stageName.contains(" ")){
			throw new InvalidNameException("Stage name contains whitespace");
		}

		// InvalidLength
		if (length < 5) {
			throw new InvalidLengthException("The length of the stage is too short or null");
		}

		// method logic
		Stage newStage = new Stage(raceId, stageName, description, length, startTime, type);
		int numOfStages = stageHash.size();

		if (numOfStages == 0){
			stageHash.put(0, newStage);
		} else {
			stageHash.put(Collections.max(stageHash.keySet()) + 1, newStage);
		}

		return Collections.max(stageHash.keySet()); 
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException{
		// IDNotRecognisedException
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("Race ID not in system");
		}

		// Put all stages from the race in a list
		ArrayList<Stage> stageList = new ArrayList<Stage>();
		for (Stage stage : stageHash.values()){
			if (stage.getRaceId() == raceId){
				stageList.add(stage);
			}
		}

		// bubblesort list based on startTime
		boolean swapFlag = true;
		for (int i = 1; i < stageList.size(); i++){
			// stopping sort if no swaps made last pass
			if (swapFlag){
				break;
			}

			// resseting flag
			swapFlag = false;

			// bubble sort pass
			for (int j = 0; j < stageList.size() - i; j++){
				// comparing times of current item and item ahead
				if (stageList.get(j).getStartTime().isAfter(stageList.get(j + 1).getStartTime())){
					// swapping items
					Stage temp = stageList.get(j + 1);
					stageList.set(j + 1, stageList.get(j));
					stageList.set(j, temp);

					swapFlag = true;
				}
			}
		}

		// return the IDs
		int[] Ids = new int[stageList.size()];
		// loop through all items in the list
		for (int i = 0; i < stageList.size(); i++){
			// looping hasmap finding current stage
			for (Map.Entry<Integer, Stage> entry : stageHash.entrySet()){
				if (entry.getValue() == stageList.get(i)){
					Ids[i] = entry.getKey();
				}
			}
		}

		return Ids;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("Stage ID not in system");
		}
		
		// method logic
		return stageHash.get(stageId).getLength();
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("race ID not in system");
		}

		// method logic
		int count = 0;

		// loop through all stages finding matching raceIds
		for (Stage stage : stageHash.values()){
			if (stage.getRaceId() == raceId){
				count++;
			}
		}

		return count;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("stage ID not in system");
		}

		// remove stage
		stageHash.remove(stageId);
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type,
	Double averageGradient, Double length) throws IDNotRecognisedException,
	InvalidLocationException, InvalidStageStateException, InvalidStageTypeException{

		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("stage ID does not exist");
		}

		// InvalidLocation
		if (stageHash.get(stageId).getLength() < location){
			throw new InvalidLengthException("The checkpoint is not within the stage bounds");
		}

		// InvalidStageState
		if (stageHash.get(stageId).getStageState() == "waiting for results"){
			throw new InvalidStageStateException("Can't add checkpoint, stage has concluded");
		}

		// InvalidStageType
		if (stageHash.get(stageId).getType() == StageType.TT){
			throw new InvalidStageTypeException("Cannot add a checkpoint to a time trial stage");
		}

		// method logic
		// initialise checkpoint object
		Checkpoint newCheckpoint = new Checkpoint(stageId, location, type, averageGradient, length);

		// add checkpoint to hashmap
		if (checkpointHash.size() == 0){
			checkpointHash.put(0, newCheckpoint);
		} else {
			checkpointHash.put(Collections.max(checkpointHash.keySet()) + 1, newCheckpoint);
		}

		// return Id
		return Collections.max(checkpointHash.keySet());
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location)
	throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
	InvalidStageTypeException{

		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("stage ID does not exist");
		}

		// InvalidLocation
		if (stageHash.get(stageId).getLength() < location){
			throw new InvalidLengthException("The checkpoint is not within the stage bounds");
		}

		// InvalidStageState
		if (stageHash.get(stageId).getStageState() == "waiting for results"){
			throw new InvalidStageStateException("Can't add checkpoint, stage has concluded");
		}

		// InvalidStageType
		if (stageHash.get(stageId).getType() == StageType.TT){
			throw new InvalidStageTypeException("Cannot add a checkpoint to a time trial stage");
		}

		// method logic
		// initialise checkpoint object
		Checkpoint newCheckpoint = new Checkpoint(stageId, location, CheckpointType.SPRINT);

		// add checkpoint to hashmap
		if (checkpointHash.size() == 0){
			checkpointHash.put(0, newCheckpoint);
		} else {
			checkpointHash.put(Collections.max(checkpointHash.keySet()) + 1, newCheckpoint);
		}

		// return Id
		return Collections.max(checkpointHash.keySet());
	}

	@Override
	public void removeCheckpoint(int checkpointId)
	throws IDNotRecognisedException, InvalidStageStateException{

		// IDNotRecognised
		if (!checkpointHash.containsKey(checkpointId)){
			throw new IDNotRecognisedException("checkpoint ID not in system");
		}

		// InvalidStageState
		int stageId = checkpointHash.get(checkpointId).getStageId();
		if (stageHash.get(stageId).getStageState() == "waiting for results"){
			throw new InvalidStageStateException("Can't remove checkpoint, stage has concluded");
		}

		// remove checkpoint
		checkpointHash.remove(checkpointId);
	}

	@Override
	public void concludeStagePreparation(int stageId)
	throws IDNotRecognisedException, InvalidStageStateException{

		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("stage ID not in system");
		}

		// InvalidStageState
		if (stageHash.get(stageId).getStageState() == "waiting for results"){
			throw new InvalidStageStateException("Can't conclude stage, stage already concluded");
		}

		// method logic
		stageHash.get(stageId).concludeStage();
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException{
		// IDNotRecognisedException
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("stage ID not in system");
		}

		// Put all stages from the race in a list
		ArrayList<Checkpoint> checkpointList = new ArrayList<Checkpoint>();
		for (Checkpoint checkpoint : checkpointHash.values()){
			if (checkpoint.getStageId() == stageId){
				checkpointList.add(checkpoint);
			}
		}

		// bubblesort list based on location
		boolean swapFlag = true;
		for (int i = 1; i < checkpointList.size(); i++){
			// stopping sort if no swaps made last pass
			if (swapFlag){
				break;
			}

			// resseting flag
			swapFlag = false;

			// bubble sort pass
			for (int j = 0; j < checkpointList.size() - i; j++){
				// comparing times of current item and item ahead
				if (checkpointList.get(j).getLocation() > checkpointList.get(j + 1).getLocation()){
					// swapping items
					Checkpoint temp = checkpointList.get(j + 1);
					checkpointList.set(j + 1, checkpointList.get(j));
					checkpointList.set(j, temp);

					swapFlag = true;
				}
			}
		}

		// return the IDs
		int[] Ids = new int[checkpointList.size()];
		// loop through all items in the list
		for (int i = 0; i < checkpointList.size(); i++){
			// looping hasmap finding current stage
			for (Map.Entry<Integer, Checkpoint> entry : checkpointHash.entrySet()){
				if (entry.getValue() == checkpointList.get(i)){
					Ids[i] = entry.getKey();
				}
			}
		}

		return Ids;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("race ID not in system");
		}

		// method logic
		// calculating total length
		int total = 0;
		for (Map.Entry<Integer, Stage> entry : stageHash.entrySet()){
			if (entry.getValue().getRaceId() == raceId){
				total += entry.getValue().getLength();
			}
		}

		String output = "RACE INFO: Race ID = " + String.valueOf(raceId) + ", ";
		output += "Race Name = " + raceHash.get(raceId).getName() + ", ";
		output += "Race Description = " + raceHash.get(raceId).getDescription() + ", ";
		output += "Number of stages = " + String.valueOf(getNumberOfStages(raceId)) + ", ";
		output += "Total length = " + String.valueOf(total) + ".";

		return output;
	}
}
