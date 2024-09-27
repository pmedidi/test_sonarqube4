
public class GiftCardItem extends Item {
    
    private int numMonthsValid;
    public GiftCardItem(String name, double price, int quantity, DiscountType discountType, double discountAmount){
        super(name, price, quantity, discountType, discountAmount);
        this.numMonthsValid = 12;
    }

    public GiftCardItem(String name, double price, int quantity, DiscountType discountType, double discountAmount,
                        int numMonthsValid){
        super(name, price, quantity, discountType, discountAmount);
        this.numMonthsValid = numMonthsValid;
    }

    public int getNumMonthsValid(){
        return numMonthsValid;
    }
    public void setNumMonthsValid(int numMonthsValid) {
        if (numMonthsValid > 0) {
            this.numMonthsValid = numMonthsValid;
        }
    }



}
