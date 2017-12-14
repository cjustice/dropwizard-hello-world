package io.cjustice.resources;

import com.codahale.metrics.annotation.Timed;
import io.cjustice.daos.SayingDAO;
import io.cjustice.representations.Saying;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/* Resource classes are used by multiple threads concurrently. In general,
    recommended that resources be stateless/immutable */
@Path("/hello-world")
@Produces(APPLICATION_JSON)
public class HelloWorldResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    private final SayingDAO sayingDAO;

    public HelloWorldResource(String template, String defaultName, SayingDAO sayingDao) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        this.sayingDAO = sayingDao;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

    @POST
    @Timed
    @Consumes(APPLICATION_JSON)
    @UnitOfWork
    public long receiveSaying(Saying saying) {
        System.out.println(saying.getContent());
        return sayingDAO.create(saying);
    }
}
