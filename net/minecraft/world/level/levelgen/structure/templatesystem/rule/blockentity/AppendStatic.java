/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ 
/*    */ public class AppendStatic implements RuleBlockEntityModifier {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)CompoundTag.CODEC.fieldOf("data").forGetter(())).apply((Applicative)$$0, AppendStatic::new));
/*    */   }
/*    */   public static final Codec<AppendStatic> CODEC;
/*    */   private final CompoundTag tag;
/*    */   
/*    */   public AppendStatic(CompoundTag $$0) {
/* 17 */     this.tag = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag apply(RandomSource $$0, @Nullable CompoundTag $$1) {
/* 22 */     return ($$1 == null) ? this.tag.copy() : $$1.merge(this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public RuleBlockEntityModifierType<?> getType() {
/* 27 */     return RuleBlockEntityModifierType.APPEND_STATIC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\rule\blockentity\AppendStatic.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */