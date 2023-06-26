package resources_cache.model.dto;

import java.io.Serializable;

public class ResourceCatalogCompClass_DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8709647949454573003L;
	private Long resourceCatalogSeqNo;
	private Long companyClassSeqNo;

	public ResourceCatalogCompClass_DTO() {
	}

	public Long getResourceCatalogSeqNo() {
		return this.resourceCatalogSeqNo;
	}

	public void setResourceCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public Long getCompanyClassSeqNo() {
		return this.companyClassSeqNo;
	}

	public void setCompanyClassSeqNo(Long companyClassSeqNo) {
		this.companyClassSeqNo = companyClassSeqNo;
	}

	public ResourceCatalogCompClass_DTO(Long resourceCatalogSeqNo, Long companyClassSeqNo) {
		super();
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
		this.companyClassSeqNo = companyClassSeqNo;
	}

}