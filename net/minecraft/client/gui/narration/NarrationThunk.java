/*    */ package net.minecraft.client.gui.narration;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.Unit;
/*    */ 
/*    */ 
/*    */ public class NarrationThunk<T>
/*    */ {
/*    */   private final T contents;
/*    */   private final BiConsumer<Consumer<String>, T> converter;
/* 14 */   public static final NarrationThunk<?> EMPTY = new NarrationThunk((T)Unit.INSTANCE, ($$0, $$1) -> {
/*    */       
/*    */       }); private NarrationThunk(T $$0, BiConsumer<Consumer<String>, T> $$1) {
/* 17 */     this.contents = $$0;
/* 18 */     this.converter = $$1;
/*    */   }
/*    */   
/*    */   public static NarrationThunk<?> from(String $$0) {
/* 22 */     return new NarrationThunk($$0, Consumer::accept);
/*    */   }
/*    */   
/*    */   public static NarrationThunk<?> from(Component $$0) {
/* 26 */     return new NarrationThunk($$0, ($$0, $$1) -> $$0.accept($$1.getString()));
/*    */   }
/*    */   
/*    */   public static NarrationThunk<?> from(List<Component> $$0) {
/* 30 */     return new NarrationThunk($$0, ($$1, $$2) -> $$0.stream().map(Component::getString).forEach($$1));
/*    */   }
/*    */   
/*    */   public void getText(Consumer<String> $$0) {
/* 34 */     this.converter.accept($$0, this.contents);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 39 */     if (this == $$0) {
/* 40 */       return true;
/*    */     }
/*    */     
/* 43 */     if ($$0 instanceof NarrationThunk) { NarrationThunk<?> $$1 = (NarrationThunk)$$0;
/* 44 */       return ($$1.converter == this.converter && $$1.contents.equals(this.contents)); }
/*    */ 
/*    */     
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     int $$0 = this.contents.hashCode();
/* 53 */     $$0 = 31 * $$0 + this.converter.hashCode();
/* 54 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\narration\NarrationThunk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */