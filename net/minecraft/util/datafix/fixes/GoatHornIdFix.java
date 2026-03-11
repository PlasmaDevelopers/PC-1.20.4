/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class GoatHornIdFix extends ItemStackTagFix {
/*  7 */   private static final String[] INSTRUMENTS = new String[] { "minecraft:ponder_goat_horn", "minecraft:sing_goat_horn", "minecraft:seek_goat_horn", "minecraft:feel_goat_horn", "minecraft:admire_goat_horn", "minecraft:call_goat_horn", "minecraft:yearn_goat_horn", "minecraft:dream_goat_horn" };
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
/*    */   public GoatHornIdFix(Schema $$0) {
/* 19 */     super($$0, "GoatHornIdFix", $$0 -> $$0.equals("minecraft:goat_horn"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected <T> Dynamic<T> fixItemStackTag(Dynamic<T> $$0) {
/* 24 */     int $$1 = $$0.get("SoundVariant").asInt(0);
/* 25 */     String $$2 = INSTRUMENTS[($$1 >= 0 && $$1 < INSTRUMENTS.length) ? $$1 : 0];
/* 26 */     return $$0.remove("SoundVariant").set("instrument", $$0.createString($$2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\GoatHornIdFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */