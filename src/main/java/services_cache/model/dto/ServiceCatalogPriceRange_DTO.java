package services_cache.model.dto;

import java.io.Serializable;

public class ServiceCatalogPriceRange_DTO implements Serializable 
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Float priceFr;
	private Long serviceCatalogSeqNo;
	private Float priceTo;

	public Float getPriceFr() {
		return priceFr;
	}

	public void setPriceFr(Float priceFr) {
		this.priceFr = priceFr;
	}

	public Long getServiceCatalogSeqNo() {
		return serviceCatalogSeqNo;
	}

	public void setServiceCatalogSeqNo(Long serviceCatalogSeqNo) {
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

	public Float getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(Float priceTo) {
		this.priceTo = priceTo;
	}

	public ServiceCatalogPriceRange_DTO(Float priceFr, Long serviceCatalogSeqNo, Float priceTo) {
		super();
		this.priceFr = priceFr;
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
		this.priceTo = priceTo;
	}

	public ServiceCatalogPriceRange_DTO() {
		super();
	}

}