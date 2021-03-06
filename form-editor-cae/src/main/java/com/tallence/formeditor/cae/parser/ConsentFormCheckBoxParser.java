/*
 * Copyright 2018 Tallence AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tallence.formeditor.cae.parser;

import com.coremedia.blueprint.common.contentbeans.CMTeasable;
import com.coremedia.cap.struct.Struct;
import com.coremedia.objectserver.beans.ContentBeanFactory;
import com.coremedia.objectserver.dataviews.DataViewFactory;
import com.tallence.formeditor.cae.elements.ConsentFormCheckBox;
import com.tallence.formeditor.cae.validator.ConsentFormCheckboxValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Parser for elements of type {@link ConsentFormCheckBox}
 *
 */
@Component
public class ConsentFormCheckBoxParser extends AbstractFormElementParser<ConsentFormCheckBox> {

  private static final String FORM_LINK_TARGET = "linkTarget";
  public static final String CONSENT_FORM_CHECK_BOX_TYPE = "ConsentFormCheckBox";

  private final ContentBeanFactory contentBeanFactory;
  private final DataViewFactory dataViewFactory;

  @Autowired
  public ConsentFormCheckBoxParser(ContentBeanFactory contentBeanFactory, DataViewFactory dataViewFactory) {
    this.contentBeanFactory = contentBeanFactory;
    this.dataViewFactory = dataViewFactory;
  }

  @Override
  public ConsentFormCheckBox instantiateType(Struct elementData) {
    return new ConsentFormCheckBox();
  }

  @Override
  public String getParserKey() {
    return CONSENT_FORM_CHECK_BOX_TYPE;
  }

  @Override
  public void parseSpecialFields(ConsentFormCheckBox formElement, Struct elementData) {

    if (elementData.get(FORM_LINK_TARGET) != null) {
      CMTeasable linkTarget = contentBeanFactory.createBeanFor(elementData.getLink(FORM_LINK_TARGET), CMTeasable.class);
      formElement.setLinkTarget(dataViewFactory.loadCached(linkTarget, null));
    }

    doForStructElement(elementData, FORM_DATA_VALIDATOR, struct -> {
      ConsentFormCheckboxValidator validator = new ConsentFormCheckboxValidator();

      validator.setMandatory(parseBoolean(struct, FORM_VALIDATOR_MANDATORY));
      formElement.setValidator(validator);
    });
  }
}
