package a2;

import java.util.*;

public class HeightCalculator {

	/**
	 * This method takes a (non-null) List of platform positions and returns a
	 * (non-null) map from each of those platform positions to a list of
	 * positions, as described in the assignment handout, representing
	 * subsegments of the platform and their propping heights. See the
	 * assignment handout for details.
	 */
	public static Map<Position, List<Position>> getProppingHeights(
			List<Position> platforms) {
		
		//Create map object to add to if succesfull addition
		Map<Position, List<Position>> result = new LinkedHashMap<>();
		List<Position> presult;
		//bestPoint for Check
		Position bestPoint = null;
		
		
		//Iterate through each platform
		for (int i=0;i<platforms.size();i++) {
		//int i = 1;
		
		
			System.out.println("===Doing PLATFORM: "+i+" ===: "+platforms.get(i));
	
			//set starting point of platform
			double startpnt = platforms.get(i).start();
			double endpnt = platforms.get(i).end();
			double point_progress = startpnt;
			presult = new ArrayList<Position>();
			
			//Goes through from the start Point fo the platform
			while (point_progress < endpnt) {
				
				System.out.println("    For Platform: "+i+"Progression at: "+point_progress+" Until: "+endpnt);
				
				//Best matching point --Reset each new platform Loop for Main Platform
				bestPoint = null;
				Double bestX = null;
				int BestXCheck = 0;
				Double bestXHeight = null;
				
				
				//Go through each other platform
				for (int j=0;j<platforms.size();j++) {
				//int j=2;
					System.out.println("        Checking sub Plat - "+j+" at point: "+platforms.get(j));
					
					//Don't check if height is above or equal to main Platform
					//--System.out.println(platforms.get(j).height());
					if (platforms.get(j).height() >= platforms.get(i).height()) {
						System.out.println("        Exit Loop - Height is above or equal");
						continue;
					}
					
					//boolean bestXpass = false;
					//Do bestPoint vs BestX clause
					//if (bestPoint != null && bestX != null) {
					//	if (bestPoint.end() > bestX) {
					//		bestXpass = true;
					//	}
					//}
					
					Boolean abovebesth = false;
					if (bestPoint !=null && bestXHeight != null) {
						if (bestXHeight < bestPoint.height() ) {
							abovebesth = true;
						}
					}
					
					
					//Store Best X!!
					if (platforms.get(j).start() > point_progress && 
							(platforms.get(j).start() < platforms.get(i).end())) {
						//IF it hasn't been set yet or j start is better then current bestX - set
						System.out.println("BESTX NIGGAAA!!!!!!!!!!!!!!!!!!!!!!!!!: "+bestX);
						System.out.println("TriGAAAAAAAKLDFJJFFFFFFFF!!!!!!!!!!!!!: "+bestPoint);
						if (  ((bestX ==  null)  || (platforms.get(j).start() < bestX ||  ((platforms.get(j).height() > bestXHeight) && abovebesth )) )) {
							//if (bestXpass)
							//if (bestPoint != nullplatforms.get(j).height() > bestPoint.height()) {
								
								System.out.println("BESTPOINTATBESTX: "+bestPoint);
								if ((bestPoint != null) && 
								((platforms.get(i).height() - platforms.get(j).height()) >= bestPoint.height()) ) {
									System.out.println("DONT SET BESTX");
								} else {								
							 
									bestX = platforms.get(j).start();
									bestXHeight = platforms.get(j).height();
									System.out.println("        Best X Has been set to: "+bestX);
								}
							//}
						} 
					}
					
					//Do teh bestX check up her sinc in there sucks balls
					if (bestX != null) {
						if (platforms.get(j).start() < bestX) {
							BestXCheck = 1;
						}
					}
					
					//--System.out.println("GOES ON");
					//Don't check if height isn't higher then the current bestPoint height
					if (bestPoint != null) {	
						
						if ( ((platforms.get(i).height() - platforms.get(j).height() >= bestPoint.height() ) || 
								(point_progress < bestPoint.start())) && BestXCheck == 0 ) {
							//j's x is closer to point_prog use it then kill  
							System.out.println("first cool2 check calc is: "+(platforms.get(i).height() - platforms.get(j).height()));
							System.out.println("second cool2 cehck calc is: "+(platforms.get(j).start() < bestPoint.start()));
							System.out.println("        bestPoint at heigh checkercool2 is: "+bestPoint.height());
							//if pp doenst' exist on the platforms or the start of new platforms doens't beat bestX quit
							//if (BestXCheck == 1) {
							
								System.out.println("COOL2");
								continue;
							//}
						}
					}
					
					//System.out.println("failureheight higher thne best");
					
					
					
					//Don't continue if both  less than - has nothing to do with point then
					// (this should leave only 3 cases), bit out at start, end or all inside, all outside
					if (platforms.get(j).start() <= point_progress && platforms.get(j).end() <= point_progress) {
						continue;
					}
					
					//If they stack up identically
					if (platforms.get(j).end() == platforms.get(i).end() &&
							(platforms.get(j).start() == platforms.get(i).start())) {
						System.out.println("        Doing the stack up identical Check for: "+j+": against Platform: "+i);
						bestPoint = new Position(point_progress, platforms.get(j).end(), 
								platforms.get(i).height() - platforms.get(j).height());
						System.out.println(platforms.get(j).height()+"MAINH: "+platforms.get(i).height());
						System.out.println("        bestpoint at identical insdie: "+bestPoint);
						continue;
					}
					
					//End Inside
					//Check if end point of loop platforms check is within the end point of the platform
					if (platforms.get(j).end() <= platforms.get(i).end() && 
							(platforms.get(j).start() <= point_progress)) {
						System.out.println("        Doing the End Inside Check for: "+j+": against Platform: "+i);
						//Set the best point
						//Calc New height
						if (bestX == null || (platforms.get(j).end() > bestX) ) {
												
							bestPoint = new Position(point_progress, platforms.get(j).end(), 
									platforms.get(i).height() - platforms.get(j).height());
							System.out.println("        BestPoint set - at End  Inside: "+bestPoint);
						} else {
							
							bestPoint = new Position(point_progress, bestX, 
									platforms.get(i).height() - platforms.get(j).height());
							System.out.println("        BestPoint set - at End  Inside: "+bestPoint);
						}
						continue;	
					}		
					
					//End Outside
					//Check if end point of main platform is within the bounds of the loop platform
					if (platforms.get(j).end() > platforms.get(i).end() && (platforms.get(j).start() <= point_progress)) {
						//System.out.println("Checking platform: "+j);
						System.out.println("        Doing the End Outside Check for: "+j+": against Platform: "+i);
						//Set the best point
						System.out.println("Height check of i is: "+platforms.get(i).height()+" and hc of j: "+platforms.get(j).height());
						System.out.println("J is: "+platforms.get(j)+" i PLatform is: "+platforms.get(i));
						bestPoint = new Position(point_progress, platforms.get(i).end(), platforms.get(i).height() - platforms.get(j).height());
						System.out.println("        bestpoint at end outside: "+bestPoint);
						continue;		
					}
					
					//Special empty to base case //And mamke sure no other bestpoint exists at the x
					if (( platforms.get(j).start() > point_progress) && 
							(platforms.get(j).start() < platforms.get(i).end()) ) {
						System.out.println("        Doing the Emtpy to Base Check: "+j+": against Platform: "+i);
						if (bestPoint != null) {
							if (bestPoint.start() < platforms.get(j).start()) {
								continue;
							}
						}
						
						System.out.println("        Special Case - hits bottom until: "+j+"pointprog:"+point_progress);
						System.out.println("        LOOPPLAT START: "+platforms.get(j).start());
						//Set the best point
						bestPoint = new Position(point_progress, platforms.get(j).start(), platforms.get(i).height());
						//System.out.println(bestPoint);
						System.out.println("        bestpoint at special "+bestPoint);
						continue;	
					}
				}
				
				//If BestPoint is set -  it found the best point so far for this current point
				//--System.out.println("BEST POINT IS: "+bestPoint);
				
				//3. If not set it means the current point until some x is jus2t height of main platform
				if (bestPoint == null) {
					
					System.out.println("    ITS NULL OH SHITT");
					//Takes up the whole thing to the very bottom no things underneath
					bestPoint = new Position(point_progress,
							platforms.get(i).end(),platforms.get(i).height());
				}
				
				
				
				Position newPoint;
				System.out.println("POOOOP bestX: "+bestX+" bestPOINT is: "+bestPoint);
				System.out.println("BESTXHEIGHTIS: "+bestXHeight);
				System.out.println(platforms.get(i).height());
				//double diffofbestxheight = ;
				//if () {}
					//Do the greater than dance
					
				
				
				if (bestX == null || (bestPoint.end() < bestX ) || (platforms.get(i).height()-bestXHeight) > bestPoint.height()) {
					
					newPoint = bestPoint;
					
				} else {
					newPoint = new Position(bestPoint.start(), bestX, bestPoint.height() );
				}
				//1. Add the point for the platform
				presult.add(newPoint);
				System.out.println("    NEW POINT ADDED: "+newPoint);
				//2. set the new current point's start
				point_progress = newPoint.end();
				
				//--System.out.println("POINT PROG AT END "+point_progress);
			}
			
			//Add Platform and points to Map
			result.put(platforms.get(i), presult);
			
		}
		
		//Return the Map Object with points
		System.out.println(result+"\n");
		return result;
		
			
	}
	
	
		
}
