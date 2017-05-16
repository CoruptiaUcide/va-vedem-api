package ro.vavedem.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author CoruptiaUcide
 */
@Data
public class EmailModel {


    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull
    private String subject;
    @NotNull
    private String content;


}
