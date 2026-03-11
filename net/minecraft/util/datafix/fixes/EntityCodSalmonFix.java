/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class EntityCodSalmonFix
/*    */   extends SimplestEntityRenameFix {
/*  9 */   public static final Map<String, String> RENAMED_IDS = (Map<String, String>)ImmutableMap.builder()
/* 10 */     .put("minecraft:salmon_mob", "minecraft:salmon")
/* 11 */     .put("minecraft:cod_mob", "minecraft:cod")
/* 12 */     .build();
/*    */   
/* 14 */   public static final Map<String, String> RENAMED_EGG_IDS = (Map<String, String>)ImmutableMap.builder()
/* 15 */     .put("minecraft:salmon_mob_spawn_egg", "minecraft:salmon_spawn_egg")
/* 16 */     .put("minecraft:cod_mob_spawn_egg", "minecraft:cod_spawn_egg")
/* 17 */     .build();
/*    */   
/*    */   public EntityCodSalmonFix(Schema $$0, boolean $$1) {
/* 20 */     super("EntityCodSalmonFix", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String rename(String $$0) {
/* 25 */     return RENAMED_IDS.getOrDefault($$0, $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityCodSalmonFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */