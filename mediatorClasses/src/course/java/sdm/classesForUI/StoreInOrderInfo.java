package course.java.sdm.classesForUI;

public class StoreInOrderInfo {
    public final StoreInfo Store;
    public final Double DistanceFromUser;
    public final Double ShippingCost;
    public final Double PriceOfItems;
    public final Integer AmountOfItems; //its like before..

    public StoreInOrderInfo(StoreInfo store, Double distanceFromUser, Double shippingCost, Double priceOfItems, Integer amountOfItemsType) {
        Store = store;
        DistanceFromUser = distanceFromUser;
        ShippingCost = shippingCost;
        PriceOfItems = priceOfItems;
        AmountOfItems = amountOfItemsType;
    }

    public StoreInfo getStore() {
        return Store;
    }

    public Double getDistanceFromUser() {
        return DistanceFromUser;
    }

    public Double getShippingCost() {
        return ShippingCost;
    }

    public Double getPriceOfItems() {
        return PriceOfItems;
    }

    public Integer getAmountOfItems() {
        return AmountOfItems;
    }

    public Integer getPPK () {
        return Store.PPK;
    }

    public String getStoreName() {
        return Store.Name;
    }

    public Long getStoreId() {
        return Store.StoreID;
    }
}
