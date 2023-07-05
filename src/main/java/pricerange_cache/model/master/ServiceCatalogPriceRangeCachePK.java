package pricerange_cache.model.master;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SERVICE_CATALOG_PRICERANGE database table.
 * 
 */
@Embeddable
public class ServiceCatalogPriceRangeCachePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "PRICE_FR")
	private Float priceFr;

	@Column(name = "SERVICE_CATALOG_SEQ_NO")
	private Long serviceCatalogSeqNo;

	@Column(name = "PRICE_TO")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((priceFr == null) ? 0 : priceFr.hashCode());
		result = prime * result + ((priceTo == null) ? 0 : priceTo.hashCode());
		result = prime * result + (int) (serviceCatalogSeqNo ^ (serviceCatalogSeqNo >>> 32));
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
		ServiceCatalogPriceRangeCachePK other = (ServiceCatalogPriceRangeCachePK) obj;
		if (priceFr == null) {
			if (other.priceFr != null)
				return false;
		} else if (!priceFr.equals(other.priceFr))
			return false;
		if (priceTo == null) {
			if (other.priceTo != null)
				return false;
		} else if (!priceTo.equals(other.priceTo))
			return false;
		if (serviceCatalogSeqNo != other.serviceCatalogSeqNo)
			return false;
		return true;
	}

	public ServiceCatalogPriceRangeCachePK(Float priceFr, Long serviceCatalogSeqNo, Float priceTo) {
		super();
		this.priceFr = priceFr;
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
		this.priceTo = priceTo;
	}

	public ServiceCatalogPriceRangeCachePK() {
		super();
	}

}