package com.gudperna.rest;

import java.util.List;

import javax.ws.rs.DELETE; 
import javax.ws.rs.GET; 
import javax.ws.rs.POST; 
import javax.ws.rs.PUT; 
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.gudperna.util.ConnectionUtil;
import com.gudperna.dao.UserDAO;
import com.gudperna.dao.impl.UserDAOImpl;

import com.gudperna.model.User;

@Path("/users")
public class UserRest {

	UserDAO userService = new UserDAOImpl(ConnectionUtil.getConnection());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		List<User> listUser = userService.getAll(); 
		return listUser; 
	}

	@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") int id) { 

    	return userService.getById(id); 
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) { 
    	userService.insert(user);

        return Response.status(201).entity("Success").build();
    }

    @PUT 
    @Produces(MediaType.APPLICATION_JSON) 
    public Response updateUser(User user) { 
    	userService.edit(user);

        return Response.status(201).entity("Success").build();
    }

    @DELETE 
    @Path("/{id}") 
    @Produces(MediaType.APPLICATION_JSON) 
    public void deleteUser(@PathParam("id") int id) {
    	userService.delete(id);
    }


}