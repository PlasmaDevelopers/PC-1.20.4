/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ import net.minecraft.util.profiling.ProfileResults;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class State
/*    */ {
/*    */   final String name;
/*    */   final ProfileResults preparationResult;
/*    */   final ProfileResults reloadResult;
/*    */   final AtomicLong preparationNanos;
/*    */   final AtomicLong reloadNanos;
/*    */   
/*    */   State(String $$0, ProfileResults $$1, ProfileResults $$2, AtomicLong $$3, AtomicLong $$4) {
/* 93 */     this.name = $$0;
/* 94 */     this.preparationResult = $$1;
/* 95 */     this.reloadResult = $$2;
/* 96 */     this.preparationNanos = $$3;
/* 97 */     this.reloadNanos = $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ProfiledReloadInstance$State.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */