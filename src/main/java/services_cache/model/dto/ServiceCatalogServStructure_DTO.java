package services_cache.model.dto;

import java.io.Serializable;

public class ServiceCatalogServStructure_DTO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4158292859969236085L;
	private Long serviceClassSeqNo;
	private Long parServiceClassSeqNo;
	private Long serviceCatalogSeqNo;

	public Long getServiceClassSeqNo() {
		return serviceClassSeqNo;
	}

	public void setServiceClassSeqNo(Long serviceClassSeqNo) {
		this.serviceClassSeqNo = serviceClassSeqNo;
	}

	public Long getParServiceClassSeqNo() {
		return parServiceClassSeqNo;
	}

	public void setParServiceClassSeqNo(Long parServiceClassSeqNo) {
		this.parServiceClassSeqNo = parServiceClassSeqNo;
	}

	public Long getServiceCatalogSeqNo() {
		return serviceCatalogSeqNo;
	}

	public void setServiceCatalogSeqNo(Long serviceCatalogSeqNo) {
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

	public ServiceCatalogServStructure_DTO(Long serviceClassSeqNo, Long parServiceClassSeqNo,
			Long serviceCatalogSeqNo) {
		super();
		this.serviceClassSeqNo = serviceClassSeqNo;
		this.parServiceClassSeqNo = parServiceClassSeqNo;
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

	public ServiceCatalogServStructure_DTO() {
		super();
	}

}