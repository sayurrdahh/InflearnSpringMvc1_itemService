package springMVC1web.item_service.web.item.basic;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springMVC1web.item_service.domain.item.Item;
import springMVC1web.item_service.domain.item.ItemRepository;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //final 이 붙은 멤버변수만 사용해서 생성자를 자동으로 만들어준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

    //@Autowired : 생성자가 딱 하나면 생략 가능
  /*  public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    => RequiredArgsConstructor 추가하면 생략 가능
    */

    //1. 아이템 목록
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    //2. 아이템 상세페이지
    //pathVariable : 경로 변수{itemId}를 표시하기 위해 메서드에 매개변수에 사용된다.
    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    // 3. 아이템 등록 폼
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

   // @PostMapping("/add")
    public String addItemV1(@RequestParam("itemName") String itemName,
                       @RequestParam("price") int price,
                       @RequestParam("quantity") Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "basic/item";

    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemRepository.save(item);
      //  model.addAttribute("item",item); // 자동 추가, 생략 가능
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        itemRepository.save(item);
        return "basic/item";
    }

    //4. 아이템 등록 기능
    //@PostMapping("/add")
    public String addItemV4(Item item){ //ModelAttribute 생략 가능!
        itemRepository.save(item);
        return "basic/item";
        //이렇게 하면 등록 완료된 페이지에서 새로고침 하면 계속 아이템이 등록되는 문제가 발생
        //마지막이 POST로 됨. 이게 문제가 됨.
    }

    //@PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        //저장하고 redirect 로 하면 새로운 getmapping 작용.
        //POST REDIRECT GET 해서 PRG라고 한다.
        return "redirect:/basic/items/"+item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        //리다이렉트 할 때 파라미터를 붙여서 해보자
        Item savedItem = itemRepository.save(item); //저장된 결과를 가져온다.
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/basic/items/{itemId}";
    }

    //5. 수정 폼 불러오기
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    //6. 수정 기능
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    //초기 데이터 설정
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("item1",10000, 10));
        itemRepository.save(new Item("item2",20000, 20));
    }

}
