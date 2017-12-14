package io.cjustice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class DropWizardConfig extends Configuration {
    // TODO: implement service configuration
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Connor";

    @JsonProperty
    public String getTemplate() {
        return this.template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return this.defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }
}
