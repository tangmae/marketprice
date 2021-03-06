package marketprice.marketpriceapp.entity;

import java.util.Date;

public class Egg extends Product{

	private int eggId;
	private int pricePerUnit;
	private int volumn;
	private Date importDate;
	private boolean deleteFlag;
	private int ownerId;
	
	public int getEggId() {
		return eggId;
	}
	public void setEggId(int eggId) {
		this.eggId = eggId;
	}
	public int getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(int pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public int getVolumn() {
		return volumn;
	}
	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	
}
