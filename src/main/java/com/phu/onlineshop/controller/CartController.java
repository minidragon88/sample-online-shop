package com.phu.onlineshop.controller;

import com.phu.onlineshop.APIResponse;
import com.phu.onlineshop.model.cart.Cart;
import com.phu.onlineshop.model.log.UserActionLog;
import com.phu.onlineshop.model.log.UserActionLog.Severity;
import com.phu.onlineshop.service.CartService;
import com.phu.onlineshop.service.LogService;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @GetMapping("/cart")
    public ResponseEntity<APIResponse<List<Cart>>> findAll()
    {
        final List<Cart> results = cartService.findAll();
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new APIResponse<List<Cart>>(HttpStatus.OK.value(), null, results).toResponseEntity();
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<APIResponse<Cart>> findById(@PathVariable final Long id)
    {
        final Cart result = cartService.finbyId(id);
        if (result == null) {
            return ResponseEntity.noContent().build();
        }
        return new APIResponse<Cart>(HttpStatus.OK.value(), null, result).toResponseEntity();
    }

    @PostMapping("/cart/order")
    public ResponseEntity<String> order(@RequestHeader final Map<String, String> headers, @RequestBody final Cart cart)
    {
        final String endpoint = "/product/search";
        final String uuid = UUID.randomUUID().toString();
        final UserActionLog log = UserActionLog.newLog(uuid, Severity.INFO, headers, endpoint, cart.toString());
        logService.addLog(log);
        LOGGER.info("Request {} search {} with body {}", uuid, endpoint, cart);
        return ResponseEntity.noContent().build();
    }
}
