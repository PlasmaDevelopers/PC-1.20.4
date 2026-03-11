/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ public enum BanReason
/*    */ {
/*  9 */   GENERIC_VIOLATION("generic_violation"),
/* 10 */   FALSE_REPORTING("false_reporting"),
/* 11 */   HATE_SPEECH("hate_speech"),
/* 12 */   HATE_TERRORISM_NOTORIOUS_FIGURE("hate_terrorism_notorious_figure"),
/* 13 */   HARASSMENT_OR_BULLYING("harassment_or_bullying"),
/* 14 */   DEFAMATION_IMPERSONATION_FALSE_INFORMATION("defamation_impersonation_false_information"),
/* 15 */   DRUGS("drugs"),
/* 16 */   FRAUD("fraud"),
/* 17 */   SPAM_OR_ADVERTISING("spam_or_advertising"),
/* 18 */   NUDITY_OR_PORNOGRAPHY("nudity_or_pornography"),
/* 19 */   SEXUALLY_INAPPROPRIATE("sexually_inappropriate"),
/* 20 */   EXTREME_VIOLENCE_OR_GORE("extreme_violence_or_gore"),
/* 21 */   IMMINENT_HARM_TO_PERSON_OR_PROPERTY("imminent_harm_to_person_or_property");
/*    */   
/*    */   private final Component title;
/*    */ 
/*    */   
/*    */   BanReason(String $$0) {
/* 27 */     this.title = (Component)Component.translatable("gui.banned.reason." + $$0);
/*    */   }
/*    */   
/*    */   public Component title() {
/* 31 */     return this.title;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static BanReason byId(int $$0) {
/* 36 */     switch ($$0) { case 17: case 19: case 23: case 31: case 2: case 5: case 16: case 25: case 21: case 27: case 28: case 29: case 30: case 32: case 33: case 34: case 53:  }  return 
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
/* 52 */       null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\BanReason.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */