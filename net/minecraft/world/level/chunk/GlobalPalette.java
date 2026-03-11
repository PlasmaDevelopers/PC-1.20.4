/*    */ package net.minecraft.world.level.chunk;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class GlobalPalette<T>
/*    */   implements Palette<T> {
/*    */   private final IdMap<T> registry;
/*    */   
/*    */   public GlobalPalette(IdMap<T> $$0) {
/* 13 */     this.registry = $$0;
/*    */   }
/*    */   
/*    */   public static <A> Palette<A> create(int $$0, IdMap<A> $$1, PaletteResize<A> $$2, List<A> $$3) {
/* 17 */     return new GlobalPalette<>($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int idFor(T $$0) {
/* 22 */     int $$1 = this.registry.getId($$0);
/*    */     
/* 24 */     return ($$1 == -1) ? 0 : $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean maybeHas(Predicate<T> $$0) {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public T valueFor(int $$0) {
/* 34 */     T $$1 = (T)this.registry.byId($$0);
/* 35 */     if ($$1 == null) {
/* 36 */       throw new MissingPaletteEntryException($$0);
/*    */     }
/* 38 */     return $$1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(FriendlyByteBuf $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {}
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 51 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 56 */     return this.registry.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public Palette<T> copy() {
/* 61 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\GlobalPalette.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */