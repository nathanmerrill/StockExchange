Your submission needs to extend `com.ppcg.stockexchange.Player`.  You can use the following player as a starting point:

    import java.util.List;
    import com.ppcg.stockexchange.*;
    public class YourBot extends Player {
        public Offer acceptOffer(List<Offer> offers) {
            return null;
        }
        public Offer makeOffer(List<Stock> currentStock) {
            return null;
        }
        public void secretValue(int stockType, double price) {
            super.secretValue(stockType, price);
        }
    }

Make sure to replace `YourBot` with your bot's name, and name the file `YourBot.java`.  Your submission should be placed inside the `/submissions/java` folder.

If you want to use randomness for your bot, you can call `getRandom()` to get a `Random` object.