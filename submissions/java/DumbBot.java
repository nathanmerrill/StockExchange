import java.util.List;
import com.ppcg.stockexchange.*;
public class DumbBot extends Player {
    public Offer acceptOffer(List<Offer> offers) {
        return null;
    }
    public Offer makeOffer(List<Stock> currentStock){
        return new Offer(currentStock.get(secretStockType).setAmount(1), Math.max(1, secretStockValue - 5));
    }
    public void secretValue(int stockType, int value) {
        super.secretValue(stockType, value);
    }
    public void acceptedOffers(List<Offer> acceptedOffers) {
    }
}