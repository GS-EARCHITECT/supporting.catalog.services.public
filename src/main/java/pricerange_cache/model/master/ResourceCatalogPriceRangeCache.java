package pricerange_cache.model.master;

import java.io.Serializable;

public class ResourceCatalogPriceRangeCache implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9029952926477601070L;
	private Float frPrice;
	private Float toPrice;
	private Long resourceCatalogSeqNo;

	public Float getFrPrice() {
		return frPrice;
	}

	public void setFrPrice(Float frPrice) {
		this.frPrice = frPrice;
	}

	public Float getToPrice() {
		return toPrice;
	}

	public void setToPrice(Float toPrice) {
		this.toPrice = toPrice;
	}

	public Long getResourceCatalogSeqNo() {
		return resourceCatalogSeqNo;
	}

	public void setResourceCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frPrice == null) ? 0 : frPrice.hashCode());
		result = prime * result + ((resourceCatalogSeqNo == null) ? 0 : resourceCatalogSeqNo.hashCode());
		result = prime * result + ((toPrice == null) ? 0 : toPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceCatalogPriceRangeCache other = (ResourceCatalogPriceRangeCache) obj;
		if (frPrice == null) {
			if (other.frPrice != null)
				return false;
		} else if (!frPrice.equals(other.frPrice))
			return false;
		if (resourceCatalogSeqNo == null) {
			if (other.resourceCatalogSeqNo != null)
				return false;
		} else if (!resourceCatalogSeqNo.equals(other.resourceCatalogSeqNo))
			return false;
		if (toPrice == null) {
			if (other.toPrice != null)
				return false;
		} else if (!toPrice.equals(other.toPrice))
			return false;
		return true;
	}

	public ResourceCatalogPriceRangeCache(Float frPrice, Float toPrice, Long resourceCatalogSeqNo) {
		super();
		this.frPrice = frPrice;
		this.toPrice = toPrice;
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogPriceRangeCache() {
		super();
	}

}