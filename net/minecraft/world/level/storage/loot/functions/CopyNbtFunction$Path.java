/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import net.minecraft.commands.arguments.NbtPathArgument;
/*    */ 
/*    */ final class Path extends Record {
/*    */   private final String string;
/*    */   private final NbtPathArgument.NbtPath path;
/*    */   public static final Codec<Path> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   private Path(String $$0, NbtPathArgument.NbtPath $$1)
/*    */   {
/* 28 */     this.string = $$0; this.path = $$1; } public String string() { return this.string; } public NbtPathArgument.NbtPath path() { return this.path; } static {
/* 29 */     CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*    */           
/*    */           try {
/*    */             return DataResult.success(of($$0));
/* 33 */           } catch (CommandSyntaxException $$1) {
/*    */             return DataResult.error(());
/*    */           } 
/*    */         }Path::string);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Path of(String $$0) throws CommandSyntaxException {
/* 41 */     NbtPathArgument.NbtPath $$1 = (new NbtPathArgument()).parse(new StringReader($$0));
/* 42 */     return new Path($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyNbtFunction$Path.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */