package ideau.controlePatrimonioAPI.controller;

import ideau.controlePatrimonioAPI.model.Item;
import ideau.controlePatrimonioAPI.model.ItemDTO;

import java.util.List;

public interface ItemController extends GenericController<Item, ItemDTO> {
    List<ItemDTO> retornaPorIdCategoria(Long idCategoria);
}
