/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class JigsawRotationFix extends DataFix {
/* 14 */   private static final Map<String, String> RENAMES = (Map<String, String>)ImmutableMap.builder()
/* 15 */     .put("down", "down_south")
/* 16 */     .put("up", "up_north")
/* 17 */     .put("north", "north_up")
/* 18 */     .put("south", "south_up")
/* 19 */     .put("west", "west_up")
/* 20 */     .put("east", "east_up")
/* 21 */     .build();
/*    */   
/*    */   public JigsawRotationFix(Schema $$0, boolean $$1) {
/* 24 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   private static Dynamic<?> fix(Dynamic<?> $$0) {
/* 28 */     Optional<String> $$1 = $$0.get("Name").asString().result();
/* 29 */     if ($$1.equals(Optional.of("minecraft:jigsaw"))) {
/* 30 */       return $$0.update("Properties", $$0 -> {
/*    */             String $$1 = $$0.get("facing").asString("north");
/*    */             
/*    */             return $$0.remove("facing").set("orientation", $$0.createString(RENAMES.getOrDefault($$1, $$1)));
/*    */           });
/*    */     }
/*    */     
/* 37 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 42 */     return fixTypeEverywhereTyped("jigsaw_rotation_fix", getInputSchema().getType(References.BLOCK_STATE), $$0 -> $$0.update(DSL.remainderFinder(), JigsawRotationFix::fix));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\JigsawRotationFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */