/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ public enum ReportReason
/*    */ {
/*  9 */   GENERIC("generic"),
/* 10 */   HATE_SPEECH("hate_speech"),
/* 11 */   HARASSMENT_OR_BULLYING("harassment_or_bullying"),
/* 12 */   SELF_HARM_OR_SUICIDE("self_harm_or_suicide"),
/* 13 */   IMMINENT_HARM("imminent_harm"),
/* 14 */   DEFAMATION_IMPERSONATION_FALSE_INFORMATION("defamation_impersonation_false_information"),
/* 15 */   ALCOHOL_TOBACCO_DRUGS("alcohol_tobacco_drugs"),
/* 16 */   CHILD_SEXUAL_EXPLOITATION_OR_ABUSE("child_sexual_exploitation_or_abuse"),
/* 17 */   TERRORISM_OR_VIOLENT_EXTREMISM("terrorism_or_violent_extremism"),
/* 18 */   NON_CONSENSUAL_INTIMATE_IMAGERY("non_consensual_intimate_imagery");
/*    */   
/*    */   private final String backendName;
/*    */   
/*    */   private final Component title;
/*    */   
/*    */   private final Component description;
/*    */   
/*    */   ReportReason(String $$0) {
/* 27 */     this.backendName = $$0.toUpperCase(Locale.ROOT);
/* 28 */     String $$1 = "gui.abuseReport.reason." + $$0;
/* 29 */     this.title = (Component)Component.translatable($$1);
/* 30 */     this.description = (Component)Component.translatable($$1 + ".description");
/*    */   }
/*    */   
/*    */   public String backendName() {
/* 34 */     return this.backendName;
/*    */   }
/*    */   
/*    */   public Component title() {
/* 38 */     return this.title;
/*    */   }
/*    */   
/*    */   public Component description() {
/* 42 */     return this.description;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\ReportReason.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */