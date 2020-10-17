package eu.macphail.katalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private long id = -1;
    private String label;
    private String color;
    private int quantity;
}
