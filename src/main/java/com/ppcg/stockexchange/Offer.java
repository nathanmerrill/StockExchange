package com.ppcg.stockexchange;

public final class Offer {
    private final Stock offer, payment;
    public Offer(Stock offer, Stock payment){
        assert(offer.getType() != payment.getType());
        this.offer = offer;
        this.payment = payment;
    }

    public Stock getOffer() {
        return offer;
    }

    public Stock getPayment() {
        return payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer1 = (Offer) o;

        if (!offer.equals(offer1.offer)) return false;
        if (!payment.equals(offer1.payment)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = offer.hashCode();
        result = 31 * result + payment.hashCode();
        return result;
    }
}
