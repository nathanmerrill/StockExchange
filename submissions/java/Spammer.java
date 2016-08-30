import java.util.List;
import java.util.ArrayList;
import com.ppcg.stockexchange.*;

public class Spammer extends Player {
    private boolean panic = false;

    public Offer acceptOffer(List<Offer> offers) {
        for (Offer offer : offers) {
            if (this.panic || offer.getPayment() < 20)
                return offer;
        }
        return null;
    }
    public Offer makeOffer(List<Stock> currentStock) {
        if (currentStock.size() > 1) { // Don't sell all the stock
            this.panic = false;
            return new Offer(currentStock.get(secretStockType).setAmount(1), 1);
        }
        this.panic = true; // BUY
        return null;
    }
}