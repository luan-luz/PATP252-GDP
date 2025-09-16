package ideau.controlePatrimonioAPI.repository;

import ideau.controlePatrimonioAPI.model.Item;
import ideau.controlePatrimonioAPI.model.ItemDTO;

import java.util.List;

public interface ItemRepository extends GenericRepository<Item, ItemDTO> {
    List<ItemDTO> retornaPorIdCategoria(Long idCategoria);
    List<ItemDTO> retornaPorIdSetor(Long idSetor);
    List<ItemDTO> retornaPorIdStatus(Long idStatus);
}
