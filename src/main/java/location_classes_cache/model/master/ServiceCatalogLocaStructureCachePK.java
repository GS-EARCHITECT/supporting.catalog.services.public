package location_classes_cache.model.master;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SERVICE_CATALOG_LOCSTRUCTURE database table.
 * 
 */
@Embeddable
public class ServiceCatalogLocaStructureCachePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "PAR_PLACE_CLASS_SEQ_NO")
	private Long parPlaceClassSeqNo;

	@Column(name = "PLACE_CLASS_SEQ_NO")
	private Long placeClassSeqNo;

	@Column(name = "SERVICE_CATALOG_SEQ_NO")
	private Long serviceCatalogSeqNo;

	public Long getParPlaceClassSeqNo() {
		return this.parPlaceClassSeqNo;
	}

	public void setParPlaceClassSeqNo(Long parPlaceClassSeqNo) {
		this.parPlaceClassSeqNo = parPlaceClassSeqNo;
	}

	public Long getPlaceClassSeqNo() {
		return this.placeClassSeqNo;
	}

	public void setPlaceClassSeqNo(Long placeClassSeqNo) {
		this.placeClassSeqNo = placeClassSeqNo;
	}

	public Long getServiceCatalogSeqNo() {
		return this.serviceCatalogSeqNo;
	}

	public void setServiceCatalogSeqNo(Long serviceCatalogSeqNo) {
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ServiceCatalogLocaStructureCachePK)) {
			return false;
		}
		ServiceCatalogLocaStructureCachePK castOther = (ServiceCatalogLocaStructureCachePK) other;
		return (this.parPlaceClassSeqNo == castOther.parPlaceClassSeqNo)
				&& (this.placeClassSeqNo == castOther.placeClassSeqNo)
				&& (this.serviceCatalogSeqNo == castOther.serviceCatalogSeqNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.parPlaceClassSeqNo ^ (this.parPlaceClassSeqNo >>> 32)));
		hash = hash * prime + ((int) (this.placeClassSeqNo ^ (this.placeClassSeqNo >>> 32)));
		hash = hash * prime + ((int) (this.serviceCatalogSeqNo ^ (this.serviceCatalogSeqNo >>> 32)));

		return hash;
	}

	public ServiceCatalogLocaStructureCachePK(Long parPlaceClassSeqNo, Long placeClassSeqNo, Long serviceCatalogSeqNo) {
		super();
		this.parPlaceClassSeqNo = parPlaceClassSeqNo;
		this.placeClassSeqNo = placeClassSeqNo;
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

	public ServiceCatalogLocaStructureCachePK() {
		super();
	}

}