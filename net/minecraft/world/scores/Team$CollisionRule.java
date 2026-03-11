/*    */ package net.minecraft.world.scores;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
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
/*    */ public enum CollisionRule
/*    */ {
/*    */   private static final Map<String, CollisionRule> BY_NAME;
/*    */   public final String name;
/*    */   public final int id;
/* 73 */   ALWAYS("always", 0),
/* 74 */   NEVER("never", 1),
/* 75 */   PUSH_OTHER_TEAMS("pushOtherTeams", 2),
/* 76 */   PUSH_OWN_TEAM("pushOwnTeam", 3);
/*    */   static {
/* 78 */     BY_NAME = (Map<String, CollisionRule>)Arrays.<CollisionRule>stream(values()).collect(Collectors.toMap($$0 -> $$0.name, $$0 -> $$0));
/*    */   }
/*    */   @Nullable
/*    */   public static CollisionRule byName(String $$0) {
/* 82 */     return BY_NAME.get($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   CollisionRule(String $$0, int $$1) {
/* 89 */     this.name = $$0;
/* 90 */     this.id = $$1;
/*    */   }
/*    */   
/*    */   public Component getDisplayName() {
/* 94 */     return (Component)Component.translatable("team.collision." + this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\Team$CollisionRule.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */