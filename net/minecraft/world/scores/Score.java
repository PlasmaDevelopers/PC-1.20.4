/*    */ package net.minecraft.world.scores;
/*    */ 
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtOps;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.numbers.NumberFormat;
/*    */ import net.minecraft.network.chat.numbers.NumberFormatTypes;
/*    */ 
/*    */ public class Score
/*    */   implements ReadOnlyScoreInfo
/*    */ {
/*    */   private static final String TAG_SCORE = "Score";
/*    */   private static final String TAG_LOCKED = "Locked";
/*    */   private static final String TAG_DISPLAY = "display";
/*    */   private static final String TAG_FORMAT = "format";
/*    */   private int value;
/*    */   private boolean locked = true;
/*    */   @Nullable
/*    */   private Component display;
/*    */   @Nullable
/*    */   private NumberFormat numberFormat;
/*    */   
/*    */   public int value() {
/* 27 */     return this.value;
/*    */   }
/*    */   
/*    */   public void value(int $$0) {
/* 31 */     this.value = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLocked() {
/* 36 */     return this.locked;
/*    */   }
/*    */   
/*    */   public void setLocked(boolean $$0) {
/* 40 */     this.locked = $$0;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Component display() {
/* 45 */     return this.display;
/*    */   }
/*    */   
/*    */   public void display(@Nullable Component $$0) {
/* 49 */     this.display = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public NumberFormat numberFormat() {
/* 55 */     return this.numberFormat;
/*    */   }
/*    */   
/*    */   public void numberFormat(@Nullable NumberFormat $$0) {
/* 59 */     this.numberFormat = $$0;
/*    */   }
/*    */   
/*    */   public CompoundTag write() {
/* 63 */     CompoundTag $$0 = new CompoundTag();
/* 64 */     $$0.putInt("Score", this.value);
/* 65 */     $$0.putBoolean("Locked", this.locked);
/* 66 */     if (this.display != null) {
/* 67 */       $$0.putString("display", Component.Serializer.toJson(this.display));
/*    */     }
/* 69 */     if (this.numberFormat != null) {
/* 70 */       NumberFormatTypes.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.numberFormat).result().ifPresent($$1 -> $$0.put("format", $$1));
/*    */     }
/* 72 */     return $$0;
/*    */   }
/*    */   
/*    */   public static Score read(CompoundTag $$0) {
/* 76 */     Score $$1 = new Score();
/* 77 */     $$1.value = $$0.getInt("Score");
/* 78 */     $$1.locked = $$0.getBoolean("Locked");
/* 79 */     if ($$0.contains("display", 8)) {
/* 80 */       $$1.display = (Component)Component.Serializer.fromJson($$0.getString("display"));
/*    */     }
/* 82 */     if ($$0.contains("format", 10)) {
/* 83 */       NumberFormatTypes.CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$0.get("format")).result().ifPresent($$1 -> $$0.numberFormat = $$1);
/*    */     }
/* 85 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\Score.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */