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

// For Testing

// **************************** IN THE FUTURE: Make sure remove team also removes riders in that team (when its implemented) *********************


// Checking if portals are independant of one another
try {
    teamTwo = portal1.createTeam("TeamTwo", "Another team");
} catch (IllegalNameException | InvalidNameException e) {
    e.printStackTrace();
}
assert (portal1.getTeams().length == 2) : "Portal1 should have two teams.";
assert (portal2.getTeams().length == 1) : "Portal2 should have one team.";

// Checking remove team
try {
	portal1.removeTeam(teamTwo);
} catch (IDNotRecognisedException e){
	e.printStackTrace();
}
assert (portal1.getTeams().length == 1) : "Portal1 should have one team.";



// Checking createRider
try{
	teams = portal1.getTeams(); // Gets Team ID's from portal1
	rider = portal1.createRider(teams[0], "Bob Smith", 2000); // Created Rider, returns rider id
} catch (IDNotRecognisedException e) {
	e.printStackTrace();
} catch (IllegalArgumentException e) {
	e.printStackTrace();
}

// Checking if Rider was created into the correct team.



assert(portal1.removeRider(rider)) : "ID could not be removed";

