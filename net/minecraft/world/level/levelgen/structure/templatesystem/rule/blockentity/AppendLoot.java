/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtOps;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class AppendLoot implements RuleBlockEntityModifier {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final Codec<AppendLoot> CODEC; private final ResourceLocation lootTable;
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("loot_table").forGetter(())).apply((Applicative)$$0, AppendLoot::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AppendLoot(ResourceLocation $$0) {
/* 25 */     this.lootTable = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag apply(RandomSource $$0, @Nullable CompoundTag $$1) {
/* 30 */     CompoundTag $$2 = ($$1 == null) ? new CompoundTag() : $$1.copy();
/*    */ 
/*    */     
/* 33 */     Objects.requireNonNull(LOGGER); ResourceLocation.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.lootTable).resultOrPartial(LOGGER::error)
/* 34 */       .ifPresent($$1 -> $$0.put("LootTable", $$1));
/* 35 */     $$2.putLong("LootTableSeed", $$0.nextLong());
/*    */     
/* 37 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public RuleBlockEntityModifierType<?> getType() {
/* 42 */     return RuleBlockEntityModifierType.APPEND_LOOT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\rule\blockentity\AppendLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */