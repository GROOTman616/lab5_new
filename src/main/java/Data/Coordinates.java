package Data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Максимальное значение поля: 817, Поле не может быть null
    private Long y; //Поле не может быть null
    public Coordinates(Long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }
    public Long getY() {
        return y;
    }
}
