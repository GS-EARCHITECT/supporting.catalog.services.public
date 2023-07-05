package services_cache.model.dto;

import java.io.Serializable;

public class ServiceCatalogCompClass_DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8709647949454573003L;
	private Long serviceCatalogSeqNo;
	private Long companyClassSeqNo;

	public ServiceCatalogCompClass_DTO() {
	}

	public Long getServiceCatalogSeqNo() {
		return this.serviceCatalogSeqNo;
	}

	public void setServiceCatalogSeqNo(Long serviceCatalogSeqNo) {
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
	}

	public Long getCompanyClassSeqNo() {
		return this.companyClassSeqNo;
	}

	public void setCompanyClassSeqNo(Long companyClassSeqNo) {
		this.companyClassSeqNo = companyClassSeqNo;
	}

	public ServiceCatalogCompClass_DTO(Long serviceCatalogSeqNo, Long companyClassSeqNo) {
		super();
		this.serviceCatalogSeqNo = serviceCatalogSeqNo;
		this.companyClassSeqNo = companyClassSeqNo;
	}

}