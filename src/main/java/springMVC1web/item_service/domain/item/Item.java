package springMVC1web.item_service.domain.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Item {

    private long id;
    private String itemName;
    private Integer price; //Integer = null 값 허용
    private Integer quantity;
    private Boolean open; //판매여부
    private List<String> regions; //등록 지역
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송종류

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity){

        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;

    }
}
