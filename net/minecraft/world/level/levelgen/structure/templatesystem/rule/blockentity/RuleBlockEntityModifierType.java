/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public interface RuleBlockEntityModifierType<P extends RuleBlockEntityModifier> {
/*  8 */   public static final RuleBlockEntityModifierType<Clear> CLEAR = register("clear", Clear.CODEC);
/*  9 */   public static final RuleBlockEntityModifierType<Passthrough> PASSTHROUGH = register("passthrough", Passthrough.CODEC);
/* 10 */   public static final RuleBlockEntityModifierType<AppendStatic> APPEND_STATIC = register("append_static", AppendStatic.CODEC);
/* 11 */   public static final RuleBlockEntityModifierType<AppendLoot> APPEND_LOOT = register("append_loot", AppendLoot.CODEC);
/*    */   
/*    */   Codec<P> codec();
/*    */   
/*    */   private static <P extends RuleBlockEntityModifier> RuleBlockEntityModifierType<P> register(String $$0, Codec<P> $$1) {
/* 16 */     return (RuleBlockEntityModifierType<P>)Registry.register(BuiltInRegistries.RULE_BLOCK_ENTITY_MODIFIER, $$0, () -> $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\rule\blockentity\RuleBlockEntityModifierType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */