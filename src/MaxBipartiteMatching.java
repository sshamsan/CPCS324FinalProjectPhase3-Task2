// https://algorithms.tutorialhorizon.com/maximum-bipartite-matching-problem/
/**
 * CPCS324 Final Project: Phase 3
 * Task 2: Maximum Bipartite Matching 
 * Shahad Shamsan
 * Suha Shafi 
 * Safia Aljahdali
 */

public class MaxBipartiteMatching {

    // Global variables used for printing
    static int finalMatching[][] = new int[6][6];
    static String applicantName[] = {"Ahmed", "Mahmoud", "Eman", "Fatimah", "Kamel", "Nojood"};
    static String hosptialN[] = {"King Abdulaziz Univeristy", "King Fahad", "East Jeddah"
            , "King Fahad Armed Forces", "King Faisal Specialist", "Ministry of National Guard"};
    
    // for printing each iteration when assigning applicantss
    static int iteration = 0;
    
    static class Graph {
        int hospitals;
        int applicants;
        
        // to store the graph before applying the algorithm
        int adjMatrix[][];

        /**
         * 
         * @param applicants to add applicants of the graph
         * @param hospitals to hospitals of the graph
         */
        public Graph(int applicants, int hospitals) {
            this.hospitals = hospitals;
            this.applicants = applicants;
            adjMatrix = new int[applicants][hospitals];
        }

        /**
         * Add edges for the applicants possible hospitals. 
         * the hospitals that the application can apply to 
         * @param applicant
         * @param job 
         */
        public void canDoResidency(int applicant, int job) {
            //add edge - means applicant can do this hospital
            adjMatrix[applicant][job] = 1;
        }
    }
    
    /**
     * 
     * @param graph
     * @return 
     */
    public int maxMatching(Graph graph) {
        int applicants = graph.applicants;
        int hospitals = graph.hospitals;

        //an array to track which hospital is assigned to which applicant
        int assign[] = new int[hospitals];
        
        for (int i = 0; i < hospitals; i++) {
            //initially set all hospitals are available
            assign[i] = -1;    
        }
        int hospitalCount = 0;

        //for all applicants
        for (int applicant = 0; applicant < applicants; applicant++) {
            
            //for each applicant, all hospitals will be not visited initially
            boolean visited[] = new boolean[hospitals];

            //check if applicant can get a hospital
            if (bipartiteMatch(graph, applicant, visited, assign)) {
                //if yes then increase the hospital count
                hospitalCount++;
            }
        }
        System.out.println("--------------------------------------------------\n");
        return hospitalCount;
    }

    /**
     * 
     * @param graph 
     * @param applicant 
     * @param visited to track if the applicant has been assigned to this hospital before
     * @param assign array to to track which hospital is assigned to which applicant
     * @return true if the applicant can get a hospital and false otherwise
     */
    boolean bipartiteMatch(Graph graph, int applicant, boolean visited[], int assign[]) {
        
        // check each hospital for the applicant
        for (int hospital = 0; hospital < graph.hospitals; hospital++) {
            // check if applicant can do this hospital
            // and has not applied for this hospital before
            
            if (graph.adjMatrix[applicant][hospital] == 1 && !visited[hospital]) {
                
                // mark as hospital is visited means 
                // applicant applied for this hospital
                visited[hospital] = true;
                
                // now check if hospital was not assigned earlier then assign it to this applicant
                // OR hospital was assigned earlier to some other applicant 'X' earlier then
                // make recursive call for applicant 'X' to check if some other hospital can be assigned
                // so that this hospital can be assigned to current candidate.
                int assignedApplicant = assign[hospital];
                
                if (assignedApplicant < 0 || bipartiteMatch(graph, assignedApplicant, visited, assign)) {
                    //assign hospital to applicant 
                    assign[hospital] = applicant;    
                    
                    // Print applicants and thier hospital assignment for each iteration
                    System.out.println("Iteration: " + (++iteration) );
                    System.out.println("Applicant: " + applicantName[applicant] 
                            +", Assigned to: " + hosptialN[hospital]+"\n");
                    
                    for (int i = 0; i < finalMatching.length; i++) {
                        // if the applicant has an old assigned hospital,
                        // clear the array before assigning the new hospital
                        // this happens in the case of changing a hospital for the applicant
                        if(finalMatching[applicant][i] != 0)
                            finalMatching[applicant][i] = 0;
                    }
                    
                    // Now assign hospital for each application (= 1)
                    // this 2D array is used for the final result
                   
                    finalMatching[applicant][hospital] = 1;
                    
                    return true;
                }
            }
            
        }
        return false;
    }

    /**
     * Driver Program,
     * Construct Graph with applicants and hospitals.
     * call maxMatching method to apply the Maximum Bipartite Matching
     * and then return the max number of applicants
     * 
     * @param args 
     */
    public static void main(String[] args) {
        // Construct Graph with applicants and hospitals
        int applicants = 6;
        int hospitals = 6;
        
        // Create graph object
        Graph graph = new Graph(applicants, hospitals);
        
        /**
         * Applicants        Hospitals
         * 0: Ahemd          0: KAU  
         * 1: Mahmoud        1: King Fahad
         * 2: Eman           2: East Jeddah
         * 3: Fatima         3: KFAF
         * 4: Kamel          4: King Faisal Specialist
         * 5: Nojood         5: Ministry of National Guard
         */
        
        // Add each applicant with thier possible Residencies' programs
        graph.canDoResidency(0, 0);
        graph.canDoResidency(0, 1);
        graph.canDoResidency(1, 5);
        graph.canDoResidency(2, 0);
        graph.canDoResidency(2, 3);
        graph.canDoResidency(3, 2);
        graph.canDoResidency(4, 3);
        graph.canDoResidency(4, 4);
        graph.canDoResidency(5, 5);

        // create an object m
        MaxBipartiteMatching m = new MaxBipartiteMatching();
        
        // call maxMatching method to apply the Maximum Bipartite Matching
        // and then return the max number of applicants
        System.out.println("Maximum number of applicants that "
                + "can be assigned to hospitals is: \n"
                + "------ "
                + m.maxMatching(graph)+" ------\n");
        
        // Print the final result for the Maximum Bipartite Matching
        // The applicants with thier assigned hospital
        System.out.println("---- Maximum Bipartite Matching ----\n");
        for (int i = 0; i < finalMatching.length; i++) {
            for (int j = 0; j < finalMatching.length; j++) {
                
                // has a value other than zero means it's assigned to a hospital
                if (finalMatching[i][j] != 0){ 
                    System.out.println(applicantName[i] + "'s Residiencey Postion will be at: \n" + hosptialN[j]);
                } 
            }
            System.out.println("");
        }
        

    }
}