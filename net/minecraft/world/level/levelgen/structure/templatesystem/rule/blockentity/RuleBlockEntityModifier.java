/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public interface RuleBlockEntityModifier
/*    */ {
/* 11 */   public static final Codec<RuleBlockEntityModifier> CODEC = BuiltInRegistries.RULE_BLOCK_ENTITY_MODIFIER.byNameCodec().dispatch(RuleBlockEntityModifier::getType, RuleBlockEntityModifierType::codec);
/*    */   
/*    */   @Nullable
/*    */   CompoundTag apply(RandomSource paramRandomSource, @Nullable CompoundTag paramCompoundTag);
/*    */   
/*    */   RuleBlockEntityModifierType<?> getType();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\rule\blockentity\RuleBlockEntityModifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */