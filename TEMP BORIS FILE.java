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
