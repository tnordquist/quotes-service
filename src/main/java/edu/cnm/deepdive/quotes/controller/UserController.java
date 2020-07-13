package edu.cnm.deepdive.quotes.controller;

import edu.cnm.deepdive.quotes.model.entity.User;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

  // TODO Deal with UserRepository/service/etc.

}
