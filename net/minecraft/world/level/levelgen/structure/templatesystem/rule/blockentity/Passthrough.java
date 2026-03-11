/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class Passthrough
/*    */   implements RuleBlockEntityModifier {
/* 10 */   public static final Passthrough INSTANCE = new Passthrough();
/* 11 */   public static final Codec<Passthrough> CODEC = Codec.unit(INSTANCE);
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public CompoundTag apply(RandomSource $$0, @Nullable CompoundTag $$1) {
/* 16 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public RuleBlockEntityModifierType<?> getType() {
/* 21 */     return RuleBlockEntityModifierType.PASSTHROUGH;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\rule\blockentity\Passthrough.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */