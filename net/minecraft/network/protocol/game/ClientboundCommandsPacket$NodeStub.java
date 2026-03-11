package net.minecraft.network.protocol.game;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.FriendlyByteBuf;

interface NodeStub {
  ArgumentBuilder<SharedSuggestionProvider, ?> build(CommandBuildContext paramCommandBuildContext);
  
  void write(FriendlyByteBuf paramFriendlyByteBuf);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundCommandsPacket$NodeStub.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */