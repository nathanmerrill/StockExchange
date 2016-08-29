package com.ppcg.stockexchange;

import com.ppcg.kothcomm.messaging.serialization.IntegerSerializer;
import com.ppcg.kothcomm.messaging.serialization.Serializer;

public class OfferSerializer implements Serializer<Offer> {
    public final static String SEPARATOR = "@";
    private final StockSerializer stock;
    private final IntegerSerializer integer;
    public OfferSerializer(){
        stock = new StockSerializer();
        integer = new IntegerSerializer();
    }
    @Override
    public String serialize(Offer value) {
        return stock.serialize(value.getOffer())+SEPARATOR+integer.serialize(value.getPayment());
    }

    @Override
    public Offer deserialize(String representation) {
        String[] parts = representation.split(SEPARATOR);
        return new Offer(stock.deserialize(parts[0]), integer.deserialize(parts[1]));
    }
}
