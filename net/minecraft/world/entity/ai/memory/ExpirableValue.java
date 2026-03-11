/*    */ package net.minecraft.world.entity.ai.memory;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.VisibleForDebug;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExpirableValue<T>
/*    */ {
/*    */   private final T value;
/*    */   private long timeToLive;
/*    */   
/*    */   public ExpirableValue(T $$0, long $$1) {
/* 18 */     this.value = $$0;
/* 19 */     this.timeToLive = $$1;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 23 */     if (canExpire()) {
/* 24 */       this.timeToLive--;
/*    */     }
/*    */   }
/*    */   
/*    */   public static <T> ExpirableValue<T> of(T $$0) {
/* 29 */     return new ExpirableValue<>($$0, Long.MAX_VALUE);
/*    */   }
/*    */   
/*    */   public static <T> ExpirableValue<T> of(T $$0, long $$1) {
/* 33 */     return new ExpirableValue<>($$0, $$1);
/*    */   }
/*    */   
/*    */   public long getTimeToLive() {
/* 37 */     return this.timeToLive;
/*    */   }
/*    */   
/*    */   public T getValue() {
/* 41 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean hasExpired() {
/* 45 */     return (this.timeToLive <= 0L);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return "" + this.value + this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   @VisibleForDebug
/*    */   public boolean canExpire() {
/* 56 */     return (this.timeToLive != Long.MAX_VALUE);
/*    */   }
/*    */   
/*    */   public static <T> Codec<ExpirableValue<T>> codec(Codec<T> $$0) {
/* 60 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)$$0.fieldOf("value").forGetter(()), (App)Codec.LONG.optionalFieldOf("ttl").forGetter(())).apply((Applicative)$$1, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\memory\ExpirableValue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */