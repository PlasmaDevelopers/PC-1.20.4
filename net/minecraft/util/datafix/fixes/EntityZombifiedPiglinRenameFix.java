/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntityZombifiedPiglinRenameFix
/*    */   extends SimplestEntityRenameFix {
/* 10 */   public static final Map<String, String> RENAMED_IDS = (Map<String, String>)ImmutableMap.builder()
/* 11 */     .put("minecraft:zombie_pigman_spawn_egg", "minecraft:zombified_piglin_spawn_egg")
/* 12 */     .build();
/*    */   
/*    */   public EntityZombifiedPiglinRenameFix(Schema $$0) {
/* 15 */     super("EntityZombifiedPiglinRenameFix", $$0, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String rename(String $$0) {
/* 20 */     return Objects.equals("minecraft:zombie_pigman", $$0) ? "minecraft:zombified_piglin" : $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityZombifiedPiglinRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */