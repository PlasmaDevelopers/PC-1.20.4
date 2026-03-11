/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class BlockEntityBannerColorFix extends NamedEntityFix {
/*    */   public BlockEntityBannerColorFix(Schema $$0, boolean $$1) {
/* 11 */     super($$0, $$1, "BlockEntityBannerColorFix", References.BLOCK_ENTITY, "minecraft:banner");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 15 */     $$0 = $$0.update("Base", $$0 -> $$0.createInt(15 - $$0.asInt(0)));
/*    */     
/* 17 */     $$0 = $$0.update("Patterns", $$0 -> {
/*    */           Objects.requireNonNull($$0);
/*    */           
/*    */           return (Dynamic)DataFixUtils.orElse($$0.asStreamOpt().map(()).map($$0::createList).result(), $$0);
/*    */         });
/*    */     
/* 23 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 28 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntityBannerColorFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */