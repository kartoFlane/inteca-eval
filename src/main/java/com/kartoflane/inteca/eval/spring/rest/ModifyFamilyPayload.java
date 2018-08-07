package com.kartoflane.inteca.eval.spring.rest;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class ModifyFamilyPayload {
	@Nullable
	private Long fatherId;
	@Nullable
	private Long childId;

	private ModifyFamilyPayload() {
	}

	public static ModifyFamilyPayload withFatherId(@NonNull Long fatherId) {
		ModifyFamilyPayload p = new ModifyFamilyPayload();
		p.fatherId = fatherId;
		return p;
	}

	public static ModifyFamilyPayload withChildId(@NonNull Long childId) {
		ModifyFamilyPayload p = new ModifyFamilyPayload();
		p.childId = childId;
		return p;
	}

	public Long getFatherId() {
		return fatherId;
	}

	public boolean hasFatherId() {
		return fatherId != null;
	}

	public Long getChildId() {
		return childId;
	}

	public boolean hasChildId() {
		return childId != null;
	}

	public boolean isValid() {
		// Only one field may be not-null at a time.
		// If both fields are null, the request is not valid.
		// If both fields are not-null, the request is also not valid.
		return (fatherId != null && childId == null) ||
				(fatherId == null && childId != null);
	}
}