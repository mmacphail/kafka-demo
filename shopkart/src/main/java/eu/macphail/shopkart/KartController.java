package eu.macphail.shopkart;

import eu.macphail.shopkart.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class KartController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/catalog")
    public String getCatalog(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "catalog";
    }

}
