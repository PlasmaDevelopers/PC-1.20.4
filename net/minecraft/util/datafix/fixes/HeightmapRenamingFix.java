/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class HeightmapRenamingFix extends DataFix {
/*    */   public HeightmapRenamingFix(Schema $$0, boolean $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 20 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 21 */     OpticFinder<?> $$1 = $$0.findField("Level");
/* 22 */     return fixTypeEverywhereTyped("HeightmapRenamingFix", $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Dynamic<?> fix(Dynamic<?> $$0) {
/* 28 */     Optional<? extends Dynamic<?>> $$1 = $$0.get("Heightmaps").result();
/* 29 */     if ($$1.isEmpty()) {
/* 30 */       return $$0;
/*    */     }
/*    */     
/* 33 */     Dynamic<?> $$2 = $$1.get();
/*    */     
/* 35 */     Optional<? extends Dynamic<?>> $$3 = $$2.get("LIQUID").result();
/* 36 */     if ($$3.isPresent()) {
/* 37 */       $$2 = $$2.remove("LIQUID");
/* 38 */       $$2 = $$2.set("WORLD_SURFACE_WG", $$3.get());
/*    */     } 
/*    */     
/* 41 */     Optional<? extends Dynamic<?>> $$4 = $$2.get("SOLID").result();
/* 42 */     if ($$4.isPresent()) {
/* 43 */       $$2 = $$2.remove("SOLID");
/* 44 */       $$2 = $$2.set("OCEAN_FLOOR_WG", $$4.get());
/* 45 */       $$2 = $$2.set("OCEAN_FLOOR", $$4.get());
/*    */     } 
/*    */     
/* 48 */     Optional<? extends Dynamic<?>> $$5 = $$2.get("LIGHT").result();
/* 49 */     if ($$5.isPresent()) {
/* 50 */       $$2 = $$2.remove("LIGHT");
/* 51 */       $$2 = $$2.set("LIGHT_BLOCKING", $$5.get());
/*    */     } 
/*    */     
/* 54 */     Optional<? extends Dynamic<?>> $$6 = $$2.get("RAIN").result();
/* 55 */     if ($$6.isPresent()) {
/* 56 */       $$2 = $$2.remove("RAIN");
/* 57 */       $$2 = $$2.set("MOTION_BLOCKING", $$6.get());
/* 58 */       $$2 = $$2.set("MOTION_BLOCKING_NO_LEAVES", $$6.get());
/*    */     } 
/*    */     
/* 61 */     return $$0.set("Heightmaps", $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\HeightmapRenamingFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */