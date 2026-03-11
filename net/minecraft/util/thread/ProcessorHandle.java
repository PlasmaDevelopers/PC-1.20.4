/*    */ package net.minecraft.util.thread;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ProcessorHandle<Msg>
/*    */   extends AutoCloseable
/*    */ {
/*    */   default void close() {}
/*    */   
/*    */   default <Source> CompletableFuture<Source> ask(Function<? super ProcessorHandle<Source>, ? extends Msg> $$0) {
/* 19 */     CompletableFuture<Source> $$1 = new CompletableFuture<>();
/* 20 */     Objects.requireNonNull($$1); Msg $$2 = $$0.apply(of("ask future procesor handle", $$1::complete));
/* 21 */     tell($$2);
/* 22 */     return $$1;
/*    */   }
/*    */   
/*    */   default <Source> CompletableFuture<Source> askEither(Function<? super ProcessorHandle<Either<Source, Exception>>, ? extends Msg> $$0) {
/* 26 */     CompletableFuture<Source> $$1 = new CompletableFuture<>();
/* 27 */     Msg $$2 = $$0.apply(of("ask future procesor handle", $$1 -> {
/*    */             Objects.requireNonNull($$0); $$1.ifLeft($$0::complete); Objects.requireNonNull($$0);
/*    */             $$1.ifRight($$0::completeExceptionally);
/*    */           }));
/* 31 */     tell($$2);
/* 32 */     return $$1;
/*    */   }
/*    */   
/*    */   static <Msg> ProcessorHandle<Msg> of(final String name, final Consumer<Msg> tell) {
/* 36 */     return new ProcessorHandle<Msg>()
/*    */       {
/*    */         public String name() {
/* 39 */           return name;
/*    */         }
/*    */ 
/*    */         
/*    */         public void tell(Msg $$0) {
/* 44 */           tell.accept($$0);
/*    */         }
/*    */ 
/*    */         
/*    */         public String toString() {
/* 49 */           return name;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   String name();
/*    */   
/*    */   void tell(Msg paramMsg);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\ProcessorHandle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */