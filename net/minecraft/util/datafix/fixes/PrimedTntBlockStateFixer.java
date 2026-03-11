/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class PrimedTntBlockStateFixer
/*    */   extends NamedEntityWriteReadFix
/*    */ {
/*    */   public PrimedTntBlockStateFixer(Schema $$0) {
/* 12 */     super($$0, true, "PrimedTnt BlockState fixer", References.ENTITY, "minecraft:tnt");
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> renameFuse(Dynamic<T> $$0) {
/* 16 */     Optional<Dynamic<T>> $$1 = $$0.get("Fuse").get().result();
/* 17 */     if ($$1.isPresent()) {
/* 18 */       return $$0.set("fuse", $$1.get());
/*    */     }
/* 20 */     return $$0;
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> insertBlockState(Dynamic<T> $$0) {
/* 24 */     return $$0.set("block_state", $$0.createMap(Map.of($$0
/* 25 */             .createString("Name"), $$0.createString("minecraft:tnt"))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected <T> Dynamic<T> fix(Dynamic<T> $$0) {
/* 31 */     return renameFuse(insertBlockState($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\PrimedTntBlockStateFixer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */