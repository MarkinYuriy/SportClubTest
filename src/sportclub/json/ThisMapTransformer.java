package sportclub.json;

import java.util.Map;

import flexjson.JSONContext;
import flexjson.Path;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;
import flexjson.transformer.TransformerWrapper;
import sportclub.model.EquipmentPool;

public class ThisMapTransformer extends AbstractTransformer{

	@Override
	public void transform(Object object) {
		JSONContext context = getContext();
        Path path = context.getPath();
        Map<EquipmentPool, Integer> value = (Map<EquipmentPool, Integer>) object;

        TypeContext typeContext = getContext().writeOpenObject();
        for (Object key : value.keySet()) {

            path.enqueue(key != null ? key.toString() : null);

            if (context.isIncluded(key != null ? key.toString() : null, value.get(key))) {

                TransformerWrapper transformer = (TransformerWrapper)context.getTransformer(value.get(key));


                if(!transformer.isInline()) {
                    if (!typeContext.isFirst()) getContext().writeComma();
                    typeContext.setFirst(false);
                    if( key != null ) {
                        getContext().writeName(key.toString());
                    } else {
                        getContext().writeName(null);
                    }
                }

                if( key != null ) {
                    typeContext.setPropertyName(key.toString());
                } else {
                    typeContext.setPropertyName(null);
                }

                transformer.transform(value.get(key));

            }

            path.pop();

        }
        getContext().writeCloseObject();
    }

}
