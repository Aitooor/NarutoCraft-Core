package es.narutocraft.narutocraftcore.data.converters;

import com.cryptomorin.xseries.XItemStack;
import de.exlll.configlib.Converter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemStackMapConverter implements Converter<ItemStack, Map<String, Object>> {
    @Override
    public Map<String, Object> convertTo(ItemStack itemStack, ConversionInfo conversionInfo) {
        return XItemStack.serialize(itemStack);
    }

    @Override
    public ItemStack convertFrom(Map<String, Object> stringObjectMap, ConversionInfo conversionInfo) {
        return XItemStack.deserialize(stringObjectMap);
    }
}
