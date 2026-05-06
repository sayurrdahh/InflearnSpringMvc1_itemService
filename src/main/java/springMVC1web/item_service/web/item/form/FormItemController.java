package springMVC1web.item_service.web.item.form;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springMVC1web.item_service.domain.item.Item;
import springMVC1web.item_service.domain.item.ItemRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor //final 이 붙은 멤버변수만 사용해서 생성자를 자동으로 만들어준다.
public class FormItemController {

    private final ItemRepository itemRepository;

    //1. 아이템 목록
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "form/items";
    }

    //2. 아이템 상세페이지
    //pathVariable : 경로 변수{itemId}를 표시하기 위해 메서드에 매개변수에 사용된다.
    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "form/item";
    }

    // 3. 아이템 등록 폼
    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("item",new Item());
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(Item item, RedirectAttributes redirectAttributes){
        log.info("item.open={}", item.getOpen());

        //리다이렉트 할 때 파라미터를 붙여서 해보자
        Item savedItem = itemRepository.save(item); //저장된 결과를 가져온다.
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/form/items/{itemId}";
    }

    //5. 수정 폼 불러오기
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "form/editForm";
    }

    //6. 수정 기능
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

    //초기 데이터 설정
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("item1",10000, 10));
        itemRepository.save(new Item("item2",20000, 20));
    }

}
