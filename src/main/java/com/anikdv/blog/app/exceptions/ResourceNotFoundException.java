package com.anikdv.blog.app.exceptions;

/**
 * @category Custom Exception
 * @author AnikDV
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * Default Serial UID
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String resourceField;
	private long resourceId;

	/**
	 * @param resourceName
	 * @param resourceField
	 * @param resourceId
	 */
	public ResourceNotFoundException(String resourceName, String resourceField, long resourceId) {
		super(String.format("%s Resource Not Found! %s : %s", resourceName, resourceField, resourceId));
		this.resourceName = resourceName;
		this.resourceField = resourceField;
		this.resourceId = resourceId;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceField
	 */
	public String getResourceField() {
		return resourceField;
	}

	/**
	 * @param resourceField the resourceField to set
	 */
	public void setResourceField(String resourceField) {
		this.resourceField = resourceField;
	}

	/**
	 * @return the resourceId
	 */
	public long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public String toString() {
		return "ResourceNotFoundException [resourceName=" + resourceName + ", resourceField=" + resourceField
				+ ", resourceId=" + resourceId + "]";
	}

}
