package ideau.controlePatrimonioAPI.services;

import ideau.controlePatrimonioAPI.model.Item;
import ideau.controlePatrimonioAPI.model.ItemDTO;

import java.util.List;
import java.util.Map;

public interface ItemService extends GenericService<Item, ItemDTO> {
    List<ItemDTO> retornarPorIdCategoria(Long idCategoria);
    List<ItemDTO> retornarPorIdSetor(Long idSetor);
    List<ItemDTO> retornarPorIdStatus(Long idStatus);
}
