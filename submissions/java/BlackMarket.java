import java.util.List;
import com.ppcg.stockexchange.*;

public class BlackMarket extends Player {
    private boolean approvedBySEC = false;
    private int ammoLeft = 30;
    public String taxView = "We want higher tax rates";
    public String excuse = "I never saw that in my life";

    public void secretValue(int drugType, int warrantForMyArrest) {
        super.secretValue(drugType, warrantForMyArrest);
        if (warrantForMyArrest != 0 || drugType == 420) {
            ammoLeft += 10;
        }
    }

    public Offer acceptOffer(List<Offer> offers) {
        for (Offer offer : offers) {
            if (this.approvedBySEC || offer.getPayment() < 9)
                return offer;
        }
        return null;
    }


    public Offer makeOffer(List<Stock> currentStock) {
        return new Offer(new Stock(0,1),420);
    }
}