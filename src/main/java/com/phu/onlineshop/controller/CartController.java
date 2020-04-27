package com.phu.onlineshop.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phu.onlineshop.APIResponse;
import com.phu.onlineshop.Utils;
import com.phu.onlineshop.model.cart.Cart;
import com.phu.onlineshop.model.cart.CartItem;
import com.phu.onlineshop.model.cart.CartMessage;
import com.phu.onlineshop.model.cart.CartMessage.Item;
import com.phu.onlineshop.model.log.UserActionLog;
import com.phu.onlineshop.model.log.UserActionLog.Severity;
import com.phu.onlineshop.service.CartService;
import com.phu.onlineshop.service.LogService;
import com.phu.onlineshop.service.ProductService;
import com.phu.onlineshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CartController
{
    @Autowired
    private CartService cartService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @GetMapping("/cart")
    public ResponseEntity<APIResponse<List<Cart>>> getAll()
    {
        final List<Cart> results = cartService.findAll();
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new APIResponse<List<Cart>>(HttpStatus.OK.value(), null, results).toResponseEntity();
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<APIResponse<Cart>> getById(@PathVariable final Long id)
    {
        final Cart result = cartService.finbyId(id);
        if (result == null) {
            return ResponseEntity.noContent().build();
        }
        return new APIResponse<Cart>(HttpStatus.OK.value(), null, result).toResponseEntity();
    }

    @PostMapping("/cart/checkout")
    @Transactional
    public ResponseEntity<APIResponse<ObjectNode>> checkout(@RequestHeader final Map<String, String> headers, @RequestBody final CartMessage message)
    {
        final String endpoint = "/cart/order";
        final String uuid = UUID.randomUUID().toString();
        final UserActionLog log = UserActionLog.newLog(uuid, Severity.INFO, headers, endpoint, message.toString());
        logService.addLog(log);
        LOGGER.info("Request {} endpoint {} with body {}", uuid, endpoint, message);

        if (message.getUser() == null || userService.findById(message.getUser()) == null) {
            final String errorMessage = "User does not exist";
            return new APIResponse<ObjectNode>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        for (final Item item : message.getItems()) {
            if (item.getProduct() == null) {
                final String errorMessage = "product is required";
                return new APIResponse<ObjectNode>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                final String errorMessage = "quantity is required and must be greater than 0";
                return new APIResponse<ObjectNode>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
            }
            if (productService.findById(item.getProduct()) == null) {
                final String errorMessage = "Product does not exist";
                return new APIResponse<ObjectNode>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
            }
        }
        final Cart cart = newCartFromMessage(message);
        entityManager.persist(cart);
        entityManager.flush();
        final ObjectNode result = Utils.MAPPER.createObjectNode();
        result.put("id", cart.getId());
        return new APIResponse<ObjectNode>(HttpStatus.CREATED.value(), null, result).toResponseEntity();
    }

    private Cart newCartFromMessage(final CartMessage message)
    {
        final Cart cart = new Cart();
        cart.setOwner(userService.findById(message.getUser()));
        for (final Item item : message.getItems()) {
            final CartItem cartItem = new CartItem();
            cartItem.setProduct(productService.findById(item.getProduct()));
            cartItem.setQuantity(item.getQuantity());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }
        return cart;
    }
}
