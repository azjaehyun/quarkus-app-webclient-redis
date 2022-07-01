package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/v2/beers")
@RegisterRestClient(configKey = "extensions-api")
public interface ExtensionsService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Set<Extension> getByStream( @QueryParam("page") String id);
}
