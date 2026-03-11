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
/*    */ public enum Visibility
/*    */ {
/*    */   private static final Map<String, Visibility> BY_NAME;
/*    */   public final String name;
/*    */   public final int id;
/* 43 */   ALWAYS("always", 0),
/* 44 */   NEVER("never", 1),
/* 45 */   HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
/* 46 */   HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);
/*    */   static {
/* 48 */     BY_NAME = (Map<String, Visibility>)Arrays.<Visibility>stream(values()).collect(Collectors.toMap($$0 -> $$0.name, $$0 -> $$0));
/*    */   }
/*    */   public static String[] getAllNames() {
/* 51 */     return (String[])BY_NAME.keySet().toArray((Object[])new String[0]);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static Visibility byName(String $$0) {
/* 56 */     return BY_NAME.get($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Visibility(String $$0, int $$1) {
/* 63 */     this.name = $$0;
/* 64 */     this.id = $$1;
/*    */   }
/*    */   
/*    */   public Component getDisplayName() {
/* 68 */     return (Component)Component.translatable("team.visibility." + this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\Team$Visibility.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */