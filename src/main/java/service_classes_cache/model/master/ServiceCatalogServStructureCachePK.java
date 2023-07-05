package service_classes_cache.model.master;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SERVICE_CATALOG_SERVSTRUCTURE database table.
 * 
 */
@Embeddable
public class ServiceCatalogServStructureCachePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SERVICE_CLASS_SEQ_NO")
	private Long serviceClassSeqNo;

	@Column(name = "PAR_SERVICE_CLASS_SEQ_NO")
	private Long parServiceClassSeqNo;

	@Column(name = "SERVICE_CATALOG_SEQ_NO")
	private Long serviceCatalogSeqNo;

	public ServiceCatalogServStructureCachePK() {
	}

	public Long getServiceClassSeqNo() {
		return this.serviceClassSeqNo;
	}

	public void setServiceClassSeqNo(Long serviceClassSeqNo) {
		this.serviceClassSeqNo = serviceClassSeqNo;
	}

	public Long getParServiceClassSeqNo() {
		return this.parServiceClassSeqNo;
	}

	public void setParServiceClassSeqNo(Long parServiceClassSeqNo) {
		this.parServiceClassSeqNo = parServiceClassSeqNo;
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
		if (!(other instanceof ServiceCatalogServStructureCachePK)) {
			return false;
		}
		ServiceCatalogServStructureCachePK castOther = (ServiceCatalogServStructureCachePK) other;
		return (this.serviceClassSeqNo == castOther.serviceClassSeqNo)
				&& (this.parServiceClassSeqNo == castOther.parServiceClassSeqNo)
				&& (this.serviceCatalogSeqNo == castOther.serviceCatalogSeqNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.serviceClassSeqNo ^ (this.serviceClassSeqNo >>> 32)));
		hash = hash * prime + ((int) (this.parServiceClassSeqNo ^ (this.parServiceClassSeqNo >>> 32)));
		hash = hash * prime + ((int) (this.serviceCatalogSeqNo ^ (this.serviceCatalogSeqNo >>> 32)));

		return hash;
	}

	public ServiceCatalogServStructureCachePK(Long serviceClassSeqNo, Long parServiceClassSeqNo,
			Long serviceCatalogSeqNo) {
		super();
		this.serviceClassSeqNo = serviceClassSeqNo;
		this.parServiceClassSeqNo = parServiceClassSeqNo;
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

}