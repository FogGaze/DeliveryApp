package main;

public class PerishableParcel extends Parcel {
    protected int timeToLive;
    protected boolean isExpired = false;
    private static final int BASE_COST = 3;

    public PerishableParcel(String description, double weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    protected int getBaseCost() {
        return BASE_COST;
    }

    public boolean isExpired(int currentDay) {
        if ((sendDay + timeToLive) < currentDay) {
            isExpired = true;
        }
        return isExpired;
    }

}