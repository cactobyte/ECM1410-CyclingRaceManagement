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



@Override
public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
	if (!stageHash.containsKey(stageId)){
		throw new IDNotRecognisedException("Stage ID not in system");
	}

	ArrayList<Checkpoint> checkpoints = getStageCheckpoints(stageId); // Gets all checkpoints in that stage
    ArrayList<Rider> riders = getRidersInStage(stageId); // Get Riders in stage

    int[] mountainPoints = new int[riders.size()]; 

    Map<CheckpointType, int[]> pointDistributionMap = new HashMap<>();
pointDistributionMap.put(CheckpointType.HC, new int[]{50, 30, 20, 15}); // ADD AND FIX


    for (int i = 0; i < checkpoints.size(); i++) {
Checkpoint checkpoint = checkpoints.get(i);

// Check if the checkpoint is a mountain checkpoint
if (checkpoint.getType() != CheckpointType.SPRINT) {
    int[] pointDistribution = pointDistributionMap.get(checkpoint.getType());

    // Sort riders based on their checkpoint times
    

    // Assign points based on rider positions and point distribution
    // for (int j = 0; j < riders.size(); j++) {
    //     int points = (j < pointDistribution.length) ? pointDistribution[j] : 0;
    //     mountainPoints[j] += points;
    // }
}


return mountainPoints;
}
