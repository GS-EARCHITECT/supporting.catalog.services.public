package pricerange_cache.model.master;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SERVICE_CATALOG_PRICERANGE database table.
 * 
 */
@Entity
@Table(name="SERVICE_CATALOG_PRICERANGE")
public class ServiceCatalogPriceRangeCache implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ServiceCatalogPriceRangeCachePK id;

	public ServiceCatalogPriceRangeCache() {
	}

	public ServiceCatalogPriceRangeCachePK getId() {
		return this.id;
	}

	public void setId(ServiceCatalogPriceRangeCachePK id) {
		this.id = id;
	}

	public ServiceCatalogPriceRangeCache(ServiceCatalogPriceRangeCachePK id) {
		super();
		this.id = id;
	}

	
}