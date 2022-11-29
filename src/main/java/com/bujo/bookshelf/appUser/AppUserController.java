package com.bujo.bookshelf.appUser;

import com.bujo.bookshelf.appUser.models.AppUserDTO;
import com.bujo.bookshelf.appUser.models.AppUserDetails;
import com.bujo.bookshelf.response.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RepositoryRestController
@ConditionalOnWebApplication
public class AppUserController {
    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/appUsers")
    public @ResponseBody ResponseEntity<?> createAppUser(@RequestBody AppUserDTO appUserDto) {
        Result<AppUserDetails> result = service.create(appUserDto);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        Map<String, Long> responseBody = new HashMap<>();
        responseBody.put("app_user_id", result.getPayload().getAppUserId());

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
