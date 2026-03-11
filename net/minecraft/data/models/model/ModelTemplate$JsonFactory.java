package net.minecraft.data.models.model;

import com.google.gson.JsonObject;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public interface JsonFactory {
  JsonObject create(ResourceLocation paramResourceLocation, Map<TextureSlot, ResourceLocation> paramMap);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\ModelTemplate$JsonFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */