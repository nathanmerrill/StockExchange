package com.ppcg.stockexchange;

import com.ppcg.kothcomm.game.RepeatedGame;
import com.ppcg.kothcomm.game.scoreboards.AggregateScoreboard;
import com.ppcg.kothcomm.utils.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StockExchange extends RepeatedGame<Player> {
    public static final int INITIAL_STOCK_QUANTITY = 1000;
    public static final int MAX_STOCK_VALUE = 1000;
    public static final int NUM_EXCHANGES = 1000;

    private List<Integer> prices;

    private Map<Player, List<Stock>> stockMarket;
    private Map<Player, Long> networth;
    private int numStocks;

    public StockExchange(){
        super(NUM_EXCHANGES);
    }

    @Override
    public void setup() {
        numStocks = players.size();
        initPrices();
        initStockMarket();
        givePlayersPrivateInfo();
    }

    private void initPrices(){
        prices = Stream.generate(() ->
                (int) Math.ceil(Math.pow(random.nextDouble(), 2)*1000))
                .limit(numStocks).collect(Collectors.toList());
    }

    private void initStockMarket(){
        stockMarket = new HashMap<>();
        List<Stock> initialStock = new ArrayList<>();
        for (int i = 0; i< numStocks; i++){
            initialStock.add(new Stock(i, INITIAL_STOCK_QUANTITY));
        }
        players.forEach(p -> stockMarket.put(p, new ArrayList<>(initialStock)));
        networth = players.stream().collect(Collectors.toMap(Function.identity(), p -> 0L));
    }

    private void givePlayersPrivateInfo(){
        for (int i = 0; i < numStocks; i++){
            players.get(i).secretValue(i, prices.get(i));
        }
    }

    @Override
    public AggregateScoreboard<Player> getScores() {
        AggregateScoreboard<Player> scoreboard = new AggregateScoreboard<>();
        players.forEach(p -> scoreboard.addScore(p, getNetWorth(p)));
        return scoreboard;
    }

    private double getNetWorth(Player player){
        return stockMarket.get(player).stream().mapToDouble(this::stockValue).sum()
                + networth.get(player);
    }

    private double stockValue(Stock stock){
        return prices.get(stock.getType())*stock.getAmount();
    }

    private void updatePlayersStocks(){
        players.forEach(player -> player.setCurrentStock(new ArrayList<>(stockMarket.get(player))));
    }

    private List<Pair<Player, Offer>> getPlayerOffers(){
        return players.stream()
                .map(Pair.fromValue(p -> p.makeOffer(new ArrayList<>(stockMarket.get(p)))))
                .filter(p -> p.second() != null)
                .filter(p -> canPay(p.first(), p.second().getOffer()))
                .collect(Collectors.toList());
    }

    private boolean canPay(Player player, Stock stock){
        return stock.getAmount() <= stockMarket.get(player).get(stock.getType()).getAmount();
    }

    private Pair<Player, Offer> giveOffers(Player player, List<Pair<Player, Offer>> availableOffers){
        Offer accepted = player.acceptOffer(availableOffers.stream().map(Pair::second).collect(Collectors.toList()));
        if (accepted == null){
            return null;
        }
        for (Pair<Player, Offer> available: availableOffers){
            if (accepted.equals(available.second())){
                return available;
            }
        }
        System.out.println(player.getName()+" tried to accept an invalid offer");
        return null;
    }

    @Override
    protected void nextStep() {
        updatePlayersStocks();
        List<Pair<Player, Offer>> currentOffers = getPlayerOffers();
        List<Offer> acceptedOffers = new ArrayList<>();
        Collections.shuffle(players);
        for (Player player: players){
            if (currentOffers.isEmpty()){
                break;
            }
            Pair<Player, Offer> accepted = giveOffers(player, new ArrayList<>(currentOffers));
            if (accepted == null){
                continue;
            }
            acceptedOffers.add(accepted.second());
            currentOffers.remove(accepted);
            exchange(accepted.second(), accepted.first(), player);
        }
        informAccepted(acceptedOffers);
    }

    private void informAccepted(List<Offer> accepted){
        players.forEach(player -> player.acceptedOffers(new ArrayList<>(accepted)));
    }

    private void exchange(Offer offer, Player offerer, Player accepter){
        if (!canPay(offerer, offer.getOffer())){
            return;
        }
        int stockType = offer.getOffer().getType();

        List<Stock> accepterStock = stockMarket.get(accepter);
        accepterStock.set(stockType, accepterStock.get(stockType).plus(offer.getOffer()));
        networth.put(accepter, networth.get(accepter)-offer.getPayment());

        List<Stock> offererStock = stockMarket.get(offerer);
        offererStock.set(stockType, accepterStock.get(stockType).minus(offer.getOffer()));
        networth.put(offerer, networth.get(offerer)+offer.getPayment());
    }

}

