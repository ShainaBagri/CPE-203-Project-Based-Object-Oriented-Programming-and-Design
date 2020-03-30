
public class View {
	private String product_id;
	private int price;
	
	public View(String product_id, String price) {
		this.product_id = product_id;
		this.price =  Integer.parseInt(price);
	}
	
	public String getProduct() {
		return product_id;
	}
	
	public int getPrice() {
		return price;
	}
	
}