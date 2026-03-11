/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntityPufferfishRenameFix
/*    */   extends SimplestEntityRenameFix {
/* 10 */   public static final Map<String, String> RENAMED_IDS = (Map<String, String>)ImmutableMap.builder()
/* 11 */     .put("minecraft:puffer_fish_spawn_egg", "minecraft:pufferfish_spawn_egg")
/* 12 */     .build();
/*    */   
/*    */   public EntityPufferfishRenameFix(Schema $$0, boolean $$1) {
/* 15 */     super("EntityPufferfishRenameFix", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String rename(String $$0) {
/* 20 */     return Objects.equals("minecraft:puffer_fish", $$0) ? "minecraft:pufferfish" : $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityPufferfishRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */