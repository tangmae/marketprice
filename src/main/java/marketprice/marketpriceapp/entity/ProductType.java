package marketprice.marketpriceapp.entity;

public class ProductType {
	
	private int producTypeId;
	private String name;
	private String description;
	private String importDate;
	private boolean deleteFlag;
	
	public int getProducTypeId() {
		return producTypeId;
	}
	public void setProducTypeId(int producTypeId) {
		this.producTypeId = producTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImportDate() {
		return importDate;
	}
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
