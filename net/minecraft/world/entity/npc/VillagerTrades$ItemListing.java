package net.minecraft.world.entity.npc;

import javax.annotation.Nullable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.trading.MerchantOffer;

public interface ItemListing {
  @Nullable
  MerchantOffer getOffer(Entity paramEntity, RandomSource paramRandomSource);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\VillagerTrades$ItemListing.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */