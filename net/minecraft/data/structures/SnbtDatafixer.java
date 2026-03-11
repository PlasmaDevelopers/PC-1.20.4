/*    */ package net.minecraft.data.structures;
/*    */ 
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.DetectedVersion;
/*    */ import net.minecraft.SharedConstants;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.server.Bootstrap;
/*    */ 
/*    */ public class SnbtDatafixer
/*    */ {
/*    */   public static void main(String[] $$0) throws IOException {
/* 19 */     SharedConstants.setVersion(DetectedVersion.BUILT_IN);
/* 20 */     Bootstrap.bootStrap();
/* 21 */     for (String $$1 : $$0) {
/* 22 */       updateInDirectory($$1);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void updateInDirectory(String $$0) throws IOException {
/* 27 */     Stream<Path> $$1 = Files.walk(Paths.get($$0, new String[0]), new java.nio.file.FileVisitOption[0]); try {
/* 28 */       $$1.filter($$0 -> $$0.toString().endsWith(".snbt")).forEach($$0 -> {
/*    */             try {
/*    */               String $$1 = Files.readString($$0);
/*    */               CompoundTag $$2 = NbtUtils.snbtToStructure($$1);
/*    */               CompoundTag $$3 = StructureUpdater.update($$0.toString(), $$2);
/*    */               NbtToSnbt.writeSnbt(CachedOutput.NO_CACHE, $$0, NbtUtils.structureToSnbt($$3));
/* 34 */             } catch (CommandSyntaxException|IOException $$4) {
/*    */               throw new RuntimeException($$4);
/*    */             } 
/*    */           });
/* 38 */       if ($$1 != null) $$1.close(); 
/*    */     } catch (Throwable throwable) {
/*    */       if ($$1 != null)
/*    */         try {
/*    */           $$1.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }  
/*    */       throw throwable;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\structures\SnbtDatafixer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */