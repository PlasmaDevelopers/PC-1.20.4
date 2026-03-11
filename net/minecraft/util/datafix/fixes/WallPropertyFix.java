/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class WallPropertyFix extends DataFix {
/* 14 */   private static final Set<String> WALL_BLOCKS = (Set<String>)ImmutableSet.of("minecraft:andesite_wall", "minecraft:brick_wall", "minecraft:cobblestone_wall", "minecraft:diorite_wall", "minecraft:end_stone_brick_wall", "minecraft:granite_wall", (Object[])new String[] { "minecraft:mossy_cobblestone_wall", "minecraft:mossy_stone_brick_wall", "minecraft:nether_brick_wall", "minecraft:prismarine_wall", "minecraft:red_nether_brick_wall", "minecraft:red_sandstone_wall", "minecraft:sandstone_wall", "minecraft:stone_brick_wall" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WallPropertyFix(Schema $$0, boolean $$1) {
/* 32 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 37 */     return fixTypeEverywhereTyped("WallPropertyFix", getInputSchema().getType(References.BLOCK_STATE), $$0 -> $$0.update(DSL.remainderFinder(), WallPropertyFix::upgradeBlockStateTag));
/*    */   }
/*    */   
/*    */   private static String mapProperty(String $$0) {
/* 41 */     return "true".equals($$0) ? "low" : "none";
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> fixWallProperty(Dynamic<T> $$0, String $$1) {
/* 45 */     return $$0.update($$1, $$0 -> {
/*    */           Objects.requireNonNull($$0);
/*    */           return (Dynamic)DataFixUtils.orElse($$0.asString().result().map(WallPropertyFix::mapProperty).map($$0::createString), $$0);
/*    */         }); } private static <T> Dynamic<T> upgradeBlockStateTag(Dynamic<T> $$0) {
/* 49 */     Objects.requireNonNull(WALL_BLOCKS); boolean $$1 = $$0.get("Name").asString().result().filter(WALL_BLOCKS::contains).isPresent();
/* 50 */     if (!$$1) {
/* 51 */       return $$0;
/*    */     }
/*    */     
/* 54 */     return $$0.update("Properties", $$0 -> {
/*    */           Dynamic<?> $$1 = fixWallProperty($$0, "east");
/*    */           $$1 = fixWallProperty($$1, "west");
/*    */           $$1 = fixWallProperty($$1, "north");
/*    */           return fixWallProperty($$1, "south");
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\WallPropertyFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */