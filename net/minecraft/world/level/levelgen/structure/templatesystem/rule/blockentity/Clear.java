/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class Clear
/*    */   implements RuleBlockEntityModifier {
/* 10 */   private static final Clear INSTANCE = new Clear();
/* 11 */   public static final Codec<Clear> CODEC = Codec.unit(INSTANCE);
/*    */ 
/*    */   
/*    */   public CompoundTag apply(RandomSource $$0, @Nullable CompoundTag $$1) {
/* 15 */     return new CompoundTag();
/*    */   }
/*    */ 
/*    */   
/*    */   public RuleBlockEntityModifierType<?> getType() {
/* 20 */     return RuleBlockEntityModifierType.CLEAR;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\rule\blockentity\Clear.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */