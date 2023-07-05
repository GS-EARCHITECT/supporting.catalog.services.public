package services_cache.model.dto;

import java.io.Serializable;

public class ServiceCatalogLocStructure_DTO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4158292859969236085L;
	private Long locationClassSeqNo;
	private Long parLocationClassSeqNo;
	private Long serviceCatalogSeqNo;

	public Long getLocationClassSeqNo() {
		return locationClassSeqNo;
	}

	public void setLocationClassSeqNo(Long locationClassSeqNo) {
		this.locationClassSeqNo = locationClassSeqNo;
	}

	public Long getParLocationClassSeqNo() {
		return parLocationClassSeqNo;
	}

	public void setParLocationClassSeqNo(Long parLocationClassSeqNo) {
		this.parLocationClassSeqNo = parLocationClassSeqNo;
	}

	public Long getServiceCatalogSeqNo() {
		return serviceCatalogSeqNo;
	}

	public void setServiceCatalogSeqNo(Long serviceCatalogSeqNo) {
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

	public ServiceCatalogLocStructure_DTO(Long locationClassSeqNo, Long parLocationClassSeqNo,
			Long serviceCatalogSeqNo) {
		super();
		this.locationClassSeqNo = locationClassSeqNo;
		this.parLocationClassSeqNo = parLocationClassSeqNo;
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

	public ServiceCatalogLocStructure_DTO() {
		super();
	}

}