package com.ppcg.stockexchange;


import com.ppcg.kothcomm.messaging.PipeCommunicator;
import com.ppcg.kothcomm.messaging.SerializedCommunicator;
import com.ppcg.kothcomm.messaging.serialization.ListSerializer;
import com.ppcg.kothcomm.messaging.serialization.OptionalSerializer;
import com.ppcg.kothcomm.messaging.serialization.VoidSerializer;

import java.util.List;
import java.util.Random;

public class PipeBot extends Player{
    private final PipeCommunicator pipeCommunicator;
    private final SerializedCommunicator<List<Offer>, Offer> acceptCommunicator;
    private final SerializedCommunicator<List<Stock>, Offer> makeCommunicator;
    private final SerializedCommunicator<List<Offer>, ?> acceptedCommunicator;

    public PipeBot(PipeCommunicator pipeCommunicator){
        this.pipeCommunicator = pipeCommunicator;
        acceptCommunicator = new SerializedCommunicator<>(pipeCommunicator,
                new ListSerializer<>(new OfferSerializer()),
                new OptionalSerializer<>(new OfferSerializer()));
        makeCommunicator = new SerializedCommunicator<>(pipeCommunicator,
                new ListSerializer<>(new StockSerializer()),
                new OptionalSerializer<>(new OfferSerializer()));
        acceptedCommunicator = new SerializedCommunicator<>(pipeCommunicator,
                new ListSerializer<>(new OfferSerializer()),
                new VoidSerializer());
    }

    @Override
    public void setRandom(Random random) {
        super.setRandom(random);
        pipeCommunicator.sendMessage(random.nextInt()+"", "RandomSeed");
    }

    @Override
    public Offer acceptOffer(List<Offer> offers) {
        return acceptCommunicator.sendMessage(offers, "AcceptOffer");
    }

    @Override
    public void acceptedOffers(List<Offer> acceptedOffers) {
        super.acceptedOffers(acceptedOffers);
        acceptedCommunicator.sendMessage(acceptedOffers, "AcceptedOffers");
    }

    @Override
    public void secretValue(int stock, int price) {
        super.secretValue(stock, price);
        pipeCommunicator.sendMessage(stock+":"+price, "SecretValue");
    }

    @Override
    public Offer makeOffer(List<Stock> currentStock) {
        return makeCommunicator.sendMessage(currentStock, "MakeOffer");
    }

}
