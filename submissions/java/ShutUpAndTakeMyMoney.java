import java.util.List;
import com.ppcg.stockexchange.*;

public class ShutUpAndTakeMyMoney extends Player {
    public ShutUpAndTakeMyMoney() {}

    public Offer acceptOffer(List<Offer> offers) {
        try {
            return offers.get(0);
        } catch (Exception ex) {
            return null;
        }
    }
    public Offer makeOffer(List<Stock> stock) {
        return null;
    }
}