
import java.util.ArrayList;
//removing a random man and a woman from the arraylist

public class death {
    public death(ArrayList<Integer> men, ArrayList<Integer> women) {
        int index_men = (int) (Math.random() * men.size());
        int index_women = (int) (Math.random() * women.size());
        men.remove(index_men);
        women.remove(index_women);


    }


}
