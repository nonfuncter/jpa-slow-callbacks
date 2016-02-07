package com.nonfunc.jpa.slow.callbacks;

/*
 * #%L
 * em
 * %%
 * Copyright (C) 2016 nonfunc.com
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Foo {
		
	Long id;
	Integer code;
	String description;
	String insertAuditValue;
	String updateAuditValue;
	
	@Id
	@GeneratedValue()
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column
	public String getInsertAuditValue() {
		return insertAuditValue;
	}
	
	public void setInsertAuditValue(String insertAuditValue) {
		this.insertAuditValue = insertAuditValue;
	}
	
	@Column
	public String getUpdateAuditValue() {
		return updateAuditValue;
	}
	
	public void setUpdateAuditValue(String updateAuditValue) {
		this.updateAuditValue = updateAuditValue;
	}
	
	@PrePersist
	public void beforeInsert() {
		this.insertAuditValue = AuditUtil.determineAuditValue();
	}
	
	@PreUpdate
	public void beforeUpdate() {
		this.updateAuditValue = AuditUtil.determineAuditValue();
	}
}
