package dev.matheus.services;

import dev.matheus.entity.Fruta;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/frutas")
public class FrutaResource {

    // Read - Consulta o banco de frutas
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fruta> list() {
        return Fruta.listAll();
    }

    // Create - Cria uma nova fruto no banco de dados fruta
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response novaFruta(Fruta novaFruta) {
        novaFruta.persist();
        return Response.status(Response.Status.CREATED).entity(novaFruta).build();
    }

    // UPDATE - Atualiza o registro de uma fruta
    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarFruta(@PathParam("id") Long id, Fruta frutaEditada) {
        Fruta fruta = Fruta.findById(id);
        if (fruta == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Fruta não encontrada").build();
        }
        fruta.nome = frutaEditada.nome;
        fruta.quantidade = frutaEditada.quantidade;
        fruta.persist();
        return Response.ok(fruta).build();
    }

    // DELETE - Deleta uma fruta do meu banco de dados
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarFruta(@PathParam("id") Long id) {
        if (Fruta.deleteById(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Fruta não encontrada").build();
        }
    }
}
