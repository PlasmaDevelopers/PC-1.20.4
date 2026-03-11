/*    */ package net.minecraft.world.scores;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ public abstract class Team
/*    */ {
/*    */   public boolean isAlliedTo(@Nullable Team $$0) {
/* 15 */     if ($$0 == null) {
/* 16 */       return false;
/*    */     }
/* 18 */     if (this == $$0) {
/* 19 */       return true;
/*    */     }
/* 21 */     return false;
/*    */   }
/*    */   public abstract String getName();
/*    */   
/*    */   public abstract MutableComponent getFormattedName(Component paramComponent);
/*    */   
/*    */   public abstract boolean canSeeFriendlyInvisibles();
/*    */   
/*    */   public abstract boolean isAllowFriendlyFire();
/*    */   
/*    */   public abstract Visibility getNameTagVisibility();
/*    */   
/*    */   public abstract ChatFormatting getColor();
/*    */   
/*    */   public abstract Collection<String> getPlayers();
/*    */   
/*    */   public abstract Visibility getDeathMessageVisibility();
/*    */   
/*    */   public abstract CollisionRule getCollisionRule();
/*    */   
/*    */   public enum Visibility { private static final Map<String, Visibility> BY_NAME;
/*    */     public final String name;
/* 43 */     ALWAYS("always", 0),
/* 44 */     NEVER("never", 1),
/* 45 */     HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
/* 46 */     HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3); public final int id;
/*    */     static {
/* 48 */       BY_NAME = (Map<String, Visibility>)Arrays.<Visibility>stream(values()).collect(Collectors.toMap($$0 -> $$0.name, $$0 -> $$0));
/*    */     }
/*    */     public static String[] getAllNames() {
/* 51 */       return (String[])BY_NAME.keySet().toArray((Object[])new String[0]);
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public static Visibility byName(String $$0) {
/* 56 */       return BY_NAME.get($$0);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     Visibility(String $$0, int $$1) {
/* 63 */       this.name = $$0;
/* 64 */       this.id = $$1;
/*    */     }
/*    */     
/*    */     public Component getDisplayName() {
/* 68 */       return (Component)Component.translatable("team.visibility." + this.name);
/*    */     } }
/*    */ 
/*    */   
/*    */   public enum CollisionRule {
/* 73 */     ALWAYS("always", 0),
/* 74 */     NEVER("never", 1),
/* 75 */     PUSH_OTHER_TEAMS("pushOtherTeams", 2),
/* 76 */     PUSH_OWN_TEAM("pushOwnTeam", 3); private static final Map<String, CollisionRule> BY_NAME; public final String name; public final int id;
/*    */     static {
/* 78 */       BY_NAME = (Map<String, CollisionRule>)Arrays.<CollisionRule>stream(values()).collect(Collectors.toMap($$0 -> $$0.name, $$0 -> $$0));
/*    */     }
/*    */     @Nullable
/*    */     public static CollisionRule byName(String $$0) {
/* 82 */       return BY_NAME.get($$0);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     CollisionRule(String $$0, int $$1) {
/* 89 */       this.name = $$0;
/* 90 */       this.id = $$1;
/*    */     }
/*    */     
/*    */     public Component getDisplayName() {
/* 94 */       return (Component)Component.translatable("team.collision." + this.name);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\Team.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */