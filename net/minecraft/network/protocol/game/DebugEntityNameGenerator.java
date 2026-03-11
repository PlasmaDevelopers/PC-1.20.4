/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugEntityNameGenerator
/*    */ {
/* 17 */   private static final String[] NAMES_FIRST_PART = new String[] { "Slim", "Far", "River", "Silly", "Fat", "Thin", "Fish", "Bat", "Dark", "Oak", "Sly", "Bush", "Zen", "Bark", "Cry", "Slack", "Soup", "Grim", "Hook", "Dirt", "Mud", "Sad", "Hard", "Crook", "Sneak", "Stink", "Weird", "Fire", "Soot", "Soft", "Rough", "Cling", "Scar" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   private static final String[] NAMES_SECOND_PART = new String[] { "Fox", "Tail", "Jaw", "Whisper", "Twig", "Root", "Finder", "Nose", "Brow", "Blade", "Fry", "Seek", "Wart", "Tooth", "Foot", "Leaf", "Stone", "Fall", "Face", "Tongue", "Voice", "Lip", "Mouth", "Snail", "Toe", "Ear", "Hair", "Beard", "Shirt", "Fist" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getEntityName(Entity $$0) {
/* 31 */     if ($$0 instanceof net.minecraft.world.entity.player.Player) {
/* 32 */       return $$0.getName().getString();
/*    */     }
/* 34 */     Component $$1 = $$0.getCustomName();
/* 35 */     if ($$1 != null) {
/* 36 */       return $$1.getString();
/*    */     }
/* 38 */     return getEntityName($$0.getUUID());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getEntityName(UUID $$0) {
/* 47 */     RandomSource $$1 = getRandom($$0);
/* 48 */     return getRandomString($$1, NAMES_FIRST_PART) + getRandomString($$1, NAMES_FIRST_PART);
/*    */   }
/*    */   
/*    */   private static String getRandomString(RandomSource $$0, String[] $$1) {
/* 52 */     return (String)Util.getRandom((Object[])$$1, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   private static RandomSource getRandom(UUID $$0) {
/* 57 */     return RandomSource.create(($$0.hashCode() >> 2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\DebugEntityNameGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */