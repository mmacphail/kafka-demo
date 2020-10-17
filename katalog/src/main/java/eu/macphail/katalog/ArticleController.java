package eu.macphail.katalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    KafkaService kafkaService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        model.addAttribute("article", new Article());
        return "articles";
    }

    @PostMapping("/articles")
    public String showResultView(@ModelAttribute Article article, Model model) {
        try {
            kafkaService.sendArticle(article);
            model.addAttribute("article", article);
            model.addAttribute("error", false);
        } catch(Throwable t) {
            logger.error(t.getMessage());
            t.printStackTrace();
            model.addAttribute("error", true);
        }
        return "article";
    }
}
