import cycling.*;
import java.util.Arrays;
import java.time.LocalDateTime;
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
		// check for system compiling
		System.out.println("The system compiled and started the execution...");

		// check for creating instance of class impl
		CyclingPortal portal1 = new CyclingPortalImpl();
		CyclingPortal portal2 = new CyclingPortalImpl();

		// checking everything starts empty
		assert (portal1.getRaceIds().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";
		assert (portal1.getTeams().length == 0)
				: "Innitial Portal not empty as required or not returning an empty array.";

		// ** IN THE FUTURE: Make sure remove team also removes riders
		// in that team (when its implemented) **

		// Modular tests for each module created:
		// createTeam
		try{
			// creating a team
			int teamOne = portal1.createTeam("TeamOne", "The first team");

			// checking if the team has been created
			assert (portal1.getTeams().length == 1):"Problem with creating a team";
		} catch(IllegalNameException e){
			e.printStackTrace();
		} catch(InvalidNameException e){
			e.printStackTrace();
		}

		// removeTeam
		try{
			// removing the created team
			portal1.removeTeam(0);

			// checking if the team has been removed
			assert(portal1.getTeams().length == 0):"problem with removing a team";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		}

		// getTeams
		try{
			// create 4 teams
			int teamTwo = portal1.createTeam("teamTwo", "The second team");
			int teamThree = portal1.createTeam("teamThree", "The third team");
			int teamFour = portal1.createTeam("teamfour", "The fourth team");
			int teamFive = portal1.createTeam("teamfive", "The fifth team");

			// remove 2
			portal1.removeTeam(teamThree);
			portal1.removeTeam(teamFive);

			// check if there are the right number of teams the Id's are correct
			assert(portal1.getTeams().length == 2)
					:"problem with getTeams, number of teams is wrong";
			int[] expected = {0, 2};
			assert(Arrays.equals(portal1.getTeams(), expected)):"problem with getting teamIds";

		} catch(Exception e){
			e.printStackTrace();
		}

		// createRider
		try{
			// creating a rider
			int riderOne = portal1.createRider(0,"riderOne", 2000);

			// checking if the rider has been created
			assert (portal1.getTeamRiders(0).length == 1):"Problem with creating a rider";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}

		// removeRider
		try{
			// removing the created team
			portal1.removeRider(0);

			// checking if the Rider has been removed
			assert(portal1.getTeamRiders(0).length == 0):"problem with removing a Rider";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		}

		// getTeamRiders
		try{
			// add a bunch of riders to both teams
			int riderTwo = portal1.createRider(0,"riderTwo", 2001);
			int riderThree = portal1.createRider(0,"riderThree", 2002);
			int riderFour = portal1.createRider(0,"riderFour", 2003);
			int riderFive = portal1.createRider(2,"riderFive", 2004);
			int riderSix = portal1.createRider(2,"riderSix", 2005);

			// remove a bunch of riders from both
			portal1.removeRider(riderThree);
			portal1.removeRider(riderFive);

			// test if length and ids correct
			assert(portal1.getTeamRiders(0).length == 2): "problem with getTeamRiders";
			assert(portal1.getTeamRiders(2).length == 1): "problem with getTeamRiders";
			int[] expected1 = {0, 2};
			int[] expected2 = {4};
			assert(Arrays.equals(portal1.getTeamRiders(0), expected1))
					:"problem with getting riders in team";
			assert(Arrays.equals(portal1.getTeamRiders(2), expected2))
					:"problem with getting riders in team";

			// empty one check if return empty array
			portal1.removeRider(4);
			assert(portal1.getTeamRiders(2).length == 0)
					:"getTeamRiders not returning empty when no riders";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		}

		// createRace
		try{
			// creating a race
			int raceOne = portal1.createRace("raceOne", "First");

			// checking if the race has been created
			assert (portal1.getRaceIds().length == 1):"Problem with creating a race";
		} catch(IllegalNameException e){
			e.printStackTrace();
		} catch(InvalidNameException e){
			e.printStackTrace();
		}

		// removeRaceById
		try{
			// removing the created race
			portal1.removeRaceById(0);

			// checking if the race has been removed
			assert(portal1.getRaceIds().length == 0):"problem with removing a Race";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		}

		// getRaceIds
		// creating races
		try{
			int raceZero = portal1.createRace("raceZero", "zeroth");
			int raceOne = portal1.createRace("raceOne", "first");
			int raceTwo = portal1.createRace("raceTwo", "second");
			int raceThree = portal1.createRace("raceThree", "third");

			// removing races
			portal1.removeRaceById(raceOne);
			portal1.removeRaceById(raceTwo);

			// getting raceIds
			int[] expected = {0, 3};
			assert(Arrays.equals(portal1.getRaceIds(), expected)):"problem with getting race Ids";
		}catch (Exception e){
			e.printStackTrace();
		}

		// addStageToRace
		try{
			// creating a stage
			int stageOne = portal1.addStageToRace(0, "stageOne", "first", 26.7, LocalDateTime.now(), StageType.FLAT);

			// checking if the race has been created
			assert (portal1.getNumberOfStages(0) == 1):"Problem with creating a race";
		} catch(IllegalNameException e){
			e.printStackTrace();
		} catch(InvalidNameException e){
			e.printStackTrace();
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		} catch(InvalidLengthException e){
			e.printStackTrace();
		}

		// getStageLength
		try{
			double length = portal1.getStageLength(0);
			assert(length == 26.7):"error with getStageLenth";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		}

		// removeStageById
		try{
			// removing the created stage
			portal1.removeStageById(0);

			// checking if the stage has been removed
			assert(portal1.getNumberOfStages(0) == 0):"problem with removing a stage";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		}

		// getRaceStages
		try{
			// add a bunch of stages to both races
			int stageOne = portal1.addStageToRace(0, "stageOne", "first", 26.7, LocalDateTime.now(), StageType.FLAT);
			int stageTwo = portal1.addStageToRace(0, "stageTwo", "second", 26.7, LocalDateTime.now(), StageType.FLAT);
			int stageThree = portal1.addStageToRace(3, "stageThree", "third", 26.7, LocalDateTime.now(), StageType.FLAT);
			int stageFour = portal1.addStageToRace(3, "stageFour", "fourth", 26.7, LocalDateTime.now(), StageType.FLAT);
			int stageFive = portal1.addStageToRace(3, "stageFive", "fifth`", 26.7, LocalDateTime.now(), StageType.FLAT);

			// remove a stages from both
			portal1.removeStageById(0);
			portal1.removeStageById(3);

			// test if length and ids correct
			assert(portal1.getNumberOfStages(0) == 1): "problem with getStages";
			assert(portal1.getNumberOfStages(3) == 2): "problem with getStages";
			int[] expected1 = {1};
			int[] expected2 = {2, 4};
			assert(Arrays.equals(portal1.getRaceStages(0), expected1))
					:"problem with getting stages in a race";
			assert(Arrays.equals(portal1.getRaceStages(3), expected2))
					:"problem with getting stages in a race";

			// empty one check if return empty array
			portal1.removeStageById(1);
			assert(portal1.getNumberOfStages(0) == 0)
					:"getRaceStages not returning empty when no stages";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		} catch(IllegalNameException e){
			e.printStackTrace();
		} catch(InvalidNameException e){
			e.printStackTrace();
		} catch(InvalidLengthException e){
			e.printStackTrace();
		}

		// getNumberOfStages
		try{
			assert(portal1.getNumberOfStages(3) == portal1.getRaceStages(3).length)
					:"problem with getNumberOfStages";
		} catch (IDNotRecognisedException e){
			e.printStackTrace();
		}

		// addCategorizedClimbToStage
		try{
			// creating a checkpoint
			portal1.addCategorizedClimbToStage(2, 4.8, CheckpointType.C3, 1.6, 13.0);

			// checking if it's been created
			assert(portal1.getStageCheckpoints(2).length == 1):"Problem with addCCtostage";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		} catch(InvalidLocationException e){
			e.printStackTrace();
		} catch(InvalidStageStateException e){
			e.printStackTrace();
		} catch(InvalidStageTypeException e){
			e.printStackTrace();
		}

		// addIntermediateSprintToStage
		try{
			// creating a checkpoint
			portal1.addIntermediateSprintToStage(2, 5.6);

			// checking if it's been created
			assert(portal1.getStageCheckpoints(2).length == 2):"Problem with addIStostage";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		} catch(InvalidLocationException e){
			e.printStackTrace();
		} catch(InvalidStageStateException e){
			e.printStackTrace();
		} catch(InvalidStageTypeException e){
			e.printStackTrace();
		}

		// removeCheckpoint
		try{
			// remove checkpoint
			portal1.removeCheckpoint(0);
			portal1.removeCheckpoint(1);

			// checking if been removed
			assert(portal1.getStageCheckpoints(2).length == 0):"problem with remove checkpoint";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		} catch(InvalidStageStateException e){
			e.printStackTrace();
		}

		// getStageCheckpoints
		try{
			// add a bunch of checkpoints to both stages
			int ch0 = portal1.addIntermediateSprintToStage(2, 14.5);
			int ch1 = portal1.addCategorizedClimbToStage(2, 7.5, CheckpointType.C4, 2.6, 16.4);
			int ch2 = portal1.addIntermediateSprintToStage(2, 14.5);
			int ch3 = portal1.addCategorizedClimbToStage(4, 7.5, CheckpointType.C4, 2.6, 16.4);
			int ch4 = portal1.addIntermediateSprintToStage(4, 14.5);
			int ch5 = portal1.addCategorizedClimbToStage(4, 7.5, CheckpointType.C4, 2.6, 16.4);

			// remove checkpoints from both
			portal1.removeCheckpoint(ch1);
			portal1.removeCheckpoint(ch3);
			portal1.removeCheckpoint(ch5);

			// test if length and ids correct
			assert(portal1.getStageCheckpoints(2).length == 2):"problem with getting checkpoints";
			assert(portal1.getStageCheckpoints(4).length == 1):"problem with getting checkpoints";
			int[] expected1 = {0, 2};
			int[] expected2 = {4};
			assert(Arrays.equals(portal1.getStageCheckpoints(2), expected1)):"problem with GSC";
			assert(Arrays.equals(portal1.getStageCheckpoints(4), expected2)):"problem with GSC";

			// empty one check if return empty array
			portal1.removeCheckpoint(4);
			assert(portal1.getStageCheckpoints(4).length == 0):"problem with GSC";
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		} catch(InvalidLocationException e){
			e.printStackTrace();
		} catch(InvalidStageStateException e){
			e.printStackTrace();
		} catch(InvalidStageTypeException e){
			e.printStackTrace();
		}

		// concludeStagePreparation
		try{
			// conclude a stage
			portal1.concludeStagePreparation(2);

			// try to interact with stage
			portal1.addIntermediateSprintToStage(2, 14.5);
			portal1.addCategorizedClimbToStage(2, 7.5, CheckpointType.C4, 2.6, 16.9);
			portal1.removeCheckpoint(0);
		} catch(InvalidStageStateException e){
			System.out.println("Successful trigger!");
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		} catch(InvalidLocationException e){
			e.printStackTrace();
		} catch(InvalidStageTypeException e){
			e.printStackTrace();
		}

		// viewRaceDetails
		try{
			System.out.println(portal1.viewRaceDetails(0));
			System.out.println(portal1.viewRaceDetails(3));
		} catch(IDNotRecognisedException e){
			e.printStackTrace();
		}

		System.out.println("Testing was complete");
	}

}
