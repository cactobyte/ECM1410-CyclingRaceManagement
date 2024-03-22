/*

 /$$$$$$$                      /$$           /$$        /$$$$$$                  /$$          
| $$__  $$                    |__/          | $/       /$$__  $$                | $$          
| $$  \ $$  /$$$$$$   /$$$$$$  /$$  /$$$$$$$|_/       | $$  \__/  /$$$$$$   /$$$$$$$  /$$$$$$ 
| $$$$$$$  /$$__  $$ /$$__  $$| $$ /$$_____/          | $$       /$$__  $$ /$$__  $$ /$$__  $$
| $$__  $$| $$  \ $$| $$  \__/| $$|  $$$$$$           | $$      | $$  \ $$| $$  | $$| $$$$$$$$
| $$  \ $$| $$  | $$| $$      | $$ \____  $$          | $$    $$| $$  | $$| $$  | $$| $$_____/
| $$$$$$$/|  $$$$$$/| $$      | $$ /$$$$$$$/          |  $$$$$$/|  $$$$$$/|  $$$$$$$|  $$$$$$$
|_______/  \______/ |__/      |__/|_______/            \______/  \______/  \_______/ \_______/
                                                                                              
                                                                                                                                                                          

*/

@Override
public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
	if (!stageHash.containsKey(stageId)){
		throw new IDNotRecognisedException("Stage ID not in system");
	}

	ArrayList<Checkpoint> checkpoints = new ArrayList<>(); // Gets all checkpoints in that stage
    for (Checkpoint checkpoint : checkpointHash.values()) {
	if (checkpoint.getStageId() == stageId) {
	    checkpoints.add(checkpoint); // Could be condensed to also check if type is sprint but i feel like its better to seperate it
	}
    }

    // Find categorized climb checkpoints
    for (Checkpoint checkpoint : checkpoints){
	if (checkpoint.getType() != CheckpointType.SPRINT){
		// Then that checkpoint is a climb
		// Calculate points
	}
    }

	return null;
}



// i already updated rider.java, check there for some of the new stuff :)

@Override 														// Idk what these three dots are but its in all the documentations???? IDK
public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) 
throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
	if (!stageHash.containsKey(stageId)){
		throw new IDNotRecognisedException("Stage ID not in system");
	}
	if (!riderHash.containsKey(riderId)){
		throw new IDNotRecognisedException("Rider ID not in system");
	}

	Stage stage = stageHash.get(stageId);
	if (stageHash.get(stageId).getStageState() != "waiting for results"){
		throw new InvalidStageStateException("Stage is not at the correct state"); // Sounds dodgy, change in future
	}

	// Lowkey need help with this one...
	/* 
	@throws InvalidCheckpointTimesException Thrown if the length of checkpointTimes is
 *                                     not equal to n+2, where n is the number
 *                                     of checkpoints in the stage; +2 represents
 *                                     the start time and the finish time of the
	*/

	Rider rider = riderHash.get(riderId);
	if (rider.addStageResult().containsKey(stageId)){
		throw new DuplicatedResultException("Rider already has a result for this stage");
	} else {
		rider.addStageResult(stageId, checkpointTimes);
	}
}


@Override
public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
	if (!riderHash.containsKey(riderId)){
		throw new IDNotRecognisedException("Rider ID not in system");
	}
	if (!stageHash.containsKey(stageId)){
		throw new IDNotRecognisedException("Stage ID not in system");
	}

	Rider rider = riderHash.get(riderId);
	LocalTime[] stageResults = rider.getStageResult(stageId);
	return stageResults;
}

@Override
public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException{
	if (!riderHash.containsKey(riderId)){
		throw new IDNotRecognisedException("Rider ID not in system");
	}
	if (!stageHash.containsKey(stageId)){
		throw new IDNotRecognisedException("Stage ID not in system");
	}
	
	Rider rider = riderHash.get(riderId);
	rider.deleteStageResults(stageId);
}



