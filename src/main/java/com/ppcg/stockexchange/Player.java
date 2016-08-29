package com.ppcg.stockexchange;


import com.ppcg.kothcomm.game.AbstractPlayer;
import com.ppcg.kothcomm.utils.Tools;

import java.util.List;

public abstract class Player extends AbstractPlayer<Player> {
    protected List<Stock> currentStock;
    protected int secretStockType;
    protected int secretStockValue;
    /**
     * @param offers All available offers
     * @return An offer you want to accept, or null if you want to accept neither.
     */
    public abstract Offer acceptOffer(List<Offer> offers);

    /**
     * Allows you to place an offer on the market.  Called once per round.
     * If you return null, no offer will be given.
     * @return The offer you would like to make this round
     */
    public abstract Offer makeOffer(List<Stock> currentStock);

    /**
     * Shows you all of the accepted offers
     */
    public void acceptedOffers(List<Offer> acceptedOffers){

    }

    /**
     * Informs you of the real value of a single stock.  Only called once at the beginning of the game
     * @param stockType The stock number
     * @param value The cash-out price of the indicated stock
     */
    public void secretValue(int stockType, int value){
        this.secretStockType = stockType;
        this.secretStockValue = value;
    }

    public final void setCurrentStock(List<Stock> stock){
        currentStock = stock;
    }

    protected int getCurrentStock(int stockType){
        return currentStock.get(stockType).getAmount();
    }

    public Stock randomStock(){
        return Tools.sample(currentStock, getRandom());
    }
}
