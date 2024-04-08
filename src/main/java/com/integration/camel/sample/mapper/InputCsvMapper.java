package com.integration.camel.sample.mapper;

import lombok.Data;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@CsvRecord(separator = ",")
public class InputCsvMapper {

    @DataField(pos = 1, columnName = "id")
    private String id;

    @DataField(pos = 2, columnName = "firstname")
    private String firstName;

    @DataField(pos = 3, columnName = "lastname")
    private String lastName;

    private String fullName;

    @DataField(pos = 4, columnName = "email")
    private String email;

    @DataField(pos = 5, columnName = "email2")
    private String email2;

    @DataField(pos = 6, columnName = "profession")
    private String profession;

}