/*    */ package net.minecraft.world.entity.monster.warden;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import java.util.Comparator;
/*    */ import net.minecraft.world.entity.Entity;
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
/*    */ @VisibleForTesting
/*    */ public final class Sorter
/*    */   extends Record
/*    */   implements Comparator<Entity>
/*    */ {
/*    */   private final AngerManagement angerManagement;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #58	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #58	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #58	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/monster/warden/AngerManagement$Sorter;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public AngerManagement angerManagement() {
/* 58 */     return this.angerManagement; } protected Sorter(AngerManagement $$0) {
/* 59 */     this.angerManagement = $$0;
/*    */   }
/*    */   public int compare(Entity $$0, Entity $$1) {
/* 62 */     if ($$0.equals($$1)) {
/* 63 */       return 0;
/*    */     }
/*    */     
/* 66 */     int $$2 = this.angerManagement.angerBySuspect.getOrDefault($$0, 0);
/* 67 */     int $$3 = this.angerManagement.angerBySuspect.getOrDefault($$1, 0);
/*    */ 
/*    */     
/* 70 */     this.angerManagement.highestAnger = Math.max(this.angerManagement.highestAnger, Math.max($$2, $$3));
/*    */     
/* 72 */     boolean $$4 = AngerLevel.byAnger($$2).isAngry();
/* 73 */     boolean $$5 = AngerLevel.byAnger($$3).isAngry();
/* 74 */     if ($$4 != $$5) {
/* 75 */       return $$4 ? -1 : 1;
/*    */     }
/*    */ 
/*    */     
/* 79 */     boolean $$6 = $$0 instanceof net.minecraft.world.entity.player.Player;
/* 80 */     boolean $$7 = $$1 instanceof net.minecraft.world.entity.player.Player;
/* 81 */     if ($$6 != $$7) {
/* 82 */       return $$6 ? -1 : 1;
/*    */     }
/* 84 */     return Integer.compare($$3, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\warden\AngerManagement$Sorter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */