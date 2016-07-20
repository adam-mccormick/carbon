package org.carbon.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.jcr.Repository;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for managing repositories
 *
 * @author Adam McCormick
 */
@RestController
@RequestMapping(path = "/repositories")
public class RepositoryController {

    private final Repository repository;

    @Inject
    RepositoryController(Repository repository){
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> index() throws Exception{
        Session session = null;
        try{
            session = this.repository.login();
            HashMap<String, Object> response = new HashMap<>();
            response.put("workspace", session.getWorkspace().getName());
            return response;
        }
        finally {
            if(session != null)
                session.logout();
        }
    }
}
