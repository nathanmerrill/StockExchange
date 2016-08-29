package com.ppcg.stockexchange;

public final class Offer {
    private final Stock offer;
    private final int payment;
    public Offer(Stock offer, int payment){
        this.offer = offer;
        this.payment = payment;
    }

    public Stock getOffer() {
        return offer;
    }

    public int getPayment() {
        return payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer1 = (Offer) o;

        if (!offer.equals(offer1.offer)) return false;
        if (payment != offer1.payment) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = offer.hashCode();
        result = 31 * result + Integer.hashCode(payment);
        return result;
    }
}
