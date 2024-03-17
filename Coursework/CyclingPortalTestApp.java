import cycling.*;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortal interface -- note you
 * will want to increase these checks, and run it on your CyclingPortalImpl class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 2.0
 */
public class CyclingPortalTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		// TODO replace BadMiniCyclingPortalImpl by CyclingPortalImpl
		CyclingPortal portal1 = new CyclingPortalImpl();
		CyclingPortal portal2 = new CyclingPortalImpl();

		assert (portal1.getRaceIds().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";
		assert (portal1.getTeams().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";

		try {
			portal1.createTeam("TeamOne", "My favorite");
			portal2.createTeam("TeamOne", "My favorite");
		} catch (IllegalNameException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}

		assert (portal1.getTeams().length == 1)
				: "Portal1 should have one team.";

		assert (portal2.getTeams().length == 1)
				: "Portal2 should have one team.";

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

	}

}
