package eu.macphail.katalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private static final Random random = new Random();

    private long id = random.nextLong();
    private String label;
    private String color;
    private int quantity;
}
