
public class Buy {
	private String product_id;
	private int price;
	private int quantity;
	
	public Buy(String product_id, String price, String quantity) {
		this.product_id = product_id;
		this.price = Integer.parseInt(price);
		this.quantity = Integer.parseInt(quantity);
	}
	
	public String getProduct() {
		return product_id;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
}
