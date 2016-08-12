package game;


import KoTHComm.messaging.PipeCommunicator;
import KoTHComm.messaging.SerializedCommunicator;
import KoTHComm.messaging.serialization.ListSerializer;

import java.util.List;

public class PipeBot extends Player{
    private final PipeCommunicator pipeCommunicator;
    private final SerializedCommunicator<List<Offer>, Offer> acceptCommunicator;
    private final SerializedCommunicator<List<Stock>, Offer> makeCommunicator;

    public PipeBot(PipeCommunicator pipeCommunicator){
        this.pipeCommunicator = pipeCommunicator;
        acceptCommunicator = new SerializedCommunicator<>(pipeCommunicator, new ListSerializer<>(new OfferSerializer()), new OfferSerializer());
        makeCommunicator = new SerializedCommunicator<>(pipeCommunicator, new ListSerializer<>(new StockSerializer()), new OfferSerializer());
    }

    @Override
    public Offer acceptOffer(List<Offer> offers) {
        return acceptCommunicator.sendMessage(offers, "AcceptOffer");
    }

    @Override
    public void stockValue(int stock, double price) {
        pipeCommunicator.sendMessage(stock+":"+price, "StockValue");
    }

    @Override
    public Offer makeOffer() {
        return makeCommunicator.sendMessage(this.ownedStock, "MakeOffer");
    }
}
