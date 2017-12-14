package io.cjustice;

import io.cjustice.daos.SayingDAO;
import io.cjustice.healthchecks.TemplateHealthCheck;
import io.cjustice.representations.Saying;
import io.cjustice.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropWizardApp extends Application<DropWizardConfig> {
    private final HibernateBundle<?> hibernate = new HibernateBundle<DropWizardConfig>(Saying.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(DropWizardConfig configuration) {
            return configuration.getDataSourceFactory();
        }
    };

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
        bootstrap.addBundle(hibernate);

        bootstrap.addBundle(new MigrationsBundle<DropWizardConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(DropWizardConfig config) {
                return config.getDataSourceFactory();
            }
        });
    }

    /* A Dropwizard application can contain many resource classes,
     * each corresponding to its own URI pattern.
     * Just add another @Path-annotated resource class
     * and call register with an instance of the new class.
     */
    @Override
    public void run(final DropWizardConfig configuration,
                    final Environment environment) {
        final SayingDAO sayingDAO = new SayingDAO(hibernate.getSessionFactory());

        final HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(),
                configuration.getDefaultName(), sayingDAO);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(resource);
    }

}
