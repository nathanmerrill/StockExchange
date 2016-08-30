import java.util.List;
import com.ppcg.stockexchange.*;
import com.ppcg.kothcomm.game.AbstractPlayer;
import com.ppcg.kothcomm.utils.Tools;

import java.util.List;

public class WarGamer extends Player {
static final boolean FRAUD = false;
    /**
     * @param offers All available offers
     * @return An offer you want to accept, or null if you want to accept neither.
     */
    public Offer acceptOffer(List<Offer> offers){
        return null;
    }

    public Offer makeOffer(List<Stock> currentStock){
    if(FRAUD)
    return new Offer(new Stock(0,1),Integer.MAX_VALUE);
        //defraud shut up and take my money            
    return null;
    }
}