import java.util.List;
import java.util.Random;
import com.ppcg.stockexchange.*;

public class Cheater extends Player {
    private Random random = new Random();

    public Offer acceptOffer(List<Offer> offers) {
        return null;
    }

    public Offer makeOffer(List<Stock> currentStock){
        Stock stock = randomStock();
        int price = random.nextInt(100) + 1;
        return new Offer(stock.setAmount(0), price);
    }
}