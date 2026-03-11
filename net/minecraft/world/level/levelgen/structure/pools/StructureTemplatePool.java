/*     */ package net.minecraft.world.level.levelgen.structure.pools;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.RegistryFileCodec;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.GravityProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ public class StructureTemplatePool {
/*     */   private static final int SIZE_UNSET = -2147483648;
/*  30 */   private static final MutableObject<Codec<Holder<StructureTemplatePool>>> CODEC_REFERENCE = new MutableObject();
/*     */   public static final Codec<StructureTemplatePool> DIRECT_CODEC;
/*  32 */   static { DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
/*     */           Objects.requireNonNull(CODEC_REFERENCE);
/*     */ 
/*     */ 
/*     */           
/*     */           return (Function)$$0.group((App)ExtraCodecs.lazyInitializedCodec(CODEC_REFERENCE::getValue).fieldOf("fallback").forGetter(StructureTemplatePool::getFallback), (App)Codec.mapPair(StructurePoolElement.CODEC.fieldOf("element"), Codec.intRange(1, 150).fieldOf("weight")).codec().listOf().fieldOf("elements").forGetter(())).apply((Applicative)$$0, StructureTemplatePool::new);
/*     */         });
/*     */ 
/*     */     
/*  41 */     Objects.requireNonNull(CODEC_REFERENCE); } public static final Codec<Holder<StructureTemplatePool>> CODEC = (Codec<Holder<StructureTemplatePool>>)Util.make(RegistryFileCodec.create(Registries.TEMPLATE_POOL, DIRECT_CODEC), CODEC_REFERENCE::setValue); private final List<Pair<StructurePoolElement, Integer>> rawTemplates; private final ObjectArrayList<StructurePoolElement> templates;
/*     */   private final Holder<StructureTemplatePool> fallback;
/*     */   
/*  44 */   public enum Projection implements StringRepresentable { TERRAIN_MATCHING("terrain_matching", 
/*     */       
/*  46 */       ImmutableList.of(new GravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1))),
/*     */     
/*  48 */     RIGID("rigid", 
/*     */       
/*  50 */       ImmutableList.of());
/*     */ 
/*     */     
/*  53 */     public static final StringRepresentable.EnumCodec<Projection> CODEC = StringRepresentable.fromEnum(Projection::values); private final String name; private final ImmutableList<StructureProcessor> processors;
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     Projection(String $$0, ImmutableList<StructureProcessor> $$1) {
/*  59 */       this.name = $$0;
/*  60 */       this.processors = $$1;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  64 */       return this.name;
/*     */     }
/*     */     
/*     */     public static Projection byName(String $$0) {
/*  68 */       return (Projection)CODEC.byName($$0);
/*     */     }
/*     */     
/*     */     public ImmutableList<StructureProcessor> getProcessors() {
/*  72 */       return this.processors;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/*  77 */       return this.name;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private int maxSize = Integer.MIN_VALUE;
/*     */   
/*     */   public StructureTemplatePool(Holder<StructureTemplatePool> $$0, List<Pair<StructurePoolElement, Integer>> $$1) {
/*  87 */     this.rawTemplates = $$1;
/*  88 */     this.templates = new ObjectArrayList();
/*  89 */     for (Pair<StructurePoolElement, Integer> $$2 : $$1) {
/*  90 */       StructurePoolElement $$3 = (StructurePoolElement)$$2.getFirst();
/*  91 */       for (int $$4 = 0; $$4 < ((Integer)$$2.getSecond()).intValue(); $$4++) {
/*  92 */         this.templates.add($$3);
/*     */       }
/*     */     } 
/*     */     
/*  96 */     this.fallback = $$0;
/*     */   }
/*     */   
/*     */   public StructureTemplatePool(Holder<StructureTemplatePool> $$0, List<Pair<Function<Projection, ? extends StructurePoolElement>, Integer>> $$1, Projection $$2) {
/* 100 */     this.rawTemplates = Lists.newArrayList();
/* 101 */     this.templates = new ObjectArrayList();
/* 102 */     for (Pair<Function<Projection, ? extends StructurePoolElement>, Integer> $$3 : $$1) {
/* 103 */       StructurePoolElement $$4 = ((Function<Projection, StructurePoolElement>)$$3.getFirst()).apply($$2);
/* 104 */       this.rawTemplates.add(Pair.of($$4, $$3.getSecond()));
/* 105 */       for (int $$5 = 0; $$5 < ((Integer)$$3.getSecond()).intValue(); $$5++) {
/* 106 */         this.templates.add($$4);
/*     */       }
/*     */     } 
/*     */     
/* 110 */     this.fallback = $$0;
/*     */   }
/*     */   
/*     */   public int getMaxSize(StructureTemplateManager $$0) {
/* 114 */     if (this.maxSize == Integer.MIN_VALUE) {
/* 115 */       this
/*     */ 
/*     */ 
/*     */         
/* 119 */         .maxSize = this.templates.stream().filter($$0 -> ($$0 != EmptyPoolElement.INSTANCE)).mapToInt($$1 -> $$1.getBoundingBox($$0, BlockPos.ZERO, Rotation.NONE).getYSpan()).max().orElse(0);
/*     */     }
/* 121 */     return this.maxSize;
/*     */   }
/*     */   
/*     */   public Holder<StructureTemplatePool> getFallback() {
/* 125 */     return this.fallback;
/*     */   }
/*     */   
/*     */   public StructurePoolElement getRandomTemplate(RandomSource $$0) {
/* 129 */     return (StructurePoolElement)this.templates.get($$0.nextInt(this.templates.size()));
/*     */   }
/*     */   
/*     */   public List<StructurePoolElement> getShuffledTemplates(RandomSource $$0) {
/* 133 */     return Util.shuffledCopy(this.templates, $$0);
/*     */   }
/*     */   
/*     */   public int size() {
/* 137 */     return this.templates.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\StructureTemplatePool.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */