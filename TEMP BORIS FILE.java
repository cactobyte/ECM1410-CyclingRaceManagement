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

// New Functions 12/03/24

public int getRaceId() {
	int[] raceId = new int[raceHash.size()];
	int index = 0;

	for (Integer i : raceHash.keySet()) {
		raceId[index++] = i
	}

	return raceId;
}

public void removeRaceById(int raceId) throws IDNotRecognisedException{
// Not sure if containsKey works lol, probably does! haha..
// no but fr it exists so its ok right??!?!?!?!?!?!?!? why am i talking to myself ;-;
	if (!raceHash.containsKey(raceId)){
		throw new IDNotRecognisedException
	}

	raceHash.remove(raceId);
}

private HashMap<Integer, Stage> stageHash = new HashMap<Integer, Stage>();

public int addStageToRace(int raceId, string stageName, string description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
	
	if (!raceHash.containsKey(raceId)){
		throw new IDNotRecognisedException("ID does not match to any race in the system")
	}

	for (String name : stageHash.values()) {
		if (stageName == name){
			throw new IllegalNameException("Stage name already exists");
		}
	}

	if (stageName == null){
		throw new InvalidNameException("Stage name is null");
	} else if (stageName.isEmpty()){
		throw new InvalidNameException("Stage name is empty");
	} else if (stageName.length() > 30){
		throw new InvalidNameException("Stage name is too long");
	} else if (stageName.contains(" ")){
		throw new InvalidNameException("Stage name contains whitespace");
	}


	if (length < 5 || length == null){
		throw new InvalidLengthException()
	}

	Stage newStage = new Stage(raceId, stageName, description, length, startTime, type);
	int numOfStages = stageHash.size()
	if (numOfStages == 0){
		stageHash.put(0, newStage);
	} else {
		stageHash.put(Collections.max(stageHash.keySet()) + 1, newStage);
	}

	return Collections.max(stageHash.keySet()); 
}
