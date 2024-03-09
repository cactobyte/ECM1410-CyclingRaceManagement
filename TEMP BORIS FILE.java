// Add to the top 
private HashMap<Integer, Race> raceHash = new HashMap<Integer, Race>();

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

// Add this in the bottom
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
		for (String i : raceHash.values()) {
			if (name == i){
				throw new IllegalNameException("Race name already exists");
			}
		}

		// main method logic
		Race newRace = new Race(name, description);
		// assigning ID to race, ******NEED EDITING****
		int raceId = raceHash.size()
		if (raceId == 0){
			raceHash.put(0, newRace);
		} else {
			raceHash.put(Collections.max(raceHash.keySet()) + 1, newRace);
		}

		return Collections.max(teamHash.keySet()); // can this not just be raceId + 1 ??? 

}
