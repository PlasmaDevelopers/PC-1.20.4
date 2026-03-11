/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.nbt.Tag;
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
/*    */ final class CopyOperation
/*    */   extends Record
/*    */ {
/*    */   private final CopyNbtFunction.Path sourcePath;
/*    */   private final CopyNbtFunction.Path targetPath;
/*    */   private final CopyNbtFunction.MergeStrategy op;
/*    */   public static final Codec<CopyOperation> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   CopyOperation(CopyNbtFunction.Path $$0, CopyNbtFunction.Path $$1, CopyNbtFunction.MergeStrategy $$2) {
/* 46 */     this.sourcePath = $$0; this.targetPath = $$1; this.op = $$2; } public CopyNbtFunction.Path sourcePath() { return this.sourcePath; } public CopyNbtFunction.Path targetPath() { return this.targetPath; } public CopyNbtFunction.MergeStrategy op() { return this.op; } static {
/* 47 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)CopyNbtFunction.Path.CODEC.fieldOf("source").forGetter(CopyOperation::sourcePath), (App)CopyNbtFunction.Path.CODEC.fieldOf("target").forGetter(CopyOperation::targetPath), (App)CopyNbtFunction.MergeStrategy.CODEC.fieldOf("op").forGetter(CopyOperation::op)).apply((Applicative)$$0, CopyOperation::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void apply(Supplier<Tag> $$0, Tag $$1) {
/*    */     try {
/* 55 */       List<Tag> $$2 = this.sourcePath.path().get($$1);
/* 56 */       if (!$$2.isEmpty()) {
/* 57 */         this.op.merge($$0.get(), this.targetPath.path(), $$2);
/*    */       }
/* 59 */     } catch (CommandSyntaxException commandSyntaxException) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyNbtFunction$CopyOperation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */