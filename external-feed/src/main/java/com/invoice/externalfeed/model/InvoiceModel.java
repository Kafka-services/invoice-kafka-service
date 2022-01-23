package com.invoice.externalfeed.model;

public class InvoiceModel {

	private String dealerId;
	private String dealerName;

	private String carName;
	private String fuelType;
	private String engineType;

	/*private String ownerName;
	private String ownerAge;
	private String ownerAddress;

	private BigDecimal price;
	private String emiYears;
	private String emiAmount;*/

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

}
