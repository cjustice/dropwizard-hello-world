package io.cjustice.healthchecks;

import com.codahale.metrics.health.HealthCheck;

public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "HealthConnor");
        if (!saying.contains("HealthConnor")) {
            return Result.unhealthy("template doesn't include name");
        }
        return Result.healthy();
    }
}
