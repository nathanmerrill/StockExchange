import java.util.List;
import com.ppcg.stockexchange.*;
public class MaxBot extends Player {
    int toSell;
    int sellPrice;

    public void secretValue(int stockType, int value) {
        super.secretValue(stockType, value);
        toSell = stockType;
        sellPrice = (value + 1000)/2;
    }
    public Offer acceptOffer(List<Offer> offers) {
        Offer max = null;
        int maxDif = 0;
        for(Offer o: offers){
            int price = secretStockType == o.getOffer().getType()? secretStockValue: 250;
            int val = price * o.getOffer().getAmount();
            int dif = val - o.getPayment();
            if(maxDif < dif){
                max = o;
                maxDif = dif;
            }
        }
        return max;
    }
    public Offer makeOffer(List<Stock> currentStock){
        if(toSell == -1){
            return null;
        }
        int sum = 0;
        for (Stock s: currentStock){
            if(s.getType() == toSell){
                sum += s.getAmount;
            }
        }
        int n = sum - sum/2;
        return new Offer(new Stock(toSell, n), n * sellPrice);
    }
    public void acceptedOffers(List<Offer> acceptedOffers) {
        int highStock = -1;
        int highPrice = 0;
        int markup = 0;
        for(Offer o: offers){
            int trueVal = secretStockType == o.getOffer().getType()? secretStockValue: 250;
            int marketVal = o.getPayment()/o.getOffer().getAmount();
            if(marketVal - trueVal > markup){
                highStock = o.getOffer().getType();
                highPrice = marketVal;
                markup = marketVal - trueVal;
            }
        }
        toSell = highStock;
    }
}