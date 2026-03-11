/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ScoreboardDisplaySlotFix extends DataFix {
/*    */   public ScoreboardDisplaySlotFix(Schema $$0) {
/* 17 */     super($$0, false);
/*    */   }
/*    */   
/* 20 */   private static final Map<String, String> SLOT_RENAMES = (Map<String, String>)ImmutableMap.builder()
/* 21 */     .put("slot_0", "list")
/* 22 */     .put("slot_1", "sidebar")
/* 23 */     .put("slot_2", "below_name")
/* 24 */     .put("slot_3", "sidebar.team.black")
/* 25 */     .put("slot_4", "sidebar.team.dark_blue")
/* 26 */     .put("slot_5", "sidebar.team.dark_green")
/* 27 */     .put("slot_6", "sidebar.team.dark_aqua")
/* 28 */     .put("slot_7", "sidebar.team.dark_red")
/* 29 */     .put("slot_8", "sidebar.team.dark_purple")
/* 30 */     .put("slot_9", "sidebar.team.gold")
/* 31 */     .put("slot_10", "sidebar.team.gray")
/* 32 */     .put("slot_11", "sidebar.team.dark_gray")
/* 33 */     .put("slot_12", "sidebar.team.blue")
/* 34 */     .put("slot_13", "sidebar.team.green")
/* 35 */     .put("slot_14", "sidebar.team.aqua")
/* 36 */     .put("slot_15", "sidebar.team.red")
/* 37 */     .put("slot_16", "sidebar.team.light_purple")
/* 38 */     .put("slot_17", "sidebar.team.yellow")
/* 39 */     .put("slot_18", "sidebar.team.white")
/* 40 */     .build();
/*    */   
/*    */   @Nullable
/*    */   private static String rename(String $$0) {
/* 44 */     return SLOT_RENAMES.get($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 49 */     Type<?> $$0 = getInputSchema().getType(References.SAVED_DATA_SCOREBOARD);
/* 50 */     OpticFinder<?> $$1 = $$0.findField("data");
/*    */     
/* 52 */     return fixTypeEverywhereTyped("Scoreboard DisplaySlot rename", $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ScoreboardDisplaySlotFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */