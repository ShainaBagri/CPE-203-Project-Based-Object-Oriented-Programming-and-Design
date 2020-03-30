import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LogAnalyzer
{
      //constants to be used when pulling data out of input
      //leave these here and refer to them to pull out values
   private static final String START_TAG = "START";
   private static final int START_NUM_FIELDS = 3;
   private static final int START_SESSION_ID = 1;
   private static final int START_CUSTOMER_ID = 2;
   private static final String BUY_TAG = "BUY";
   private static final int BUY_NUM_FIELDS = 5;
   private static final int BUY_SESSION_ID = 1;
   private static final int BUY_PRODUCT_ID = 2;
   private static final int BUY_PRICE = 3;
   private static final int BUY_QUANTITY = 4;
   private static final String VIEW_TAG = "VIEW";
   private static final int VIEW_NUM_FIELDS = 4;
   private static final int VIEW_SESSION_ID = 1;
   private static final int VIEW_PRODUCT_ID = 2;
   private static final int VIEW_PRICE = 3;
   private static final String END_TAG = "END";
   private static final int END_NUM_FIELDS = 2;
   private static final int END_SESSION_ID = 1;

      //a good example of what you will need to do next
      //creates a map of sessions to customer ids
   private static void processStartEntry(
      final String[] words,
      final Map<String, List<String>> sessionsFromCustomer)
   {
      if (words.length != START_NUM_FIELDS)
      {
         return;
      }

         //check if there already is a list entry in the map
         //for this customer, if not create one
      List<String> sessions = sessionsFromCustomer
         .get(words[START_CUSTOMER_ID]);
      if (sessions == null)
      {
         sessions = new LinkedList<>();
         sessionsFromCustomer.put(words[START_CUSTOMER_ID], sessions);
      }

         //now that we know there is a list, add the current session
      sessions.add(words[START_SESSION_ID]);
   }

      //similar to processStartEntry, should store relevant view
      //data in a map - model on processStartEntry, but store
      //your data to represent a view in the map (not a list of strings)
   private static void processViewEntry(final String[] words,
      /* add parameters as needed */
		   final Map<String, List<View>> viewsFromSession)
   {
      if (words.length != VIEW_NUM_FIELDS)
      {
         return;
      }

         //check if there already is a list entry in the map
         //for this session, if not create one
      List<View> views = viewsFromSession
         .get(words[VIEW_SESSION_ID]);
      if (views == null)
      {
         views = new LinkedList<>();
         viewsFromSession.put(words[VIEW_SESSION_ID], views);
      }

         //now that we know there is a list, add the current product
      views.add(new View(words[VIEW_PRODUCT_ID], words[VIEW_PRICE]));
   }

      //similar to processStartEntry, should store relevant purchases
      //data in a map - model on processStartEntry, but store
      //your data to represent a purchase in the map (not a list of strings)
   private static void processBuyEntry(final String[] words,
      /* add parameters as needed */
		   final Map<String, List<Buy>> buysFromSession)
   {
      if (words.length != BUY_NUM_FIELDS)
      {
         return;
      }

         //check if there already is a list entry in the map
         //for this session, if not create one
      List<Buy> buys = buysFromSession
         .get(words[BUY_SESSION_ID]);
      if (buys == null)
      {
         buys = new LinkedList<>();
         buysFromSession.put(words[BUY_SESSION_ID], buys);
      }

         //now that we know there is a list, add the current product
      buys.add(new Buy(words[BUY_PRODUCT_ID], words[BUY_PRICE], words[BUY_QUANTITY]));
   }

   private static void processEndEntry(final String[] words)
   {
      if (words.length != END_NUM_FIELDS)
      {
         return;
      }
   }

      //this is called by processFile below - its main purpose is
      //to process the data using the methods you write above
   private static void processLine(
      final String line,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      )
   {
      final String[] words = line.split("\\h");

      if (words.length == 0)
      {
         return;
      }

      switch (words[0])
      {
         case START_TAG:
            processStartEntry(words, sessionsFromCustomer);
            break;
         case VIEW_TAG:
            processViewEntry(words, viewsFromSession);
            break;
         case BUY_TAG:
            processBuyEntry(words, buysFromSession);
            break;
         case END_TAG:
            processEndEntry(words);
            break;
      }
   }
   
   private static void printAverageViewsWithoutPurchase(
		   Map<String, List<String>> sessionsFromCustomer,
		   Map<String, List<Buy>> buysFromSession,
		   Map<String, List<View>> viewsFromSession) {
	   double session_id_num = 0;
	   double view_sum = 0;
	   //loops through viewsFromSession hash map
	   for(Map.Entry<String, List<View>> views: 
	          viewsFromSession.entrySet()) {
		   //checks whether session id for views is also a session id in buysFromSession
		   if(!buysFromSession.containsKey(views.getKey())) {
			   view_sum += views.getValue().size();
			   session_id_num ++;
		   }
	   }
	   //loops through all session id's in sessionsFromCustomer hash map
	   for(Map.Entry<String, List<String>> sessions: sessionsFromCustomer.entrySet()) {
		   for(String session_id: sessions.getValue()) {
			   //checks for session id's that are not in buysFromSession nor viewsFromSession
			   //takes care of situation where customer doesn't do anything in session
			   if(!buysFromSession.containsKey(session_id) && !viewsFromSession.containsKey(session_id)) {
				   session_id_num++;
			   }
		   }
	   }
	   //finds average of total views/total session id's
	   double avg = view_sum/session_id_num;
	   System.out.println("Average Views without Purchase: " 
			   + avg);
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printSessionPriceDifference(
		   Map<String, List<Buy>> buysFromSession,
		   Map<String, List<View>> viewsFromSession
      /* add parameters as needed */
      )
   {
      System.out.println("Price Difference for Purchased Product by Session");
      //loops through buysFromSession
      for(Map.Entry<String, List<Buy>> buys: 
          buysFromSession.entrySet()) {
    	  double view_num = 0;
          double view_sum = 0;
    	  String sessionID = buys.getKey();
    	  System.out.println(sessionID);
    	  //loops through views list that corresponds with each session id from buys hash map
    	  for(View v: viewsFromSession.get(sessionID)) {
    		  view_num++;
    		  view_sum += v.getPrice();
    	  }
    	  double view_avg = view_sum/view_num;
    	  //loops through buys list and prints each value
    	  for(Buy b: buys.getValue()) {
    		  System.out.println("\t" + b.getProduct() + " " +
    				  (b.getPrice()-view_avg));
    	  }
      }
      /* add printing */
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printCustomerItemViewsForPurchase(
		   Map<String, List<String>> sessionsFromCustomer,
		   Map<String, List<Buy>> buysFromSession,
		   Map<String, List<View>> viewsFromSession
      /* add parameters as needed */
      )
   {
      System.out.println("Number of Views for Purchased Product by Customer");
      //loops through sessionsFromCustomer hash map
      for(Map.Entry<String, List<String>> sessions: 
          sessionsFromCustomer.entrySet()) {
    	  String customer = sessions.getKey();
    	  boolean customerPrint = false;
    	  //loops through each session id in list of session id's for each customer
    	  for(String s: sessions.getValue()) {
    		  //checks if product has been bought in that session
    		  if(buysFromSession.containsKey(s)) {
    			  if(!customerPrint) {
    				  System.out.println(customer);
    				  customerPrint = true;
    			  }
    			  //loops through buys in that session
    			  for(Buy b: buysFromSession.get(s)) {
    				  int view_num = 0;
    				  //loops through viewsFromSession
    				  for(Map.Entry<String, List<View>> views: 
    			          viewsFromSession.entrySet()) {
    					  String view_session = views.getKey();
    					  //loops through views in view list
        				  for(View v: views.getValue()) {
        					  //checks if the same product that was bought has been viewed in one of the
        					  //specific customer's sessions
        					  if(sessions.getValue().contains(view_session)&& v.getProduct().equals(b.getProduct())) {
        						  view_num++;
        						  break;
        					  }
        				  }
    				  }
    				  System.out.println("\t" + b.getProduct() + " " + view_num);
    			  }
    		  }
    	  }
      }
      /* add printing */
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printStatistics(
		   Map<String, List<String>> sessionsFromCustomer,
		   Map<String, List<Buy>> buysFromSession,
		   Map<String, List<View>> viewsFromSession
      /* add parameters as needed */
      )
   {
	   printAverageViewsWithoutPurchase(sessionsFromCustomer, buysFromSession,
	    		  viewsFromSession);
	   System.out.println();
      printSessionPriceDifference(buysFromSession,
    		  viewsFromSession
    		  /*add arguments as needed */);
      System.out.println();
      printCustomerItemViewsForPurchase(sessionsFromCustomer,
    		  buysFromSession,
    		  viewsFromSession
    		  /*add arguments as needed */);

      /* This is commented out as it will not work until you read
         in your data to appropriate data structures, but is included
         to help guide your work - it is an example of printing the
         data once propogated 
         printOutExample(sessionsFromCustomer, viewsFromSession, buysFromSession);
      */
   }

   /* provided as an example of a method that might traverse your
      collections of data once they are written 
      commented out as the classes do not exist yet - write them! */
/*
   private static void printOutExample(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
   {
      //for each customer, get their sessions
      //for each session compute views
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
         System.out.println(entry.getKey());
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
            System.out.println("\tin " + sessionID);
            List<View> theViews = viewsFromSession.get(sessionID);
            for (View thisView: theViews)
            {
               System.out.println("\t\tviewed " + thisView.getProduct());
            }
         }
      }
   }
*/

      //called in populateDataStructures
   private static void processFile(
      final Scanner input,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
   {
      while (input.hasNextLine())
      {
         processLine(input.nextLine(), sessionsFromCustomer, viewsFromSession, buysFromSession
            /* add arguments as needed */ );
      }
   }

      //called from main - mostly just pass through important data structures
   private static void populateDataStructures(
      final String filename,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
      throws FileNotFoundException
   {
      try (Scanner input = new Scanner(new File(filename)))
      {
         processFile(input, sessionsFromCustomer, viewsFromSession, buysFromSession
            /* add arguments as needed */ );
      }
   }

   private static String getFilename(String[] args)
   {
      if (args.length < 1)
      {
         System.err.println("Log file not specified.");
         System.exit(1);
      }

      return args[0];
   }

   public static void main(String[] args)
   {
      /* Map from a customer id to a list of session ids associated with
       * that customer.
       */
      final Map<String, List<String>> sessionsFromCustomer = new HashMap<>();
      /* Map from a session id to a list of views associated with
       * that session id.
       */
      final Map<String, List<View>> viewsFromSession = new HashMap<>();
      /* Map from a session id to a list of buys associated with
       * that session id.
       */
      final Map<String, List<Buy>> buysFromSession = new HashMap<>();

      /* create additional data structures to hold relevant information */
      /* they will most likely be maps to important data in the logs */

      final String filename = getFilename(args);

      try
      {
         populateDataStructures(filename, sessionsFromCustomer, viewsFromSession, buysFromSession
            /* add parameters as needed */
            );
         printStatistics(sessionsFromCustomer, buysFromSession, viewsFromSession
            /* add parameters as needed */
            );
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }
}
