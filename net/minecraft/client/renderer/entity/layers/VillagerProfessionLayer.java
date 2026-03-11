/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.VillagerHeadModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.resources.metadata.animation.VillagerMetaDataSection;
/*    */ import net.minecraft.core.DefaultedRegistry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.npc.VillagerData;
/*    */ import net.minecraft.world.entity.npc.VillagerDataHolder;
/*    */ import net.minecraft.world.entity.npc.VillagerProfession;
/*    */ import net.minecraft.world.entity.npc.VillagerType;
/*    */ 
/*    */ public class VillagerProfessionLayer<T extends LivingEntity & VillagerDataHolder, M extends EntityModel<T> & VillagerHeadModel> extends RenderLayer<T, M> {
/*    */   static {
/* 29 */     LEVEL_LOCATIONS = (Int2ObjectMap<ResourceLocation>)Util.make(new Int2ObjectOpenHashMap(), $$0 -> {
/*    */           $$0.put(1, new ResourceLocation("stone"));
/*    */           $$0.put(2, new ResourceLocation("iron"));
/*    */           $$0.put(3, new ResourceLocation("gold"));
/*    */           $$0.put(4, new ResourceLocation("emerald"));
/*    */           $$0.put(5, new ResourceLocation("diamond"));
/*    */         });
/*    */   }
/* 37 */   private static final Int2ObjectMap<ResourceLocation> LEVEL_LOCATIONS; private final Object2ObjectMap<VillagerType, VillagerMetaDataSection.Hat> typeHatCache = (Object2ObjectMap<VillagerType, VillagerMetaDataSection.Hat>)new Object2ObjectOpenHashMap();
/* 38 */   private final Object2ObjectMap<VillagerProfession, VillagerMetaDataSection.Hat> professionHatCache = (Object2ObjectMap<VillagerProfession, VillagerMetaDataSection.Hat>)new Object2ObjectOpenHashMap();
/*    */   
/*    */   private final ResourceManager resourceManager;
/*    */   private final String path;
/*    */   
/*    */   public VillagerProfessionLayer(RenderLayerParent<T, M> $$0, ResourceManager $$1, String $$2) {
/* 44 */     super($$0);
/* 45 */     this.resourceManager = $$1;
/* 46 */     this.path = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 51 */     if ($$3.isInvisible()) {
/*    */       return;
/*    */     }
/*    */     
/* 55 */     VillagerData $$10 = ((VillagerDataHolder)$$3).getVillagerData();
/* 56 */     VillagerType $$11 = $$10.getType();
/* 57 */     VillagerProfession $$12 = $$10.getProfession();
/*    */     
/* 59 */     VillagerMetaDataSection.Hat $$13 = getHatData(this.typeHatCache, "type", BuiltInRegistries.VILLAGER_TYPE, $$11);
/* 60 */     VillagerMetaDataSection.Hat $$14 = getHatData(this.professionHatCache, "profession", BuiltInRegistries.VILLAGER_PROFESSION, $$12);
/*    */     
/* 62 */     M $$15 = getParentModel();
/*    */     
/* 64 */     ((VillagerHeadModel)$$15).hatVisible(($$14 == VillagerMetaDataSection.Hat.NONE || ($$14 == VillagerMetaDataSection.Hat.PARTIAL && $$13 != VillagerMetaDataSection.Hat.FULL)));
/* 65 */     ResourceLocation $$16 = getResourceLocation("type", BuiltInRegistries.VILLAGER_TYPE.getKey($$11));
/* 66 */     renderColoredCutoutModel((EntityModel<T>)$$15, $$16, $$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F);
/* 67 */     ((VillagerHeadModel)$$15).hatVisible(true);
/*    */     
/* 69 */     if ($$12 != VillagerProfession.NONE && !$$3.isBaby()) {
/* 70 */       ResourceLocation $$17 = getResourceLocation("profession", BuiltInRegistries.VILLAGER_PROFESSION.getKey($$12));
/* 71 */       renderColoredCutoutModel((EntityModel<T>)$$15, $$17, $$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F);
/* 72 */       if ($$12 != VillagerProfession.NITWIT) {
/* 73 */         ResourceLocation $$18 = getResourceLocation("profession_level", (ResourceLocation)LEVEL_LOCATIONS.get(Mth.clamp($$10.getLevel(), 1, LEVEL_LOCATIONS.size())));
/* 74 */         renderColoredCutoutModel((EntityModel<T>)$$15, $$18, $$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private ResourceLocation getResourceLocation(String $$0, ResourceLocation $$1) {
/* 80 */     return $$1.withPath($$1 -> "textures/entity/" + this.path + "/" + $$0 + "/" + $$1 + ".png");
/*    */   }
/*    */   
/*    */   public <K> VillagerMetaDataSection.Hat getHatData(Object2ObjectMap<K, VillagerMetaDataSection.Hat> $$0, String $$1, DefaultedRegistry<K> $$2, K $$3) {
/* 84 */     return (VillagerMetaDataSection.Hat)$$0.computeIfAbsent($$3, $$3 -> (VillagerMetaDataSection.Hat)this.resourceManager.getResource(getResourceLocation($$0, $$1.getKey($$2))).flatMap(()).orElse(VillagerMetaDataSection.Hat.NONE));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\VillagerProfessionLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */