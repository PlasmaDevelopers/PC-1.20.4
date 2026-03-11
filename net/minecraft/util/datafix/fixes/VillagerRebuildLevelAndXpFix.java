/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.List;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class VillagerRebuildLevelAndXpFix
/*    */   extends DataFix {
/*    */   private static final int TRADES_PER_LEVEL = 2;
/* 18 */   private static final int[] LEVEL_XP_THRESHOLDS = new int[] { 0, 10, 50, 100, 150 };
/*    */   
/*    */   public static int getMinXpPerLevel(int $$0) {
/* 21 */     return LEVEL_XP_THRESHOLDS[Mth.clamp($$0 - 1, 0, LEVEL_XP_THRESHOLDS.length - 1)];
/*    */   }
/*    */   
/*    */   public VillagerRebuildLevelAndXpFix(Schema $$0, boolean $$1) {
/* 25 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 30 */     Type<?> $$0 = getInputSchema().getChoiceType(References.ENTITY, "minecraft:villager");
/* 31 */     OpticFinder<?> $$1 = DSL.namedChoice("minecraft:villager", $$0);
/*    */     
/* 33 */     OpticFinder<?> $$2 = $$0.findField("Offers");
/* 34 */     Type<?> $$3 = $$2.type();
/* 35 */     OpticFinder<?> $$4 = $$3.findField("Recipes");
/* 36 */     List.ListType<?> $$5 = (List.ListType)$$4.type();
/* 37 */     OpticFinder<?> $$6 = $$5.getElement().finder();
/*    */     
/* 39 */     return fixTypeEverywhereTyped("Villager level and xp rebuild", getInputSchema().getType(References.ENTITY), $$5 -> $$5.updateTyped($$0, $$1, ()));
/*    */   }
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
/*    */   private static Typed<?> addLevel(Typed<?> $$0, int $$1) {
/* 72 */     return $$0.update(DSL.remainderFinder(), $$1 -> $$1.update("VillagerData", ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Typed<?> addXpFromLevel(Typed<?> $$0, int $$1) {
/* 79 */     int $$2 = getMinXpPerLevel($$1);
/* 80 */     return $$0.update(DSL.remainderFinder(), $$1 -> $$1.set("Xp", $$1.createInt($$0)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\VillagerRebuildLevelAndXpFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */