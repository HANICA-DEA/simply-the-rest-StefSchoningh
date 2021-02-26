package nl.han.dea;

import nl.han.dea.services.ItemService;
import nl.han.dea.services.dto.ItemDTO;

import javax.ejb.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/items")
@Singleton
public class ItemResource {

    private ItemService itemService;

    public ItemResource() {
        this.itemService = new ItemService();
    }

    @GET
    @Path("/")
    @Produces("text/plain")
    public String itemsAsText() {
        return "bread, butter";
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response itemsAsJson() {
//         je kan ook zo een ok status met de response sturen:
//        return Response
//            .status(Response.Status.OK)
//            .entity(itemService.getAll())
//            .build();
        return Response.ok(itemService.getAll()).build();
    }

    @GET
    @Path("/{item_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response itemAsJson(@PathParam("item_id") int itemId) {
        return Response.ok(itemService.getItem(itemId)).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newItem(ItemDTO item){
        System.out.println(item);
        itemService.addItem(item);
        return Response.status(Response.Status.CREATED).build();
    }

}
