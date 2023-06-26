package resources_cache.model.dto;

import java.io.Serializable;

public class ResourceCatalogPriceRange_DTO implements Serializable 
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Float priceFr;
	private Long resourceCatalogSeqNo;
	private Float priceTo;

	public Float getPriceFr() {
		return priceFr;
	}

	public void setPriceFr(Float priceFr) {
		this.priceFr = priceFr;
	}

	public Long getResourceCatalogSeqNo() {
		return resourceCatalogSeqNo;
	}

	public void setResourceCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public Float getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(Float priceTo) {
		this.priceTo = priceTo;
	}

	public ResourceCatalogPriceRange_DTO(Float priceFr, Long resourceCatalogSeqNo, Float priceTo) {
		super();
		this.priceFr = priceFr;
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
		this.priceTo = priceTo;
	}

	public ResourceCatalogPriceRange_DTO() {
		super();
	}

}