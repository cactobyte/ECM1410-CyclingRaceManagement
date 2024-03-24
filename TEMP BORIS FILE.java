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
