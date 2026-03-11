/*    */ package net.minecraft.network.syncher;
/*    */ 
/*    */ public class EntityDataAccessor<T> {
/*    */   private final int id;
/*    */   private final EntityDataSerializer<T> serializer;
/*    */   
/*    */   public EntityDataAccessor(int $$0, EntityDataSerializer<T> $$1) {
/*  8 */     this.id = $$0;
/*  9 */     this.serializer = $$1;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 13 */     return this.id;
/*    */   }
/*    */   
/*    */   public EntityDataSerializer<T> getSerializer() {
/* 17 */     return this.serializer;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 22 */     if (this == $$0) {
/* 23 */       return true;
/*    */     }
/* 25 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     EntityDataAccessor<?> $$1 = (EntityDataAccessor)$$0;
/*    */     
/* 31 */     return (this.id == $$1.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 36 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     return "<entity data: " + this.id + ">";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\EntityDataAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */