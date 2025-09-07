package ideau.controlePatrimonioAPI.utils;

import ideau.controlePatrimonioAPI.model.Patrimonio;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class Utils {
    public static boolean isPatrimonioValido(Patrimonio objPatr, Map<Integer, String> mapErros) {
            Integer intQtdErros = 0;
        try {
            if (StringUtils.isBlank(objPatr.getNomeItem())) {
                intQtdErros++;
                mapErros.put(intQtdErros, "nomeItem em branco!");
            }
            if (objPatr.getIdCategoria() == null) {
                intQtdErros++;
                mapErros.put(intQtdErros, "idCategoria em branco!");
            }
            if (objPatr.getIdStatus() == null) {
                intQtdErros++;
                mapErros.put(intQtdErros, "idStatus em branco!");
            }
            if (objPatr.getIdSetor() == null) {
                intQtdErros++;
                mapErros.put(intQtdErros, "idSetor em branco!");
            }
            if (objPatr.getIdNota() == null) {
                intQtdErros++;
                mapErros.put(intQtdErros, "idNota em branco!");
            }
            return mapErros.isEmpty();
        } catch (Exception e) {
            intQtdErros++;
            mapErros.put(intQtdErros, "Erro ao processar objeto Patrimônio! " + e.getMessage());
            return false;
        }
    }
}
