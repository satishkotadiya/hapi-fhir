package ca.uhn.fhir.util;

/*
 * #%L
 * HAPI FHIR - Core Library
 * %%
 * Copyright (C) 2014 - 2016 University Health Network
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

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.context.BaseRuntimeChildDefinition;
import ca.uhn.fhir.context.BaseRuntimeElementCompositeDefinition;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.RuntimeResourceDefinition;

/**
 * Fetch resources from a bundle
 */
public class BundleUtil {

	/**
	 * Extract all of the resources from a given bundle
	 */
	public static List<IBaseResource> toListOfResources(FhirContext theContext, IBaseBundle theBundle) {
		List<IBaseResource> retVal = new ArrayList<IBaseResource>();

		RuntimeResourceDefinition def = theContext.getResourceDefinition(theBundle);
		BaseRuntimeChildDefinition entryChild = def.getChildByName("entry");
		List<IBase> entries = entryChild.getAccessor().getValues(theBundle);

		BaseRuntimeElementCompositeDefinition<?> entryChildElem = (BaseRuntimeElementCompositeDefinition<?>) entryChild.getChildByName("entry");
		BaseRuntimeChildDefinition resourceChild = entryChildElem.getChildByName("resource");
		for (IBase nextEntry : entries) {
			for (IBase next : resourceChild.getAccessor().getValues(nextEntry)) {
				retVal.add((IBaseResource) next);
			}
		}

		return retVal;
	}

}
