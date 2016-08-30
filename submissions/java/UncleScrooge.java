import java.util.List;
import com.ppcg.stockexchange.*;

public class UncleScrooge extends Player {
    public Offer acceptOffer(List<Offer> offers) {
        Offer offer;
        try {
            offer = offers.get(0);
        } catch (Exception ex) {
            return null;
        }
        if (offer.getPayment() < 100)
            return offer;
        else
            return null;
    }
    public Offer makeOffer(List<Stock> currentStock){
        if (this.getRandom().nextDouble() < 0.6)
            return new Offer(currentStock.get(secretStockType).setAmount(1), Integer.MAX_VALUE);
        else
            return null;
    }
    public void secretValue(int stockType, int value) {
        super.secretValue(stockType, value);
    }
    public void acceptedOffers(List<Offer> acceptedOffers) { }
}