package eu.macphail.shopkart;

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
    public void addNewArticle(Article article) {
        article.setId(null);
        article = repository.save(article);

        logger.info("Assigned ID {} to article {}", article.getId(), article.getLabel());
    }

}
