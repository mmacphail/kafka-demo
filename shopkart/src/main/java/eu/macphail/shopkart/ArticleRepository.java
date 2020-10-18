package eu.macphail.shopkart;

import eu.macphail.shopkart.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
