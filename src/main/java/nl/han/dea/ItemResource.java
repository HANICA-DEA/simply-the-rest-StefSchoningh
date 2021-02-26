package nl.han.dea;

import nl.han.dea.services.ItemService;
import nl.han.dea.services.dto.ItemDTO;
import nl.han.dea.services.exceptions.IdAlreadyInUseException;
import nl.han.dea.services.exceptions.ItemNotAvailableException;

import javax.ejb.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

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
        try {
            return Response.ok(itemService.getItem(itemId)).build();
        } catch (ItemNotAvailableException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newItem(ItemDTO item){
        try{
            itemService.addItem(item);
            return Response.status(Response.Status.CREATED).build();
        } catch (IdAlreadyInUseException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

}
