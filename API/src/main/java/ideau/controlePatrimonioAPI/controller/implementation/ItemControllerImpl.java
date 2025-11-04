package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.controller.ItemController;
import ideau.controlePatrimonioAPI.model.Item;
import ideau.controlePatrimonioAPI.model.ItemDTO;
import ideau.controlePatrimonioAPI.services.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemControllerImpl implements ItemController {
    private final ItemService svc;

    public ItemControllerImpl(ItemService svc) {
        this.svc = svc;
    }

    @Override
    @PostMapping
    public Map<Integer, ItemDTO> cadastra(@RequestBody Map<Integer, Item> mapPatrimonios) {
        return svc.cadastrarLote(mapPatrimonios);
    }

    @Override
    @GetMapping
    public List<ItemDTO> retornaTodos() {
        return svc.retornarTodos();
    }

    @Override
    @GetMapping(params = "id")
    public ItemDTO retornaPorId(Long id) {
        return svc.retornaPorId(id);
    }

    @Override
    @GetMapping(params = "idCategoria")
    public List<ItemDTO> retornaPorIdCategoria(@RequestParam Long idCategoria) {
        return svc.retornarPorIdCategoria(idCategoria);
    }

    @Override
    @PutMapping
    public ItemDTO atualiza(@RequestBody Item obj) {
        System.out.println(obj.toString());
        return svc.atualizar(obj);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleta(@PathVariable("id") Long id) {
        return svc.deletar(id);
    }
}
