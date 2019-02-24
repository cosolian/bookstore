package com.scb.test.bookstore.controllers;

import com.scb.test.bookstore.domain.Order;
import com.scb.test.bookstore.domain.User;
import com.scb.test.bookstore.exceptions.BadRequestException;
import com.scb.test.bookstore.exceptions.ResourceConflictException;
import com.scb.test.bookstore.exceptions.ServerInternalErrorException;
import com.scb.test.bookstore.models.UserOrder;
import com.scb.test.bookstore.service.UserOrderService;
import com.scb.test.bookstore.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "users", description = "Operations pertaining to user")
public class UserController {

    private UserService userService;
    private UserOrderService userOrderService;

    @Autowired
    public UserController(UserService userService, UserOrderService userOrderService) {
        this.userService = userService;
        this.userOrderService = userOrderService;
    }

    @ApiOperation(value = "Get a user information by user id. This return information related to user and books ordered.")
    @GetMapping()
    public UserOrder getById(@NotNull Principal principal){
        User user = userService.findByUsername(principal.getName());
        List<Long> books = userOrderService.findBookIdByUser(user);
        return new UserOrder(user.getName(), user.getSurname(), user.getDateOfBirth(), books);
    }

    @ApiOperation(value = "Create a user account and store user's information in users.")
    @PostMapping
    public void create(
            @ApiParam(required = true)
            @Valid @RequestBody User user){
        try {
            if(userService.isUsernameExist(user.getUsername())) {
                userService.save(user);
            }
        }catch (TransactionSystemException ex){
            throw new BadRequestException(String.format("Create failed for Username - %s" , user.getUsername()));
        }catch (ResourceConflictException ex){
            throw new ResourceConflictException(ex.getMessage());
        }catch (Exception ex){
            throw new ServerInternalErrorException();
        }
    }

    @ApiOperation(value = "Delete a user record and orders history.")
    @DeleteMapping()
    public void delete(@NotNull Principal principal) {
        try {
            User user = userService.findByUsername(principal.getName());
            userOrderService.delete(user);
            userService.delete(user);
        }catch (TransactionSystemException ex){
            throw new BadRequestException(String.format("Delete failed for user id - %s" , principal.getName()));
        }catch (Exception ex){
            throw new ServerInternalErrorException();
        }
    }

    @ApiOperation(value = "Order book and store order information in orders. This return the price for order.")
    @PostMapping("/orders")
    public UserOrder create(
            @NotNull Principal principal,
            @NotNull @RequestBody List<Long> bookIds){
        User user = userService.findByUsername(principal.getName());
        List<Order> orders = userOrderService.save(user, bookIds);
        return new UserOrder(userOrderService.getTotalPrice(orders));
    }
}
