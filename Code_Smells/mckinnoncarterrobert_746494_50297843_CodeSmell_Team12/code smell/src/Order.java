import java.util.List;

interface DiscountStrategy {
    double applyDiscount(Item item);
}

class PercentageDiscountStrategy implements DiscountStrategy {
    public double applyDiscount(Item item) {
        return item.getPrice() * (1 - item.getDiscountAmount());
    }
}

class AmountDiscountStrategy implements DiscountStrategy {
    public double applyDiscount(Item item) {
        return item.getPrice() - item.getDiscountAmount();
    }
}

public class Order {
    private List<Item> items;
    private String customerName;
    private String customerEmail;

    public Order(List<Item> items, String customerName, String customerEmail) {
        this.items = items;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public double calculateTotalPrice() {
        double total = 0.0;
        for (Item item : items) {
            double price = item.getPrice();
            // Delegate discount calculation to the appropriate strategy
            DiscountStrategy discountStrategy;
            if (item.getDiscountType() == DiscountType.PERCENTAGE) {
                discountStrategy = new PercentageDiscountStrategy();
            } else {
                discountStrategy = new AmountDiscountStrategy();
            }
            price = discountStrategy.applyDiscount(item);
            total += price * item.getQuantity();
            double tax = calculateTax(item);
            total += tax;
        }
        if (hasGiftCard()) {
            total -= 10.0; // subtract $10 for gift card
        }
        if (total > 100.0) {
            total *= 0.9; // apply 10% discount for orders over $100
        }
        return total;
    }

    private double calculateTax(Item item) {
        if (item instanceof TaxableItem) {
            TaxableItem taxableItem = (TaxableItem) item;
            return taxableItem.getTaxRate() / 100 * taxableItem.getPrice();
        }
        return 0;
    }

    public void sendConfirmationEmail() {
        String message = "Thank you for your order, " + customerName + "!\n\n" +
                "Your order details:\n";
       
        String orderDetails = generateOrderDetails();        
        orderDetails += "Total: " + calculateTotalPrice();
        EmailSender.sendEmail(customerEmail, "Order Confirmation", orderDetails);
    }


    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public boolean hasGiftCard() {
        boolean has_gift_card = false;
        for (Item item : items) {
            if (item instanceof GiftCardItem) {
                has_gift_card = true;
                break;
            }
        }
        return has_gift_card;
    }

   public void printOrder() {
    System.out.println("Order Details:");
    String orderDetails = generateOrderDetails();
    System.out.println(orderDetails);
   }

   private String generateOrderDetails() {
    String message = "";
    for (Item item : items) {
        message += item.getName() + " - " + item.getPrice() + "\n";
    }
    return message;
}

public void addItemsFromAnotherOrder(Order otherOrder) {
    for (Item item : otherOrder.getItems()) {
        addItem(item);
    }
}

}

