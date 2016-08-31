import com.ppcg.stockexchange.Offer;
import com.ppcg.stockexchange.Player;
import com.ppcg.stockexchange.Stock;

import java.util.List;

public class VincentKasuga extends Player {
    private int knownStock;
    private int knownPrice;
    private int corneredStockType = -1;
    private int corneredLikelehood = 0;
    private boolean marketCornered;
    private int ticks;

    public Offer acceptOffer(List<Offer> offers) {
        if (!marketCornered) {
            Offer maxOffer = null;
            int maxAmount = 0;
            if (corneredStockType == -1) {
                for (Offer offer: offers) {
                    if (offer.getOffer().getAmount() > maxAmount) {
                        maxAmount = offer.getOffer().getAmount();
                        maxOffer = offer;
                    }
                }
            } else {
                for (Offer offer: offers) {
                    if (offer.getOffer().getAmount() > maxAmount && offer.getOffer().getType() == corneredStockType) {
                        maxAmount = offer.getOffer().getAmount();
                        maxOffer = offer;
                    }
                }
            }


            if (maxOffer == null) {
                // may have cornered the market
                corneredLikelehood++;
                if (corneredLikelehood == 5) {
                    // probably cornered the market
                    marketCornered = true;
                }
            }
            return maxOffer;
        } else {
            // who needs offers when the market is cornered!?
            return null;
        }
    }

    public Offer makeOffer(List<Stock> currentStock) {
        ticks++;
        if (ticks >= 999) {
            // SELL SELL SELL!
            return new Offer(new Stock(corneredStockType, 1000), 1000);
        } else {
            return null;
        }
    }

    public void secretValue(int stockType, int value) {
        knownStock = stockType;
        knownPrice = value;
        if (stockType == corneredStockType) {
            if (knownPrice == 1000) {
                corneredLikelehood += 3;
            } else if (knownPrice < 900){
                // didn't corner the market.
                corneredLikelehood = 0;
            }
        }
    }
}