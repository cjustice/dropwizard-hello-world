package io.cjustice;

import io.cjustice.healthchecks.TemplateHealthCheck;
import io.cjustice.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropWizardApp extends Application<DropWizardConfig> {

    public static void main(final String[] args) throws Exception {
        new DropWizardApp().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-hello";
    }

    @Override
    public void initialize(final Bootstrap bootstrap) {
        // TODO: application initialization
    }

    /* A Dropwizard application can contain many resource classes,
     * each corresponding to its own URI pattern.
     * Just add another @Path-annotated resource class
     * and call register with an instance of the new class.
     */
    @Override
    public void run(final DropWizardConfig configuration,
                    final Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(),
                configuration.getDefaultName());

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(resource);
    }

}
