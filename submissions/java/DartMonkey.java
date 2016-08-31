import com.ppcg.stockexchange.Offer;
import com.ppcg.stockexchange.Player;
import com.ppcg.stockexchange.Stock;

import java.util.List;
import java.util.Random;

public class DartMonkey extends Player {
    private int basePrice = 100;
    private int numStocks;
    private int[] dartBoard;
    private boolean first = true;

    @Override
    public Offer acceptOffer(List<Offer> offers) {
        for(Offer offer : offers) {
            Stock stock = offer.getOffer();
            int type = stock.getType();
            int amount = stock.getAmount();
            int price = offer.getPayment();
            if(this.dartBoard[type] < 0 && amount <= -this.dartBoard[type] && price <= this.dartBoard[type + this.numStocks]) {
                this.dartBoard[type] = 0;
                return offer;
            }
        }
        return null;
    }

    @Override
    public Offer makeOffer(List<Stock> stocks) {
        if(this.first) {
            this.first = false;
            this.numStocks = stocks.size();
            this.dartBoard = new int[this.numStocks * 2];
            Random random = this.getRandom();
            for (int i = 0; i < 20; i++) {
                int index = random.nextInt(this.dartBoard.length / 2);
                this.dartBoard[index] = random.nextInt(1001);
                this.dartBoard[this.numStocks + index] = random.nextInt(1001);
            }

            for (int i = 0; i < 20; i++) {
                int index = random.nextInt(this.dartBoard.length / 2);
                this.dartBoard[index] = -random.nextInt(1001);
                this.dartBoard[this.numStocks + index] = random.nextInt(1001);                
            }
        }

        for (Stock stock : stocks) {
            int type = stock.getType();
            if(this.dartBoard[type] > 0) {
                Offer offer = new Offer(stock.setAmount(this.dartBoard[type]), this.basePrice + this.dartBoard[type + this.numStocks]);
                this.dartBoard[type] = 0;
                this.dartBoard[type + this.numStocks] = 0;
                return offer;
            }
        }

        return null;
    }

}