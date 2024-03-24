package cycling;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.IOException;
import java.time.temporal.ChronoUnit;

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

	// done
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
		} else{
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

	// done
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

	// done
	@Override
	public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException{
		// IDNotrecognisedException
		if (!teamHash.containsKey(teamID)){
			throw new IDNotRecognisedException("teamId does not exist");
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

	// done
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

	// done
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

		return Collections.max(raceHash.keySet());
	}

	// done
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

	// done
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

	// done
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

	// done
	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("Stage ID not in system");
		}
		
		// method logic
		return stageHash.get(stageId).getLength();
	}

	// done
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

	// done
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
			throw new InvalidLocationException("The checkpoint is not within the stage bounds");
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

	// done
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
			throw new InvalidLocationException("The checkpoint is not within the stage bounds");
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

	// done
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

	// done
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
		outside:
			for (int i = 1; i < checkpointList.size(); i++){
				// stopping sort if no swaps made last pass
				if (!swapFlag){
					break outside;
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

	// done
	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("race ID not in system");
		}

		// method logic
		// calculating total length
		double total = 0;
		for (Map.Entry<Integer, Stage> entry : stageHash.entrySet()){
			if (entry.getValue().getRaceId() == raceId){
				total += entry.getValue().getLength();
			}
		}

		String output = "RACE INFO: Race ID = " + String.valueOf(raceId) + ", ";
		output += "Race Name = " + raceHash.get(raceId).getName() + ", ";
		output += "Race Description = " + raceHash.get(raceId).getDescription() + ", ";
		output += "Number of stages = " + String.valueOf(getNumberOfStages(raceId)) + ", ";
		output += "Total length = " + String.valueOf(total);

		return output;
	}

	// done
	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
		throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
		
		// IDNotRecognised 
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("Stage ID not in system");
		}
		if (!riderHash.containsKey(riderId)){
			throw new IDNotRecognisedException("Rider ID not in system");
		}

		// InvalidstageState
		Stage stage = stageHash.get(stageId);
		if (stageHash.get(stageId).getStageState() != "waiting for results"){
			throw new InvalidStageStateException("Stage is not at the correct state"); // Sounds dodgy, change in future
		}

		// InvalidCheckpointTimes
		// finding how many checkpoints are in the stage
		int numberOfCheckpoints = 0;
		for(Map.Entry<Integer, Checkpoint> entry : checkpointHash.entrySet()){
			if (entry.getValue().getStageId() == stageId){
				numberOfCheckpoints++;
			}
		}
		// checking if right number of results were input
		if (checkpoints.length != (numberOfCheckpoints + 2)){
			throw new InvalidCheckpointTimesException("Wrong number of results input");
		}

		// DuplicatedResult Exception
		Rider rider = riderHash.get(riderId);
		if (!rider.hasResults(stageId)){
			throw new DuplicatedResultException("Rider already has a result for this stage");
		}
		
		// Method logic
		rider.addStageResult(stageId, checkpoints);
	}

	// done
	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// IDNotRecognised
		if (!riderHash.containsKey(riderId)){
			throw new IDNotRecognisedException("Rider ID not in system");
		}
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("Stage ID not in system");
		}

		// method logic
		Rider rider = riderHash.get(riderId);
		LocalTime[] stageResults = rider.getStageResult(stageId);
		return stageResults;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// IDNotRecognisedException
		if (!riderHash.containsKey(riderId)){
			throw new IDNotRecognisedException("Rider ID not in system");
		}
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("Stage ID not in system");
		}
		
		// method logic
		Rider rider = riderHash.get(riderId);
		rider.deleteStageResults(stageId);
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// NameNotRecognised
		boolean nameExists = false;
		int raceId = 0;
		for (Map.Entry<Integer, Race> entry : raceHash.entrySet()){
			if (entry.getValue().getName() == name){
				nameExists = true;
				raceId = entry.getKey();
			}
		}
		if (!nameExists){
			throw new NameNotRecognisedException("Race Name not in system");
		}

		// method logic
		raceHash.remove(raceId);
	}

	// done
	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("StageId doesn't exist");
		}
		if (!riderHash.containsKey(riderId)){
			throw new IDNotRecognisedException("RiderId doesn't exist");
		}

		// get all riders in a stage
		ArrayList<Rider> riderList = new ArrayList<Rider>();
		riderList = getRidersInStage(stageId);

		// declaring output
		LocalTime currentTime;

		// edge case TT stage
		if (stageHash.get(stageId).getType() == StageType.TT){
			// bubblesort first to last based on thier time
			boolean swapFlag = true;
			outside:
				for (int i = 1; i < riderList.size(); i++){
					// stopping sort if no swaps made last pass
					if (!swapFlag){
						break outside;
					}

					// resseting flag
					swapFlag = false;

					// bubble sort pass
					for (int j = 0; j < riderList.size() - i; j++){
						// comparing times of current item and item ahead
						// rider 1 time
						LocalTime finishTime = riderList.get(j).getFinalStageTime(stageId);
						LocalTime startTime= riderList.get(j).getCheckpointTime(stageId, 0);
						LocalTime t1 = calcTimeTrialTime(startTime, finishTime);

						// rider 2 time
						finishTime = riderList.get(j + 1).getFinalStageTime(stageId);
						startTime = riderList.get(j + 1).getCheckpointTime(stageId, 0);
						LocalTime t2 = calcTimeTrialTime(startTime, finishTime);

						if (t1.isAfter(t2)){
							// swapping items
							Rider temp = riderList.get(j + 1);
							riderList.set(j + 1, riderList.get(j));
							riderList.set(j, temp);

							swapFlag = true;
						}
					}
				}

			// getting the requested riderIndex in sorted list
			int riderIndex = 0;
			Rider rider = riderHash.get(riderId);
			for (int i = 0; i < riderList.size(); i++){
				if (riderList.get(i) == rider){
					riderIndex = i;
				}
			}

			// finding the adjusted time of specific rider
			LocalTime finishTime = riderList.get(riderIndex).getFinalStageTime(stageId);
			LocalTime startTime = riderList.get(riderIndex).getCheckpointTime(stageId, 0);
			currentTime = calcTimeTrialTime(startTime, finishTime);
			withinOne:
				for(int i = riderIndex; i > 0; i--){
					// comparing times of current item and item before
					finishTime = riderList.get(i).getFinalStageTime(stageId);
					startTime = riderList.get(i).getCheckpointTime(stageId, 0);
					long t1 = ChronoUnit.SECONDS.between(startTime, finishTime);

					finishTime = riderList.get(i - 1).getFinalStageTime(stageId);
					startTime = riderList.get(i - 1).getCheckpointTime(stageId, 0);
					long t2 = ChronoUnit.SECONDS.between(startTime, finishTime);

					double difference = t1 - t2;
					if (difference < 1){
						currentTime = calcTimeTrialTime(startTime, finishTime);
					} else{
						break withinOne;
					}
				}
		} else{
			// bubblesort first to last based on thier time
			boolean swapFlag = true;
			outside:
				for (int i = 1; i < riderList.size(); i++){
					// stopping sort if no swaps made last pass
					if (!swapFlag){
						break outside;
					}

					// resseting flag
					swapFlag = false;

					// bubble sort pass
					for (int j = 0; j < riderList.size() - i; j++){
						// comparing times of current item and item ahead
						if (riderList.get(j).getFinalStageTime(stageId).isAfter(riderList.get(j + 1).getFinalStageTime(stageId))){
							// swapping items
							Rider temp = riderList.get(j + 1);
							riderList.set(j + 1, riderList.get(j));
							riderList.set(j, temp);

							swapFlag = true;
						}
					}
				}

			// calculate specific adjusted time for rider requested
			// finding the specific rider
			int riderIndex = 0;
			Rider rider = riderHash.get(riderId);
			for (int i = 0; i < riderList.size(); i++){
				if (riderList.get(i) == rider){
					riderIndex = i;
				}
			}

			// checking if rider ahead has time within one second
			currentTime = riderHash.get(riderId).getFinalStageTime(stageId);
			withinOne:
				for(int i = riderIndex; i > 0; i--){
					LocalTime timeOne = riderList.get(i).getFinalStageTime(stageId);
					LocalTime timeTwo = riderList.get(i - 1).getFinalStageTime(stageId);
					long difference = ChronoUnit.SECONDS.between(timeOne, timeTwo);
					if (difference < 1){
						currentTime = timeTwo;
					} else{
						break withinOne;
					}
				}
		}

		return currentTime;
	}

	// done
	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("StageId does not exist");
		}

		ArrayList<Rider> riderList = new ArrayList<Rider>();
		riderList = getRidersInStage(stageId);

		// edge case TT stage
		if (stageHash.get(stageId).getType() == StageType.TT){
			// bubblesort first to last based on thier time
			boolean swapFlag = true;
			outside:
				for (int i = 1; i < riderList.size(); i++){
					// stopping sort if no swaps made last pass
					if (!swapFlag){
						break outside;
					}

					// resseting flag
					swapFlag = false;

					// bubble sort pass
					for (int j = 0; j < riderList.size() - i; j++){
						// comparing times of current item and item ahead
						// rider 1 time
						LocalTime finishTime = riderList.get(j).getFinalStageTime(stageId);
						LocalTime startTime= riderList.get(j).getCheckpointTime(stageId, 0);
						LocalTime t1 = calcTimeTrialTime(startTime, finishTime);

						// rider 2 time
						finishTime = riderList.get(j + 1).getFinalStageTime(stageId);
						startTime = riderList.get(j + 1).getCheckpointTime(stageId, 0);
						LocalTime t2 = calcTimeTrialTime(startTime, finishTime);

						if (t1.isAfter(t2)){
							// swapping items
							Rider temp = riderList.get(j + 1);
							riderList.set(j + 1, riderList.get(j));
							riderList.set(j, temp);

							swapFlag = true;
						}
					}
				}
		} else{
			riderList = bubbleSortRiderListOnFinalTime(stageId, riderList);			
		}

		// list of Ids based on sorted list
		ArrayList<Integer> idList = new ArrayList<Integer>();
		for (int i = 0; i < riderList.size(); i++){
			for (Map.Entry<Integer, Rider> entry : riderHash.entrySet()){
				if (riderList.get(i) == entry.getValue()){
					idList.add(entry.getKey());
				}
			}
		}

		int[] idArr = idList.stream().mapToInt(i->i).toArray();
		return idArr;
	}

	// done
	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException{
		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("StageId does not exist");
		}

		// method logic
		// creating list for riders
		ArrayList<Rider> riderList = new ArrayList<Rider>();
		riderList = getRidersInStage(stageId);

		// time trial stage edge case
		if (stageHash.get(stageId).getType() == StageType.TT){
			// sorting the riders
			// bubblesort first to last based on thier time
			boolean swapFlag = true;
			outside:
				for (int i = 1; i < riderList.size(); i++){
					// stopping sort if no swaps made last pass
					if (!swapFlag){
						break outside;
					}

					// resseting flag
					swapFlag = false;

					// bubble sort pass
					for (int j = 0; j < riderList.size() - i; j++){
						// comparing times of current item and item ahead
						// rider 1 time
						LocalTime finishTime = riderList.get(j).getFinalStageTime(stageId);
						LocalTime startTime= riderList.get(j).getCheckpointTime(stageId, 0);
						LocalTime t1 = calcTimeTrialTime(startTime, finishTime);

						// rider 2 time
						finishTime = riderList.get(j + 1).getFinalStageTime(stageId);
						startTime = riderList.get(j + 1).getCheckpointTime(stageId, 0);
						LocalTime t2 = calcTimeTrialTime(startTime, finishTime);

						if (t1.isAfter(t2)){
							// swapping items
							Rider temp = riderList.get(j + 1);
							riderList.set(j + 1, riderList.get(j));
							riderList.set(j, temp);

							swapFlag = true;
						}
					}
				}

			// create a list of the times
			ArrayList<LocalTime> timeList = new ArrayList<LocalTime>();
			for (Rider rider : riderList){
				LocalTime startTime = rider.getCheckpointTime(stageId, 0);
				LocalTime finishTime = rider.getFinalStageTime(stageId);
				timeList.add(calcTimeTrialTime(startTime, finishTime));
			}

			// change times to adjusted times
			int streak = 0;
			for(int i = timeList.size() - 1; i > 0; i--){
				LocalTime t1;
				LocalTime t2;
				t1 = timeList.get(i);
				t2 = timeList.get(i - 1);
				
				long difference = ChronoUnit.SECONDS.between(t1, t2);
				if (difference < 1){
					timeList.set(i, t2);

					// streak
					for (int j = streak; j > 0; j--){
						timeList.set(i + j, t2);
					}

					streak++;
				} else{
					streak = 0;
				}
			}
			
			// return array of times
			LocalTime[] timeArr = timeList.toArray(new LocalTime[timeList.size()]);
			return timeArr;
		}

		// Other stages
		riderList = bubbleSortRiderListOnFinalTime(stageId, riderList);

		// create a list of times based on sorted rider list
		ArrayList<LocalTime> timeList = new ArrayList<LocalTime>();
		for (Rider rider : riderList){
			timeList.add(rider.getFinalStageTime(stageId));
		}

		// calc adjusted times
		int streak = 0;
		for (int i = riderList.size() - 1; i > 0; i--){
			LocalTime timeOne = timeList.get(i);
			LocalTime timeTwo = timeList.get(i - 1);
			long difference = ChronoUnit.SECONDS.between(timeOne, timeTwo);
			if (difference < 1){
				// current items
				timeList.set(i, timeTwo);

				// previous items
				for (int j = streak; j > 0; j--){
					timeList.set(i + j, timeTwo);
				}

				streak++;
			} else{
				streak = 0;
			}
		}

		// return array of times
		LocalTime[] timeArr = timeList.toArray(new LocalTime[timeList.size()]);
		return timeArr;
	}

	// done
	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// IDNotRecognised
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("Stage Id does not exist");
		}

		// Method logic
		// get ranks of rider who competed in stage
		int[] rankArr;
		rankArr = getRidersRankInStage(stageId);

		// assign points based on stage type
		// get stage type
		StageType stageType = stageHash.get(stageId).getType();

		// flat stage
		if (stageType == StageType.FLAT){
			int[] points = {50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};
			assignSprintPoints(stageId, rankArr, points);			
		}

		// Medium mountain
		if (stageType == StageType.MEDIUM_MOUNTAIN){
			int[] points = {30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
			assignSprintPoints(stageId, rankArr, points);
		}

		// high mountain
		if (stageType == StageType.HIGH_MOUNTAIN){
			int[] points = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
			assignSprintPoints(stageId, rankArr, points);
		}

	 	// time trial
	 	if (stageType == StageType.TT){
	 		int[] points = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	 		assignSprintPoints(stageId, rankArr, points);
	 	}

		// calc points for intermediate sprints
		// getting sprint checkpoints
		int[] checkpoints = getStageCheckpoints(stageId);

		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<Checkpoint> sprintCheckpoints = new ArrayList<Checkpoint>();
		for (int i = 0; i < checkpoints.length; i++){
			CheckpointType checkpointType = checkpointHash.get(checkpoints[i]).getType();
			if (checkpointType == CheckpointType.SPRINT){
				sprintCheckpoints.add(checkpointHash.get(checkpoints[i]));
				indexes.add(i);
			}
		}

		// checking if there is any sprints
		ArrayList<Rider> riderList = new ArrayList<Rider>();
		if (sprintCheckpoints.size() != 0){
			// find all riders that did the stage
			riderList = getRidersInStage(stageId);
		}

		// looping through each sprint to add points
		for (int z = 0; z < sprintCheckpoints.size(); z++){
			// sort them based on their time
			// bubblesort first to last based on thier time
			boolean swapFlag = true;
			outside:
				for (int i = 1; i < riderList.size(); i++){
					// stopping sort if no swaps made last pass
					if (!swapFlag){
						break outside;
					}

					// resseting flag
					swapFlag = false;

					// bubble sort pass
					for (int j = 0; j < riderList.size() - i; j++){
						// comparing times of current item and item ahead
						LocalTime t1 = riderList.get(j).getCheckpointTime(stageId, indexes.get(i));
						LocalTime t2 = riderList.get(j + 1).getCheckpointTime(stageId, indexes.get(i));
						if (t1.isAfter(t2)){
							// swapping items
							Rider temp = riderList.get(j + 1);
							riderList.set(j + 1, riderList.get(j));
							riderList.set(j, temp);

							swapFlag = true;
						}
					}
				}

			// adding points to riders
			int[] points = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
			for (int i = 0; i < riderList.size(); i++){
				riderList.get(i).addSprintPoints(stageId, points[i]);
			}
		}

		// make output list of points from all riders
		ArrayList<Integer> pointsList = new ArrayList<Integer>();
		for (int i = 0; i < rankArr.length; i++){
			int riderPoints = riderHash.get(rankArr[i]).getSprintPoints(stageId);
			pointsList.add(riderPoints);
		}

		int[] output = pointsList.stream().mapToInt(i->i).toArray();
		return output;
	}

	// done
	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// IDNotRecognised
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("Race ID not in system");
		}

		HashMap<Rider, LocalTime> riderTotalTimeMap = new HashMap<Rider, LocalTime>();

		// getting all stages
		int[] raceStages = getRaceStages(raceId);

		for (int i = 0; i < raceStages.length; i++){
			// getting all riders
			int stageId = raceStages[i];
			ArrayList<Rider> riderList = getRidersInStage(stageId);

			// getting rider time
			for (Rider rider : riderList){
				LocalTime stageTime;
				LocalTime prevTime;
				// edge case TT stage
				if (stageHash.get(stageId).getType() == StageType.TT){
					// finding trial time
					LocalTime startTime = rider.getCheckpointTime(stageId, 0);
					LocalTime finalTime = rider.getFinalStageTime(stageId);
					stageTime = calcTimeTrialTime(startTime, finalTime);

					// if hashmap empty
					if (i == 0){
						riderTotalTimeMap.put(rider, stageTime);
					}

					prevTime = riderTotalTimeMap.get(rider);

				} else{
					// adding it to previous rider time
					// get previous times summed
					if (i == 0){
						riderTotalTimeMap.put(rider, rider.getFinalStageTime(stageId));
					}

					stageTime = rider.getFinalStageTime(stageId);
					prevTime = riderTotalTimeMap.get(rider);
				}


				// convert both times to strings
				String stageTimeString = stageTime.toString();
				String prevTimeString = prevTime.toString();

				// split into individual units
				String[] stageArr = new String[3];
				stageArr = stageTimeString.split(":");
				String[] prevArr = new String[3];
				prevArr = prevTimeString.split(":");

				// totalling all of the units
				int hours = Integer.valueOf(stageArr[0]) + Integer.valueOf(prevArr[0]);
				int minutes = Integer.valueOf(stageArr[1]) + Integer.valueOf(prevArr[1]);
				double seconds = Double.valueOf(stageArr[2]) + Double.valueOf(prevArr[2]);
				
				// converting back to localTime
				String timeInput = String.valueOf(hours) + String.valueOf(minutes) + String.valueOf(seconds);
				LocalTime input = LocalTime.parse(timeInput);

				// putting in hashmap
				riderTotalTimeMap.remove(rider);
				riderTotalTimeMap.put(rider, input);
			}
		}

		// make list of riders
		// Assuming if a rider is input they complete the whole race TODO
		ArrayList<Rider> riderList = new ArrayList<Rider>();
		for (Map.Entry<Integer, Rider> entry : riderHash.entrySet()){
			if (riderTotalTimeMap.containsKey(entry.getValue())){				
				riderList.add(entry.getValue());
			}
		}

		// bubblesort first to last based on thier total time
		boolean swapFlag = true;
		outside:
			for (int i = 1; i < riderList.size(); i++){
				// stopping sort if no swaps made last pass
				if (!swapFlag){
					break outside;
				}

				// resseting flag
				swapFlag = false;

				// bubble sort pass
				for (int j = 0; j < riderList.size() - i; j++){
					// comparing times of current item and item ahead
					LocalTime t1 = riderTotalTimeMap.get(riderList.get(j));
					LocalTime t2 = riderTotalTimeMap.get(riderList.get(j + 1));
					if (t1.isAfter(t2)){
						// swapping items
						Rider temp = riderList.get(j + 1);
						riderList.set(j + 1, riderList.get(j));
						riderList.set(j, temp);

						swapFlag = true;
					}
				}
			}

		// create a list of times in order
		ArrayList<LocalTime> timeList = new ArrayList<LocalTime>();
		for (Rider rider : riderList){
			timeList.add(riderTotalTimeMap.get(rider));
		}

		// converting to Array and returning 
		LocalTime[] timeArr = timeList.toArray(new LocalTime[timeList.size()]);
		return timeArr; 
	}

	// done
	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// IDNotRecognised
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("Race ID not in system");
		}

		HashMap<Rider, LocalTime> riderTotalTimeMap = new HashMap<Rider, LocalTime>();

		// getting all stages
		int[] raceStages = getRaceStages(raceId);

		// looping through each stage
		for (int i = 0; i < raceStages.length; i++){
			// getting all riders
			int stageId = raceStages[i];
			ArrayList<Rider> riderList = getRidersInStage(stageId);

			// getting rider time
			for (Rider rider : riderList){
				LocalTime stageTime;
				LocalTime prevTime;
				// edge case TT stage
				if (stageHash.get(stageId).getType() == StageType.TT){
					// finding trial time
					LocalTime startTime = rider.getCheckpointTime(stageId, 0);
					LocalTime finalTime = rider.getFinalStageTime(stageId);
					stageTime = calcTimeTrialTime(startTime, finalTime);

					// if hashmap empty
					if (i == 0){
						riderTotalTimeMap.put(rider, stageTime);
					}

					prevTime = riderTotalTimeMap.get(rider);

				} else{
					// adding it to previous rider time
					// get previous times summed
					if (i == 0){
						riderTotalTimeMap.put(rider, rider.getFinalStageTime(stageId));
					}

					stageTime = rider.getFinalStageTime(stageId);
					prevTime = riderTotalTimeMap.get(rider);
				}


				// convert both times to strings
				String stageTimeString = stageTime.toString();
				String prevTimeString = prevTime.toString();

				// split into individual units
				String[] stageArr = new String[3];
				stageArr = stageTimeString.split(":");
				String[] prevArr = new String[3];
				prevArr = prevTimeString.split(":");

				// totalling all of the units
				int hours = Integer.valueOf(stageArr[0]) + Integer.valueOf(prevArr[0]);
				int minutes = Integer.valueOf(stageArr[1]) + Integer.valueOf(prevArr[1]);
				double seconds = Double.valueOf(stageArr[2]) + Double.valueOf(prevArr[2]);
				
				// converting back to localTime
				String timeInput = String.valueOf(hours) + String.valueOf(minutes) + String.valueOf(seconds);
				LocalTime input = LocalTime.parse(timeInput);

				// putting in hashmap
				riderTotalTimeMap.remove(rider);
				riderTotalTimeMap.put(rider, input);
			}
		}

		// make list of riders
		// Assuming if a rider is input they complete the whole race TODO
		ArrayList<Rider> riderList = new ArrayList<Rider>();
		for (Map.Entry<Integer, Rider> entry : riderHash.entrySet()){
			if (riderTotalTimeMap.containsKey(entry.getValue())){				
				riderList.add(entry.getValue());
			}
		}

		// bubblesort first to last based on thier total time
		boolean swapFlag = true;
		outside:
			for (int i = 1; i < riderList.size(); i++){
				// stopping sort if no swaps made last pass
				if (!swapFlag){
					break outside;
				}

				// resseting flag
				swapFlag = false;

				// bubble sort pass
				for (int j = 0; j < riderList.size() - i; j++){
					// comparing times of current item and item ahead
					LocalTime t1 = riderTotalTimeMap.get(riderList.get(j));
					LocalTime t2 = riderTotalTimeMap.get(riderList.get(j + 1));
					if (t1.isAfter(t2)){
						// swapping items
						Rider temp = riderList.get(j + 1);
						riderList.set(j + 1, riderList.get(j));
						riderList.set(j, temp);

						swapFlag = true;
					}
				}
			}

		// create Id list based off sorted list
		int[] idArr = new int[riderList.size()];
		for (int i = 0; i < riderList.size(); i++){
			for (Map.Entry<Integer, Rider> entry : riderHash.entrySet()){
				if (entry.getValue() == riderList.get(i)){
					idArr[i] = entry.getKey();
				}
			}
		}

		return idArr;
	}

	// TODO
	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// // IDNotRecognised
		// if (!raceHash.containsKey(raceId)){
		// 	throw new IDNotRecognisedException("Race ID does not exist");
		// }

		

		// making a list of all riders that competed in all stages
		// calculate total time for each rider
			// loop through each stage add time to total
				// store totalTime in rider
		// order them based on total time
		// return 


		return null;
	}

	// TODO
	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		if (!stageHash.containsKey(stageId)){
			throw new IDNotRecognisedException("Stage ID not in system");
		}

		ArrayList<Checkpoint> checkpoints = getStageCheckpoints(stageId); // Gets all checkpoints in that stage

	    	Map<CheckpointType, int[]> pointDistributionMap = new HashMap<>();
    		pointDistributionMap.put(CheckpointType.HC, new int[]{20, 15, 12, 10, 8, 6, 4, 2}); // ADD AND FIX
    		pointDistributionMap.put(CheckpointType.C1, new int[]{10, 8, 6, 4, 2, 1}); 
    		pointDistributionMap.put(CheckpointType.C2, new int[]{5, 3, 2, 1}); 
    		pointDistributionMap.put(CheckpointType.C3, new int[]{2, 1}); 
    		pointDistributionMap.put(CheckpointType.C4, new int[]{1}); 

	    	// Gets a list of indexes of valid checkpoints in all stage checkpoints, also stores correct checkpoints
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<Checkpoint> mountainCheckpoints = new ArrayList<Checkpoint>();
		for (int i = 0; i < checkpoints.length; i++){
			checkpointType = checkpointHash.get(i).getType(); // checkpointHash? 
			if (checkpointType != CheckpointType.SPRINT){
				mountainCheckpoints.add(checkpoint);
				indexes.add(i);
			}
		}

		if (mountainCheckpoints.size() != 0){
			// find all riders that did the stage
			riderList = getRidersInStage(stageId);
		}

		// Loop thru all M-CP
		for (Checkpoint mountainCheckpoint : mountainCheckpoints) {
			boolean swapFlag = true;
			// Sort Riders Times for that specific checkpoint
			outside:
				for (int i = 1; i < riderList.size(); i++){
					// stopping sort if no swaps made last pass
					if (!swapFlag){
						break outside;
					}

					// resseting flag
					swapFlag = false;

					// bubble sort pass
					for (int j = 0; j < riderList.size() - i; j++){
						// comparing times of current item and item ahead
						LocalTime t1 = riderList.get(j).getCheckpointTime(stageId, indexes.get(i));
						LocalTime t2 = riderList.get(j + 1).getCheckpointTime(stageId, indexes.get(i));
						if (t1.isAfter(t2)){
							// swapping items
							Rider temp = riderList.get(j + 1);
							riderList.set(j + 1, riderList.get(j));
							riderList.set(j, temp);

							swapFlag = true;
						}
					}
				}
			// Assign Points
			int[] pointsList = pointDistributionMap.get(mountainCheckpoint.getType());

			for (int i = 0; i < riderList.size(); i++) {
				if (pointsList.length > i && pointsList[i] != 0) {
			        riderList.get(i).addMountainPoints(stageId, pointsList[i]);
			    } else {
			    	riderList.get(i).addMountainPoints(stageId, 0);
			    }
			}

		} 
		// Order of riders we need to return
		ranksInStage = getRidersRankInStage(stageId);
		int [] output = new int[ranksInStage.size()];

		// For each rider in order of gRRIS
		for (int i = 0; i < ranksInStage.size(); i++){
			int id = ranksInStage.get(i);
			Rider rider = riderHash.get(id);
			points = rider.getMountainPoints();
			output[i] = points;
		}

    		return output;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		if (!raceHash.containsKey(raceId)){
			throw new IDNotRecognisedException("Race ID not in system");
		}

		int [] raceStages = getRaceStages(raceId); // returns list of ids
		Map<Rider, Integer> mountainPoints = new HashMap<>();

		for (int stageId : raceStages){
			Stage stage = stageHash.get(stageId);
			for (Rider rider : stage.getRidersInStage(stageId)){
				int points = rider.getMountainPoints(stageId);
				mountainPoints.put(rider, mountainPoints.getOrDefault(rider, 0) + points)
			}
		}
		
		rankedListOfIds = getRidersGeneralClassificationRank(raceId); // name is hella scuffed
		int [] output = new int[rankedListOfIds.length];

		for (int i = 0; i < rankedListOfIds.length; i++){
			int riderId = rankedListOfIds[i];
		    Rider rider = riderHash.get(riderId);
		    int riderMountainPoints = mountainPoints.getOrDefault(rider, 0);
		    output[i] = riderMountainPoints;
		}
        
		return output;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	public void assignSprintPoints(int stageId, int[] rankArr, int[] points){
		// adding points to each rider based on position
		for (int i = 0; i < 15; i++){
			Rider rider = riderHash.get(rankArr[i]);
			rider.addSprintPoints(stageId, points[i]);
		}
	}

	public ArrayList<Rider> bubbleSortRiderListOnFinalTime(int stageId, ArrayList<Rider> riderList){
		// bubblesort first to last based on thier time
		boolean swapFlag = true;
		outside:
			for (int i = 1; i < riderList.size(); i++){
				// stopping sort if no swaps made last pass
				if (!swapFlag){
					break outside;
				}

				// resseting flag
				swapFlag = false;

				// bubble sort pass
				for (int j = 0; j < riderList.size() - i; j++){
					// comparing times of current item and item ahead
					LocalTime t1 = riderList.get(j).getFinalStageTime(stageId);
					LocalTime t2 = riderList.get(j + 1).getFinalStageTime(stageId);
					if (t1.isAfter(t2)){
						// swapping items
						Rider temp = riderList.get(j + 1);
						riderList.set(j + 1, riderList.get(j));
						riderList.set(j, temp);

						swapFlag = true;
					}
				}
			}

		return riderList;
	}

	// done
	public ArrayList<Rider> getRidersInStage(int stageId){
		ArrayList<Rider> riderList = new ArrayList<Rider>();
		for (Map.Entry<Integer, Rider> entry : riderHash.entrySet()){
			if (entry.getValue().hasResults(stageId)){
				riderList.add(entry.getValue());
			}
		}

		return riderList;
	}

	// done
	public LocalTime calcTimeTrialTime(LocalTime startTime, LocalTime finishTime){
		String finishString = finishTime.toString();
		String startString = startTime.toString();

		String[] finishArr = new String[3];
		finishArr = finishString.split(":");
		String[] startArr = new String[3];
		startArr = startString.split(":");

		int hours = Integer.valueOf(finishArr[0]) - Integer.valueOf(startArr[0]);
		int minutes = Integer.valueOf(finishArr[1]) - Integer.valueOf(startArr[1]);
		double seconds = Double.valueOf(finishArr[2]) - Double.valueOf(startArr[2]);
		
		String timeInput = String.valueOf(hours) + String.valueOf(minutes) + String.valueOf(seconds);
		LocalTime output = LocalTime.parse(timeInput);
		return output;
	}
}
