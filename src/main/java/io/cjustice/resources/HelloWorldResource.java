package io.cjustice.resources;

import com.codahale.metrics.annotation.Timed;
import io.cjustice.representations.Saying;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

}
