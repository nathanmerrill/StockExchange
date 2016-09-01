import java.util.List;

import com.ppcg.stockexchange.*;

public class InsideTrader extends Player {
    public String coverStory = "I can tell the good companies from the bad ones.";
    private String theTruth = "I'm cheating. (but so is everyone else)";
    private String ambitions = "Learn to \"follow the market\"";  // don't steal this idea
    private int secretStock = -1;
    private int secretStockValue = -1;

    private int appraiseOffer(Offer offer) {
        /* get how much the offer is worth, 0 if it's not the secret stock */
        if (offer.getOffer().getType() != secretStock ||offer.getOffer().getAmount() == 0) {
            return 0;
        }
        return (offer.getPayment()/offer.getOffer().getAmount())  // price per stock...
                - secretStockValue  // minus value of stock.
                ;
    }
    public Offer acceptOffer(List<Offer> offers) {
        Offer bestOffer = null;
        int bestOfferValue = -1;
        for (Offer offer :
                offers) {
            int value = appraiseOffer(offer);
            if (value > bestOfferValue && value > 0) {
                bestOfferValue = value;
                bestOffer = offer;
            }
        }
        return bestOffer;
    }

    public Offer makeOffer(List<Stock> currentStock) {
        return new Offer(new Stock(0,1), Integer.MAX_VALUE);
    }

    public void secretValue(int stockType, int value) {
        secretStock = stockType;
        secretStockValue = value;
    }

    public void acceptedOffers(List<Offer> acceptedOffers) {

    }
}