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

// bruh im not cooking here


// This is a map where the key is the stageId, and the value is another map
// The inside/inner map stores the rider id and the result time, ill call it resultsHash, but technically its a big stageRiderResultHash lol
private Map<Integer, Map<Integer, LocalTime[]>> resultsHash = new HashMap<>();


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

	if (resultsHash.containsKey(stageId) && resultsHash.get(stageId).containsKey(riderId)) {
		throw new DuplicatedResultException("Rider already has a result during this stage");
	}

	// Lowkey need help with this one...
	/* 
	@throws InvalidCheckpointTimesException Thrown if the length of checkpointTimes is
 *                                     not equal to n+2, where n is the number
 *                                     of checkpoints in the stage; +2 represents
 *                                     the start time and the finish time of the
	*/


	if (!resultsHash.containsKey(stageId)) { // could use putIfAbsent
	    resultsHash.put(stageId, new HashMap<>()); // Creates a hash with stageid and another empty hashmap
	}
	resultsHash.get(stageId).put(riderId, checkpointTimes); // Adds the rider id and corresponding time to that "inner" hashmap

}



