import com.ppcg.stockexchange.Offer;
import com.ppcg.stockexchange.Player;
import com.ppcg.stockexchange.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Profiteer extends Player {
    private List<StockInfo> onMarket;
    private List<StockInfo> stocks;
    private int money;
    private boolean first = true;

    @Override
    public Offer acceptOffer(List<Offer> offers) {
        Offer finalOffer;

        Optional<Offer> offer = offers.stream().filter(o -> o.getOffer().getType() == this.secretStockType && o.getPayment() < this.secretStockValue * o.getOffer().getAmount()).sorted((a, b) -> Integer.compare((this.secretStockValue * a.getOffer().getAmount()) - b.getPayment(), (this.secretStockValue * b.getOffer().getAmount()) - b.getPayment())).findFirst();
        if (offer.isPresent()) {
            finalOffer = offer.get();
        } else {
            finalOffer = offers.stream().sorted((a, b) -> Integer.compare(a.getPayment(), b.getPayment())).findFirst().orElse(null);
        }

        if (finalOffer == null || this.money <= finalOffer.getPayment()) {
            return null;
        } else {
            this.stocks.add(new StockInfo(finalOffer.getOffer(), finalOffer.getPayment()));
            this.refreshMoney();
            return finalOffer;
        }
    }

    @Override
    public Offer makeOffer(List<Stock> stocks) {
        if (this.first) {
            this.init(stocks);
        } else {
            this.refreshMarketList(stocks);
        }

        Optional<StockInfo> least = this.stocks.stream().sorted((a, b) -> Integer.compare(a.getBoughtPrice(), b.getBoughtPrice())).findFirst();
        Optional<StockInfo> secret = this.stocks.stream().filter(stockInfo -> stockInfo.getStock().getType() == this.secretStockType).sorted((a, b) -> Integer.compare(a.getBoughtPrice(), b.getBoughtPrice())).findFirst();

        StockInfo finalOffer;
        int price;
        if (secret.isPresent()) {
            finalOffer = secret.get();
        } else if (least.isPresent()) {
            finalOffer = least.get();
        } else {
            return null;
        }

        this.onMarket.add(finalOffer);
        this.stocks.remove(finalOffer);
        price = this.calculatePrice(finalOffer.boughtPrice);
        return new Offer(new Stock(finalOffer.getStock().getType(), finalOffer.getStock().getAmount()), price);
    }

    private int calculatePrice(int boughtPrice) {
        return (int) (boughtPrice + ((boughtPrice / (double) this.money) * this.money)) + 1;
    }

    private void refreshMarketList(List<Stock> stocks) {
        this.stocks.addAll(this.onMarket.stream().filter(stockInfo -> stocks.contains(stockInfo.getStock())).collect(Collectors.toList()));
        this.onMarket.clear();
    }

    private void refreshMoney() {
        this.money = this.stocks.stream().mapToInt(info -> this.secretStockType == info.getStock().getType() ? this.secretStockValue : 5).reduce((a, b) -> a + b).orElseGet(() -> 0) - this.stocks.stream().mapToInt(StockInfo::getBoughtPrice).reduce((a, b) -> a + b).orElseGet(() -> 0);
    }

    private void init(List<Stock> stocks) {
        this.stocks = stocks.stream().map(stock -> new StockInfo(stock, 0)).collect(Collectors.toList());
        this.onMarket = new ArrayList<>();
        this.money = 0;
        this.first = false;
        this.refreshMoney();
    }

    private static class StockInfo {
        private Stock stock;
        private int boughtPrice;

        public StockInfo(Stock stock, int boughtPrice) {
            this.stock = stock;
            this.boughtPrice = boughtPrice;
        }

        public Stock getStock() {
            return this.stock;
        }

        public int getBoughtPrice() {
            return this.boughtPrice;
        }

    }

}