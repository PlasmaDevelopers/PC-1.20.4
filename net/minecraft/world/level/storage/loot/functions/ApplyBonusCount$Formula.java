package net.minecraft.world.level.storage.loot.functions;

import net.minecraft.util.RandomSource;

interface Formula {
  int calculateNewCount(RandomSource paramRandomSource, int paramInt1, int paramInt2);
  
  ApplyBonusCount.FormulaType getType();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\ApplyBonusCount$Formula.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */