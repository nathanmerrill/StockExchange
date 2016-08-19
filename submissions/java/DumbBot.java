import java.util.List;
import com.ppcg.stockexchange.*;
public class DumbBot extends Player {
    public Offer acceptOffer(List<Offer> offers) {
        return null;
    }
    public Offer makeOffer(List<Stock> currentStock) {
        return null;
    }
    public void secretValue(int stockType, double price) {
        super.secretValue(stockType, price);
    }
    public void acceptedOffers(int stockType, double price) {
        super.secretValue(stockType, price);
    }
}