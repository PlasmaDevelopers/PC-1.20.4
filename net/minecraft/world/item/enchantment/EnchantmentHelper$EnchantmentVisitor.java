package net.minecraft.world.item.enchantment;

@FunctionalInterface
interface EnchantmentVisitor {
  void accept(Enchantment paramEnchantment, int paramInt);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\EnchantmentHelper$EnchantmentVisitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */