/*    */ package net.minecraft.world.scores;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.HoverEvent;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.network.chat.Style;
/*    */ 
/*    */ public interface ScoreHolder
/*    */ {
/* 12 */   public static final ScoreHolder WILDCARD = new ScoreHolder()
/*    */     {
/*    */       public String getScoreboardName() {
/* 15 */         return "*";
/*    */       }
/*    */     };
/*    */   
/*    */   public static final String WILDCARD_NAME = "*";
/*    */   
/*    */   @Nullable
/*    */   default Component getDisplayName() {
/* 23 */     return null;
/*    */   }
/*    */   
/*    */   default Component getFeedbackDisplayName() {
/* 27 */     Component $$0 = getDisplayName();
/* 28 */     if ($$0 != null) {
/* 29 */       return (Component)$$0.copy().withStyle($$0 -> $$0.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(getScoreboardName()))));
/*    */     }
/* 31 */     return (Component)Component.literal(getScoreboardName());
/*    */   }
/*    */   
/*    */   static ScoreHolder forNameOnly(final String name) {
/* 35 */     if (name.equals("*")) {
/* 36 */       return WILDCARD;
/*    */     }
/*    */     
/* 39 */     final MutableComponent feedbackName = Component.literal(name);
/* 40 */     return new ScoreHolder()
/*    */       {
/*    */         public String getScoreboardName() {
/* 43 */           return name;
/*    */         }
/*    */ 
/*    */         
/*    */         public Component getFeedbackDisplayName() {
/* 48 */           return feedbackName;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static ScoreHolder fromGameProfile(GameProfile $$0) {
/* 54 */     final String name = $$0.getName();
/* 55 */     return new ScoreHolder()
/*    */       {
/*    */         public String getScoreboardName() {
/* 58 */           return name;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   String getScoreboardName();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\ScoreHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */