package springMVC1web.item_service.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    private long id;
    private String itemName;
    private Integer price; //Integer = null 값 허용
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity){

        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;

    }
}
