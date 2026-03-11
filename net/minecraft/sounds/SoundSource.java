/*    */ package net.minecraft.sounds;
/*    */ 
/*    */ public enum SoundSource {
/*  4 */   MASTER("master"),
/*  5 */   MUSIC("music"),
/*  6 */   RECORDS("record"),
/*  7 */   WEATHER("weather"),
/*  8 */   BLOCKS("block"),
/*  9 */   HOSTILE("hostile"),
/* 10 */   NEUTRAL("neutral"),
/* 11 */   PLAYERS("player"),
/* 12 */   AMBIENT("ambient"),
/* 13 */   VOICE("voice");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   SoundSource(String $$0) {
/* 19 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\sounds\SoundSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */