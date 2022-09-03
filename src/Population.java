import java.util.*;

public class Population {
    static double stable_population = 0.05;  //value to check/compare the difference in gains of each year & conclude if pop. stabilizes
    static int a; //a,b,c are the input to calculate gain of each of 4 type of people
    static int b;
    static int c;
    static int philanderer;
    static int faithful;
    static int coy;
    static int fast;
    boolean flag1 = false; // flag value to display stable/ not stable populations message

    public Population(int a, int b, int c, int philanderer, int faithful, int coy, int fast) {
        try{
            if (philanderer<0 || faithful<0 || fast<0 || coy<0){
                throw new myexception();
            }
        } catch (Exception e) {
            e.getMessage();
            System.exit(0);
        }

        //calculating gains (profit) according to formula given in project description
        int profit_coy_philanderer_coy = 0;
        int profit_coy_philanderer_philanderer = 0;
        int profit_coy_faithful_coy = a - (b / 2) - c;
        int profit_coy_faithful_faithful = a - b / 2 - c;
        int profit_fast_faithful_fast = a - b / 2;
        int profit_fast_faithful_faithful = a - b / 2;
        int profit_fast_philanderer_fast = a - b;
        int profit_fast_philanderer_philanderer = a;
        this.a = a;
        this.b = b;
        this.c = c;
        this.philanderer = philanderer;
        this.faithful = faithful;
        this.coy = coy;
        this.fast = fast;
        // using 0 for philanderer ,1 for faithful , 2 for coy,3 for fast//
        //creating 4 arraylists to append 0,1,2,3 acc to no. of each type of people from input
        ArrayList<Integer> array_philanderer = new ArrayList<Integer>(Collections.nCopies(philanderer, 0));
        ArrayList<Integer> array_faithful = new ArrayList<Integer>(Collections.nCopies(faithful, 1));
        ArrayList<Integer> array_coy = new ArrayList<Integer>(Collections.nCopies(coy, 2));
        ArrayList<Integer> array_fast = new ArrayList<Integer>(Collections.nCopies(fast, 3));

        //merging the 2 types of men into 1 array to get increase randomness in picking men for mating, same for women below
        ArrayList<Integer> men = new ArrayList<>();
        men.addAll(array_faithful);
        men.addAll(array_philanderer);
        ArrayList<Integer> women = new ArrayList<>();
        women.addAll(array_coy);
        women.addAll(array_fast);

        Random rand = new Random();


        int count_coy = Collections.frequency(women, 2);
        int count_fast = Collections.frequency(women, 3);
        int count_faithful = Collections.frequency(men, 1);
        int count_philanderer = Collections.frequency(men, 0);

        //calculating avg(total) gain of each type of people acc. to specification in project description (By taking ratio of profit to
        //composition of that type of men/women
        double avg_gain_fast = (count_faithful / (double) men.size()) * profit_fast_faithful_fast + (count_philanderer / (double) men.size()) * profit_fast_philanderer_fast;
      //       avg_gain_fast = (count_faithful / (double) men.size()) * profit_fast_faithful_fast + (count_philanderer / (double) men.size()) * profit_fast_philanderer_fast;
        double avg_gain_coy = (count_faithful / (double) men.size()) * profit_coy_faithful_coy + (count_philanderer / (double) men.size()) * profit_coy_philanderer_coy;
        double avg_gain_philanderer = (count_coy / (double) women.size()) * profit_coy_philanderer_philanderer + (count_fast / (double) women.size()) * profit_fast_philanderer_philanderer;
        double avg_gain_faithful = (count_coy / (double) women.size()) * profit_coy_faithful_faithful + (count_fast / (double) women.size()) * profit_fast_faithful_faithful;

        //mating
        int y = 0;
        while (y <= 10000) {    //we can give in input the years we want !!!!!!!!!!!!!!!
            int index_men = (int) (Math.random() * men.size());
            int index_women = (int) (Math.random() * women.size());
            int w = 0;
            int m=0;
            try {
                m = men.get(index_men);
                w = women.get(index_women);
            } catch (Exception e) {
                System.out.println("Sorry population vanished");
                System.exit(0);
            }

            int child = rand.nextInt(2);
            if (w == 3) {
                //int i = women.indexOf(w);
                //women.set(i, 5);
                //int child = rand.nextInt(2);
                if (child == 0) {   //1 then add in women else add in men
                    if (avg_gain_faithful > avg_gain_philanderer) {
                        //int j = men.indexOf(m);
                        //men.set(j, 5);
                        men.add(0);

                    } else {
                        //int j = men.indexOf(m);
                        //men.set(j, 5);
                        men.add(1);

                    }

                } else {
                    if (avg_gain_coy > avg_gain_fast) {
                        women.add(3);

                    } else {
                        women.add(2);

                    }
                }
                if ((w == 2) && (m == 1)) {
                    //child = rand.nextInt(2);
                    if (child == 0) {
                        //int j = men.indexOf(m);
                        //men.set(j, 5);
                        men.add(1);

                    } else {
                        //int x = women.indexOf(w);
                        //women.set(x, 5);
                        women.add(2);

                    }
                }
            }

            count_coy = Collections.frequency(women, 2);
            count_fast = Collections.frequency(women, 3);
            count_faithful = Collections.frequency(men, 1);
            count_philanderer = Collections.frequency(men, 0);
            avg_gain_fast = (count_faithful / (double) men.size()) * profit_fast_faithful_fast + (count_philanderer / (double) men.size()) * profit_fast_philanderer_fast;
            avg_gain_coy = (count_faithful / (double) men.size()) * profit_coy_faithful_coy + (count_philanderer / (double) men.size()) * profit_coy_philanderer_coy;
            avg_gain_philanderer = (count_coy / (double) women.size()) * profit_coy_philanderer_philanderer + (count_fast / (double) women.size()) * profit_fast_philanderer_philanderer;
            avg_gain_faithful = (count_coy / (double) women.size()) * profit_coy_faithful_faithful + (count_fast / (double) women.size()) * profit_fast_faithful_faithful;

            //checking if avg gains < value we chose to declare a stable growing population
            boolean b1 = Math.abs(avg_gain_fast - avg_gain_coy) <= stable_population;
            boolean b2 = Math.abs(avg_gain_faithful - avg_gain_philanderer) <= stable_population;

            if (b1 == true && b2 == true) {
                System.out.println("Stability reached after " + y + "years");
                flag1 = true;
                break;


            }

            //every 15 years,
            if (y % 15 == 0) {
                new death(men, women);


            }

            //har 10 saal me 5 vale 10 bnde nikal dene hai

            //System.out.println("stable population if");
            //System.out.println(Collections.frequency(women,2));
            //System.out.println(women.size());
            /*System.out.println("coy = " + (Collections.frequency(women, 2) / (double) women.size() * 100));
            System.out.println("fast = " + (Collections.frequency(women, 3) / (double) women.size()) * 100);
            System.out.println("philanderer = " + (Collections.frequency(men, 0) / (double) men.size()) * 100);
            System.out.println("faithful = " + (Collections.frequency(men, 1) / (double) men.size()) * 100);*/
            y++;

        }
        System.out.println(women.size());
        System.out.println(men.size());
        if (flag1 == false){
            System.out.println("stability NOT reached even after "+ (y-1) +"years ");
        }

        //printing the % composition of the population after reaching stability OR if stability is not reached at all even after
        //the number of years(iterations) we decided to run
        System.out.println("coy = " + (Collections.frequency(women, 2) / (double) women.size() * 100));
        System.out.println("fast = " + (Collections.frequency(women, 3) / (double) women.size()) * 100);
        System.out.println("philanderer = " + (Collections.frequency(men, 0) / (double) men.size()) * 100);
        System.out.println("faithful = " + (Collections.frequency(men, 1) / (double) men.size()) * 100);
    }
}