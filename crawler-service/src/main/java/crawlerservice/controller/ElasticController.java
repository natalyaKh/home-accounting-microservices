package crawlerservice.controller;



import crawlerservice.dao.QueryDAO;
import crawlerservice.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prod")
public class ElasticController {

    private final QueryDAO dao;

    public ElasticController(final QueryDAO dao){
        this.dao = dao;
    }

    /**
     *
     * @param product
     * @return
     */
    @PostMapping(consumes = "application/json; charset=utf-8")
    public String create(@RequestBody Product product) {
        return dao.indexRequest(product);
    }

    @PostMapping(value = "/update", consumes = "application/json; charset=utf-8")
    public String update(@RequestBody Product product){
        return dao.updateDocument(product);
    }

    /**
     *
     * @param id
     */
    @GetMapping(value = "/delete")
    public void delete(String id){
        dao.deleteDocument(id);
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/all", produces = "application/json; charset=utf-8")
    public List<Product> getAllDocuments() {
        return dao.matchAllQuery();
    }

    /**
     *
     * @param query
     * @return
     */
    @GetMapping("/search")
    public List<Product> search(@RequestParam("query") String query) {
        return dao.wildcardQuery(query);
    }
}


