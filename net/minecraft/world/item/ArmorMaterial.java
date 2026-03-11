package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

public interface ArmorMaterial {
  int getDurabilityForType(ArmorItem.Type paramType);
  
  int getDefenseForType(ArmorItem.Type paramType);
  
  int getEnchantmentValue();
  
  SoundEvent getEquipSound();
  
  Ingredient getRepairIngredient();
  
  String getName();
  
  float getToughness();
  
  float getKnockbackResistance();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ArmorMaterial.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */