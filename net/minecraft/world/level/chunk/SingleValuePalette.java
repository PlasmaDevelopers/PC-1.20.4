/*    */ package net.minecraft.world.level.chunk;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.VarInt;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class SingleValuePalette<T>
/*    */   implements Palette<T> {
/*    */   private final IdMap<T> registry;
/*    */   @Nullable
/*    */   private T value;
/*    */   private final PaletteResize<T> resizeHandler;
/*    */   
/*    */   public SingleValuePalette(IdMap<T> $$0, PaletteResize<T> $$1, List<T> $$2) {
/* 19 */     this.registry = $$0;
/* 20 */     this.resizeHandler = $$1;
/* 21 */     if ($$2.size() > 0) {
/* 22 */       Validate.isTrue(($$2.size() <= 1), "Can't initialize SingleValuePalette with %d values.", $$2.size());
/* 23 */       this.value = $$2.get(0);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static <A> Palette<A> create(int $$0, IdMap<A> $$1, PaletteResize<A> $$2, List<A> $$3) {
/* 28 */     return new SingleValuePalette<>($$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public int idFor(T $$0) {
/* 33 */     if (this.value == null || this.value == $$0) {
/* 34 */       this.value = $$0;
/* 35 */       return 0;
/*    */     } 
/* 37 */     return this.resizeHandler.onResize(1, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean maybeHas(Predicate<T> $$0) {
/* 42 */     if (this.value == null) {
/* 43 */       throw new IllegalStateException("Use of an uninitialized palette");
/*    */     }
/* 45 */     return $$0.test(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public T valueFor(int $$0) {
/* 50 */     if (this.value == null || $$0 != 0) {
/* 51 */       throw new IllegalStateException("Missing Palette entry for id " + $$0 + ".");
/*    */     }
/* 53 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(FriendlyByteBuf $$0) {
/* 58 */     this.value = (T)this.registry.byIdOrThrow($$0.readVarInt());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 63 */     if (this.value == null) {
/* 64 */       throw new IllegalStateException("Use of an uninitialized palette");
/*    */     }
/* 66 */     $$0.writeVarInt(this.registry.getId(this.value));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 71 */     if (this.value == null) {
/* 72 */       throw new IllegalStateException("Use of an uninitialized palette");
/*    */     }
/* 74 */     return VarInt.getByteSize(this.registry.getId(this.value));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 79 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Palette<T> copy() {
/* 84 */     if (this.value == null) {
/* 85 */       throw new IllegalStateException("Use of an uninitialized palette");
/*    */     }
/* 87 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\SingleValuePalette.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */