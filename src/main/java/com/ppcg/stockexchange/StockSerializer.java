package com.ppcg.stockexchange;


import com.ppcg.kothcomm.messaging.serialization.Serializer;

public class StockSerializer implements Serializer<Stock> {
    public final static String SEPARATOR = ":";
    @Override
    public Stock deserialize(String representation) {
        try {
            String[] parts = representation.split(SEPARATOR);
            return new Stock(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } catch (NumberFormatException|ArrayIndexOutOfBoundsException e){
            throw new RuntimeException("Expected input in format 'Integer:Integerr', but got "+representation);
        }
    }

    @Override
    public String serialize(Stock value) {
        return value.getType()+SEPARATOR+value.getAmount();
    }
}
