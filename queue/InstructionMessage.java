package travis.queue;

import static java.lang.String.format;

import java.util.Calendar;
import java.util.Date;



public class InstructionMessage implements Comparable<InstructionMessage> {

	private ProductType productType;
	private String productCode;
	private int quantity;
	private int UOM;
	private Date msgDateTime;
	
	public InstructionMessage(ProductType productType, String prodCode, 
			int qty, int uom, Date timestamp)
	{
		this.productType = productType;
		this.productCode = prodCode;
		this.quantity = qty;
		this.UOM = uom;
		this.msgDateTime = timestamp;
	}
	
	public Date getMsgDateTime() {
		return msgDateTime;
	}

	public ProductType getProductType() {
		return productType;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getUOM() {
		return UOM;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(InstructionMessage msg) {
		
		return (this.productType.toString().compareTo(msg.productType.toString()));
	}

	/*
	 * Validate Product Code, Quantity, UOM and Message Date
	 */
	public void validate() {
		
		if (!this.productCode.matches("[A-Z][A-Z][0-9][0-9]")){
			throw new InvalidMessageException("The Product Code Is Invalid");
		}
		if(this.quantity <= 0){
			throw new InvalidMessageException("Quantity Cannot Be Zero or Less Than Zero");
		}
		if(this.UOM < 0 || this.UOM >= 256){
			throw new InvalidMessageException("UOM Should Be Between Zero To 256 (0 <= UOM < 256)");
		}
		
		Calendar cal = Calendar.getInstance();
		//This is to get the epoch date time.
		cal.set(1970, 1, 1);
		if(this.msgDateTime.compareTo(new Date(System.currentTimeMillis())) >= 0 || this.msgDateTime.compareTo(cal.getTime()) < 0){
			throw new InvalidMessageException("Date Should Be Between Unix Epoch (01/01/1970) and Current Date");
		}
		
	}

	public String toString()
    {
        return format("Message instruction type: %s product code: %s quantity: %s UOM: %s timestamp: %s",
        		productType, productCode, quantity, UOM, msgDateTime);
    }

}






