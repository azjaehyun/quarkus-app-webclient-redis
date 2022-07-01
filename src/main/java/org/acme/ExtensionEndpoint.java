package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.Cancellable;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/v2/beers")
@ApplicationScoped
public class ExtensionEndpoint {
    @Inject
    @RestClient
    ExtensionsService service;

    @GET
    public List id(@QueryParam("page") String id ) {
        Set<Extension> ex =  service.getByStream(id);
        List<Extension> list =  ex.stream().sorted(Comparator.comparing(Extension::getId)).collect(Collectors.toList());

        return list;
    }
}
