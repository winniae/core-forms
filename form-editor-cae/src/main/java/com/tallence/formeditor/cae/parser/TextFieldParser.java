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

import com.coremedia.cap.struct.Struct;
import com.tallence.formeditor.cae.elements.TextField;
import com.tallence.formeditor.cae.elements.ZipField;
import com.tallence.formeditor.cae.validator.TextValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.tallence.formeditor.cae.FormElementFactory.FORM_DATA_KEY_TYPE;

/**
 * Parser for elements of type {@link TextField} and some subTypes, e.g ZipField.
 * They do not currently use extra fields -> no extra parser is used for them.
 */
@Component
public class TextFieldParser extends AbstractFormElementParser<TextField> {

  private static final String KEY_TEXT_FIELD = "TextField";
  private static final String KEY_ZIP_FIELD = "ZipField";
  private static final String KEY_PHONE_FIELD = "PhoneField";
  private static final String KEY_FAX_FIELD = "FaxField";
  private static final String KEY_STREET_NUMBER_FIELD = "StreetNumberField";


  @Override
  public TextField instantiateType(Struct elementData) {
    String type = elementData.getString(FORM_DATA_KEY_TYPE);
    switch (type) {
      case KEY_TEXT_FIELD:
        return new TextField();
      case KEY_ZIP_FIELD:
        return new ZipField();
      case KEY_PHONE_FIELD:
        return new ZipField();
      case KEY_FAX_FIELD:
        return new ZipField();
      case KEY_STREET_NUMBER_FIELD:
        return new ZipField();
      default:
        throw new IllegalStateException("Cannot instantiate a field for type " + type);
    }
  }


  @Override
  public void parseSpecialFields(TextField formElement, Struct elementData) {
    doForStructElement(elementData, FORM_DATA_VALIDATOR, validator -> {
      TextValidator textValidator = new TextValidator();

      textValidator.setMandatory(parseBoolean(validator, FORM_VALIDATOR_MANDATORY));
      textValidator.setMinSize(parseInteger(validator, FORM_VALIDATOR_MINSIZE));
      textValidator.setMaxSize(parseInteger(validator, FORM_VALIDATOR_MAXSIZE));
      textValidator.setRegexp(parseString(validator, FORM_VALIDATOR_REGEXP));

      formElement.setValidator(textValidator);
    });
  }

  @Override
  public List<String> getParserKeys() {
    return Arrays.asList(KEY_TEXT_FIELD, KEY_ZIP_FIELD, KEY_PHONE_FIELD, KEY_FAX_FIELD, KEY_STREET_NUMBER_FIELD);
  }
}
