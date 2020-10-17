package eu.macphail.shopkart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @Column
    @NotNull
    private Long id;
    @Column(nullable = false)
    @NotNull
    private String label;
    @Column(nullable = false)
    @NotNull
    private String color;
    @Column(nullable = false)
    @NotNull
    private Integer quantity;
}
