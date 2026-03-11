/*    */ package net.minecraft.data.structures;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.util.datafix.DataFixTypes;
/*    */ import net.minecraft.util.datafix.DataFixers;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class StructureUpdater implements SnbtToNbt.Filter {
/* 14 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */   
/*    */   public CompoundTag apply(String $$0, CompoundTag $$1) {
/* 18 */     if ($$0.startsWith("data/minecraft/structures/")) {
/* 19 */       return update($$0, $$1);
/*    */     }
/* 21 */     return $$1;
/*    */   }
/*    */   
/*    */   public static CompoundTag update(String $$0, CompoundTag $$1) {
/* 25 */     StructureTemplate $$2 = new StructureTemplate();
/* 26 */     int $$3 = NbtUtils.getDataVersion($$1, 500);
/* 27 */     int $$4 = 3678;
/* 28 */     if ($$3 < 3678) {
/* 29 */       LOGGER.warn("SNBT Too old, do not forget to update: {} < {}: {}", new Object[] { Integer.valueOf($$3), Integer.valueOf(3678), $$0 });
/*    */     }
/* 31 */     CompoundTag $$5 = DataFixTypes.STRUCTURE.updateToCurrentVersion(DataFixers.getDataFixer(), $$1, $$3);
/* 32 */     $$2.load((HolderGetter)BuiltInRegistries.BLOCK.asLookup(), $$5);
/* 33 */     return $$2.save(new CompoundTag());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\structures\StructureUpdater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */