package eu.macphail.shopkart;

import eu.macphail.shopkart.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleRepository repository;

    @Transactional
    public void addNewArticle(eu.macphail.data.Article article) {
        Article art = new Article();
        art.setLabel(article.getLabel());
        art.setColor(article.getColor().toString());
        art.setQuantity(article.getQuantity());
        art = repository.save(art);

        logger.info("Assigned ID {} to article {}", art.getId(), art.getLabel());
    }

}
