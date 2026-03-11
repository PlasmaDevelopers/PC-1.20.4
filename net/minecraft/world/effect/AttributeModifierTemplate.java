package net.minecraft.world.effect;

import java.util.UUID;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public interface AttributeModifierTemplate {
  UUID getAttributeModifierId();
  
  AttributeModifier create(int paramInt);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\AttributeModifierTemplate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */